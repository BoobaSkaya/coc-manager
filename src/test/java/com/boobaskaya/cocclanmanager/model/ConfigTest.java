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
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;

public class ConfigTest {
    
    public ConfigTest() {
    }
    
    @Test
    public void testCannonConfig() throws ParseException {
        //parse cannon config
        Config c = Config.parse("cannon.cfg");
        ArrayList<Config.Level> levels = c.getLevels();
        Config.Level lvl1 = levels.get(0);
        Assert.assertEquals(13, levels.size());
        Assert.assertEquals(1, lvl1.level);
        Assert.assertEquals(9, lvl1.dps);
        
    
    }
}
