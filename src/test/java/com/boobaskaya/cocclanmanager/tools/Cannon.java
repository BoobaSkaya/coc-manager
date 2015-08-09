package com.boobaskaya.cocclanmanager.tools;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cannon extends Building {
	public Cannon() {
		x = 0;
		y = 0;
	}

	public Cannon(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@XmlAttribute
	public int x;
	@XmlAttribute
	public int y;

}
