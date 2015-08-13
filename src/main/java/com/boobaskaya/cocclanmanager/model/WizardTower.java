package com.boobaskaya.cocclanmanager.model;

public class WizardTower extends Building {

	public WizardTower() {
		super();
	}

	public WizardTower(Integer level) {
		super(level);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.WIZARD_TOWER;
	}

	@Override
	public String getConfigBasename() {
		return "wizard_tower";
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
