package com.boobaskaya.cocclanmanager.tools;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import junit.framework.Assert;

public class JAXBToolsTest {

	@Test
	public void testToFile() throws JAXBException {
		Clan pll = new Clan();

		Player pl = new Player();
		pl.name = "player1";
		pl.buildings.add(new Cannon(0, 0));
		pl.buildings.add(new Cannon(1, 1));

		pll.members.add(pl);

		pl = new Player();
		pl.name = "player2";
		pl.buildings.add(new Cannon(2, 2));
		pl.buildings.add(new Cannon(3, 3));
		pll.members.add(pl);

		JAXBTools.toFile(pll, Clan.class, new File("target/pointList.xml"));

		// load
		Clan restoredPL = JAXBTools.fromFile(new File("target/pointList.xml"), Clan.class);
		Assert.assertEquals(2, restoredPL.members.get(0).buildings.size());
		Assert.assertEquals(2, restoredPL.members.get(1).buildings.size());

	}

}
