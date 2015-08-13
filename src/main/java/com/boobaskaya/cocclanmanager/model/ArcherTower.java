package com.boobaskaya.cocclanmanager.model;

public class ArcherTower extends Building {

	public ArcherTower() {
		super();
	}

	public ArcherTower(Integer level) {
		super(level);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.ARCHER_TOWER;
	}

	@Override
	public String getConfigBasename() {
		return "archer_tower";
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
