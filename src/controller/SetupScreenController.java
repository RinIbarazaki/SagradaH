package controller;

import model.CommunicationModel;
import view.InviteGetScreen;
import view.InviteScreen;
import view.SetupScreen;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SetupScreenController {
	
	private SetupScreen setupScreen;
	private CommunicationModel cModel;
	private InviteScreen inviteScreen;
	private InviteGetScreen inviteGetScreen;
	private Scene scene;
	
	public SetupScreenController(Stage stage, DatabaseController dataController) {
		cModel = new CommunicationModel(dataController.getInviteQueries());
		inviteScreen = new InviteScreen(this);
		setupScreen = new SetupScreen(this);
		scene = new Scene(setupScreen);
		
		stage.setScene(scene);

	}

	// bepaalt random/standaard patterns
	public void setRandomWindow(boolean random) {
		
	}
	

	
	// schakel van setup scherm naar invite scherm
	public void openInviterMenu() {
		scene.setRoot(inviteScreen);
		inviteScreen.clearList();
		addPlayersToInviteList();
	}
	
	// schakel van invite naar setup scherm
	public void openSetupMenu() {
		scene.setRoot(setupScreen);
		setupScreen.clearJoinedList();
		addJoinedPlayers();
	}
	
	// voeg spelers to aan de invite lijst
	public void addPlayersToInviteList() {
		ArrayList<ArrayList <Object>> result = cModel.getInviteablePlayers();
		
		for(int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).get(0));
			inviteScreen.addPlayer((String) result.get(i).get(0));
		}
	}
	
	
	// voeg toegevoegde spelers aan speler lijst
	public void addJoinedPlayers() {
		ArrayList<ArrayList <Object>> result = cModel.getJoinedPlayers();
		
		for(int i = 0; i < result.size(); i++) {
			if(result.get(i).get(0) != null) {
				setupScreen.addJoinedPlayer((String) result.get(i).get(0));
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
			color = cModel.getPrivateObjectiveColor();
			cModel.invitePlayer(username, color);	
		}else {
			inviteScreen.maxInvitedWarning();
		}
	}
	
//////////////////////// inviteGetScreen ///////////////////////////////	
	// schakel van setup scherm naar patternkeuze scherm
	public void finishSetup() {
		inviteGetScreen = new InviteGetScreen(this);
		addPlayersToInviteGetList();
		scene.setRoot(inviteGetScreen);
	}

	public void addPlayersToInviteGetList() {
		ArrayList<ArrayList <Object>> result = cModel.getInviteGetList();
		
		for(int i = 0; i < result.size(); i++) {
			
			int gameid = (int) result.get(i).get(0);
			System.out.println(gameid);
			String inviter = (String)cModel.getInviter(gameid).get(0).get(0);
			inviteGetScreen.addPlayer(inviter, gameid);
		}
	}
	
	public void acceptInvite(String host, int gameid) {
		cModel.acceptInvite(host, gameid);
	}
	
	public void declineInvite(String host, int gameid) {
		cModel.declineInvite(host, gameid);
	}
}
