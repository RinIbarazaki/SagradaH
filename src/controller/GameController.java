package controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import main.GUI;
import model.Game;
import model.WindowPattern;
import view.ChatScreen;
import view.GameInfoScreen;
import view.GameScreen;
import view.WindowPatternChooseScreen;

public class GameController extends Scene {
	private GameInfoScreen gameInfo;
	private ChatScreen chat;
	private GameInfoScreen kaarten;
	private GameScreen gameScreen;

	private WindowPatternChooseScreen windowChoooseScreen;
	
	private Game gameModel;
	

	private WindowController WC;
	private DiceController DC;
	
	private GUI gui;
	

	public GameController(GUI gui, WindowController WC, DiceController DC, ChatController CC) {
		super(new Pane());
		this.gui = gui;
		this.WC = WC;
		this.DC = DC;
		
		gameModel = new Game();
		gameModel.getPlayer(0).givePlayerWindowPattern(WC.getWindow1().getWindowPatternModel());
		gameModel.getPlayer(1).givePlayerWindowPattern(WC.getWindow2().getWindowPatternModel());
		gameModel.getPlayer(2).givePlayerWindowPattern(WC.getWindow3().getWindowPatternModel());
		gameModel.getPlayer(3).givePlayerWindowPattern(WC.getWindow4().getWindowPatternModel());

		gameScreen = new GameScreen();

		gameInfo = new GameInfoScreen(gui, "GameInfo");
		chat = CC.getChatScreen();
		kaarten = new GameInfoScreen(gui,"Kaarten");

		gameInfo.setStyle("-fx-background-radius: 0 0 300 0;-fx-background-color: DEEPSKYBLUE; ");
		chat.setStyle("-fx-background-radius: 0 300 0 0;-fx-background-color: DEEPSKYBLUE;");
		kaarten.setStyle("-fx-background-radius: 300 0 0 0;-fx-background-color: DEEPSKYBLUE;");

		windowChoooseScreen = new WindowPatternChooseScreen(gui, WC);
		
		windowChoooseScreen.add(WC.getWindow1(), 0, 1);
		windowChoooseScreen.add(WC.getWindow2(), 1, 1);
		windowChoooseScreen.add(WC.getWindow3(), 2, 1);
		windowChoooseScreen.add(WC.getWindow4(), 3, 1);

		setRoot(windowChoooseScreen);
		
		WC.setGameController(this);
		WC.setDiceController(DC);
	}

	public void createGame(WindowPattern windowModel) {

		gameScreen.add(gameInfo, 0, 0, 2, 1);
		gameScreen.add(DC.getDiceOnTableScreen(), 2, 0, 2, 1);
		gameScreen.add(chat, 0, 2, 2, 1);
		gameScreen.add(kaarten, 2, 2, 2, 1);

		if (windowModel != WC.getWindow1().getWindowPatternModel()) {
			WC.setWindow1(windowModel);
		}

		WC.makeWindowsGray(WC.getWindow2().getWindowPatternModel());
		WC.makeWindowsGray(WC.getWindow3().getWindowPatternModel());
		WC.makeWindowsGray(WC.getWindow4().getWindowPatternModel());

		gameScreen.add(WC.getWindow1(), 0, 1);
		gameScreen.add(WC.getWindow2(), 1, 1);
		gameScreen.add(WC.getWindow3(), 2, 1);
		gameScreen.add(WC.getWindow4(), 3, 1);
		

		gameScreen.setMargin(WC.getWindow1(), new Insets(0, 0, 0, 80));
		gameScreen.setMargin(WC.getWindow4(), new Insets(0, 80, 0, 0));

		setRoot(gameScreen);
	}
	
	public void handleCheatGame(boolean allPossible, boolean bestChoice) {
		WC.setCheatAllPossible(allPossible);
		WC.setCheatBestChoice(bestChoice);
	}
	
	public void setPoints(int value) {
		gameInfo.setPoints(value);
	}

}
