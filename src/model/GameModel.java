package model;

import java.util.ArrayList;

import queries.GameQueries;
import queries.InviteHandleQueries;

public class GameModel {
	
	private GameQueries gameQueries;
	
	public GameModel(GameQueries gameQueries) {
		this.gameQueries = gameQueries;
	}
	
	// returnt rondebord dobbelstenen
	public ArrayList<ArrayList<Object>> getDicesOnRoundBoard(int round) {
		return gameQueries.getDicesOnRoundBoard(round);
	}

	// verwijdert dobbelsteen van rondebord
	public void removeDice(int diceID, String colorText) {
		gameQueries.removeDice(diceID, colorText);
		
	}
}
