package com.thumbtack.Games.Model;

/**
 * Created with IntelliJ IDEA.
 * User: rakeshkumar
 * Date: 11/7/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */


//this is the model class which can be persisted on permanent store.
public class Block {
	private boolean  isCovered;
	private boolean isMined;
	private int numberOfMinesInSurrounding;

	public boolean isCovered() {
		return isCovered;
	}

	public boolean hasMine() {
		return isMined;
	}

	public void setNumberOfMinesInSurrounding(int number) {
		numberOfMinesInSurrounding=number;
	}

	public int getNumberOfMinesInSurrounding() {
		return numberOfMinesInSurrounding;
	}

	public void initialize() {
		isCovered=true;
		isMined=false;
		numberOfMinesInSurrounding=0;

	}

	public void setIsCovered(boolean covered) {
		isCovered = covered;
	}

	public void setIsMined(boolean mined) {
		isMined = mined;
	}
}
