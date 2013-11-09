package com.thumbtack.Games.View;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import com.thumbtack.Games.Model.Block;
import com.thumbtack.Games.R;

/**
 * Created with IntelliJ IDEA.
 * User: rakeshkumar
 * Date: 11/3/13
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Brick extends Button {
	Block block;

	public Brick(Context context) {
		super(context);
		block=new Block();
	}

	public void initialize() {
		block.initialize();
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

	public void breakBrick() {
		if(!block.isCovered()) {
			return;
		}
		setBlockAsDisabled(false);
		block.setIsCovered(false);
		if(block.hasMine()) {
			setMineIcon(false);
		}else {
			setNearbyMines(block.getNumberOfMinesInSurrounding());
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

	public void triggerMine() {
		setMineIcon(true);
		this.setTextColor(Color.RED);
	}

	public boolean hasMine() {
		return block.hasMine();
	}

	public boolean isCovered() {
		return block.isCovered();
	}

	public void plantMine() {
		block.setIsMined(true);
	}

	public void setNumberOfMinesInSurrounding(int number) {
		block.setNumberOfMinesInSurrounding(number);
	}

	public int getNumberOfMinesInSurrounding() {
		return block.getNumberOfMinesInSurrounding();
	}


}
