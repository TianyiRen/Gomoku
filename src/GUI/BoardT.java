/**
 * A board class that represents the current board state, current player, 
 * possible moves of the current state, etc. which can be passed to Minimax and Genetics algorithm 
 * to select the best move with the best heuristic
 * 
 *@author Tianyi Ren and Shuang
 */
package GUI;

import java.awt.Point;
import java.util.HashSet;

public class BoardT {

	public int[][] board;
	int rows, cols;
	public int currentPlayer = 1;
	boolean win = false;
	
	public HashSet<Point> possibleMoves = new HashSet<Point>();
	public boolean black = true;
	
	/**
	 * Constructors. Initializes a default (19x19) board.
	 */
	
	public BoardT(int x, int y, int player) {
		board = new int[x][y];
		this.rows = x;
		this.cols = y;
		this.currentPlayer = player;
		if(player == -1)
			this.black = false;
		for(int i = 0; i < x; i ++) {
			for(int j = 0; j < y; j ++) {
				board[i][j] = 0;
			}
		}
	}
	public BoardT() {
		board = new int[19][19];
		rows = 19;
		cols = 19;
		for(int i = 0; i < 19; i ++) {
			for(int j = 0; j < 19; j ++) {
				board[i][j] = 0;
			}
		}
	}
	
	
	
	public BoardT(int[][] board)
	{
		this.board = board.clone();
		this.rows = 19;
		this.cols = 19;
	}

	public BoardT(BoardT board) {

		this.rows = board.rows;
		this.cols = board.cols;
		this.board = new int[rows][cols];
		
		for(int i = 0; i < rows; i ++) {
			for(int j = 0; j < cols; j ++) {
				this.board[i][j] = board.get(i, j);
			}
		}
		this.black = board.black;
		this.currentPlayer = board.currentPlayer;
		
	}
	/**
	 * Getter method for a position on the board.
	 * 
	 * @return value 1: black, value 0: empty, value -1: white
	 */
	public int get(int x, int y) {
		return board[x][y];
	}
	
	/**
	 * 
	 * 
	 * @param board
	 */
	public boolean move(int x, int y) {
		if(board[x][y] != 0) {
			System.out.println("Not Empty");
			return false;	
		}
		
		board[x][y] = (black) ? 1 : -1;
		black = !black;
		this.currentPlayer = black ? 1: -1;
		this.updatePossibleMoves();
		return true;
	}
	
	public void move(Point p)
	{
	    if(board[p.x][p.y] != 0) {
            return;
        }
        board[p.x][p.y] = (black) ? 1 : -1;
        black = !black;
        this.currentPlayer = black ? 1: -1;
        
        //Point current = new Point(i,j);
        Point neighbour = new Point();
        for(int direction=1;direction<5;direction++)
        {
            if(this.getNeighbour(p, neighbour, direction)==0 && board[neighbour.x][neighbour.y]==0)
            {
                this.possibleMoves.add(new Point(neighbour));
                //System.out.println(neighbour);
            }
            if(this.getNeighbour(p, neighbour, -direction)==0 && board[neighbour.x][neighbour.y]==0)
            {
                this.possibleMoves.add(new Point(neighbour));
                //this.possibleMoves.add(new Point(neighbour));System.out.println(neighbour);
            }
        }
	}
	/**
	 * Undo the latest move.
	 * 
	 * @param latest position
	 */
	public void undo(Point p)
	{
	    board[p.x][p.y]=0;
	    black = !black;
	    this.currentPlayer = black ? 1: -1;
	    this.updatePossibleMoves();
	}

	
	/**
	 * Compute the heuristic given a player
	 * 
	 * @param player
	 * @return heuristic value
	 */

	public int evaluate(int player)
	{
	    int score = 0;
	    Point current = new Point();
	    Point next = new Point();
	    
	    //for each point
	    for(int m =0;m<19;m++)
	    {
	        for(int n=0;n<19;n++)
	        {
	            if(this.board[m][n]==player)
	            {
	            //for each direction
	                int oneScore=1;
    	            for(int direction=1;direction<5;direction++)
    	            {
    	                int count = 1;
    	                int consecu = 1;
    	                boolean consecutive=true;
    	                int space =1;
    	                boolean blocked = false;
    	                
    	                for(int i=0; i<2; i++)
    	                {
    	                    current.x=m;current.y=n;
    	                    consecutive = true;
    	                    for(int j=0; j<4; j++)
    	                    {
    	                        //boundry
    	                        if(getNeighbour(current,next,direction)==-1)
    	                            break;
    	                        //empty
    	                        //System.out.println("Current"+current.toString()+" Next"+next.toString());
    	                        if(this.board[next.x][next.y]==0)
    	                        {
    	                            if(this.board[current.x][current.y]==0 && count>2)
    	                            {
    	                                space++;
    	                                break;
    	                            }
    	                            else
    	                            {
    	                                current.setLocation(next);
    	                                space++;
    	                                consecutive=false;
    	                                continue;
    	                            }
    	                        }
    	                        
    	                        //same player
    	                        else if(this.board[next.x][next.y] == player)
    	                        {
    	                            count++;
    	                            space++;
    	                            if(consecutive)
    	                                consecu++;
    	                            current.setLocation(next);
    	                            continue;
    	                        }
    	                        else if(this.board[next.x][next.y] != player)
    	                        {
    	                        //different player
    	                            if(this.board[current.x][current.y]==0)
    	                            {
    	                                break;
    	                            }
    	                            else
    	                            {
    	                                blocked=true;
    	                                break;
    	                            }
    	                        }
    	                    }
    	                    direction=0-direction;
    	                }

    	                if(space<5)
    	                    ;
    	                else{
        	                if(consecu==2)
        	                {
        	                    //close 2
        	                    if(blocked)
        	                    {
        	                        
        	                        oneScore+=3;
        	                    }
        	                    else //open 2
        	                    {
        	                        if(oneScore >5)
        	                            oneScore+=20;
        	                        else
        	                            oneScore+=5;
        	                    }
        	                }
        	                if(count==3 && consecu==3)
        	                {
        	                    if(blocked)
        	                    {
        	                        if(oneScore<50)
        	                            oneScore+=50;//close 3
        	                        else if(oneScore > 200)
        	                            oneScore+=1000;
        	                    }
        	                    else
        	                    {
        	                        if(oneScore<200)
        	                            oneScore+=200;
        	                        else
        	                            oneScore+=5000;
        	                    }
        	                }
        	                if(count == 4)
        	                {
        	                	//System.out.println("Count4: " +count);
        	                    if(consecu==4)
        	                    {
        	                        if(blocked)
        	                        {
        	                            if(oneScore<200)
        	                                oneScore+=500;
        	                            else
        	                                oneScore+=10000;
        	                        }
        	                        else
        	                            oneScore+=10000;
        	                    }
        	                    if(consecu==3)
        	                    {
        	                    
        	                        if(oneScore<200)
                                        oneScore+=500;
                                    else
                                        oneScore+=10000;
        	              
        	                    }
        	                }
        	               if(consecu ==5) {
       	                	 oneScore+=100000;//win
       	                	
       	                }

    	               }
        	                
        	              
        	                    
    	                }
    	            
    	            score += oneScore;
	            }
	        }
	    }
	    //System.out.println(player+ " Score: " + score);
	    return score;
	    
	}
	/**
	 * Check if the current player is win 
	 * 
	 * @param board
	 */
	public boolean ifWin(BoardT board) {
		return win;
	}
	
	/**
	 * Given a position and a direction, return the neighbor
	 * 
	 * @param current position, direction
	 * @return next position
	 */
	public int getNeighbour(Point current, Point next, int direction)
	{
	    if(direction ==1)
	    {
	        if (current.y +1 > 18)
	            return -1;
	        next.setLocation(current.x, current.y+1);
	        return 0;
	    }
	    else if(direction ==2)
        {
            if (current.x +1 > 18 ||current.y +1 > 18)
                return -1;
            next.setLocation(current.x +1, current.y+1);
            return 0;
        }
	    else if(direction ==3)
        {
            if (current.x +1 > 18)
                return -1;
            next.setLocation(current.x +1, current.y);
            return 0;
        }
	    else if(direction ==4)
        {
            if (current.x +1 > 18 ||current.y -1 < 0)
                return -1;
            next.setLocation(current.x +1, current.y-1);
            return 0;
        }
	    else if(direction ==-1)
        {
            if (current.y -1 < 0)
                return -1;
            next.setLocation(current.x, current.y-1);
            return 0;
        }
        else if(direction == -2)
        {
            if (current.x -1 < 0 ||current.y -1 < 0)
                return -1;
            next.setLocation(current.x -1, current.y-1);
            return 0;
        }
        else if(direction ==-3)
        {
            if (current.x -1 <0)
                return -1;
            next.setLocation(current.x -1, current.y);
            return 0;
        }
        else if(direction ==-4)
        {
            if (current.x -1 <0 ||current.y+1 > 18)
                return -1;
            next.setLocation(current.x -1, current.y+1);
            return 0;
        }
	    return -1;
	}
	
	/**
	 * Check and update possible valid moves
	 * 
	 */
	public void updatePossibleMoves()
	{
	    this.possibleMoves.removeAll(possibleMoves);
	    for(int i =0;i<19;i++)
	    {
	        for(int j=0;j<19;j++)
	        {
	            if(board[i][j]!=0)
	            {
    	            Point current = new Point(i,j);
    	            Point neighbour = new Point();
    	            for(int direction=1;direction<5;direction++)
    	            {
    	                if(this.getNeighbour(current, neighbour, direction)==0 && board[neighbour.x][neighbour.y]==0)
    	                {
    	                    this.possibleMoves.add(new Point(neighbour));
    	                    //System.out.println(neighbour);
    	                }
    	                if(this.getNeighbour(current, neighbour, -direction)==0 && board[neighbour.x][neighbour.y]==0)
    	                {
                            this.possibleMoves.add(new Point(neighbour));
                            //this.possibleMoves.add(new Point(neighbour));System.out.println(neighbour);
    	                }
    	            }
	            }
	        }
	    }
	}
	public static void main(String[] args) {
	    BoardT b= new BoardT();
	    b.move(5, 5);
	    b.move(5, 4);
	    b.move(5, 6);
	    b.move(4, 5);
	    b.move(6, 5);
	    b.move(7, 5);
	    b.move(4, 4);
	    b.updatePossibleMoves();
	    //System.out.println("Black:"+b.evaluate(1)+" While:"+b.evaluate(-1));

	}
}


