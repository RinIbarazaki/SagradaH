
package controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.GUI;
import model.Game;
import model.Player;
import model.WindowPattern;
import timer.AnimationTimerEXT;
import view.*;

import java.util.ArrayList;

public class GameController extends Scene {
	private GameInfoScreen gameInfo;

	private ChatScreen chat;
	private ChatController CC;
	private CardsInfoScreen kaarten;
	private GameScreen gameScreen;
	private CalculateScoreController calculateScoreController;
	private WindowPatternChooseScreen windowChoooseScreen;
	private CardController CardController;

	private Game gameModel;

	private WindowController WC;
	private DiceController DC;
	private GUI gui;
	private AnimationTimerEXT timer;

	private boolean gameStarted = false;

	public GameController(GUI gui, DatabaseController databaseController, WindowController WC, DiceController DC,
						  ChatController CC, CalculateScoreController CSC) {
		super(new Pane());
		this.WC = WC;
		this.DC = DC;
		this.calculateScoreController = CSC;
		this.gui = gui;
		this.CC = CC;

		gameModel = new Game(databaseController.getGameQuery(), DC.getDiceOnTableModel(), WC, CardController);
		gameModel.addPlayer(new Player(databaseController.getPlayerQuery()));
		gameModel.addPlayer(new Player(databaseController.getPlayerQuery()));
		gameModel.addPlayer(new Player(databaseController.getPlayerQuery()));
		gameModel.addPlayer(new Player(databaseController.getPlayerQuery()));

		gameModel.getPlayer(0).givePlayerWindowPattern(WC.getWindow1().getWindowPatternModel());
		gameModel.getPlayer(1).givePlayerWindowPattern(WC.getWindow2().getWindowPatternModel());
		gameModel.getPlayer(2).givePlayerWindowPattern(WC.getWindow3().getWindowPatternModel());
		gameModel.getPlayer(3).givePlayerWindowPattern(WC.getWindow4().getWindowPatternModel());


		gameScreen = new GameScreen();
		kaarten = new CardsInfoScreen(gui,"Kaarten",this);
		gameInfo = new GameInfoScreen(gui, gameModel, "GameInfo");
		chat = CC.getChatScreen();


		gameInfo.setStyle("-fx-background-radius: 0 0 300 0;-fx-background-color: DEEPSKYBLUE; ");
		chat.setStyle("-fx-background-radius: 0 300 0 0;-fx-background-color: DEEPSKYBLUE;");
		kaarten.setStyle("-fx-background-radius: 300 0 0 0;-fx-background-color: DEEPSKYBLUE;");

		windowChoooseScreen = new WindowPatternChooseScreen(gui, WC);

		WC.setGameController(this);
		WC.setDiceController(DC);
		
		gameScreen.add(gameInfo, 0, 0, 2, 1);
		gameScreen.add(DC.getDiceOnTableScreen(), 2, 0, 2, 1);
		gameScreen.add(chat, 0, 2, 2, 1);
		gameScreen.add(kaarten, 2, 2, 2, 1);
		
		addGameScreens();

		GridPane.setMargin(WC.getWindow1(), new Insets(0, 0, 0, 80));
		GridPane.setMargin(WC.getWindow4(), new Insets(0, 80, 0, 0));
	
		createTimer();
	}
	
	void setCardController(CardController cc) {
		this.CardController=cc;
	}
	
	
	/**
	 * make the game windows visible
	 */
	public void addGameScreens() {
		gameScreen.add(WC.getWindow1(), 0, 1);
		gameScreen.add(WC.getWindow2(), 1, 1);
		gameScreen.add(WC.getWindow3(), 2, 1);
		gameScreen.add(WC.getWindow4(), 3, 1);
	}
	
	/**
	 * make the choose windows visible
	 */
	public void addWindowScreens() {
		windowChoooseScreen.add(WC.getWindow1(), 0, 1);
		windowChoooseScreen.add(WC.getWindow2(), 1, 1);
		windowChoooseScreen.add(WC.getWindow3(), 2, 1);
		windowChoooseScreen.add(WC.getWindow4(), 3, 1);
	}
	

	/**
	 * @param allPossible = show all the possible placements
	 * @param bestChoice = show the best placements
	 * handle cheat function
	 */
	public void handleCheatGame(boolean allPossible, boolean bestChoice) {
		WC.setCheatAllPossible(allPossible);
		WC.setCheatBestChoice(bestChoice);
	}

	public void switchToolcards() {
		setRoot(CardController.showcards());
	}
	
	void switchToGameScreen() {
		setRoot(gameScreen);
	}

   
    
    

	public Game getGameModel() {
		return gameModel;
	}

	/**
	 * create the timer
	 */
	private void createTimer() {
		timer = new AnimationTimerEXT(5000) {
			@Override
			public void doAction() {
				DC.getDiceOnTableScreen().removeDicesScreen();
				gameModel.selectWholeGame();
				//has to do with toolcard 8
				if(WC.skipSecondTurn() && gameModel.isSecondTurn() && gameModel.getPlayer(0).selectCurrentPlayer()) {
					WC.setSkipSecondTurnFalse();
					gameModel.giveTurnToNextPlayer();
				}

				if(!gameStarted){
					if(gameModel.gameStarted()){ gameStarted = true; }
				}
				else{
					getClientScore();
					getOtherScore();
				}
				
				if (gameModel.amITheGameCreator() && !gameModel.doesEveryPlayerHasTheirFavorTokens() && gameModel.didEveryoneChoose()) {
					gameModel.giveAllThePlayersTheirFavorTokens();
				}
				
				if(gameModel.checkIfGameIsOver()) {
					gui.handleToEndScreen();
					stopTimer();
				}

				CC.getNewMessages(gameModel.getGameID());

				CardController.SetAmountFTOnTC();
				kaarten.setAmountFT(CardController.getCardModel().getAmountFT());
				//favor tokens
				//card costs
			}
		};
	}
	
	public void stopTimer() {
		timer.stop();
	}
	
	public void startTimer() {
		timer.start();
	}
	
	/**
	 * handle everything when you finish your turn
	 */
	public void handleFinishTurn() {
		if(gameModel.getPlayer(0).selectCurrentPlayer() && gameModel.isSecondTurn() && gameModel.isMainPlayerTheLastTurnOfTheRound()) {
			gameModel.placeDicesOnRoundTrack();
			DC.getDiceOnTableScreen().removeDicesScreen();
			gameModel.selectWholeGame();
		}
		
		if(gameModel.getPlayer(0).selectCurrentPlayer() && !gameModel.checkIfMainPlayerCanThrowDices()) {
			gameModel.giveTurnToNextPlayer();
			WC.setMovedToFalse();
			WC.setCanOnlyMoveDiceWithSameColorAsDIceOnRoundTrackFalse();
			WC.setDiceCanBeMovedFalse();
		}
		
		gameModel.selectWholeGame();
		getClientScore();
		getOtherScore();
	}

	private void getClientScore(){
		int playerID = gameModel.getClientPlayer().getPlayerId();
		gameModel.getClientPlayer().getWindowPatternPlayer().setPlayerScore(Integer.toString(calculateScoreController.getClientScore(playerID)));
	}

	private void getOtherScore(){
		ArrayList<Player> players = gameModel.getAllPlayers();
		for(Player player : players.subList(1, players.size())){
			player.getWindowPatternPlayer().setPlayerScore(Integer.toString(calculateScoreController.getOtherScore(player.getPlayerId())));
		}
	}
	
	/**
	 * check if you can roll the dice and then roll the dice
	 */
	public void handleRollDices() {
		if(gameModel.checkIfMainPlayerCanThrowDices()) {
			gameModel.rollTheDices();
			gameModel.selectWholeGame();
		}
	}
	
	/**
	 * @param windowModel = the window you have chosen
	 * check wich window you have chosen and update it in the database
	 */
	public void chooseWindow(WindowPattern windowModel) {
		gameModel.getPlayer(0).updateWindowId(windowModel.getId());
	}
	
	public WindowPatternChooseScreen getChooseScreen() {
		return windowChoooseScreen;
	}

	ChatController getCC() { return CC; }

	public GameScreen getGameScreen() {
		return gameScreen;
	}

}