/*
 * Copyright (C) 2015 booba.skaya@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.boobaskaya.cocclanmanager.model;

public class GoldMine extends Building{

    @Override
    public BuildingType getType() {
        return BuildingType.GOLD_MINE;
    }

	@Override
	public String getConfigBasename() {
		return "gold_mine";
	}

	@Override
	public DPSType getDPSType() {
		return DPSType.NODPS;
	}

	@Override
	public CostType getCostType() {
		return CostType.ELIXIR;
	}
}
