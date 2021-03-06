package view;

import controller.WindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.DiceModel;
import model.FieldModel;

public class FieldScreen extends StackPane {

	private FieldModel fieldModel;

	private WindowController WC;

	public FieldScreen(FieldModel fieldModel, WindowController WC) {
		this.fieldModel = fieldModel;
		this.WC = WC;
		setMinSize(50, 50);
		setMaxSize(50, 50);
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
		setPadding(new Insets(5));

		backgroundProperty().bind(fieldModel.backgroundPropery());
		MyEyesListener eyeslistener = new MyEyesListener();
		MyDiceListener dicelistener = new MyDiceListener();
		this.fieldModel.eyesProperty().addListener(eyeslistener);
		this.fieldModel.diceProperty().addListener(dicelistener);
	}

	/**
	 * @param value = how many eyes do you want to draw
	 * draw all the eyes on the field
	 */
	private void checkNumber(int value) {
		getChildren().clear();
		switch (value) {
		case 1:
			Circle cir = new Circle(5, Color.BLACK);
			setAlignment(cir, Pos.CENTER);
			getChildren().add(cir);

			break;
		case 2:
			Circle cir2 = new Circle(5, Color.BLACK);
			setAlignment(cir2, Pos.TOP_LEFT);

			Circle cir3 = new Circle(5, Color.BLACK);
			setAlignment(cir3, Pos.BOTTOM_RIGHT);
			getChildren().addAll(cir2, cir3);

			break;
		case 3:
			Circle cir4 = new Circle(5, Color.BLACK);
			setAlignment(cir4, Pos.TOP_RIGHT);

			Circle cir5 = new Circle(5, Color.BLACK);
			setAlignment(cir5, Pos.CENTER);

			Circle cir6 = new Circle(5, Color.BLACK);
			setAlignment(cir6, Pos.BOTTOM_LEFT);

			getChildren().addAll(cir4, cir5, cir6);

			break;
		case 4:
			Circle cir7 = new Circle(5, Color.BLACK);
			setAlignment(cir7, Pos.BOTTOM_RIGHT);

			Circle cir8 = new Circle(5, Color.BLACK);
			setAlignment(cir8, Pos.BOTTOM_LEFT);

			Circle cir9 = new Circle(5, Color.BLACK);
			setAlignment(cir9, Pos.TOP_RIGHT);

			Circle cir10 = new Circle(5, Color.BLACK);
			setAlignment(cir10, Pos.TOP_LEFT);

			getChildren().addAll(cir7, cir8, cir9, cir10);

			break;
		case 5:
			Circle cir11 = new Circle(5, Color.BLACK);
			setAlignment(cir11, Pos.TOP_LEFT);

			Circle cir12 = new Circle(5, Color.BLACK);
			setAlignment(cir12, Pos.TOP_RIGHT);

			Circle cir13 = new Circle(5, Color.BLACK);
			setAlignment(cir13, Pos.BOTTOM_LEFT);

			Circle cir14 = new Circle(5, Color.BLACK);
			setAlignment(cir14, Pos.BOTTOM_RIGHT);

			Circle cir15 = new Circle(5, Color.BLACK);
			setAlignment(cir15, Pos.CENTER);

			getChildren().addAll(cir11, cir12, cir13, cir14, cir15);

			break;
		case 6:
			Circle cir16 = new Circle(5, Color.BLACK);
			setAlignment(cir16, Pos.BOTTOM_RIGHT);

			Circle cir17 = new Circle(5, Color.BLACK);
			setAlignment(cir17, Pos.CENTER_RIGHT);

			Circle cir18 = new Circle(5, Color.BLACK);
			setAlignment(cir18, Pos.TOP_RIGHT);

			Circle cir19 = new Circle(5, Color.BLACK);
			setAlignment(cir19, Pos.TOP_LEFT);

			Circle cir20 = new Circle(5, Color.BLACK);
			setAlignment(cir20, Pos.CENTER_LEFT);

			Circle cir21 = new Circle(5, Color.BLACK);
			setAlignment(cir21, Pos.BOTTOM_LEFT);

			getChildren().addAll(cir16, cir17, cir18, cir19, cir20, cir21);

			break;
		default:
			break;
		}
	}

	void cheatBorder() {
		setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
	}

	void normalBorder() {
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
	}

	public FieldModel getFieldModel() {
		return fieldModel;
	}

	/**
	 *check if eyes have changed, when changed draw eyes
	 */
	private class MyEyesListener implements ChangeListener<Number> {
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			checkNumber((int) newValue);
		}
	}

	/**
	 *check if dice on field has changed, when changed draw dice
	 */
	private class MyDiceListener implements ChangeListener<DiceModel> {
		@Override
		public void changed(ObservableValue<? extends DiceModel> observable, DiceModel oldValue, DiceModel newValue) {
			// TODO Auto-generated method stub
			try {

				if (newValue != null) {
					int eyes = newValue.getEyes();
					DiceScreen diceScreen = new DiceScreen(newValue);
					WC.dragButton(diceScreen);
					diceScreen.makeBorderWhite();
					newValue.setEyes(0);
					newValue.setEyes(eyes);
					getChildren().add(diceScreen);
				} 
				else {
					for (Node node : getChildren()) {
						if (node instanceof DiceScreen) {
							getChildren().remove(node);
						}
					}
				}
			} catch (Exception e) {
				
			}
		}
	}

}
