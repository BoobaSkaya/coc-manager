package com.boobaskaya.cocclanmanager.model;

public class AirDefense extends Building {

	public AirDefense() {
		super();
	}

	public AirDefense(Integer level) {
		super(level);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.AIR_DEFENSE;
	}

	@Override
	public String getConfigBasename() {
		return "air_defense";
	}

	@Override
	public DPSType getDPSType() {
		return DPSType.AIR;
	}

	@Override
	public CostType getCostType() {
		return CostType.GOLD;
	}

}
