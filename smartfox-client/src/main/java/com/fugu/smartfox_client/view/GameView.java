package com.fugu.smartfox_client.view;

import com.fugu.smartfox_client.model.Game;
import com.fugu.smartfox_client.model.User;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameView extends GridPane {
	
	private final Game model;
	
	// Label
	public Label guessCountLbl = new Label("Guess Count");
	public Label wordToShowLbl = new Label("Word");
	public Label guessesHitLbl = new Label("Hit"); // TBD
	public Label guessesMissedLbl = new Label("Missed"); // TBD
	
	// Text
	public Text title = new Text("Enter character");
	public Text guessCount = new Text("");
	public Text wordToShow = new Text("");
	public Text errorMessage = new Text("");
	
	// TextField
	public TextField guessFld = new TextField();
	
	// Button
	public Button guessBtn = new Button("Guess");
	
	public GameView(Game model) {
		this.model = model;
		layoutForm();
		bindFieldsToModel();
	}
	
	private void layoutForm() {
		this.setHgap(5);
		this.setVgap(5);
		this.setAlignment(Pos.CENTER);
		
		// add title and input fields
		this.add(title, 0, 0, 2, 1);
		this.add(guessCountLbl, 0, 1);
		this.add(guessCount, 1, 1);
		this.add(wordToShowLbl, 0, 2);
		this.add(wordToShow, 1, 2);
		this.add(guessFld, 0, 3, 2, 1);
		
		// add button and set it to max width
		this.add(guessBtn, 0, 4, 2, 1);
		guessBtn.setMaxWidth(Double.MAX_VALUE);
		
		
		// add error message and set it to max width
		this.add(errorMessage, 0, 4, 2, 1);
		errorMessage.maxWidth(Double.MAX_VALUE);
	}
	
	public void bindFieldsToModel() {
         guessFld.textProperty().bindBidirectional(model.guessProperty());

	}
}
