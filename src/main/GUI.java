package main;

import controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.WindowPattern;
import view.GameListScreen;
import view.HomePane;
import view.StartPane;

public class GUI extends Application {
	private DiceController diceController;
	private GameController gameController;
	private AccountController accountController;
	private Scene scene;
	private ChatController chatController;
	private UserListController userListController;
	private RoundScreenController roundController;
	private CardController cardController;
	private SetupScreenController setupScreenController;
	private EndScreenController endController;

	void startup(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {

		StartPane startPane = new StartPane(this);
		HomePane homepane = new HomePane(this);
		GameListScreen gameListScreen = new GameListScreen(this);
		DatabaseController databaseController = new DatabaseController();
		WindowController windowController = new WindowController(this, databaseController);
		CalculateScoreController calcController = new CalculateScoreController(databaseController);

		diceController = new DiceController(this, windowController);
		chatController = new ChatController(this, databaseController);
        userListController = new UserListController(this, databaseController);
		gameController = new GameController(this, databaseController, windowController, diceController, chatController, calcController);

		
		cardController = new CardController(windowController, diceController, gameController, databaseController, this);
		accountController = new AccountController(this, databaseController, homepane, startPane, gameListScreen, gameController, diceController,cardController);
		roundController = new RoundScreenController(stage, databaseController, this, windowController, gameController);
		setupScreenController = new SetupScreenController(databaseController, this, gameController, accountController);
//	  EndScreenController EndController = new EndScreenController(stage, databaseController, gameController);

		endController = new EndScreenController(databaseController, gameController, calcController, this);



		scene = new Scene(startPane);
		stage.setScene(scene);

	//	stage.setScene(gameController);
		//stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); might be nice for test day.

		stage.setFullScreen(true);
		stage.show();
	}
	
	public void createGame(WindowPattern windowModel) {
		gameController.chooseWindow(windowModel);
		gameController.addGameScreens();
		gameController.getGameModel().makeGameEmpty();
		diceController.getDiceOnTableScreen().removeDicesScreen();
		gameController.getGameModel().selectPlayerIds();
		gameController.getGameModel().selectWholeGame();
		
		scene.setRoot(gameController.getGameScreen());
		gameController.startTimer();
	}
	
	public void handleCheat(boolean allPossible, boolean bestChoice) {
		gameController.handleCheatGame(allPossible, bestChoice);
	}
	
	public void makeDices() { gameController.handleRollDices(); }
	
	public void handlelogin(TextField username, PasswordField password) { accountController.login(username, password); }
	
	public void changePane(Pane pane) { scene.setRoot(pane); }
	
	public void changePane(ScrollPane pane) { scene.setRoot(pane); }
	
	public void handleregister(TextField username, PasswordField password) {
		accountController.register(username, password);
	}
	
	public void handleUitloggen() { accountController.uitloggen(); }
	
	public void handleToGameList() { accountController.showGames(); }
                                              
	public void handlegamesort(Object sortV) { accountController.handleSort(sortV); }
	
	public void handleHomeMenu() { accountController.toHomeMenu(); gameController.stopTimer();}
	
	public void sendString(String S) { accountController.setGameboolean(S); }

	public void sendMessage(String input){chatController.sendMessage(input);}

	public void handleSort(Object val){ userListController.handleSort(val); }
	
	public void handleFinishTurn() { gameController.handleFinishTurn(); }
	
	public void handleGoToRoundTrack() { scene.setRoot(roundController.getRoundScreen()); }
	
	public void handleGoBackToGame() { scene.setRoot(gameController.getGameScreen()); }

	public void handleGoToCards() { scene.setRoot(cardController.showcards()); }
  
	public void switchToolcards() { scene.setRoot(cardController.showcards()); }

	public void handleUserList() { scene.setRoot(userListController.getUserListScreen()); }
	
	public void handleChooseScreen() { scene.setRoot(gameController.getChooseScreen()); }
	
	public void handleToEndScreen() { scene.setRoot(endController.getEndScreen());}
	
	public void handleLoadSetup(int gameid) { scene.setRoot(setupScreenController.getSetupScreen()); setupScreenController.loadSetup(gameid);}
	
	// schakel van setup scherm naar invite scherm
	public void openInviterMenu() {
		setupScreenController.getInviteScreen().clearList();
		setupScreenController.addPlayersToInviteList();
		scene.setRoot(setupScreenController.getInviteScreen());
	}

	// schakel van invite naar setup scherm
	public void openSetupMenu() {
        scene.setRoot(setupScreenController.getSetupScreen());
        setupScreenController.getSetupScreen().clearJoinedList();
        setupScreenController.addJoinedPlayers();
    }

	public void handleToGetInvite() { setupScreenController.toInviteGetScreen(); }

	public void handleToPlayerList() {
            // TODO kan ik nog niet, mis userlist.
	}

	public void handleToCreateGame() { setupScreenController.toSetupScreen(); setupScreenController.makeNewGame(); }

	public void HandleExitGame() { System.exit(0); }

}
