/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boobaskaya.cocclanmanager.model;

import com.boobaskaya.cocclanmanager.model.Clan;
import com.boobaskaya.cocclanmanager.model.Player;
import com.boobaskaya.cocclanmanager.tools.JAXBTools;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author booba
 */
public class ClanTest {
    
    private final File testOutputFile = new File("target/clantest.xml");
    
    public ClanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSerialization() throws JAXBException, IOException{
        Clan c = new Clan("SonsOfIsildur");
        c.getMembers().add(new Player("p1"));
        
        JAXBTools.toFile(c, Clan.class, testOutputFile);
        Clan o = JAXBTools.fromFile(testOutputFile, Clan.class);
        Assert.assertTrue(o instanceof Clan);
        Assert.assertEquals(c, o);
        Assert.assertEquals(1, ((Clan) o).getMembers().size());
    }
}
