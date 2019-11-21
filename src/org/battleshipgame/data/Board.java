package org.battleshipgame.data;

import java.util.ArrayList;
import java.util.List;

import org.battleshipgame.util.Constants;

public class Board {
	private int width;
	private int height;
	private int[][] dataMap;
	private List<Ship> listShips;
	
	public int[][] getDataMap() {
		return dataMap;
	}

	public void setDataMap(int[][] dataMap) {
		this.dataMap = dataMap;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public Board (int height, int width) {
		this.height = height;
		this.width = width;
		dataMap = new int[height][width];
		for (int i=0; i<height; i++) {
			for (int k=0; k<width; k++) {
				dataMap[i][k] = Constants.POSITION_EMPTY;
			}
		}
		listShips = new ArrayList<Ship>();
	}

	@Override
	public String toString() {
		StringBuilder sbldr = new StringBuilder("  ");
		for (int i=0; i<width; i++) {
			sbldr.append(Integer.toString(i+1)).append(" ");
		}
		sbldr.append("\n");
		for (int i=0; i<height; i++) {
			sbldr.append(Character.toUpperCase((char) (65 + i))).append(" ");
			for (int k=0; k<width; k++) {
				switch (dataMap[k][i]) {
					case Constants.POSITION_SHIP:
						sbldr.append("# ");
						break;
					case Constants.HIT_MISS:
						sbldr.append("- ");
						break;
					case Constants.HIT_SUCCESS:
						sbldr.append("X ");
						break;					
					default:
						sbldr.append("* ");
						break;
				}
			}
			sbldr.append("\n");
		}
		
		return sbldr.toString();
	}

	public List<Ship> getListShips() {
		return listShips;
	}

	public void setListShips(List<Ship> listShips) {
		this.listShips = listShips;
	}
	
	
}
