package view;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import main.GUI;

import java.util.ArrayList;

public class EndScreen extends BorderPane {

	private GridPane gridpane;
	private ArrayList <StackPane> stackpanes;
	private ArrayList<PlayerLabel> playerLabels;
	private GUI gui;

	public EndScreen(GUI gui) {
		this.gui = gui;
		gridpane = new GridPane();
		stackpanes = new ArrayList<>();
		playerLabels = new ArrayList<>();
		makeGridPane();
		makeTop();	
	}



	// maakt scorebord rondjes + getallen
	private void makeGridPane() {
		int rowNumber = 0;
		int columnNumber = 0;
		for (int i = 0; i < 100; i++) {
			stackpanes.add(new StackPane());
			gridpane.add(stackpanes.get(i), columnNumber, rowNumber);
			
			if (i == 19 || i == 39 || i == 59 || i == 79) {
				rowNumber++;
				columnNumber = -1;
			}
			columnNumber++;
			
			stackpanes.get(i).getChildren().add(new CustomCircle());
			stackpanes.get(i).getChildren().add(new Label("" + (i + 1)));
		}
		
		this.setCenter(gridpane);
		setAlignment(gridpane, Pos.CENTER);
	}
	
	// maakt titel
	private void makeTop() {
		Label text = new Label("Eindscherm scorebord");
		text.setFont(new Font("Arial", 30));
		this.setTop(text);
		setAlignment(text, Pos.CENTER);
	}
	
	// voegt speler label toe
	public void addPlayerLabels(String name, int points, String color) {
		String point = Integer.toString(points);
		playerLabels.add(new PlayerLabel(name, point, color));
	}
	
	// maakt speler/score lijst
	public void makeBottom() {
		HBox bottombar = new HBox();
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();

		
		VBox bottom = new VBox();
		bottom.getChildren().addAll(playerLabels);
		bottom.setAlignment(Pos.CENTER_LEFT);
		
		Button backButton = new Button("Terug naar hoofdmenu");
		backButton.setOnAction(e-> gui.handleHomeMenu());
		backButton.setAlignment(Pos.CENTER_RIGHT);
		
		bottombar.getChildren().addAll(bottom, backButton);
		bottombar.setMinWidth(screen.getWidth());
		this.setBottom(bottombar);

	}
	
	// vernieuwt scherm
	public void clearPlayers() {
		playerLabels.clear();
		stackpanes.clear();
		makeGridPane();
	}
	
	// voegt speler toe
	public void addPlayer(int score, String stringColor) {
		Color color = getColorTranslation(stringColor);
		
		if(score < 101 && score > 0) {
			int sizeHelp = stackpanes.get(score - 1).getChildren().size();
			int size = 30 - ((sizeHelp - 1) * 5);
			stackpanes.get(score - 1).getChildren().add(new PlayerCircle(color, size));
		}
	}

	// verandert kleur uit database naar javakleur
	static Color getColorTranslation(String color) {
		switch(color) {
		  case "blauw":
			 return Color.BLUE;
		  case "geel":
			 return Color.YELLOW;
		  case "rood":
			 return Color.RED;
		  case "paars":
			  return Color.PURPLE;
		  case "groen":
			  return Color.GREEN;
		  default:
		    return Color.BLACK;
		}
	}


	private class CustomCircle extends Circle{
		private CustomCircle() {
			setRadius(35);
			String[] colors = {"GREENYELLOW", "YELLOW",
					"SADDLEBROWN", "DEEPSKYBLUE", "LAVENDER", "BISQUE", "FORESTGREEN"};
			int random = (int)(Math.random() * colors.length);
			setFill(Color.valueOf(colors[random]));
			
		}
	}
	
	private class PlayerCircle extends Circle {
		private PlayerCircle(Color playercolor, int radius) {
			setFill(playercolor);
	        setRadius(radius);
	        setStroke(Color.BLACK);
	        setStrokeWidth(0.5);
	       
		}
	}
	
	private class PlayerLabel extends Label {
		private PlayerLabel(String name, String points, String stringColor) {
			setText(name + " heeft " + points + " punten!!!");
			setTextFill(getColorTranslation(stringColor));
			setFont(new Font(22));
			
		}
	}
}
