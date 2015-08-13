package com.boobaskaya.cocclanmanager.model;

public class XBow extends Building {

	public XBow() {
		super();
	}

	public XBow(Integer level) {
		super(level);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.X_BOW;
	}

	@Override
	public String getConfigBasename() {
		return "x_bow";
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
