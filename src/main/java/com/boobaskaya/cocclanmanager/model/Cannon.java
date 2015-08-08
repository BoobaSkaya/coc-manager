
package com.boobaskaya.cocclanmanager.model;

import java.util.Objects;

public class Cannon extends Building {

    private final static int[] maxLevels = new int[]{
            //0  1  2  3  4  5  6  7   8  9   10
              2, 2, 3, 4, 5, 6, 7, 8, 10, 11, 13
            };

    public Cannon(){
        super();
    }
    
    public Cannon(int level) {
        super(level);
    }
    
    @Override
    public int getMaxLevel(Hdv hdv) {
        Objects.nonNull(hdv);
        return maxLevels[hdv.getLevel()];
    }

    @Override
    public BuildingType getType() {
        return BuildingType.CANNON;
    }

    
    
}
