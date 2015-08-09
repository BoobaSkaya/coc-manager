package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;

public class Mortar extends Building {

	private static Config config;

	public Mortar() {
		super();
	}

	public Mortar(Integer level) {
		super(level);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.MORTAR;
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
				config = Config.parse("mortar-levels.csv");
			} catch (ParseException e) {
				config = Config.defaultConfig();
			}
		}
		return config;
	}

	@Override
	public DPSType getDPSType() {
		return DPSType.GROUND;
	}

	@Override
	public CostType getCostType() {
		return CostType.GOLD;
	}

}
