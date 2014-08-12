/**
 *Board Panel
 * 
 * @author Tianyi
 */
package GUI;

import Minimax.MinMaxFTz;
import Minimax.MinmaxT;
import GUI.BoardT;
import GeneticAlgorithm.GeneticAlg;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class BoardPanel extends JPanel{
	
	public static final int squareSize = 30;
	public static final int sizeX = squareSize * 19;
	public static final int sizeY = squareSize * 19;
	public static final int offset = 30;
	public boolean win = false;
	public int winPlayer = 2;
	public int valid = 0; //initial value
	public boolean end = false; //not end
	public Point last;
	public int steps=0;
	BoardT board;
	BoardFTz boardf;
	public static int version=1 ;
	
	int X;
	int Y;
	boolean Black = true;
	int black = 1;
	int white = 0;
	
	BufferedImage wImage;
	BufferedImage bImage;
	BufferedImage background;
	public BoardPanel() {
		board = new BoardT(19, 19, black);
		boardf = new BoardFTz(19, 19);
		//board.move(9, 9);
		String wPath = "img/white.gif";
		String bPath = "img/black.gif";
		String backgroundPath = "img/background.jpg";
		try {
			wImage = ImageIO.read(new File(wPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bImage = ImageIO.read(new File(bPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			background = ImageIO.read(new File(backgroundPath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1 && end == false) {
					X = (e.getX() - squareSize/2) / squareSize;
					Y = (e.getY() - squareSize/2) / squareSize;
					//System.out.println(version);
					if(board.board[X][Y] == 0)
					{
						steps++;
						valid = 1;
						if(version == 1){
							MinimaxT(X,Y);
						}
						if(version == 2){
							GAS(X,Y);
						}
						if(version == 3){
							MinimaxF(X, Y);
						}
		
					    if(board.evaluate(-1) > 500000) {
					    	winPlayer =board.currentPlayer;
					    	end = true;
					    }
					    if(board.evaluate(1) > 500000) {
					    	winPlayer = 1 - board.currentPlayer;
					    	end = true;
					    }
					}

					repaint();
				}

			}
		});
	}
	
	public int MinimaxT(int x, int y){
		board.move(x,y);	
		Point bestMove = MinmaxT.bestMove(board);
		board.move(bestMove.x, bestMove.y);
		last = bestMove;
		return 1;
		
	}
	
	public int MinimaxF(int x, int y){
		board.move(x,y);
		boardf.move(x, y);
		MinMaxFTz minmaxF = new MinMaxFTz();
		Point bestMoveF = minmaxF.bestPoint(boardf, Black);
		board.move(bestMoveF.x, bestMoveF.y);
		boardf.move(bestMoveF.x, bestMoveF.y);
		return 3;
	}
	
	public int GAS(int x, int y) {
		board.move(x, y);
		GeneticAlg ga = new GeneticAlg(board);
		Point bestMoveG = ga.bestMove();
		board.move(bestMoveG.x, bestMoveG.y);
		return 2;
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        
		super.paintComponent(g);
		
		
		g.drawImage(background, 0 ,0, null);
		g2d.setStroke(new BasicStroke(2));
		
		
		// Draw vertical lines
		for (int i = 0; i < 19*squareSize+squareSize/2; i+=squareSize) {
			g.drawLine(i, 0, i, 19*squareSize);
		}
		// Draw horizontal lines
		for (int i = 0; i < 19*squareSize+squareSize/2; i+=squareSize) {
			g.drawLine(0, i, 19*squareSize, i);
		}
		
		// Draw  pieces
		int locX, locY;
		for(int i=0; i<board.rows; i++) {
			for(int j=0; j<board.cols; j++) {
				if(board.board[i][j] == 1) {
					locX = squareSize/2 + squareSize*i;
					locY = squareSize/2 + squareSize*j;
					g.drawImage(bImage, locX, locY, null);		
				}
				if(board.board[i][j] == -1) {
					locX = squareSize/2 + squareSize*i;
					locY = squareSize/2 + squareSize*j;
					g.drawImage(wImage, locX, locY, null);
					
				}
			}

		}
		Font defaultFont = g.getFont();
		Font blackWin = new Font(defaultFont.getFontName(),2,80);
		Font whiteWin = new Font(defaultFont.getFontName(), 2, 80);
		switch(winPlayer) {
		
		case 0: // black wins
			g.setColor(Color.red);
			g.setFont(blackWin);
			g.drawString("Black Wins!",50, 200);
			System.out.println("Total Steps: " + steps);
			break;
			
		case 1:
			g.setColor(Color.red);
			g.setFont(whiteWin);//white wins
			g.drawString("White Wins!",50,200);
			System.out.println("Total Steps: " + steps);
			break;
		}
		if(valid == 2) {
			g.drawString("Invalid Move!", 50, 50);
		}


	}
public static void main(String[] args) {
	
	
		
	JFrame frame = new JFrame("Cute Gomoku");
		int startX = frame.getX();
		int startY = frame.getY();
		
		int boardSize = 19*squareSize+squareSize/2;
		int frameWidth = boardSize;
		int frameLength = boardSize + offset;
		
		frame.setSize(frameWidth, frameLength);
		BoardPanel panel = new BoardPanel();
		String ga = " (Genetic Algorithm)";
		String minmaxT = " (MinmaxT)";
		String minmaxF = " (MinmaxFTz)";
		if(args.length == 1)
		{
			int v = Integer.parseInt(args[0]);
			if(v==2 || v==3){
				version = v;
				if(version == 2){
					System.out.println("You are now running version " + version + ga);	
				}
				if(version == 3){
					System.out.println("You are now running version " + version + minmaxF);
				}
			}
			if(v==1) {
				version = v;
				System.out.println("You are now running version " + version + minmaxT);
			}
		}
		else{
			version = 1;
			System.out.println("You are now running version " + version + minmaxT);
		}
		Border menuBorder = new LineBorder(Color.BLACK);
		panel.setSize(boardSize, boardSize);
		panel.setBorder(menuBorder);
		frame.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
		panel.repaint();
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
