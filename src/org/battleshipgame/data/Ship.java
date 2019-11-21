package org.battleshipgame.data;

import java.util.Map;

public class Ship {
	int dimension;
	int orientation;
	int startX;
	int startY;	
	private Map<Integer, Map> shipData; //keyset is Y-axis
	
	public Map<Integer, Map> getShipData() {
		return shipData;
	}

	public void setShipData(Map<Integer, Map> shipData) {
		this.shipData = shipData;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}
}
