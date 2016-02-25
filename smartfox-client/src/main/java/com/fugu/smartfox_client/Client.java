package com.fugu.smartfox_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application {
	
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		
		// load sameple.fxml layout
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
		GridPane root = (GridPane) loader.load();
		
		primaryStage.setTitle("Login");
		Scene loginScene = new Scene(root, 400, 400);
		loginScene.getStylesheets().add("login.css");
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}

	
	public static void main(String[] args) {

		launch(args);
	}

}
