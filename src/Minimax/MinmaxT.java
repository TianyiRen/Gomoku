/**
 * A three depth Minimax algorithm
 *
 * @author Tianyi Ren
 */
package Minimax;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import GUI.BoardT;
public class MinmaxT extends Algorithm {
	static int depth;
	public static int player;
	
	public MinmaxT() {
		super();
		depth = 3;
	}
	
	/**
	 * Returns the best move given a board state
	 * 
	 * @return position
	 */
	public static Point bestMove(BoardT board) {
		player = board.currentPlayer;
		board.updatePossibleMoves();
		PointAndValue result =  miniMax(1, board);
		return result.p;
	}
	
	/**
	 * Sets the depth of the minimax decision tree 
	 * 
	 * @param depth
	 */
	public void setDepth(int depth) {
		this.depth = depth; 
	}
	
	/**
	 * Find the best move 
	 * 
	 * @param board
	 * @return position and the state's heuristic
	 */
		
	public static PointAndValue findBestMove(BoardT b)
	{
	    //b.updatePossibleMoves();
	    int player = b.currentPlayer;
	    int bestValue = Integer.MIN_VALUE;
	    Point bestMove = null;
	    if(b.possibleMoves.isEmpty())
	        return new PointAndValue(new Point(9,9),0);
	    HashSet<Point> set = new HashSet<Point>(b.possibleMoves);
	    for(Point p : set)
	    {
	        //if(b.board[p.x][p.y]==0)
	        {
	    	b.move(p.x, p.y);
	        int value = b.evaluate(player) - b.evaluate(-player);
	        if (value > bestValue)
	        {
	            bestValue = value;
	            bestMove = p;
	        }
	        b.undo(p);
	        }
	        
	    }
	    return new PointAndValue(bestMove,bestValue);
	}
	
	/**
	 * Minmax algorithm  
	 * 
	 * @param board, depth
	 * @return position and the state's heuristic
	 */
	public static PointAndValue miniMax(int d, BoardT b)
	{
		if(d >0)
		{
			int bestValue = player==b.currentPlayer ? Integer.MAX_VALUE:Integer.MIN_VALUE ;
			Point bestMove = null;
			HashSet<Point> set = new HashSet<Point>(b.possibleMoves);
			if (b.currentPlayer != player) {
				
				for (Point move : set) {
					{
					b.move(move.x, move.y);
					PointAndValue result = miniMax(d-1,b);
					
					if (result.value > bestValue) {
						bestMove = move;
						bestValue = result.value;
					}
					b.undo(move);
					}
				}
				
			} else {//if(b.currentPlayer == -player) {
				
				for (Point move : set) {
					//if(b.board[move.x][move.y]==0)
					{
					b.move(move.x, move.y);
					PointAndValue result = miniMax(d-1,b);
					
					if (result.value < bestValue) {
						bestMove = move;
						bestValue = result.value;
					}
					b.undo(move);
					}
				}
			}
			return new PointAndValue(bestMove,bestValue);
		}
		else
		{
			//Point r = findBestMove(b);
			return findBestMove(b);
		}
	}
	/**
	 * A class that represents the best move with its position and the best heuristic 
	 * 
	 */
	
	public static class PointAndValue
	{
		public Point p;
		public int value;
		public PointAndValue(Point point, int v)
		{
			this.p = point;
			this.value = v;
		}
	}
	
	
}



