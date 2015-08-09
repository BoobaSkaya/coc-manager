
package com.boobaskaya.cocclanmanager.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

@XmlRootElement
public class Player {

	private static int INDEX = 0;

	private final SimpleStringProperty pseudo;

	private final SimpleIntegerProperty townHall;

	private final TownHall townHallBuilding;

	@XmlElementWrapper(name = "buildings")
	@XmlElement(name = "building")
	private final ObservableList<Building> buildings;

	private final SimpleIntegerProperty dps;
	private final SimpleIntegerProperty airdps;
	private final SimpleIntegerProperty hitpoints;
	private final SimpleIntegerProperty goldCost;
	private final SimpleIntegerProperty elixirCost;

	public Player() {
		this("player#" + (INDEX));
	}

	public Player(String pseudo) {
		Objects.requireNonNull(pseudo);
		this.pseudo = new SimpleStringProperty(pseudo);
		this.townHall = new SimpleIntegerProperty(1);
		this.townHallBuilding = new TownHall(1);
		this.buildings = FXCollections.observableArrayList();
		this.dps = new SimpleIntegerProperty();
		this.airdps = new SimpleIntegerProperty();
		this.hitpoints = new SimpleIntegerProperty();
		this.goldCost = new SimpleIntegerProperty();
		this.elixirCost = new SimpleIntegerProperty();

		this.buildings.addListener(new ListChangeListener<Building>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Building> arg0) {
				updateStats();
			}

		});
		INDEX++;
	}

	protected void updateStats() {
		// update total DPS
		this.dps.set(buildings.stream().mapToInt(p -> p.getDPS()).sum());
		// update total hitpoints
		this.hitpoints.set(buildings.stream().mapToInt(p -> p.getHitPoints()).sum());
		// compute AIR DPS using the AIR and AIR_GROUND buildings
		this.airdps.set(
				buildings.stream().filter(p -> p.getDPSType() == DPSType.AIR || p.getDPSType() == DPSType.AIR_GROUND)
						.mapToInt(p -> p.getDPS()).sum());
		// compute gold and elixir cost
		this.goldCost
				.set(buildings.stream().filter(p -> p.getCostType() == CostType.GOLD).mapToInt(p -> p.getCost()).sum());
		this.elixirCost.set(
				buildings.stream().filter(p -> p.getCostType() == CostType.ELIXIR).mapToInt(p -> p.getCost()).sum());
	}

	@XmlAttribute(name = "pseudo")
	public void setPseudo(String pseudo) {
		this.pseudo.set(pseudo);
	}

	public String getPseudo() {
		return pseudo.getValue();
	}

	public SimpleStringProperty pseudoProperty() {
		return pseudo;
	}

	public SimpleIntegerProperty townHallProperty() {
		return townHall;
	}

	@XmlAttribute(name = "townHall")
	public void setTownHall(int hdv) {
		this.townHall.set(hdv);
		this.townHallBuilding.setLevel(hdv);
	}

	public int getTownHall() {
		return this.townHallBuilding.getLevel();
	}

	public SimpleIntegerProperty hdvProperty() {
		return townHall;
	}

	public ObservableList<Building> getBuildings() {
		return this.buildings;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + Objects.hashCode(this.pseudo.getValue());
		hash = 59 * hash + Objects.hashCode(this.townHall.getValue());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Player other = (Player) obj;
		if (!Objects.equals(this.pseudo.getValue(), other.pseudo.getValue())) {
			return false;
		}
		return Objects.equals(this.townHall.getValue(), other.townHall.getValue());
	}

	@Override
	public String toString() {
		return "Player{" + "pseudo=" + pseudo.getValue() + " townHall=" + townHall.getValue() + '}';
	}

	public void addBuilding(Building building) {
		buildings.add(building);
		// update values are done through listener, nothing to update here
	}

	public SimpleIntegerProperty dpsProperty() {
		return this.dps;
	}

	public SimpleIntegerProperty hitpointsProperty() {
		return this.hitpoints;
	}

	public SimpleIntegerProperty airdpsProperty() {
		return this.airdps;
	}

	public SimpleIntegerProperty goldcostProperty() {
		return this.goldCost;
	}

	public SimpleIntegerProperty elixircostProperty() {
		return this.elixirCost;
	}
}
