package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;

public class InfernoTower extends Building {

	private static Config config;

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
	public int getMaxLevel(TownHall hdv) {
		// TBD
		return 0;
	}

	@Override
	public Config getConfig() {
		if (config == null) {
			try {
				config = Config.parse("inferno_tower-levels.csv");
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
