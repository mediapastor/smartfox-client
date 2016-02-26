package com.fugu.smartfox_client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application {
	
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		
		Client.primaryStage = primaryStage;
		
		// load sameple.fxml layout
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/login.fxml"));
		GridPane root;
		try {
			root = (GridPane) loader.load();
			primaryStage.setTitle("Login");
			Scene loginScene = new Scene(root, 600, 480);
//			loginScene.getStylesheets().add("css/login.css");
			primaryStage.setScene(loginScene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Load error message: " + e.getMessage());
		}		

	}

	
	public static void main(String[] args) {

		launch(args);
	}
	


}
