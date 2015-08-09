/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boobaskaya.cocclanmanager.model;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.boobaskaya.cocclanmanager.tools.JAXBTools;

import junit.framework.Assert;

public class PlayerTest {

    private final File testOutputFile = new File("target/playertest.xml");

    @Test
    public void testSerialization() throws IOException, JAXBException{
        Player p1 = new Player();
        p1.setPseudo("lena");
        p1.setTownHall(10);
		p1.addBuilding(new Cannon(10));
		p1.addBuilding(new Cannon(10));
		p1.addBuilding(new TownHall(10));
		p1.addBuilding(new ArcherTower(5));
		p1.addBuilding(new AirDefense(8));
		p1.addBuilding(new Mortar(8));
		p1.addBuilding(new WizardTower(8));
		p1.addBuilding(new HiddenTesla(8));
		p1.addBuilding(new XBow(2));
		p1.addBuilding(new InfernoTower(3));
        JAXBTools.toFile(p1, Player.class, testOutputFile);
        Player o  = JAXBTools.fromFile(testOutputFile, Player.class);
        Assert.assertTrue(o instanceof Player);
        Assert.assertEquals(p1, o);
    }
}
