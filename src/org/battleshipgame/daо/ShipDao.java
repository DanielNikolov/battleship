package org.battleshipgame.da�;

import org.battleshipgame.data.Ship;

public class ShipDao {

	public Ship createShip(int dimension) {
		Ship result = new Ship();
		result.setDimension(dimension);
		return result;
	}
	
}
