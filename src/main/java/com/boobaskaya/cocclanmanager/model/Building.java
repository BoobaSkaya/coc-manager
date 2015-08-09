
package com.boobaskaya.cocclanmanager.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@XmlSeeAlso({ AirDefense.class, ArcherTower.class, Cannon.class, GoldMine.class, HiddenTesla.class, InfernoTower.class,
		Mortar.class,
		TownHall.class,
 WizardTower.class, XBow.class })
public abstract class Building  {
    private SimpleIntegerProperty level;
	private SimpleIntegerProperty hitpoints;
	private SimpleIntegerProperty dps;
	private SimpleIntegerProperty cost;
    public Building(){
        this(1);
    }

    public Building(int level){
		this.level = new SimpleIntegerProperty();
		this.hitpoints = new SimpleIntegerProperty();
		this.dps = new SimpleIntegerProperty();
		this.cost = new SimpleIntegerProperty();
		// update values by setting the level
		setLevel(level);

    }

    @XmlAttribute(name = "level")
    public int getLevel(){
        return level.getValue();
    }

    public void setLevel(int level){
        this.level.set(level);
		this.hitpoints.setValue(getHitPoints());
		this.dps.setValue(getDPS());
		this.cost.setValue(getCost());
    }

    public ReadOnlyStringProperty typeProperty(){
        return new SimpleStringProperty(getType().toString());
    }

	public SimpleIntegerProperty levelProperty() {
		return this.level;
	}

	public SimpleIntegerProperty hitpointsProperty() {
		return this.hitpoints;
	}

	public SimpleIntegerProperty dpsProperty() {
		return this.dps;
	}

    public abstract BuildingType getType();

    public abstract int getMaxLevel(TownHall hdv);

	public abstract Config getConfig();

	public int getDPS() {
		return getConfig().getDPS(level.getValue());
	}

	public int getHitPoints() {
		return getConfig().getHitPoints(level.getValue());
	}

	public int getCost() {
		int totalCost = 0;
		for (int i = 1; i <= level.getValue(); i++) {
			totalCost += getConfig().getCost(level.getValue());
		}
		return totalCost;
	}

	public abstract DPSType getDPSType();

	public abstract CostType getCostType();

}
