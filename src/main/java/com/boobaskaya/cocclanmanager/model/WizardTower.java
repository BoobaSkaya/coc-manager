package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;

public class WizardTower extends Building {

	private static Config config;

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
	public Config getConfig() {
		if (config == null) {
			try {
				config = Config.parse("wizard_tower-levels.csv");
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
