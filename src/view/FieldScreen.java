package view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class FieldScreen extends StackPane{
	Rectangle rec;
	Color color;
	int value;
	DiceScreen diceScreen;
	public FieldScreen(Color color, int value) {
		this.value = value;
		this.color = color;
		setMinHeight(50);
		setMinWidth(50);
		setBackground(new Background(new BackgroundFill(color, null, null)));
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
		setPadding(new Insets(5));
		checkNumber(value);
	}
	
	
	public String getColor() {
		return color.toString();
	}
	
	public Color getColorNotString() {
		return color;
	}
	

	public int getValue(){
		return value;
	}
	
	public void checkNumber(int value) {
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
	
	
	
	public void addDice(DiceScreen diceScreen) {
		this.diceScreen =  diceScreen;
		
	}
	
	public DiceScreen getDice() {
		return diceScreen;
	}
	
	public boolean hasDice() {
		if(diceScreen == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void cheatBorder() {
		setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
	}
	
	public void normalBorder() {
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
	}
}
