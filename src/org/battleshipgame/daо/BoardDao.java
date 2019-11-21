package org.battleshipgame.daî;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.battleshipgame.data.Board;
import org.battleshipgame.data.Ship;
import org.battleshipgame.util.Constants;

public class BoardDao {

	private Board board;

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Ship createBattleship(int orientation) {
		return createShip(5, orientation);
	}
	
	public Ship createDestroyer(int orientation) {
		return createShip(4, orientation);
	}
	
	private int[] getStartingPoint(int dimension, int orientation) {
		int[] result = new int[2];
		Random random = new Random();
		result[0] = random.nextInt(this.board.getWidth()-dimension);
		result[1] = random.nextInt(this.board.getHeight());
		if (orientation == Constants.ORIENTATION_Y) {
			result[0] = random.nextInt(this.board.getWidth());
			result[1] = random.nextInt(this.board.getHeight()-dimension);
		}
		
		return result;
	}
	
	/**
	 * checks and returns starting coordinates where the ship can be located
	 * @param startPoint
	 * @param dimension
	 * @param orientation
	 * @return
	 */
	private boolean checkIfShipCanBePlaced(int[] startPoint, int dimension, int orientation) {
		boolean result = true;
		
		if (orientation == Constants.ORIENTATION_X) {
			for (int i=startPoint[0]; i<(startPoint[0] + dimension); i++) {
				result = result && (this.board.getDataMap()[i][startPoint[1]] == Constants.POSITION_EMPTY);
				if (!result) {
					break;
				}
			}
		} else {
			for (int i=startPoint[1]; i<(startPoint[1] + dimension); i++) {
				result = result && (this.board.getDataMap()[startPoint[0]][i] == Constants.POSITION_EMPTY);
				if (!result) {
					break;
				}
			}			
		}
		
		return result;
	}
	
	/**
	 * creates ship's status data map for each of the cells taken
	 * @param startingPoint
	 * @param dimension
	 * @param orientation
	 * @return
	 */
	private Map<Integer, Map> buildShipData(int[] startingPoint, int dimension, int orientation) {
		Map<Integer, Map> result = new HashMap<Integer, Map>();
		Map<Integer, Integer> statusMap = new HashMap<Integer, Integer>();
		if (orientation == Constants.ORIENTATION_X) {			
			for (int i=startingPoint[0]; i<(startingPoint[0]+dimension); i++) {
				statusMap.put(i, Constants.POSITION_EMPTY);
			}
			result.put(startingPoint[1], statusMap);
		} else {
			for (int i=startingPoint[1]; i<(startingPoint[1] + dimension); i++) {
				statusMap.put(i, Constants.POSITION_EMPTY);
			}
			result.put(startingPoint[0], statusMap);
		}
		
		return result;
	}
	
	/**
	 * updates board data map
	 * @param ship
	 */
	private void placeShipOnBoard(Ship ship) {
		if (ship.getOrientation() == Constants.ORIENTATION_X) {
			for (int i=ship.getStartX(); i<(ship.getStartX() + ship.getDimension()); i++) {
				board.getDataMap()[i][ship.getStartY()] = Constants.POSITION_SHIP;
			}
		} else {
			for (int i=ship.getStartY(); i < (ship.getStartY()+ ship.getDimension()); i++) {
				board.getDataMap()[ship.getStartX()][i] = Constants.POSITION_SHIP;
			}
		}
	}
	
	/**
	 * creates a new ship with specified dimension and orientation
	 * @param dimension
	 * @param orientation
	 * @return
	 */
	private Ship createShip(int dimension, int orientation) {
		Ship result = new Ship();
		int[] startingPoint = getStartingPoint(dimension, orientation);
		boolean shipCanBePlaced = false;
		result.setOrientation(orientation);
		result.setDimension(dimension); 
		
		while (!shipCanBePlaced) {
			shipCanBePlaced = checkIfShipCanBePlaced(startingPoint, dimension, orientation);
			if (shipCanBePlaced) {
				result.setShipData(buildShipData(startingPoint, dimension, orientation));
				result.setStartX(startingPoint[0]);
				result.setStartY(startingPoint[1]);
			} else {
				startingPoint = getStartingPoint(dimension, orientation);
			}
		}
		placeShipOnBoard(result);
		this.board.getListShips().add(result);
		return result;
	}
	
	/**
	 * shows the active board with ships invisible
	 * @return
	 */
	public String printBoard() {
		return this.board.toString().replaceAll("#", "*");
	}
	
	/**
	 * shows only the ships
	 * @return
	 */
	public String showBoard() {
		return this.board.toString().replaceAll("\\*", " ").replaceAll("#", "X");
	}
	
	/**
	 * checks if ship is hit or sunk
	 * @param ship
	 * @param hitCoord
	 * @return
	 */
	private int checkIfShipHitOrSunk(Ship ship, int[] hitCoord) {
		int hitsCount = 0;
		int result = Constants.HIT_NOHIT;
		Integer marker = hitCoord[0];
		Map statusMap = ship.getShipData().get(hitCoord[1]);
		if (ship.getOrientation() == Constants.ORIENTATION_Y) {
			marker = hitCoord[1];
			statusMap = ship.getShipData().get(hitCoord[0]);
		}
		
		if (statusMap != null && statusMap.get(marker) != null) {
			statusMap.put(marker, Constants.HIT_SUCCESS);
			result = Constants.HIT_SUCCESS;
			Iterator<Integer> _statusIterator = statusMap.values().iterator();
			while (_statusIterator.hasNext()) {
				if (_statusIterator.next().intValue() == Constants.HIT_SUCCESS) {
					hitsCount++;
				}
			}
			if (hitsCount >= ship.getDimension()) {
				result = Constants.HIT_SUNK;
			}
			if (ship.getOrientation() == Constants.ORIENTATION_X) {
				ship.getShipData().put(hitCoord[1], statusMap);
			} else {
				ship.getShipData().put(hitCoord[0], statusMap);
			}
		}
		
		return result;
	}
	
	/**
	 * checks and marks if ship is hit / sunk
	 * @param hitCoord
	 */
	private int checkIfShipsSunk(int[] hitCoord) {
		int result = Constants.HIT_NOHIT;
		
		for (Ship _ship : this.board.getListShips()) {
			result = checkIfShipHitOrSunk(_ship, hitCoord);
			if (result != Constants.HIT_NOHIT) {
				break;
			}
		}
		return result;
	}
	
	public int markShot(int[] hitCoord) {
		if (this.board.getDataMap()[hitCoord[0]][hitCoord[1]] == Constants.POSITION_EMPTY) {
			this.board.getDataMap()[hitCoord[0]][hitCoord[1]] = Constants.HIT_MISS;
			return Constants.HIT_MISS;
		}
		
		if (this.board.getDataMap()[hitCoord[0]][hitCoord[1]] == Constants.POSITION_SHIP) {
			this.board.getDataMap()[hitCoord[0]][hitCoord[1]] = Constants.HIT_SUCCESS;
			return checkIfShipsSunk(hitCoord);
		}
		
		return Constants.HIT_NOHIT;
	}
	
	/**
	 * get total number of shots marked on the board
	 * @return
	 */
	public int getNumberOfShots() {
		int result = 0;
		for (int i=0; i<this.board.getWidth(); i++) {
			for (int k=0; k<this.board.getHeight(); k++) {
				if (this.board.getDataMap()[i][k] != Constants.POSITION_EMPTY) {
					result++;
				}
			}			
		}
		
		return result;
	}
}
