package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;

public class HiddenTesla extends Building {

	private static Config config;

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
	public Config getConfig() {
		if (config == null) {
			try {
				config = Config.parse("hidden_tesla-levels.csv");
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
