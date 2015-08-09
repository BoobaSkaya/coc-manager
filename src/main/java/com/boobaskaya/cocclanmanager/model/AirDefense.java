package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;

public class AirDefense extends Building {

	private static Config config;

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
	public int getMaxLevel(TownHall hdv) {
		// TBD
		return 0;
	}

	@Override
	public Config getConfig() {
		if (config == null) {
			try {
				config = Config.parse("air_defense-levels.csv");
			} catch (ParseException e) {
				config = Config.defaultConfig();
			}
		}
		return config;
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
