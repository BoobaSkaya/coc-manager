package com.boobaskaya.cocclanmanager.tools;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Point {
	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@XmlAttribute
	public int x;
	@XmlAttribute
	public int y;

}
