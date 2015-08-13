package com.boobaskaya.cocclanmanager.model;

public class HiddenTesla extends Building {

	public HiddenTesla() {
		super();
	}

	public HiddenTesla(Integer level) {
		super(level);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.HIDDEN_TESLA;
	}

	@Override
	public String getConfigBasename() {
		return "hidden_tesla";
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
