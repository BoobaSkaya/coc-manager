package com.boobaskaya.cocclanmanager.model;

public class Mortar extends Building {

	public Mortar() {
		super();
	}

	public Mortar(Integer level) {
		super(level);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.MORTAR;
	}

	@Override
	public String getConfigBasename() {
		return "air_defense";
	}

	@Override
	public DPSType getDPSType() {
		return DPSType.GROUND;
	}

	@Override
	public CostType getCostType() {
		return CostType.GOLD;
	}

}
