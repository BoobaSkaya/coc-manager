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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;

public class Config {

	private final static Logger LOGGER = Logger.getLogger("COC");

	private static Config defaultConfig = null;

	private final HashMap<Integer, Map<String, String>> levels = new HashMap<>();
	private int[] numberPerTHLevel;

	public static final String LEVEL = "level";

	public static final String DPS = "damage per second";

	private static final String HITPOINTS = "hitpoints";
	private static final String COST = "cost";
	private static final String BUILD_COST = "build cost";
	private static final String TH_LEVEL_REQUIRED = "town hall level required";


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

	public static Config parse(String basename) throws ParseException {
		Config result = new Config(basename);
		// first is to retrieve the -levels.csv config filename
		result.loadLevels();
		result.loadNumbersOfBuildingPerTHLevel();
		return result;
	}

	private void loadNumbersOfBuildingPerTHLevel() throws ParseException {
		String numbersFilename = filename + "-number.csv";
		// Retrieve input stream
		InputStream is = Config.class.getResourceAsStream("config/" + numbersFilename);
		if (is == null) {
			throw new ParseException(String.format("Failed to find file %s", numbersFilename), 0);
		}

		try {
			CsvListReader reader = new CsvListReader(new InputStreamReader(is),
					CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			// first line is TH levels - skip it
			reader.read();
			List<String> numbers = reader.read();
			numberPerTHLevel = new int[numbers.size()];
			Arrays.fill(numberPerTHLevel, 0);
			for(int i = 1; i < numbers.size(); i++){
				String numberString = numbers.get(i).trim();
				numberPerTHLevel[i] = Integer.parseInt(numberString);
			}
			reader.close();
		} catch (IOException | IllegalArgumentException e) {
			throw new ParseException("Error while reading " + numbersFilename + " :" + e.getMessage(), 0);
		}

	}

	private void loadLevels() throws ParseException {
		String levelFilename = filename + "-levels.csv";
		// Retrieve input stream
		InputStream is = Config.class.getResourceAsStream("config/" + levelFilename);
		if (is == null) {
			throw new ParseException(String.format("Failed to find file %s", levelFilename), 0);
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
				addLevelDefinition(row);
				row = reader.read(headers);
			}
			reader.close();
		} catch (IOException | IllegalArgumentException e) {
			throw new ParseException("Error while reading " + levelFilename + " :" + e.getMessage(), 0);
		}
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
			try{
				return Integer.parseInt(escapeInteger(inputValue));
			}catch(NumberFormatException e){
				LOGGER.severe(String.format("Field %s for config %s is not a number, use 0.", field, filename));
				throw e;
			}
		} else if (levels.get(level) == null) {
			String msg = String.format("Failed to find level %d for config %s.", level, filename);
			LOGGER.severe(msg);
			throw new IllegalArgumentException(msg);
		} else {
			LOGGER.warning(String.format("Failed to find field %s for config %s.", field, filename));
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

	/**
	 * returns The maximum level of this config for the given town hall level
	 * This method can be cashed at config reading
	 *
	 * @param thLevel
	 * @return
	 */
	public int getMaxLevel(int thLevel) {
		// TODO cache this computing at config read instead of computing it each
		// time
		int result = 0;
		for (int level : levels.keySet()) {
			String thRequiredLevel = levels.get(level).get(TH_LEVEL_REQUIRED);
			if (thRequiredLevel != null) {
				int thRequired = Integer.parseInt(thRequiredLevel);
				if (thRequired <= thLevel && result < level) {
					result = level;
				}
			}
		}
		return result;
	}

	public int getMaxNumberPerTHLevel(int thLevel){
		if(thLevel < 0 || thLevel >= numberPerTHLevel.length){
			throw new IllegalArgumentException("Town Hall level must be positive int lower than "+numberPerTHLevel.length+" for "+filename);
		}
		return numberPerTHLevel[thLevel];
	}

}
