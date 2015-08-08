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

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Config {

	private static Config defaultConfig = null;

	private final ArrayList<Level> levels = new ArrayList<>();

	private Config() {

	}

	public ArrayList<Level> getLevels() {
		return levels;
	}

	private void addLevel(Level level) {
		levels.add(level);
	}

	public static Config parse(String filename) throws ParseException {
		Config result = new Config();
		// Retrieve input stream
		InputStream is = Config.class.getResourceAsStream(filename);
		if (is == null) {
			throw new ParseException(String.format("Failed to find file %s", filename), 0);
		}
		Scanner scanner = new Scanner(is);

		// first line is headers skip it
		scanner.nextLine();
		// Then retrieve level config
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			result.addLevel(new Level(line));
		}
		scanner.close();
		return result;
	}

	static class Level {

		// Level Damage per Second Damage per Shot Hitpoints Cost Gold Build
		// Time Experience Gained XP Town Hall Level Required

		int level;
		int dps;
		float damagePershot;
		int hitPoints;
		int costGold;
		String buildTime;
		int experience;
		int hdvLevel;

		Level(String line) {

			String[] split = line.split("\\t");
			level = Integer.parseInt(split[0].trim());
			dps = Integer.parseInt(split[1].trim());
			damagePershot = Float.parseFloat(split[2].trim());
			hitPoints = Integer.parseInt(split[3].trim().replace(",", ""));
			costGold = Integer.parseInt(split[4].trim().replace(",", ""));
			buildTime = split[5].trim();
			experience = Integer.parseInt(split[6].trim());
			hdvLevel = Integer.parseInt(split[7].trim());

		}
	}

	public static Config defaultConfig() {
		if (defaultConfig == null) {
			try {
				defaultConfig = Config.parse("default.cfg");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return defaultConfig;
	}

}
