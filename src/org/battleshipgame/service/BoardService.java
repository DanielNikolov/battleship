package org.battleshipgame.service;

import org.battleshipgame.daî.BoardDao;
import org.battleshipgame.util.Constants;

public class BoardService {
	private BoardDao boardDao;

	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	public void initBoard() {
		this.boardDao.createDestroyer(Constants.ORIENTATION_X);
		this.boardDao.createBattleship(Constants.ORIENTATION_Y);
		this.boardDao.createDestroyer(Constants.ORIENTATION_X);
	}
	
	private int[] convertCoordinates(String hitYX) {
		int[] hitCoord = new int[2];
		hitCoord[0] = Integer.valueOf(hitYX.substring(1))-1;
		hitCoord[1] = new Integer(hitYX.toUpperCase().charAt(0))-65; 
		
		return hitCoord;
	}
	
	public int shoot(String hitYX) {
		int[] hitXY = convertCoordinates(hitYX);
		return this.boardDao.markShot(hitXY);
	}
	
	public void printBoard() {
		System.out.println(this.boardDao.printBoard());
	}
	
	public void showBoard() {
		System.out.println(this.boardDao.showBoard());
		
	}
}
