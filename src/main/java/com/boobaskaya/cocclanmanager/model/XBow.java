package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;

public class XBow extends Building {

	private static Config config;

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
	public Config getConfig() {
		if (config == null) {
			try {
				config = Config.parse("x_bow-levels.csv");
			} catch (ParseException e) {
				config = Config.defaultConfig();
			}
		}
		return config;
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
