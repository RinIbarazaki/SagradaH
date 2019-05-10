package view;

import java.util.ArrayList;

import controller.WindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import main.GUI;
import model.Dice;
import model.DiceOnTable;

public class DiceOnTableScreen extends GridPane {
	DiceOnTable diceOnTableModel;
	private Button createNewDices;
	private GUI gui;

	private WindowController WC;

	public DiceOnTableScreen(GUI gui, DiceOnTable diceOnTableModel, WindowController WC) {
		this.diceOnTableModel = diceOnTableModel;
		this.WC = WC;

		createNewDices = new Button("Gooi");
		createNewDices.setOnAction(e -> gui.makeDices());

		add(createNewDices, 2, 3, 5, 1);

		setHgap(10);
		setVgap(10);
		setAlignment(Pos.CENTER);
		setStyle("-fx-background-radius: 0 0 0 300;-fx-background-color: DEEPSKYBLUE;");

		this.diceOnTableModel.diceOnTableProperty().addListener(new MyDiceOnTableListener());
	}

	private class MyDiceOnTableListener implements ChangeListener<ArrayList<Dice>> {

		@Override
		public void changed(ObservableValue<? extends ArrayList<Dice>> observable, ArrayList<Dice> oldValue,
				ArrayList<Dice> newValue) {
			// TODO Auto-generated method stu
			try {
				getChildren().clear();
				add(createNewDices, 2, 2, 5, 1);

				if (newValue != null) {
					int column = 0;
					int row = 0;
					boolean volgendeColumn = false;
					for (int i = 0; i < newValue.size(); i++) {
						int eyes = newValue.get(i).getEyes();
						DiceScreen diceScreen = new DiceScreen(newValue.get(i));
						WC.dragButton(diceScreen);
						newValue.get(i).setEyes(0);
						newValue.get(i).setEyes(eyes);
						add(diceScreen, column, row);

						if (!volgendeColumn) {
							row++;
							volgendeColumn = true;
						} else {
							column++;
							row--;
							volgendeColumn = false;
						}
					}
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
