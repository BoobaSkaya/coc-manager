package com.boobaskaya.cocclanmanager.model;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class BuildingFactoryTest {

	@Test
	public void testGetMax() {
		List<Building> max8 = BuildingFactory.getMax(8);
		int mortarNb = max8.stream().mapToInt(p -> p.getType() == BuildingType.MORTAR?1:0).sum();
		Assert.assertEquals(4, mortarNb);

		List<Building> max0 = BuildingFactory.getMax(0);
		Assert.assertEquals(0, max0.size());
	}

}
