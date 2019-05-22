package controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.FavorToken;
import queries.CardQueries;
import view.CardScreen;
import view.FavorTokensScreen;
import view.GameCardsScreen;
import view.ToolCardScreen;

public class CardController extends Scene {
	private GameCardsScreen cardScreen;
	private GameController gameController;
	private int amountOfFavorTokens;

	private WindowController windowController;
	private DiceController diceController;

	// alle toolcards
	private ToolCardScreen toolCard1;
	private ToolCardScreen toolCard2;
	private ToolCardScreen toolCard3;
	private ToolCardScreen toolCard4;
	private ToolCardScreen toolCard5;
	private ToolCardScreen toolCard6;
	private ToolCardScreen toolCard7;
	private ToolCardScreen toolCard8;
	private ToolCardScreen toolCard9;
	private ToolCardScreen toolCard10;
	private ToolCardScreen toolCard11;
	private ToolCardScreen toolCard12;

	// alle objectiveCards
	private CardScreen purpleObjectiveCard;
	private CardScreen greenObjectiveCard;
	private CardScreen blueObjectiveCard;
	private CardScreen redObjectiveCard;
	private CardScreen yellowObjectiveCard;
	private CardScreen objectiveCard1;
	private CardScreen objectiveCard2;
	private CardScreen objectiveCard3;
	private CardScreen objectiveCard4;
	private CardScreen objectiveCard5;
	private CardScreen objectiveCard6;
	private CardScreen objectiveCard7;
	private CardScreen objectiveCard8;
	private CardScreen objectiveCard9;
	private CardScreen objectiveCard10;

	private Boolean boughtTC1 = false;
	private Boolean boughtTC2 = false;
	private Boolean boughtTC3 = false;
	private Boolean boughtTC4 = false;
	private Boolean boughtTC5 = false;
	private Boolean boughtTC6 = false;
	private Boolean boughtTC7 = false;
	private Boolean boughtTC8 = false;
	private Boolean boughtTC9 = false;
	private Boolean boughtTC10 = false;
	private Boolean boughtTC11 = false;
	private Boolean boughtTC12 = false;

	private int TC1;
	private int TC2;
	private int TC3;

	private FavorTokensScreen TC1FTS1;
	private FavorTokensScreen TC1FTS2;
	private FavorTokensScreen TC1FTS3;
	private FavorTokensScreen TC1FTS4;
	
	private FavorTokensScreen TC2FTS1;
	private FavorTokensScreen TC2FTS2;
	private FavorTokensScreen TC2FTS3;
	private FavorTokensScreen TC2FTS4;
	
	
	private FavorTokensScreen TC3FTS1;
	private FavorTokensScreen TC3FTS2;
	private FavorTokensScreen TC3FTS3;
	private FavorTokensScreen TC3FTS4;
	 

	private CardQueries CQ;

	public CardController(WindowController cc, DiceController dc, GameController GC,
			DatabaseController databaseController) {
		super(new Pane());
		this.gameController = GC;
		windowController = cc;
		diceController = dc;
		gameController.setCardController(this);

		CQ = databaseController.getCardQueries();

		cardScreen = new GameCardsScreen(this);

		toolCard1  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard1-driepuntstang.png");
		toolCard2  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard2-eglomiseBorstel.png");
		toolCard3  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard3-folie-andrukker.png");
		toolCard4  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard4-loodopenhaler.png");
		toolCard5  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard5-rondsnijder.png");
		toolCard6  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard6-fluxborstel.png");
		toolCard7  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard7-loodhamer.png");
		toolCard8  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard8-glasbreektang.png");
		toolCard9  = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard9-snijliniaal.png");
		toolCard10 = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard10-schuurblok.png");
		toolCard11 = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard11-fluxverwijderaar.png");
		toolCard12 = new ToolCardScreen("file:Sagrada-cards/sagrada-toolcards/toolcard12-olieglassnijder.png");

		purpleObjectiveCard = new CardScreen("file:Sagrada-cards/sagrade-objectivecards/objectivecard-pers.paars.png");
		greenObjectiveCard = new CardScreen("file:Sagrada-cards/sagrade-objectivecards/objectivecard-pers.groen.png");
		blueObjectiveCard = new CardScreen("file:Sagrada-cards/sagrade-objectivecards/objectivecard-pers.blauw.png");
		yellowObjectiveCard = new CardScreen("file:Sagrada-cards/sagrade-objectivecards/objectivecard-pers.geel.png");
		redObjectiveCard = new CardScreen("file:Sagrada-cards/sagrade-objectivecards/objectivecard-pers.rood.png");

		objectiveCard1 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.tintvarieteit.png");
		objectiveCard2 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.halfdonkere-tinten.png");
		objectiveCard3 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.tintvarieteit-per-kolom.png");
		objectiveCard4 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.kleurvarieteit-per-kolom.png");
		objectiveCard5 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.donkere-tinten.png");
		objectiveCard6 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.kleurvarietteit.png");
		objectiveCard7 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.kleurvarieteit-per-rij.png");
		objectiveCard8 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.kleurdiagonalen.png");
		objectiveCard9 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.lichte-tinten.png");
		objectiveCard10 = new CardScreen(
				"file:Sagrada-cards/sagrade-objectivecards/objectivecard-pub.tintvarieteit-per-rij.png");
		
		
		
		TC1FTS1 = new FavorTokensScreen(new FavorToken(0, Color.BLUE));
		TC1FTS2 = new FavorTokensScreen(new FavorToken(0, Color.RED));
		TC1FTS3 = new FavorTokensScreen(new FavorToken(0, Color.GREEN));
		TC1FTS4 = new FavorTokensScreen(new FavorToken(0, Color.YELLOW));
		
		TC2FTS1 = new FavorTokensScreen(new FavorToken(0, Color.BLUE));
		TC2FTS2 = new FavorTokensScreen(new FavorToken(0, Color.RED));
		TC2FTS3 = new FavorTokensScreen(new FavorToken(0, Color.GREEN));
		TC2FTS4 = new FavorTokensScreen(new FavorToken(0, Color.YELLOW));
		
		TC3FTS1 = new FavorTokensScreen(new FavorToken(0, Color.BLUE));
		TC3FTS2 = new FavorTokensScreen(new FavorToken(0, Color.RED));
		TC3FTS3 = new FavorTokensScreen(new FavorToken(0, Color.GREEN));
		TC3FTS4 = new FavorTokensScreen(new FavorToken(0, Color.YELLOW));
		
		 

		generateToolcards();
		generateObjectiveCards();
		cardScreen.createView();
		
	}

	private int generateRandNR(int NR) {
		return (int) (Math.random() * (NR)) + 1;
	}

	GameCardsScreen showcards() {
		return cardScreen;
	}

	public void handleExit() {
		gameController.switchToGameScreen();

	}
	
	private void checkBoughtTC(int TC, int idGame) {
		if(!CQ.CheckTCBought(TC, idGame).isEmpty()) {
			switch(TC) {
				case 1: boughtTC1 = true;
					break;
				case 2: boughtTC2 = true;
					break;
				case 3: boughtTC3 = true;
					break;
				case 4: boughtTC4 = true;
					break;
				case 5: boughtTC5 = true;
					break;
				case 6: boughtTC6 = true;
					break;
				case 7: boughtTC7 = true;
					break;
				case 8: boughtTC8 = true;
					break;
				case 9: boughtTC9 = true;
					break;
				case 10: boughtTC10 = true;
					break;
				case 11: boughtTC11 = true;
					break;
				case 12: boughtTC12 = true;
					break;
			}
		}
	}
	
	private void buyTCSetDB(int TC, Boolean bought) {
		if(!bought) {
			
			CQ.BuyTC(TC,gameController.getAmountFT(),56,16);
		}if(bought){
			CQ.BuyTCPrice2(TC, gameController.getAmountFT(), gameController.getAmountFT()-1, 56, 16);
		}
	}
	
	public void checkAmountFTonTC(int tc) {
		if(tc == TC1) {
			switch(0) {
			case 1:
				TC1FTS1.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 2:
				TC1FTS2.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 3:
				TC1FTS3.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 4:
				TC1FTS4.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			}
			
		}
		if(tc == TC2) {
			switch(0) {
			case 1:
				TC2FTS1.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 2:
				TC2FTS2.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 3:
				TC2FTS3.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 4:
				TC2FTS4.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			}
		}
		if(tc == TC3) {
			switch(0) {
			case 1:
				TC3FTS1.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 2:
				TC3FTS2.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 3:
				TC3FTS3.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			case 4:
				TC3FTS4.getModel().setAmount(TC1FTS1.getModel().getAmount()+CQ.CheckAmountFTonTC(tc, 16, 56).size());
				break;
			}
		}
		
	}
	


	
	
	public void buyTC1(CardScreen cardscreen) {
		if (gameController.getAmountFT() != 0) {

			buyToolcard(cardscreen);
			cardScreen.BoughtTC1();
		}
	}

	private void buyToolcard(CardScreen cardscreen) {
		if (cardscreen == toolCard1) {
				checkBoughtTC(1,56);
			if (boughtTC9 && gameController.getAmountFT() > 1) {
				buyTCSetDB(1,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC1();
			}
			if (!boughtTC1) {
				buyTCSetDB(1,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC1 = true;
				buyTC1();
			}
		}
		if (cardscreen == toolCard2) {
			checkBoughtTC(2,56);
			if (boughtTC9 && gameController.getAmountFT() > 1) {
				buyTCSetDB(2,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC2();
			}
			if (!boughtTC2) {
				buyTCSetDB(2,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC2 = true;
				buyTC2();
			}
		}
		if (cardscreen == toolCard3) {
			checkBoughtTC(3,56);
			if (boughtTC9 && gameController.getAmountFT() > 1) {
				buyTCSetDB(3,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC3();

			}
			if (!boughtTC3) {
				buyTCSetDB(3,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC3 = true;
				buyTC3();

			}
		}
		if (cardscreen == toolCard4) {
			checkBoughtTC(4,56);
			if (boughtTC5 && gameController.getAmountFT() > 1) {
				buyTCSetDB(4,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC4();

			}
			if (!boughtTC4) {
				buyTCSetDB(4,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC4 = true;
				buyTC4();

			}
		}
		if (cardscreen == toolCard5) {
			checkBoughtTC(5,56);
			if (boughtTC5 && gameController.getAmountFT() > 1) {
				buyTCSetDB(5,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC5();

			}
			if (!boughtTC5) {
				buyTCSetDB(5,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC5 = true;
				buyTC5();

			}



		}
		if (cardscreen == toolCard6) {
			checkBoughtTC(6,56);
			if (boughtTC6 && gameController.getAmountFT() > 1) {
				buyTCSetDB(6,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC6();
			}
			if (!boughtTC6) {
				buyTCSetDB(6,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC6 = true;
				buyTC6();
			}



		}
		if (cardscreen == toolCard7) {
			checkBoughtTC(7,56);
			if (boughtTC7 && gameController.getAmountFT() > 1) {
				buyTCSetDB(7,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC7();

			}
			if (!boughtTC7) {
				buyTCSetDB(7,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC7 = true;
				buyTC7();

			}
		}
		if (cardscreen == toolCard8) {
			checkBoughtTC(8,56);
			if (boughtTC8 && gameController.getAmountFT() > 1) {
				buyTCSetDB(8,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC8();

			}
			if (!boughtTC8) {
				buyTCSetDB(8,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC8 = true;
				buyTC8();
			}
		}
		if (cardscreen == toolCard9) {
			checkBoughtTC(9,56);
			if (boughtTC9 && gameController.getAmountFT() > 1) {
				buyTCSetDB(9,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC9();
			}
			if (!boughtTC9) {
				buyTCSetDB(19,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC9 = true;
				buyTC9();
			}
		}
		if (cardscreen == toolCard10) {
			checkBoughtTC(10,56);
			if (boughtTC10 && gameController.getAmountFT() > 1) {
				buyTCSetDB(10,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC10();
			}
			if (!boughtTC10) {
				buyTCSetDB(10,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC10 = true;
				buyTC10();

			}
		}
		if (cardscreen == toolCard11) {
			checkBoughtTC(11,56);
			if (boughtTC11 && gameController.getAmountFT() > 1) {
				buyTCSetDB(11,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC11();

			}
			if (!boughtTC11) {
				buyTCSetDB(11,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC11 = true;
				buyTC11();
			}



		}
		if (cardscreen == toolCard12) {
			checkBoughtTC(12,56);
			if (boughtTC12 && gameController.getAmountFT() > 1) {
				buyTCSetDB(12,true);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 2));
				buyTC12();

			}
			if (!boughtTC12) {
				buyTCSetDB(12,false);
				gameController.setAmountFT(Integer.toString(gameController.getAmountFT() - 1));
				boughtTC12 = true;
				buyTC12();

			}



		}
		gameController.switchToGameScreen();
	}

	public void buyTC2(CardScreen cardscreen) {
		if (gameController.getAmountFT() != 0) {
			buyToolcard(cardscreen);
			cardScreen.BoughtTC2();
		}
	}

	public void buyTC3(CardScreen cardscreen) {
		if (gameController.getAmountFT() != 0) {
			buyToolcard(cardscreen);
			cardScreen.BoughtTC3();
		}
	}

	private void buyTC1() {
		diceController.setDiceGlowBorder(1);
	}

	private void buyTC2() {
		windowController.buyTC2();
	}

	private void buyTC3() {
		windowController.buyTC3();
	}

	private void buyTC4() {
		windowController.buyTC4();
	}

	private void buyTC5() {

	}

	private void buyTC6() {
		diceController.setDiceGlowBorder(6);
	}

	private void buyTC7() {

	}

	private void buyTC8() {

	}

	private void buyTC9() {
		windowController.buyTC9();
	}

	private void buyTC10() {
		diceController.setDiceGlowBorder(10);
	}

	private void buyTC11() {
		diceController.setDiceGlowBorder(11);
	}

	private void buyTC12() {

	}

	private void generateToolcards() {

		TC1 = generateRandNR(12);
		TC2 = generateRandNR(12);
		while (TC1 == TC2) {
			TC2 = generateRandNR(12);
		}
		TC3 = generateRandNR(12);
		while (TC3 == TC1 || TC3 == TC2) {
			TC3 = generateRandNR(12);
		}

		switch (TC1) {
		case 1:
			cardScreen.setTC1(toolCard1);
			toolCard1.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);

			break;
		case 2:
			cardScreen.setTC1(toolCard2);
			toolCard2.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 3:
			cardScreen.setTC1(toolCard3);
			toolCard3.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 4:
			cardScreen.setTC1(toolCard4);
			toolCard4.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 5:
			cardScreen.setTC1(toolCard5);
			toolCard5.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 6:
			cardScreen.setTC1(toolCard6);
			toolCard6.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 7:
			cardScreen.setTC1(toolCard7);
			toolCard7.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 8:
			cardScreen.setTC1(toolCard8);
			toolCard8.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 9:
			cardScreen.setTC1(toolCard9);
			toolCard9.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 10:
			cardScreen.setTC1(toolCard10);
			toolCard10.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 11:
			cardScreen.setTC1(toolCard11);
			toolCard11.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;
		case 12:
			cardScreen.setTC1(toolCard12);
			toolCard12.addFTScreens(TC1FTS1, TC1FTS2, TC1FTS3, TC1FTS4);
			break;

		}

		switch (TC2) {
		case 1:
			cardScreen.setTC2(toolCard1);
			toolCard1.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 2:
			cardScreen.setTC2(toolCard2);
			toolCard2.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 3:
			cardScreen.setTC2(toolCard3);
			toolCard3.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 4:
			cardScreen.setTC2(toolCard4);
			toolCard4.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 5:
			cardScreen.setTC2(toolCard5);
			toolCard5.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 6:
			cardScreen.setTC2(toolCard6);
			toolCard6.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 7:
			cardScreen.setTC2(toolCard7);
			toolCard7.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 8:
			cardScreen.setTC2(toolCard8);
			toolCard8.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 9:
			cardScreen.setTC2(toolCard9);
			toolCard9.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 10:
			cardScreen.setTC2(toolCard10);
			toolCard10.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 11:
			cardScreen.setTC2(toolCard11);
			toolCard11.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;
		case 12:
			cardScreen.setTC2(toolCard12);
			toolCard12.addFTScreens(TC2FTS1, TC2FTS2, TC2FTS3, TC2FTS4);
			break;

		}
		switch (TC3) {
		case 1:
			cardScreen.setTC3(toolCard1);
			toolCard1.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 2:
			cardScreen.setTC3(toolCard2);
			toolCard2.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 3:
			cardScreen.setTC3(toolCard3);
			toolCard3.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 4:
			cardScreen.setTC3(toolCard4);
			toolCard4.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 5:
			cardScreen.setTC3(toolCard5);
			toolCard5.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 6:
			cardScreen.setTC3(toolCard6);
			toolCard6.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 7:
			cardScreen.setTC3(toolCard7);
			toolCard7.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 8:
			cardScreen.setTC3(toolCard8);
			toolCard8.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 9:
			cardScreen.setTC3(toolCard9);
			toolCard9.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 10:
			cardScreen.setTC3(toolCard10);
			toolCard10.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 11:
			cardScreen.setTC3(toolCard11);
			toolCard11.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;
		case 12:
			cardScreen.setTC3(toolCard12);
			toolCard12.addFTScreens(TC3FTS1, TC3FTS2, TC3FTS3, TC3FTS4);
			break;

		}

	}

	private void generateObjectiveCards() {

		int privOBJ = generateRandNR(5);
		int pubOBJ1 = generateRandNR(10);
		int pubOBJ2 = generateRandNR(10);
		while (pubOBJ1 == pubOBJ2) {
			pubOBJ2 = generateRandNR(10);
		}
		int pubOBJ3 = generateRandNR(10);
		while (pubOBJ3 == pubOBJ1 || pubOBJ3 == pubOBJ2) {
			pubOBJ2 = generateRandNR(10);
		}

		
		switch (pubOBJ1) {
		case 1:
			cardScreen.setPUBOBJCard1(objectiveCard1);
			break;
		case 2:
			cardScreen.setPUBOBJCard1(objectiveCard2);
			break;
		case 3:
			cardScreen.setPUBOBJCard1(objectiveCard3);
			break;
		case 4:
			cardScreen.setPUBOBJCard1(objectiveCard4);
			break;
		case 5:
			cardScreen.setPUBOBJCard1(objectiveCard5);
			break;
		case 6:
			cardScreen.setPUBOBJCard1(objectiveCard6);
			break;
		case 7:
			cardScreen.setPUBOBJCard1(objectiveCard7);
			break;
		case 8:
			cardScreen.setPUBOBJCard1(objectiveCard8);
			break;
		case 9:
			cardScreen.setPUBOBJCard1(objectiveCard9);
			break;
		case 10:
			cardScreen.setPUBOBJCard1(objectiveCard10);

		}

		switch (pubOBJ2) {
		case 1:
			cardScreen.setPUBOBJCard2(objectiveCard1);
			break;
		case 2:
			cardScreen.setPUBOBJCard2(objectiveCard2);
			break;
		case 3:
			cardScreen.setPUBOBJCard2(objectiveCard3);
			break;
		case 4:
			cardScreen.setPUBOBJCard2(objectiveCard4);
			break;
		case 5:
			cardScreen.setPUBOBJCard2(objectiveCard5);
			break;
		case 6:
			cardScreen.setPUBOBJCard2(objectiveCard6);
			break;
		case 7:
			cardScreen.setPUBOBJCard2(objectiveCard7);
			break;
		case 8:
			cardScreen.setPUBOBJCard2(objectiveCard8);
			break;
		case 9:
			cardScreen.setPUBOBJCard2(objectiveCard9);
			break;
		case 10:
			cardScreen.setPUBOBJCard2(objectiveCard10);

		}
		switch (pubOBJ3) {
		case 1:
			cardScreen.setPUBOBJCard3(objectiveCard1);
			break;
		case 2:
			cardScreen.setPUBOBJCard3(objectiveCard2);
			break;
		case 3:
			cardScreen.setPUBOBJCard3(objectiveCard3);
			break;
		case 4:
			cardScreen.setPUBOBJCard3(objectiveCard4);
			break;
		case 5:
			cardScreen.setPUBOBJCard3(objectiveCard5);
			break;
		case 6:
			cardScreen.setPUBOBJCard3(objectiveCard6);
			break;
		case 7:
			cardScreen.setPUBOBJCard3(objectiveCard7);
			break;
		case 8:
			cardScreen.setPUBOBJCard3(objectiveCard8);
			break;
		case 9:
			cardScreen.setPUBOBJCard3(objectiveCard9);
			break;
		case 10:
			cardScreen.setPUBOBJCard3(objectiveCard10);

		}

		switch (privOBJ) {
		case 1:
			cardScreen.setPRIVOBJCard(blueObjectiveCard);
			break;
		case 2:
			cardScreen.setPRIVOBJCard(purpleObjectiveCard);
			break;
		case 3:
			cardScreen.setPRIVOBJCard(yellowObjectiveCard);
			break;
		case 4:
			cardScreen.setPRIVOBJCard(greenObjectiveCard);
			break;
		case 5:
			cardScreen.setPRIVOBJCard(redObjectiveCard);
			break;

		}
	}

}