package model;

import java.util.Random;

/* This class must extend Game */
public class ClearCellGame extends Game  
{
	private int score, strategy;
	Random rnd;
	
	public ClearCellGame(int rowIndex, int colIndex, Random random, int strategy)
	{
		super(rowIndex, colIndex);
		rnd = random;
		this.strategy = strategy;
		score = 0;
	}
	public boolean isGameOver()
	{
		return emptyRow(board.length-1)?false:true;
	}
	public int getScore()
	{
		return score;
	}
	public void nextAnimationStep()
	{
		if(this.isGameOver() == false)
		{
			for(int row = this.getMaxRows()-2; row >= 0; row--)
			{
				for(int col = 0; col < getMaxCols(); col++)
				{
					setBoardCell(row+1, col, this.getBoardCell(row, col));
					
					if(row == 0)
						setBoardCell(row, col, BoardCell.getNonEmptyRandomBoardCell(rnd));			
				}
			}
		}
	}
	public void processCell(int rowIndex, int colIndex)
	{
		int upCell = rowIndex-1;
		int downCell = rowIndex+1;
		int leftCell = colIndex-1;
		int rightCell = colIndex+1;
		
		BoardCell target = board[rowIndex][colIndex];
			setBoardCell(rowIndex, colIndex, BoardCell.EMPTY);
		
	if(target!=BoardCell.EMPTY)
	{	
		if(upCell>=0) // handle up straight, up left/right diagonals
		{
			int upStraight=upCell; // up straight
			while(board[upStraight][colIndex]==target && upStraight>=0)
			{
				this.setBoardCell(upStraight, colIndex, BoardCell.EMPTY);
				score++;
				upStraight--;
				if(upStraight<0)
					break;
				
			}
			
			int leftUpCheck=leftCell;
			int upLeftDiagonal=upCell; //upper left diagonal
			if(leftCell>=0)
			{
				while(board[upLeftDiagonal][leftUpCheck]==target && upLeftDiagonal>=0 && leftUpCheck>=0)
				{
					this.setBoardCell(upLeftDiagonal, leftUpCheck, BoardCell.EMPTY);
					score++;
					upLeftDiagonal--;
					leftUpCheck--;
					if(upLeftDiagonal<0 || leftUpCheck<0)
						break;
				}
			}
			
			int rightUpCheck=rightCell;
			int upRightDiagonal=upCell; //upper right diagonal
			if(rightCell<=this.getMaxCols()-1)
			{
				while(board[upRightDiagonal][rightUpCheck]==target &&
						upRightDiagonal>=0 && rightUpCheck<=this.getMaxCols()-1)
				{
					this.setBoardCell(upRightDiagonal, rightUpCheck, BoardCell.EMPTY);
					score++;
					upRightDiagonal--;
					rightUpCheck++;
					if(upRightDiagonal<0 || rightUpCheck>=this.getMaxCols())
						break;
				}
			}	
		} //upper part ends
		
		if(downCell<=this.getMaxRows()-1) //down straight, left/right diagonals
		{
			int downCheck=downCell; //down straight
			while(board[downCheck][colIndex]==target && downCheck<=this.getMaxRows()-1)
			{
				this.setBoardCell(downCheck, colIndex, BoardCell.EMPTY);
				score++;
				downCheck++;
				if(downCheck>=this.getMaxCols())
					break;		
			}
			
			int leftDownCheck = leftCell;
			int downLeftDiagonal = downCell; //down left straight
			if(leftCell>=0)
			{
				while(board[downLeftDiagonal][leftDownCheck]==target 
						&& downLeftDiagonal<=this.getMaxCols()-1 && leftDownCheck>=0)
				{
					this.setBoardCell(downLeftDiagonal, leftDownCheck, BoardCell.EMPTY);
					score++;
					downLeftDiagonal++;
					leftDownCheck--;
					if(downLeftDiagonal>=this.getMaxCols() || leftDownCheck<0)
						break;		
				}
			}
			
			int rightDownCheck = rightCell;
			int downRightDaigonal = downCell; //down right straight
			if(rightCell<=this.getMaxCols()-1)
			{
				while(board[downRightDaigonal][rightDownCheck]==target 
						&& downRightDaigonal<=this.getMaxCols()-1 && 
						rightDownCheck<=this.getMaxCols()-1)
				{
					this.setBoardCell(downRightDaigonal, rightDownCheck, BoardCell.EMPTY);
					score++;
					downRightDaigonal++;
					rightDownCheck++;
					if(downRightDaigonal>=this.getMaxCols() || rightDownCheck>=this.getMaxCols())
						break;		
				}
			}
		} // down part end
		
		if(leftCell>=0) //left srtaight 
		{
			int leftStraightCheck=leftCell;

			while(board[rowIndex][leftStraightCheck]==target && leftStraightCheck>=0)
			{
				this.setBoardCell(rowIndex, leftStraightCheck, BoardCell.EMPTY);
				score++;
				leftStraightCheck--;
				if(leftStraightCheck<0)
					break;	
			}
		}
		if(rightCell<=this.getMaxCols()-1) //right straight
		{
			int rightStraightCheck=rightCell;
			
			while(board[rowIndex][rightStraightCheck]==target 
					&& rightStraightCheck<=this.getMaxCols()-1)
			{
				this.setBoardCell(rowIndex, rightStraightCheck, BoardCell.EMPTY);
				score++;
				rightStraightCheck++;
				if(rightStraightCheck>=this.getMaxCols())
					break;	
			}
		}
	}
		//eliminate the row that is empty
		for(int row = getMaxRows()-2; row > 0; row--)
		{
			if(emptyRow(row) == true)
			{
				for(int eliminate = row; eliminate < getMaxRows()-1; eliminate++)
				{
					for(int col = 0; col < getMaxCols(); col++)
					{
						board[eliminate][col] = board[eliminate+1][col];
						board[eliminate+1][col] = BoardCell.EMPTY;
					}
				}
			}
}
	}
	private boolean emptyRow(int row) // method to check the empty row
	{
		boolean rowEmpty = false;
		int emptyCol = 0;
		for(int col = 0; col < getMaxCols(); col++)
			if(getBoardCell(row, col) == BoardCell.EMPTY)
				emptyCol++;
		
		if(emptyCol == getMaxCols())
			rowEmpty = true;
			
		return rowEmpty;
	}
}
