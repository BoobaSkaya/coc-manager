
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

    private final SimpleIntegerProperty hdv;

    private final Hdv hdvBuilding;

    private final ObservableList<Building> buildings;

	private final SimpleIntegerProperty dps;
	private final SimpleIntegerProperty hitpoints;

    public Player(){
        this("player#"+(INDEX));
    }

    public Player(String pseudo) {
        Objects.requireNonNull(pseudo);
        this.pseudo = new SimpleStringProperty(pseudo);
        this.hdv =  new SimpleIntegerProperty(1);
        this.hdvBuilding = new Hdv(1);
        this.buildings = FXCollections.observableArrayList();
		this.dps = new SimpleIntegerProperty();
		this.hitpoints = new SimpleIntegerProperty();

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
		int dps = buildings.stream().mapToInt(p -> p.getDPS()).sum();
		this.dps.set(dps);
		// update total hitpoints
		int hitpoints = buildings.stream().mapToInt(p -> p.getHitPoints()).sum();
		this.hitpoints.set(hitpoints);
	}

	@XmlAttribute(name = "pseudo")
    public void setPseudo(String pseudo){
        this.pseudo.set(pseudo);
    }

    public String getPseudo(){
        return pseudo.getValue();
    }

    public SimpleStringProperty pseudoProperty(){
        return pseudo;
    }

    @XmlAttribute(name="hdv")
    public void setHdv(int hdv){
        this.hdv.set(hdv);
        this.hdvBuilding.setLevel(hdv);
    }

    public int getHdv(){
        return this.hdvBuilding.getLevel();
    }

    public SimpleIntegerProperty hdvProperty(){
        return hdv;
    }

    @XmlElementWrapper(name = "buildings")
    @XmlElement(name  = "building")
    public ObservableList<Building> getBuildings(){
        return this.buildings;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.pseudo.getValue());
        hash = 59 * hash + Objects.hashCode(this.hdv.getValue());
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
        return Objects.equals(this.hdv.getValue(), other.hdv.getValue());
    }

    @Override
    public String toString() {
        return "Player{"
                + "pseudo=" + pseudo.getValue()
                + " hdv=" + hdv.getValue() + '}';
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
}
