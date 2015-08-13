
package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@XmlSeeAlso({ AirDefense.class, ArcherTower.class, Cannon.class, GoldMine.class, HiddenTesla.class, InfernoTower.class,
		Mortar.class,
		TownHall.class,
 WizardTower.class, XBow.class })
public abstract class Building implements Cloneable {
	private static final Logger LOGGER = Logger.getLogger("COC");

    private SimpleIntegerProperty level;
	// derived values
	private SimpleIntegerProperty hitpoints;
	private SimpleIntegerProperty dps;
	private SimpleIntegerProperty cost;

	private final static HashMap<BuildingType, Config> configs = new HashMap<>();

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

	public int getMaxLevel(TownHall th) {
		return getConfig().getMaxLevel(th.getLevel());
	}

	public abstract String getConfigBasename();

	public Config getConfig() {
		if (configs.get(getType()) == null) {
			// load new config
			try {
				configs.put(getType(), Config.parse(getConfigBasename()));
			} catch (ParseException e) {
				LOGGER.severe("Failed to load config with basename " + getConfigBasename());
			}
		}
		return configs.get(getType());

	}

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

	@Override
	public Building clone() {
		Building clone = null;
		try {
			clone = (Building) super.clone();
			clone.setLevel(getLevel());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	public abstract DPSType getDPSType();

	public abstract CostType getCostType();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level.getValue() == null) ? 0 : level.getValue().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Building other = (Building) obj;
		if (level == null) {
			if (other.level != null) {
				return false;
			}
		} else if (!level.getValue().equals(other.level.getValue())) {
			return false;
		}
		return true;
	}

}
