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

public class ClanTest {

    private final File testOutputFile = new File("target/clantest.xml");

    @Test
    public void testSerialization() throws JAXBException, IOException{
        Clan c = new Clan("SonsOfIsildur");
        Player p = new Player("p1");
		c.getMembers().add(p);
		p.getBuildings().add(new TownHall(1));

		p = new Player("p2");
		c.getMembers().add(p);
		p.getBuildings().add(new TownHall(2));

        JAXBTools.toFile(c, Clan.class, testOutputFile);
        Clan o = JAXBTools.fromFile(testOutputFile, Clan.class);
        Assert.assertTrue(o instanceof Clan);
		Assert.assertEquals(2, o.getMembers().size());

		Assert.assertEquals(1, o.getMembers().get(0).getBuildings().size());
		Assert.assertEquals(1, o.getMembers().get(1).getBuildings().size());
    }
}
