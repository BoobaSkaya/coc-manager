
package com.boobaskaya.cocclanmanager.model;

public class Cannon extends Building {

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
	public String getConfigBasename() {
		return "cannon";
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
