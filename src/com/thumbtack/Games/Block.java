package com.thumbtack.Games;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: rakeshkumar
 * Date: 11/3/13
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Block extends Button {
	private boolean  isCovered;
	private boolean isMined;
	private boolean isClickable;
	private int numberOfMinesInSurrounding;

	public Block(Context context) {
		super(context);
	}

	public Block(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Block(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initialize() {
		isCovered=true;
		isClickable=true;
		isMined=false;
		numberOfMinesInSurrounding=0;
		this.setBackgroundResource(R.drawable.square_blue);
	}

	private void setNearbyMines(int number) {
		this.setBackgroundResource(R.drawable.square_grey);
		updateNumber(number);
	}

	public void setMineIcon(boolean enabled) {
		this.setText("M");
		if(!enabled) {
			this.setBackgroundResource(R.drawable.square_grey);
			this.setTextColor(Color.RED);
		}else {
			this.setTextColor(Color.BLACK);
		}
	}

	public void setBlockAsDisabled(boolean enabled) {
		if(!enabled) {
			this.setBackgroundResource(R.drawable.square_grey);
		}else {
			this.setBackgroundResource(R.drawable.square_blue);
		}
	}

	public void openBlock() {
		if(!isCovered) {
			return;
		}
		setBlockAsDisabled(false);
		isCovered=false;
		if(hasMine()) {
			setMineIcon(false);
		}else {
			setNearbyMines(numberOfMinesInSurrounding);
		}

	}

	private void updateNumber(int number) {
		if (number!=0) {
			this.setText(Integer.toString(number));

			switch (number) {
				case 1:
					this.setTextColor(Color.BLUE);
					break;
				case 2:
					this.setTextColor(Color.rgb(0,100,0));
					break;
				case 3:
					this.setTextColor(Color.RED);
					break;
				case 4:
					this.setTextColor(Color.rgb(85,26,139));
					break;
				case 5:
					this.setTextColor(Color.rgb(139,28,98));
					break;
				case 6:
					this.setTextColor(Color.rgb(238,173,14));
					break;
				case 7:
					this.setTextColor(Color.rgb(71,71,71));
					break;
				case 8:
					this.setTextColor(Color.rgb(205,205,0));
					break;
			}

		}
	}

	public void plantMine() {
		isMined=true;
	}

	public void triggerMine() {
		setMineIcon(true);
		this.setTextColor(Color.RED);
	}

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

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean clickable) {
		isClickable=clickable;
	}


}
