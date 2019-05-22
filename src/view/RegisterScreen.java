package view;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.GUI;

public class RegisterScreen extends VBox {
	private TextField username;
	private PasswordField password;
	private GUI myGUI;
	RegisterScreen(GUI mygui) {
		this.myGUI = mygui;
		addFields();
		addButton();
		setAlignment(Pos.CENTER_RIGHT);
	}
	
	private void addButton() {
		Button register = new Button("Register");
		register.setMaxWidth(150);
		getChildren().add(register);
		register.setOnMouseClicked(e -> handleClick());
	}
	
	private void addFields() {
		username = new TextField();
		username.setPromptText("Username");
		password = new PasswordField();
		password.setPromptText("Password");
		getChildren().addAll(username,password);
	}
	
	private void handleClick() {
		myGUI.handleregister(username, password);
	}
	
	public void setGreenBorder(TextField username, PasswordField password) {
		username.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		password.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
	}
	public void setRedBorder(TextField username, PasswordField password) {
		username.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		password.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
	}
}
