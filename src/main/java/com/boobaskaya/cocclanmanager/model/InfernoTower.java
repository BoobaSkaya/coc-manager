package com.boobaskaya.cocclanmanager.model;

public class InfernoTower extends Building {

	public InfernoTower() {
		super();
	}

	public InfernoTower(Integer level) {
		super(level);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.INFERNO_TOWER;
	}

	@Override
	public String getConfigBasename() {
		return "inferno_tower";
	}

	@Override
	public DPSType getDPSType() {
		return DPSType.AIR_GROUND;
	}

	@Override
	public CostType getCostType() {
		return CostType.GOLD;
	}
}
