package controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import main.GUI;
import model.Dice;
import model.DiceOnTable;
import view.DiceOnTableScreen;
import view.DiceScreen;
import view.FieldScreen;

public class DiceController {

	private ArrayList<Color> colorsDice = new ArrayList<>();
	private Random r = new Random();

	private WindowController WC;

	private GUI gui;

	private DiceOnTableScreen diceOnTableScreen;

	private DiceOnTable diceOnTableModel;

	public DiceController(GUI gui, WindowController WC) {
		this.gui = gui;
		this.WC = WC;

		diceOnTableModel = new DiceOnTable();

		diceOnTableScreen = new DiceOnTableScreen(gui, diceOnTableModel);

		addColorsDice();
		makeDices();

	}

	public void makeDices() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2; j++) {
				int eyes = r.nextInt((6 - 1) + 1) + 1;
				int color = r.nextInt(5);
				Dice diceModel = new Dice(eyes, colorsDice.get(color));

				DiceScreen b = new DiceScreen(diceModel);
				diceModel.setEyes(eyes);

				b.setPrefHeight(35);
				b.setPrefWidth(35);
				diceOnTableScreen.add(b, i, j);
				diceOnTableModel.addDiceToTable(diceModel);
				WC.dragButton(b);
			}
		}

	}

	public void addColorsDice() {
		colorsDice.add(Color.CORNFLOWERBLUE);
		colorsDice.add(Color.YELLOW);
		colorsDice.add(Color.RED);
		colorsDice.add(Color.MAGENTA);
		colorsDice.add(Color.LIGHTGREEN);
	}

	public DiceOnTableScreen getDiceOnTableScreen() {
		return diceOnTableScreen;
	}

	public DiceOnTable getDiceOnTableModel() {
		return diceOnTableModel;
	}

	public void setDiceGlowBorder(int nummer) {
		for (Node node : diceOnTableScreen.getChildren()) {
			if (node instanceof DiceScreen) {
				DiceScreen result = (DiceScreen) node;
				result.setGlowBorder();
				result.setOnMouseClicked(e -> selectDice(result, nummer));

			}

		}

	}

	private void setDiceBlackBorder() {
		for (Node node : diceOnTableScreen.getChildren()) {
			if (node instanceof DiceScreen) {
				DiceScreen result = (DiceScreen) node;
				result.setBlackBorder();
				result.setOnMouseClicked(null);

			}

		}

	}

	public void selectDice(DiceScreen dice, int nummer) {
		setDiceBlackBorder();
		dice.setGlowBorder();
		
		switch(nummer) {
		case 1:
			dice.setOnMouseClicked(e -> DicesPlus1(dice, false));
			break;
		case 6:	
			dice.setOnMouseClicked(e -> throwDiceOnes(dice));
			break;
			
		case 10: dice.setOnMouseClicked(e -> DiceTurnAround(dice));
			break;
		case 11: dice.setOnMouseClicked(e -> pickNewDice(dice));
		}

	}
	
	public void pickNewDice(DiceScreen dice) {
		
		dice.getDiceModel().setColor(colorsDice.get(r.nextInt(5)));
		dice.setOnMouseClicked(e -> DicesPlus1(dice, true));
	}
	
	public void throwDiceOnes(DiceScreen dice) {
		dice.setOnMouseClicked(null);
		dice.getDiceModel().setEyes(r.nextInt((6 - 1) + 1) + 1);
	}
	
	
	public void DiceTurnAround(DiceScreen dice) {
		switch(dice.getDiceModel().getEyes()) {
		case 1:	dice.getDiceModel().setEyes(6);
			break;
		case 2:	dice.getDiceModel().setEyes(5);
		break;
		case 3:	dice.getDiceModel().setEyes(4);
		break;
		case 4:	dice.getDiceModel().setEyes(3);
		break;
		case 5:	dice.getDiceModel().setEyes(5);
		break;
		case 6:	dice.getDiceModel().setEyes(1);
		break;
		}
	}

	private void DicesPlus1(DiceScreen dice, boolean repeat) {
		if (dice.getDiceModel().getEyes() == 6) {
			dice.getDiceModel().setEyes(1);
		} else {
			dice.getDiceModel().setEyes(dice.getDiceModel().getEyes() + 1);
		}if(repeat == false) {
		dice.setOnMouseClicked(e -> dicesMinus2(dice));
		}
	}

	private void dicesMinus2(DiceScreen dice) {
		if (dice.getDiceModel().getEyes() == 2) {
			dice.getDiceModel().setEyes(6);
			
		}
		if (dice.getDiceModel().getEyes() == 1) {
			dice.getDiceModel().setEyes(5);
		} else {

			dice.getDiceModel().setEyes(dice.getDiceModel().getEyes() - 2);
		}
		dice.setOnMouseClicked(e -> DicesPlus2(dice));
	}

	private void DicesPlus2(DiceScreen dice) {
		if (dice.getDiceModel().getEyes() == 6) {
			dice.getDiceModel().setEyes(2);
		}
		if (dice.getDiceModel().getEyes() == 5) {
			dice.getDiceModel().setEyes(1);
		} else {
			dice.getDiceModel().setEyes(dice.getDiceModel().getEyes() + 2);
		}
		dice.setOnMouseClicked(e -> dicesMinus2(dice));
	}

}
