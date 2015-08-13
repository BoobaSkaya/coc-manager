package com.boobaskaya.cocclanmanager.model;

import java.util.ArrayList;
import java.util.List;

public class BuildingFactory {

	public static Building createBuilding(BuildingType buildingType){
		Building newBuilding = null;
		switch (buildingType) {
		case AIR_DEFENSE:
			newBuilding = new AirDefense();
			break;
		case ARCHER_TOWER:
			newBuilding = new ArcherTower();
			break;
		case CANNON:
			newBuilding = new Cannon();
			break;
		case GOLD_MINE:
			newBuilding = new GoldMine();
			break;
		case HIDDEN_TESLA:
			newBuilding = new HiddenTesla();
			break;
		case INFERNO_TOWER:
			newBuilding = new InfernoTower();
			break;
		case MORTAR:
			newBuilding = new Mortar();
			break;
		case WIZARD_TOWER:
			newBuilding = new WizardTower();
			break;
		case X_BOW:
			newBuilding = new XBow();
			break;
		default:
			newBuilding = null;
		}
		return newBuilding;
	}

	public static List<Building> getMax(int thLevel){
		ArrayList<Building> maxedBuildings =  new ArrayList<>();
		for(BuildingType t : BuildingType.values()){
			//getMax number of t for the given thLevel
			if(isHandled(t)){
				//construct as many building as necessary
				Building b = createBuilding(t);
				if(b.getMaxNumberPerTHLevel(thLevel) > 0){
					b.setLevel(b.getMaxLevel(thLevel));
					for(int i = 0;i< b.getMaxNumberPerTHLevel(thLevel);i++){
						maxedBuildings.add(b.clone());
					}
				}
			}
		}
		return maxedBuildings;
	}

	private static boolean isHandled(BuildingType type){
		//a shitty temp way to know if we handle a building type
		return createBuilding(type) != null;
	}
}
