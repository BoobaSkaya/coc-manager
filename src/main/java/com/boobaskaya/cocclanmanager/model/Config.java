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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Config {

	private static Config defaultConfig = null;

	private final HashMap<Integer, CSVRecord> levels = new HashMap<>();

	public static final String LEVEL = "Level ";

	public static final String DPS = "Damage per Second ";

	private Config() {

	}

	public HashMap<Integer, CSVRecord> getLevels() {
		return levels;
	}

	private void addLevel(CSVRecord levelConfig) {
		// retrieve the level of the levelConfiguration
		Integer level = Integer.parseInt(levelConfig.get(LEVEL));
		levels.put(level, levelConfig);
	}

	public static Config parse(String filename) throws ParseException {
		Config result = new Config();
		// Retrieve input stream
		InputStream is = Config.class.getResourceAsStream("config/" + filename);
		if (is == null) {
			throw new ParseException(String.format("Failed to find file %s", filename), 0);
		}

		try {
			CSVFormat format = CSVFormat.DEFAULT.withHeader();
			format = format.withDelimiter(';');

			CSVParser parser = new CSVParser(new InputStreamReader(is), format);
			for (CSVRecord record : parser) {
				result.addLevel(record);
			}
			parser.close();
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
}
