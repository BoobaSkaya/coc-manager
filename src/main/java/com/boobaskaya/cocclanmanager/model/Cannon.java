
package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;

public class Cannon extends Building {

	private static Config config = null;

    public Cannon(){
        super();
    }

    public Cannon(int level) {
        super(level);
    }

    @Override
    public BuildingType getType() {
        return BuildingType.CANNON;
    }

	@Override
	public Config getConfig() {
		if (config == null) {
			try {
				config = Config.parse("cannon-levels.csv");
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
