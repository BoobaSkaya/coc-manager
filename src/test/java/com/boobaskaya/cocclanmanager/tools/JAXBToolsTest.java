package com.boobaskaya.cocclanmanager.tools;

import java.io.File;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import junit.framework.Assert;

public class JAXBToolsTest {

	@Test
	public void testToFile() throws JAXBException {
		PointListList pll = new PointListList();

		PointList pl = new PointList();
		pl.name = "list1";
		pl.points.add(new Point(0, 0));
		pl.points.add(new Point(1, 1));

		pll.list.add(pl);

		pl = new PointList();
		pl.name = "list2";
		pl.points.add(new Point(2, 2));
		pl.points.add(new Point(3, 3));
		pll.list.add(pl);

		JAXBTools.toFile(pll, PointListList.class, new File("target/pointList.xml"));

		// load
		PointListList restoredPL = JAXBTools.fromFile(new File("target/pointList.xml"), PointListList.class);
		Assert.assertEquals(2, restoredPL.list.get(0).points.size());
		Assert.assertEquals(2, restoredPL.list.get(1).points.size());

	}

}
