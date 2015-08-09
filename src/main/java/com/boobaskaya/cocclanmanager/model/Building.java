
package com.boobaskaya.cocclanmanager.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@XmlSeeAlso({Cannon.class, GoldMine.class, Hdv.class})
public abstract class Building  {  
    private SimpleIntegerProperty level;
	private SimpleIntegerProperty hitpoints;
    public Building(){
        this(1);
    }
    
    public Building(int level){
		this.level = new SimpleIntegerProperty(level);
		this.hitpoints = new SimpleIntegerProperty(getConfig().getHitPoints(level));
    }
    
    @XmlAttribute(name = "level")
    public int getLevel(){
        return level.getValue();
    }
    
    public void setLevel(int level){
        this.level.set(level);
		this.hitpoints.setValue(getConfig().getHitPoints(level));
    }
    
    public ReadOnlyStringProperty typeProperty(){
        return new SimpleStringProperty(getType().toString());
    }

	public SimpleIntegerProperty hitpointsProperty() {
		return this.hitpoints;
	}

    public abstract BuildingType getType();

    public abstract int getMaxLevel(Hdv hdv);

	public abstract Config getConfig();
    
}
