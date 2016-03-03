package com.fugu.smartfox_client;

import com.fugu.smartfox_client.handler.ConnectionHandler;
import com.fugu.smartfox_client.handler.ExtensionHandler;
import com.fugu.smartfox_client.handler.LoginHandler;
import com.fugu.smartfox_client.handler.RoomHandler;
import com.fugu.smartfox_client.model.Game;
import com.fugu.smartfox_client.model.User;
import com.fugu.smartfox_client.presenter.GamePresenter;
import com.fugu.smartfox_client.presenter.LoginPresenter;
import com.fugu.smartfox_client.view.GameView;
import com.fugu.smartfox_client.view.LoginView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;
import sfs2x.client.core.SFSEvent;

public class Client extends Application {
	
	public static Stage primaryStage;
	private static SmartFox sfs = new SmartFox(true);	
	private static User user;
	private static Game game;
	private static Scene loginScene;
	private static Scene gameScene;
	
	private ConnectionHandler connectionHandler;
	private LoginHandler loginHandler;
	private RoomHandler roomHandler;
	private ExtensionHandler extensionHandler;
	
	public Client() {

		System.out.println("@@ Constructor @@");
		
		user = new User();
		game = new Game();
		
		connectionHandler = new ConnectionHandler(user);
		loginHandler = new LoginHandler(user);
		roomHandler = new RoomHandler();
		extensionHandler = new ExtensionHandler();

		sfs.addEventListener(SFSEvent.CONNECTION, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_LOST, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_RETRY, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_RESUME, connectionHandler);
		sfs.addEventListener(SFSEvent.HANDSHAKE, connectionHandler);
		sfs.addEventListener(SFSEvent.SOCKET_ERROR, connectionHandler);
		sfs.addEventListener(SFSEvent.LOGIN, loginHandler);
		sfs.addEventListener(SFSEvent.LOGIN_ERROR, loginHandler);
		sfs.addEventListener(SFSEvent.ROOM_JOIN, roomHandler);
		sfs.addEventListener(SFSEvent.ROOM_JOIN_ERROR, roomHandler);
		sfs.addEventListener(SFSEvent.ROOM_ADD, roomHandler);
		sfs.addEventListener(SFSEvent.ROOM_CREATION_ERROR, roomHandler);
		sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, extensionHandler);
		
	
	}
	
	/**
	 * JavaFX initialization
	 */
	@Override
	public void init() {
		System.out.println("@@ JavaFX Initiate @@");
		// TBD
	}
	
	/**
	 * JavaFX client start
	 */
	@Override
	public void start(Stage primaryStage) {
		
		System.out.println("@@ JavaFX Start @@");
		
		this.primaryStage = primaryStage;
		
		// put MVP together
		LoginView loginView = new LoginView(user);
		GameView gameView = new GameView(game);
		
		// must set the scene before creating the presenter that uses
		// the scene to listen for the focus change
		loginScene = new Scene(loginView, 640, 480);
		gameScene = new Scene(gameView, 800, 640);
		
		LoginPresenter loginPresenter = new LoginPresenter(user, loginView);
		GamePresenter gamePresenter = new GamePresenter(game, gameView);
		
		primaryStage.setScene(loginScene);
		primaryStage.setTitle("JavaFX Client");
		primaryStage.show();
	
		
	}
	
	/**
	 * JavaFX client stop
	 */
	@Override
	public void stop() {
		
		System.out.println("Stopping JavaFX");
		
		// remove SmartFox listeners
		Platform.runLater(() -> {
			sfs.removeEventListener(SFSEvent.CONNECTION, connectionHandler);
			sfs.removeEventListener(SFSEvent.CONNECTION_LOST, connectionHandler);
			sfs.removeEventListener(SFSEvent.CONNECTION_RETRY, connectionHandler);
			sfs.removeEventListener(SFSEvent.CONNECTION_RESUME, connectionHandler);
			sfs.removeEventListener(SFSEvent.HANDSHAKE, connectionHandler);
			sfs.removeEventListener(SFSEvent.SOCKET_ERROR, connectionHandler);
			sfs.removeEventListener(SFSEvent.LOGIN, loginHandler);
			sfs.removeEventListener(SFSEvent.LOGIN_ERROR, loginHandler);
			sfs.removeEventListener(SFSEvent.ROOM_JOIN, roomHandler);
			sfs.removeEventListener(SFSEvent.ROOM_JOIN_ERROR, roomHandler);
			sfs.removeEventListener(SFSEvent.ROOM_ADD, roomHandler);
			sfs.removeEventListener(SFSEvent.ROOM_CREATION_ERROR, roomHandler);
			sfs.removeEventListener(SFSEvent.EXTENSION_RESPONSE, extensionHandler);
		});
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

	public static SmartFox getSmartFox() {
		return sfs;
	}
	
	public static User getUser() {
		return user;
	}
	
	public static void setScene(String scene) {
		if (scene == "game") {
			primaryStage.setScene(gameScene);
		} else if (scene == "login") {
			primaryStage.setScene(loginScene);
		} else {
			throw new IllegalArgumentException("Wrong secene parameter " + scene);
		}
		primaryStage.setTitle("JavaFX Client");
		primaryStage.show();
	}
}
