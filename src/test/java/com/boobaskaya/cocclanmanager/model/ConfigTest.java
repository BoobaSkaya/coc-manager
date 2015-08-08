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

import java.text.ParseException;
import java.util.HashMap;

import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import junit.framework.Assert;

public class ConfigTest {
    
	private final String[] CONFIG_FILES = new String[] { "gold_mine-levels.csv", "cannon-levels.csv",
			"default-levels.csv" };

    public ConfigTest() {
    }
    
    @Test
    public void testCannonConfig() throws ParseException {
        //parse cannon config
		Config c = Config.parse("cannon-levels.csv");
		HashMap<Integer, CSVRecord> levels = c.getLevels();
		Assert.assertEquals(13, levels.size());
		Assert.assertEquals("9", levels.get(1).get(Config.DPS));
		Assert.assertEquals("11", levels.get(2).get(Config.DPS));
    
    }

	@Test
	public void testAllConfigs() throws ParseException {
		for (String s : CONFIG_FILES) {
			Config.parse(s);
		}
	}

}
