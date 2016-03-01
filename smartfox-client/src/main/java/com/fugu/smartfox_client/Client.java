package com.fugu.smartfox_client;

import com.fugu.smartfox_client.model.User;
import com.fugu.smartfox_client.presenter.LoginPresenter;
import com.fugu.smartfox_client.view.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
	
	public static Stage primaryStage;
	
	/**
	 * JavaFX initialization
	 */
	@Override
	public void init() {
		
	}
	
	/**
	 * JavaFX start
	 */
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		
		// put MVP together
		User user = new User();
		LoginView loginView = new LoginView(model);
		
		// must set the scene before creating the presenter that uses
		// the scene to listen for the focus change
		Scene scene = new Scene(loginView, 640, 480);
		
		LoginPresenter loginPresenter = new LoginPresenter(user, loginView);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("JavaFX Client");
		primaryStage.show();
		
		// load sameple.fxml layout
//		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/login.fxml"));
//		GridPane root;
//		try {
//			root = (GridPane) loader.load();
//			primaryStage.setTitle("Login");
//			Scene loginScene = new Scene(root, 600, 480);
////			loginScene.getStylesheets().add("css/login.css");
//			primaryStage.setScene(loginScene);
//			primaryStage.show();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Load error message: " + e.getMessage());
//		}		
		
	}
	
	/**
	 * JavaFX stop
	 */
	@Override
	public void stop() {
		this.stop();
	}

	/**
	 * Application starts
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// start JavaFX application
		Application.launch(args);
	}
	
	/**
	 * Get JavaFX primary stage
	 * 
	 * @return Stage
	 */
	public static Stage getStage() {
		return primaryStage;
	}


}
