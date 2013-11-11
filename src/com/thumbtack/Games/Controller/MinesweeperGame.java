package com.thumbtack.Games.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.thumbtack.Games.R;
import com.thumbtack.Games.View.Brick;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: rakeshkumar
 * Date: 11/7/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class MinesweeperGame extends Activity {

	private TextView txtMineCount;
	private TextView txtTimer;
	private ImageButton btnSmile;

	private TableLayout mineField;

	Brick[][] bricks;
	private int blockDimension=40;
	private int blockPadding=2;

	private int numberOfRowsInMineField=8;
	private int numberOfColumnsInMineField =8;
	private int totalNumberOfMines=10;

	private Handler timer=new Handler();
	private int secondsPassed =0;

	private boolean isTimerStarted;
	private boolean areMinesSet;
	private boolean isGameOver;
	private int minesToFind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		txtMineCount=(TextView)findViewById(R.id.tvMineCount);
		txtTimer=(TextView)findViewById(R.id.tvTimer);

		btnSmile=(ImageButton)findViewById(R.id.ibSmiley);
		btnSmile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				endExistingGame();
				startNewGame();
			}
		});

		mineField=(TableLayout)findViewById(R.id.tlMineField);
		showMessage("click smiley to start game");
	}

	private void startNewGame() {
		createMineField();
		showMineField();
		minesToFind=totalNumberOfMines;
		isGameOver=false;
		secondsPassed=0;
	}

	private void showMineField() {
		for(int row=1;row<numberOfRowsInMineField+1;row++) {
			TableRow tableRow=new TableRow(this);
			tableRow.setLayoutParams(new TableRow.LayoutParams((blockDimension+2*blockPadding)*
					numberOfColumnsInMineField ,blockDimension+2*blockPadding));
			for(int col=1;col<numberOfColumnsInMineField+1;col++) {
				bricks[row][col].setLayoutParams(new TableRow.LayoutParams(
						blockDimension + 2 * blockPadding, blockDimension + 2 * blockPadding));
				bricks[row][col].setPadding(blockPadding,blockPadding,blockPadding,blockPadding);
				tableRow.addView(bricks[row][col]);
			}
			mineField.addView(tableRow,new TableLayout.LayoutParams(
					(blockDimension+2*blockPadding)*numberOfColumnsInMineField,blockDimension+2*blockPadding));
		}
	}

	private void endExistingGame() {
		stopTimer();
		txtTimer.setText("000");
		txtMineCount.setText("000");
		btnSmile.setBackgroundResource(R.drawable.smile);
		mineField.removeAllViews();
		isTimerStarted=false;
		areMinesSet=false;
		isGameOver=false;
		minesToFind=0;
	}

	private void createMineField() {
		bricks =new Brick[numberOfRowsInMineField+2][numberOfColumnsInMineField+2];
		for(int row=0;row<numberOfRowsInMineField+2;row++) {
			for(int col=0;col<numberOfColumnsInMineField+2;col++) {
				bricks[row][col]=new Brick(this);
				bricks[row][col].initialize();

				final int finalRow=row;
				final int finalCol=col;
				bricks[row][col].setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(!isTimerStarted) {
							startTimer();
							isTimerStarted=true;
						}

						if(!areMinesSet) {
							areMinesSet=true;
							setMines(finalRow,finalCol);
						}

						recursivelyBreakBricks(finalRow, finalCol);

						if(bricks[finalRow][finalCol].hasMine()) {
							finishGame(finalRow,finalCol);
						}
						if(checkGameWin()) {
							winGame();
						}
					}
				});
			}
		}
	}

	private void winGame() {
		stopTimer();
		isTimerStarted=false;
		isGameOver=false;
		minesToFind=0;
		btnSmile.setBackgroundResource(R.drawable.cool);
		updateMineCountDisplay();
		for(int row=1;row<numberOfRowsInMineField+1;row++) {
			for(int col=1;col<numberOfColumnsInMineField+1;col++) {
				bricks[row][col].setClickable(false);
				if(bricks[row][col].hasMine()) {
					bricks[row][col].setBlockAsDisabled(false);
				}
			}
		}
		showMessage("you won");
	}

	private boolean checkGameWin() {
		for(int row=1;row<numberOfRowsInMineField+1;row++) {
			for(int col=1;col<numberOfColumnsInMineField+1;col++) {
				if(!bricks[row][col].hasMine()&& bricks[row][col].isCovered()) {
					return false;
				}
			}
		}
		return true;
	}

	private void updateMineCountDisplay() {
		String mines=Integer.toString(minesToFind);
		if(minesToFind<10) {
			txtMineCount.setText("00"+mines);
		}else if(minesToFind<50) {
			txtMineCount.setText("0"+mines);
		}else {
			txtMineCount.setText(mines);
		}
	}

	private void showMessage(String msg) {
		Toast toast= Toast.makeText(MinesweeperGame.this,msg,Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER,toast.getXOffset()/2,toast.getYOffset()/2);
		toast.show();
	}

	private void finishGame(int curRow,int curCol) {
		isGameOver=true;
		stopTimer();
		isTimerStarted=false;
		btnSmile.setBackgroundResource(R.drawable.sad);
		for(int row=1;row<numberOfRowsInMineField+1;row++) {
			for(int col=1;col<numberOfColumnsInMineField+1;col++) {
				bricks[row][col].setBlockAsDisabled(false);
				if(bricks[row][col].hasMine()) {
					bricks[row][col].setMineIcon(false);
				}
			}
		}
		bricks[curRow][curCol].triggerMine();
		showMessage("time: "+secondsPassed);
	}

	private void setMines(int curRow,int curCol) {
		Random rand=new Random();
		int mineRow,mineCol;
		for(int row=1;row<totalNumberOfMines;row++) {
			mineRow=rand.nextInt(numberOfColumnsInMineField);
			mineCol=rand.nextInt(numberOfRowsInMineField);
			if((mineRow+1!=curCol)||(mineCol+1!=curRow)) {
				if(bricks[mineCol+1][mineRow+1].hasMine()) {
					row--;
				}
				bricks[mineCol+1][mineRow+1].plantMine();
			}else {
				row--;
			}
		}
		int nearByMineCount;

		for(int row=0;row<numberOfRowsInMineField+2;row++) {
			for(int col=0;col<numberOfColumnsInMineField+2;col++) {
				nearByMineCount=0;
				if((row!=0)&&((row!=numberOfRowsInMineField+1)&&(col!=0 && (col!=numberOfColumnsInMineField+1)))) {
					for(int prevRow=-1;prevRow<2;prevRow++) {
						for(int prevCol=-1;prevCol<2;prevCol++) {
							if(bricks[row+prevRow][col+prevCol].hasMine()) {
								nearByMineCount++;
							}
						}
					}
					bricks[row][col].setNumberOfMinesInSurrounding(nearByMineCount);
				}else {
					bricks[row][col].setNumberOfMinesInSurrounding(8);
					bricks[row][col].breakBrick();
				}
			}
		}
	}

	private void recursivelyBreakBricks(int rowClicked, int colClicked) {
		if(bricks[rowClicked][colClicked].hasMine()) {
			return;
		}
		bricks[rowClicked][colClicked].breakBrick();
		if(bricks[rowClicked][colClicked].getNumberOfMinesInSurrounding()!=0) {
			return;
		}
		for(int row=0;row<3;row++) {
			for(int col=0;col<3;col++) {
				if(bricks[rowClicked+row-1][colClicked+col-1].isCovered()&&rowClicked+row-1>0 &&colClicked+col-1>0
						&&rowClicked+row-1<numberOfRowsInMineField+1 && colClicked+col-1<numberOfColumnsInMineField+1) {
					    recursivelyBreakBricks(rowClicked + row - 1, colClicked + col - 1);
				}
			}
		}
	}

	public void startTimer() {
		if(secondsPassed==0) {
			timer.removeCallbacks(updateTimeElapsed);
			timer.postDelayed(updateTimeElapsed,1000);
		}
	}

	public void stopTimer() {
		timer.removeCallbacks(updateTimeElapsed);
	}

	private Runnable updateTimeElapsed= new Runnable() {
		@Override
		public void run() {
			long currentMilliSeconds=System.currentTimeMillis();
			++secondsPassed;
		 	 String time=Integer.toString(secondsPassed);
			if(secondsPassed<10) {
				txtTimer.setText("00"+time);
			}else if(secondsPassed<100) {
				txtTimer.setText("0"+time);
			}else {
				txtTimer.setText(time);
			}
			timer.postAtTime(this,currentMilliSeconds);
			timer.postDelayed(updateTimeElapsed,1000);
		}
	};
}
