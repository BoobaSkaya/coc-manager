package com.boobaskaya.cocclanmanager.model;

import org.junit.Test;

import junit.framework.Assert;

public class CannonTest {

	@Test
	public void testClone() {
		Cannon c = new Cannon(1);
		Cannon clone = (Cannon) c.clone();
		Assert.assertEquals(c.getLevel(), clone.getLevel());
	}

	@Test
	public void testEquals() {
		Cannon c1 = new Cannon();
		Cannon c2 = new Cannon();
		Assert.assertEquals(c1, c2);
		c1.setLevel(6);
		Assert.assertNotSame(c1, c2);
		c2.setLevel(6);
		Assert.assertEquals(c1, c2);
	}

}
