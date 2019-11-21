package org.battleshipgame.app;

import java.util.Scanner;

import org.battleshipgame.service.BoardService;
import org.battleshipgame.util.Constants;

public class BattleshipApp {

	private BoardService boardService;

	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	public void startApp() {
		Scanner keyboard = new Scanner(System.in);
		String regex = "^[A-Ja-j]([1-9]|10)$";
		int shipsSunk = 0;
		int hitResult = 0;
		
		this.boardService.initBoard();
		System.out.println();
		boardService.printBoard();
		do {
			System.out.println("Enter coordinates (row, col), e.g. A5: ");
			String input = keyboard.nextLine();
			if (input != null) {
				if ("show".equalsIgnoreCase(input.trim())) {
					this.boardService.showBoard();
					continue;
				} else if (input.trim().matches(regex)) {					
					switch (this.boardService.shoot(input.trim())) {
						case Constants.HIT_MISS:
							System.out.println("*** Miss ***");
							hitResult++;
							break;
						case Constants.HIT_SUCCESS:
							System.out.println("*** Hit ***");
							hitResult++;
							break;
						case Constants.HIT_SUNK:
							System.out.println("*** Sunk ***");
							shipsSunk++;
							hitResult++;
							break;
						default:
							break;
					}
				}
			}
			System.out.println();
			boardService.printBoard();
		} while (shipsSunk < 3);
		
		if (shipsSunk >= 3) {
			System.out.println("Well done! You completed the game in " + hitResult + " shots");
		}
	}
}
