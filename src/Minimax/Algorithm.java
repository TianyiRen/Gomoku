/**
 * Abstract class. Intended to be the parent class of the minmax and genetic
 * algorithm classes.
 * 
 * @author: Tianyi Ren
 */
package Minimax;
import GUI.BoardT;
abstract class Algorithm {
	BoardT board;
	
	/**
	 * Default constructor. Initializes a default board.
	 */
	public Algorithm() {
		board = new BoardT();
	}
	
	/**
	 * Constructor that allows a preinitialized board to be specified.
	 * 
	 * @param board The board object to be used.
	 */
	public Algorithm(BoardT board) {
		this.board = board;
	}
	
	/**
	 * Search for and return best move as an int array with values {x, y},
	 * x being the x coordinate and y the y coordinate of the position to
	 * place a piece on.
	 * 
	 * @return
	 */
	public static int[] bestMove() {
		return null;
	}
}
