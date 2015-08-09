/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boobaskaya.cocclanmanager.model;

import com.boobaskaya.cocclanmanager.model.Cannon;
import com.boobaskaya.cocclanmanager.model.TownHall;
import com.boobaskaya.cocclanmanager.model.Player;
import com.boobaskaya.cocclanmanager.tools.JAXBTools;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import junit.framework.Assert;
import org.junit.Test;

public class PlayerTest {
    
    private final File testOutputFile = new File("target/playertest.xml");
    
    @Test
    public void testSerialization() throws IOException, JAXBException{
        Player p1 = new Player();
        p1.setPseudo("lena");
        p1.setTownHall(10);
        p1.getBuildings().add(new Cannon(10));
        p1.getBuildings().add(new Cannon(10));
        p1.getBuildings().add(new TownHall(10));
        JAXBTools.toFile(p1, Player.class, testOutputFile);
        Player o  = JAXBTools.fromFile(testOutputFile, Player.class);
        Assert.assertTrue(o instanceof Player);
        Assert.assertEquals(p1, o);
    }
}
