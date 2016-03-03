package com.fugu.smartfox_client.presenter;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.model.Game;
import com.fugu.smartfox_client.model.User;
import com.fugu.smartfox_client.util.Util;
import com.fugu.smartfox_client.view.GameView;
import com.fugu.smartfox_client.view.LoginView;
import com.smartfoxserver.v2.entities.data.ISFSObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;
import sfs2x.client.requests.ExtensionRequest;

public class GamePresenter {
	
	private final Game game;
	private final GameView view;
	private final SmartFox sfs = Client.getSmartFox();
	

	public GamePresenter(Game game, GameView view) {
		this.game = game;
		this.view = view;
		attachEvents();
	}
	
	public void attachEvents() {
		view.guessBtn.setOnAction(e -> {
			guessAction(e);
			System.out.println("Sending extension request to server... event name :" + e.getEventType().getName());
		});
		
	}
	
	private void guessAction(ActionEvent e) {
		
		ISFSObject sfso = game.toSFSObject();
		sfso.iterator().forEachRemaining( ele -> System.out.println(ele.toString()));
		Platform.runLater(() ->sfs.send(new ExtensionRequest("game.guess", sfso)));
		
	}
	
	private void error() {
		view.errorMessage.setText("Wrong username or password");
	}
	
	private void redirectToLobby() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/lobby.fxml"));
		
		// go to lobby controller
		try {
			GridPane root = (GridPane) loader.load();
			Stage mainStage = Client.primaryStage;
			mainStage.getScene().setRoot(root); //we dont need to change whole scene, only set new root.
		} catch (Exception e) {
			Util.logException(e);;
		}
		
		System.out.println("redirected to lobby UI......");
	}
}
