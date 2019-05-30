package controller;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import main.GUI;
import model.CommunicationModel;
import view.InviteGetScreen;
import view.InviteScreen;
import view.SetupScreen;

public class SetupScreenController {
	
	private SetupScreen setupScreen;
	private CommunicationModel cModel;
	private InviteScreen inviteScreen;
	private InviteGetScreen inviteGetScreen;
	private GUI gui;
	private boolean randomPatterns = false;
	private GameController gameController;
	private AccountController accountController;
	
	public SetupScreenController(DatabaseController dataController, GUI gui, GameController gameController, AccountController accountController) {
		this.gui = gui;
		this.gameController = gameController;
		this.accountController = accountController;
		cModel = new CommunicationModel(dataController.getInviteQueries());
		inviteScreen = new InviteScreen(this, gui);
		setupScreen = new SetupScreen(this, gui);
		cModel.setClientUsername(accountController.getAccount());
	}
	
	// uitvoeren wanneer nieuwe game gemaakt moet worden
	public void makeNewGame() {
		setupScreen.makeNewGame();
		randomPatterns = false;
		cModel.setClientUsername(accountController.getAccount());
	}
	
	// uitvoeren wanneer bestaande setup geladen moet worden
	public void loadSetup(int gameid) {
		cModel.setGameID(gameid);
		setupScreen.loadSetup();
		randomPatterns = false;
		cModel.setClientUsername(accountController.getAccount());
		addJoinedPlayers();
	}
	
	public SetupScreen getSetupScreen() {
		return setupScreen;
	}
	
	public InviteScreen getInviteScreen() {
		return inviteScreen;
	}

	// spel wordt gestart als alle uitgenodigden geaccepteerd hebben. schakel van setup scherm naar window kies scherm 
	public void startGame() {
		if((long) cModel.getInvitedPlayerCount().get(0).get(0) < 2) {
			setupScreen.onlyOnePlayerWarning();
		}else if(cModel.checkDeclined()) {
			setupScreen.declinedInviteWarning();
		}else if(cModel.checkUnansweredInGame()) {
			setupScreen.unAnsweredInviteWarning();
		}else{
			gameController.getGameModel().setGameID(cModel.getGameID());
			gameController.getGameModel().createAllPlayerFrameFields(cModel.getGameID(), randomPatterns);
			gameController.addWindowScreens();
			gameController.getGameModel().selectwindowOptions();
			gui.handleChooseScreen();
		}
	}

	// bepaalt random/standaard patterns
	public void setRandomWindow(boolean random) {
		randomPatterns = random;
	}

	// schakel van setup scherm naar invite scherm
	public void openInviterMenu() {
		gui.changePane(inviteScreen);
		inviteScreen.clearList();
		addPlayersToInviteList();
	}
	
	// schakel van invite naar setup scherm
	public void openSetupMenu() {
		gui.changePane(setupScreen);
		setupScreen.clearJoinedList();
		addJoinedPlayers();
	}
	
	// voeg spelers to aan de invite lijst
	public void addPlayersToInviteList() {
		ArrayList<ArrayList <Object>> result = cModel.getInviteablePlayers();

		for (ArrayList<Object> objects : result) {
			inviteScreen.addPlayer((String) objects.get(0));
		}
	}
		
	// voeg toegevoegde spelers aan speler lijst
	public void addJoinedPlayers() {
		ArrayList<ArrayList <Object>> result = cModel.getJoinedPlayers();

		for (ArrayList<Object> objects : result) {
			if (objects.get(0) != null) {
				setupScreen.addJoinedPlayer((String) objects.get(0));
			}
		}
	}
	
	public void makeGame() {
		cModel.makeGame();
	}
	
	// controlleer aantal spelers in database en voeg nieuwe toe als het mag.
	public void invitePlayer(String username) {
		long invitedPlayerCount = (long) cModel.getInvitedPlayerCount().get(0).get(0);
		String color;
		
		if(invitedPlayerCount < 4) {
			if(cModel.notAlreadyAccepted(username)) {
				if(cModel.checkInviteAllowed(username)) {
					color = cModel.getPrivateObjectiveColor();
					cModel.invitePlayer(username, color);	
				}else {
					inviteScreen.inviteNotAllowedWarning();
				}
			}else {
				inviteScreen.alreadyAcceptedWarning();
			}
		}else{
			inviteScreen.maxInvitedWarning();
		}
	}
	
	public void toSetupScreen() {
		gui.changePane(setupScreen);
	}
	
//////////////////////// inviteGetScreen ///////////////////////////////	
	// schakel van setup scherm naar patternkeuze scherm
	private void openInviteGetScreen() {
		inviteGetScreen = new InviteGetScreen(this, gui);
		addPlayersToInviteGetList();
//		scene.setRoot(inviteGetScreen);
		toInviteGetScreen();
	}

	// voegt uitnodigingen en inviternaam toe aan de invite getlist
	public void addPlayersToInviteGetList() {
		ArrayList<ArrayList <Object>> result = cModel.getInviteGetList();

		for (ArrayList<Object> objects : result) {

			int gameid = (int) objects.get(0);
			String inviter = (String) cModel.getInviter(gameid).get(0).get(0);
			inviteGetScreen.addPlayer(inviter, gameid);
		}
	}
	
	// verandert spelerstatus naar geaccepteerd
	public void acceptInvite(int gameid) {
		cModel.acceptInvite(gameid);
	}
	
	// verandert spelerstatus naar geweigerd
	public void declineInvite(int gameid) {
		cModel.declineInvite(gameid);
	}
	
	public void toInviteGetScreen() {
		gui.changePane(inviteGetScreen);
	}

	
}
