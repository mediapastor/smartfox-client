package com.fugu.smartfox_client;

import com.fugu.smartfox_client.handler.ConnectionHandler;
import com.fugu.smartfox_client.handler.ExtensionHandler;
import com.fugu.smartfox_client.handler.LoginHandler;
import com.fugu.smartfox_client.handler.RoomHandler;
import com.fugu.smartfox_client.model.User;
import com.fugu.smartfox_client.presenter.LoginPresenter;
import com.fugu.smartfox_client.view.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;
import sfs2x.client.core.SFSEvent;

public class Client extends Application {
	
	public static Stage primaryStage;
	private static SmartFox sfs = new SmartFox(true);	
	private static User user;
	
	public Client() {

		System.out.println("@@ Constructor @@");
		
		this.user = new User();
		
		ConnectionHandler connectionHandler = new ConnectionHandler(user);
		LoginHandler loginHandler = new LoginHandler(user);
		RoomHandler roomHandler = new RoomHandler();
		ExtensionHandler extensionHandler = new ExtensionHandler();

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
	 * JavaFX start
	 */
	@Override
	public void start(Stage primaryStage) {
		
		System.out.println("@@ JavaFX Start @@");
		
		this.primaryStage = primaryStage;
		
		// put MVP together
		LoginView loginView = new LoginView(user);
		
		// must set the scene before creating the presenter that uses
		// the scene to listen for the focus change
		Scene scene = new Scene(loginView, 640, 480);
		
		LoginPresenter loginPresenter = new LoginPresenter(user, loginView);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("JavaFX Client");
		primaryStage.show();
	
		
	}
	
	/**
	 * JavaFX stop
	 */
//	@Override
//	public void stop() {
//		this.stop();
//	}

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
}
