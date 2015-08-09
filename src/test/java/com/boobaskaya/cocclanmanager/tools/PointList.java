package com.boobaskaya.cocclanmanager.tools;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement
public class PointList {
	@XmlElement
	ObservableList<Point> points = FXCollections.observableArrayList();

	@XmlAttribute
	String name;

}
