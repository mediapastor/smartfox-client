package com.fugu.smartfox_client;

import com.fugu.smartfox_client.handler.ConnectionHandler;
import com.fugu.smartfox_client.handler.LoginHandler;
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


		
	}
	
	/**
	 * JavaFX initialization
	 */
	@Override
	public void init() {
		// TBD
	}
	
	/**
	 * JavaFX start
	 */
	@Override
	public void start(Stage primaryStage) {
		
		ConnectionHandler connectionHandler = new ConnectionHandler(user);

		sfs.addEventListener(SFSEvent.CONNECTION, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_LOST, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_RETRY, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_RESUME, connectionHandler);
		sfs.addEventListener(SFSEvent.HANDSHAKE, connectionHandler);
		sfs.addEventListener(SFSEvent.SOCKET_ERROR, connectionHandler);
		
		this.user = new User();

		
		
		this.primaryStage = primaryStage;
		
		// put MVP together
		System.out.println("instantiating user");
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
