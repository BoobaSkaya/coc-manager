package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;

public class ArcherTower extends Building {

	private static Config config;

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
	public int getMaxLevel(TownHall hdv) {
		// TBD
		return 0;
	}

	@Override
	public Config getConfig() {
		if (config == null) {
			try {
				config = Config.parse("archer_tower-levels.csv");
			} catch (ParseException e) {
				config = Config.defaultConfig();
			}
		}
		return config;
	}

}
