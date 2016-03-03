package com.fugu.smartfox_client.model;

import java.util.ArrayList;
import java.util.List;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Game implements SerializableSFSType {

	// private List<String> word;
	
	private StringProperty wordToShow = new SimpleStringProperty(this, "wordToShow", "no word");;
	private StringProperty guess = new SimpleStringProperty(this, "guess", null);;
	private IntegerProperty guessCount = new SimpleIntegerProperty(this, "guessCount");
	// private Integer guessCountLimit;
	private BooleanProperty isOver = new SimpleBooleanProperty(this, "isOver");
	private BooleanProperty isWon = new SimpleBooleanProperty(this, "isWon");;
	private ListProperty<String> guessesHit = new SimpleListProperty<String>(this, "guessesHit", null);
	private ListProperty<String> guessesMissed = new SimpleListProperty<String>(this, "guessesMissed", null);;

	public Game() {
		wordToShow.set("");
		guess.set("");
		guessCount.set(0);
		isOver.set(false);
		isWon.set(false);
//		guessesHit.setAll(new ArrayList<>());
//		guessesMissed.setAll(new ArrayList<>());
//		System.out.println("Game constructor : " + guessesHit.get().toString());
	}
	
	/**
	 * @return the wordToShow
	 */
	public String getWordToShow() {
		return wordToShow.get();
	}

	/**
	 * @param wordToShow the wordToShow to set
	 */
	public void setWordToShow(String wordToShow) {
		wordToShowProperty().set(wordToShow);
	}
	
	public StringProperty wordToShowProperty() {
		return wordToShow;
	}

	/**
	 * @return the guess
	 */
	public String getGuess() {
		return guess.get();
	}

	/**
	 * @param guess the guess to set
	 */
	public void setGuess(String guess) {
		guessProperty().set(guess);
	}
	
	public StringProperty guessProperty() {
		return guess;
	}

	/**
	 * Get count
	 * 
	 * @return count of guess
	 */
	public int getGuessCount() {
		return guessCount.get();
	}
	
	/**
	 * @param guessCount the guessCount to set
	 */
	public void setGuessCount(int guessCount) {
		guessCountProperty().set(guessCount);
	}
	
	public IntegerProperty guessCountProperty() {
		return guessCount;
	}

	/**
	 * 
	 * @return true if player won the game 
	 */
	public boolean getIsWon() {
		return isWon.get();
	}
	
	/**
	 * @param isWon the isWon to set
	 */
	public void setWon(boolean isWon) {
		isWonProperty().set(isWon);
	}
	
	public BooleanProperty isWonProperty() {
		return isWon;
	}
	
	/**
	 * 
	 * @return true if game is over
	 */
	public boolean getIsOver() {
					
		return isOver.get();
	}

	/**
	 * @param isOver the isOver to set
	 */
	public void setOver(boolean isOver) {
		isOverProperty().set(isOver);;
	}
	
	public BooleanProperty isOverProperty() {
		return isOver;
	}
	
	/**
	 * @return the guessesHit
	 */
	public List<String> getGuessesHit() {
		return guessesHit.get();
	}

	/**
	 * @param guessesHit the guessesHit to set
	 */
	public void setGuessesHit(List<String> guessesHit) {
		guessesHitProperty().setAll(guessesHit);
	}
	
	public ListProperty<String> guessesHitProperty() {
		return guessesHit;
	} 

	/**
	 * @return the guessesMissed
	 */
	public List<String> getGuessesMissed() {
		return guessesMissed.get();
	}

	/**
	 * @param guessesMissed the guessesMissed to set
	 */
	public void setGuessesMissed(List<String> guessesMissed) {
		guessesMissedProperty().setAll(guessesMissed);
	}
	
	public ListProperty<String> guessesMissedProperty() {
		return guessesMissed;
	}
	
	/**
	 * Convert object to SFSObject
	 * 
	 * @return SFSObject 
	 */
	public SFSObject toSFSObject() {
		
		ISFSObject sfso = new SFSObject();
		
		sfso.putUtfString("wordToShow", getWordToShow());
		sfso.putUtfString("guess", getGuess());
//		sfso.putUtfStringArray("guessesHit", getGuessesHit());
//		sfso.putUtfStringArray("guessesMissed", getGuessesMissed());
		sfso.putInt("guessCount", getGuessCount());
		sfso.putBool("isOver", getIsOver());
		sfso.putBool("isWon", getIsWon());
		
		return SFSObject.newFromObject(this);
	}
	
	/**
	 * Convert SFSObject to object
	 * 
	 * @param sfsObject
	 * @return Game
	 */
	public static Game fromSFSObject(ISFSObject sfsObject) {
		
		Game game = new Game();
		
		game.setWordToShow(sfsObject.getUtfString("wordToShow"));
		game.setGuess(sfsObject.getUtfString("guess"));
		game.setGuessesHit(new ArrayList<String>(sfsObject.getUtfStringArray("guessesHit")));
		game.setGuessesMissed(new ArrayList<String>(sfsObject.getUtfStringArray("guessesHit")));
		game.setGuessCount(sfsObject.getInt("guessCount"));
		game.setOver(sfsObject.getBool("isOver"));
		game.setWon(sfsObject.getBool("isWon"));
		
		return game;
	}
	
}
