
package com.boobaskaya.cocclanmanager.model;

import java.text.ParseException;
import java.util.Objects;

public class Cannon extends Building {

    private final static int[] maxLevels = new int[]{
            //0  1  2  3  4  5  6  7   8  9   10
              2, 2, 3, 4, 5, 6, 7, 8, 10, 11, 13
            };
	private static Config config = null;

    public Cannon(){
        super();
    }
    
    public Cannon(int level) {
        super(level);
    }
    
    @Override
    public int getMaxLevel(TownHall hdv) {
        Objects.nonNull(hdv);
        return maxLevels[hdv.getLevel()];
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
    
    
}
