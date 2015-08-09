/*
 * Copyright (C) 2015 booba.skaya@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.boobaskaya.cocclanmanager.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;

public class Config {

	private final static Logger LOGGER = Logger.getLogger("COC");

	private static Config defaultConfig = null;

	private final HashMap<Integer, Map<String, String>> levels = new HashMap<>();

	public static final String LEVEL = "level";

	public static final String DPS = "damage per second";

	private static final String HITPOINTS = "hitpoints";
	private static final String COST = "cost";
	private static final String BUILD_COST = "build cost";


	private final String filename;

	private Config(String filename) {
		this.filename = filename;
	}

	public Map<String, String> getLevel(int level) {
		return levels.get(level);
	}

	private void addLevelDefinition(Map<String, String> levelConfig) {
		// retrieve the level of the levelConfiguration
		Integer level = Integer.parseInt(levelConfig.get(LEVEL));
		levels.put(level, levelConfig);
	}

	public static Config parse(String filename) throws ParseException {
		Config result = new Config(filename);
		// Retrieve input stream
		InputStream is = Config.class.getResourceAsStream("config/" + filename);
		if (is == null) {
			throw new ParseException(String.format("Failed to find file %s", filename), 0);
		}

		try {
			CsvMapReader reader = new CsvMapReader(new InputStreamReader(is),
					CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			// first line is headers
			String[] headers = reader.getHeader(false);
			// trim+lowercase the headers
			for (int i = 0; i < headers.length; i++) {
				headers[i] = headers[i].trim().toLowerCase();
			}

			// then following lines are level configuration
			CellProcessor[] cellProcessors = new CellProcessor[headers.length];
			for (int i = 0; i < cellProcessors.length; i++) {
				cellProcessors[i] = new Trim();
			}
			Map<String, String> row = reader.read(headers);
			while (row != null) {
				result.addLevelDefinition(row);
				row = reader.read(headers);
			}
			reader.close();
		} catch (IOException | IllegalArgumentException e) {
			throw new ParseException("Error while reading " + filename + " :" + e.getMessage(), 0);
		}
		return result;
	}



	public static Config defaultConfig() {
		if (defaultConfig == null) {
			try {
				defaultConfig = Config.parse("default-levels.csv");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return defaultConfig;
	}

	public int getLevelNumber() {
		return levels.keySet().size();
	}

	public Set<Integer> getLevels() {
		return levels.keySet();
	}

	public int getDPS(int level) {
		return getInt(level, DPS);
	}

	public int getHitPoints(int level) {
		return getInt(level, HITPOINTS);
	}

	private int getInt(int level, String field) {
		if (levels.get(level) != null && levels.get(level).get(field) != null) {
			String inputValue = levels.get(level).get(field);
			return Integer.parseInt(escapeInteger(inputValue));
		} else if (levels.get(level) == null) {
			LOGGER.severe(String.format("Failed to find level %d for config %s.", level, filename));
		} else {
			LOGGER.severe(String.format("Failed to find field %s for config %s.", field, filename));
		}
		return 0;
	}

	public static String escapeInteger(String badformated){
		return badformated.replace(",", "").trim();
	}

	public int getCost(Integer level) {
		// the cost could be expressed as "cost" or "build cost"
		Map<String, String> levelMap = levels.get(level);
		if (levelMap != null) {
			if (levelMap.containsKey(COST)) {
				return getInt(level, COST);
			} else if (levelMap.containsKey(BUILD_COST)) {
				return getInt(level, BUILD_COST);
			} else {
				LOGGER.severe(
						String.format("Failed to find COST field %s or %s for config %s.", COST, BUILD_COST, filename));
			}
		}

		LOGGER.severe(String.format("Failed to find level %d for config %s.", level, filename));
		return 0;
	}

}
