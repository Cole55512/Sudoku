package com.sudoku.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class Sudoku extends JPanel implements ActionListener
{	
	private static final int size = 900;
	private static final int gridLength = 9;
	private static int puzzleNum;
	
	Font font = new Font("SansSerif", Font.BOLD, 32);
	
	static ArrayList<String> puzzles = new ArrayList<String>();
	private static JTextField[][] grid;
	private static int[][] gBoard;
	private static int[][] sBoard;		
	
	// Menu Buttons
	private JButton solve;
	private JButton guess;

	// Value input
	private JLabel value;
	private static JTextField valText;
	
	// Row input
	private JLabel row;
	private static JTextField rowText;
	
	// Column input
	private JLabel col;
	private static JTextField colText;
	
	private static JLabel miss;
	private static int numMiss = 0;
	
	public Sudoku()
	{
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(size, size));
		init();
	}
	
	public void init()
	{
		JPanel gPanel = new JPanel(new GridLayout(gridLength, gridLength));
		JPanel mPanel = new JPanel(new FlowLayout());
		Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
		
		gBoard = new int[gridLength][gridLength];
		sBoard = new int[gridLength][gridLength];
		setBoard();

		solve(sBoard);
		grid = new JTextField[gridLength][gridLength];
		
		for(int r = 0; r < gridLength; r++)
		{
			for(int c = 0; c < gridLength; c++)
			{
				String num = Integer.toString(gBoard[r][c]);
				if(num.contentEquals("0"))
				{
					grid[r][c] = new JTextField("");
					grid[r][c].setBorder(border);
					grid[r][c].setFont(font);
				}
				else
				{
					grid[r][c] = new JTextField(num);
					grid[r][c].setBorder(border);
					grid[r][c].setFont(font);
				}
				grid[r][c].setHorizontalAlignment((int) CENTER_ALIGNMENT);
		        grid[r][c].setEditable(false);
		        gPanel.add(grid[r][c]);
			}
		}
		
		// Color
		Color lightBlue =new Color(197, 209, 229);	
		Color darkBlue =new Color(126, 165, 229);
		for(int r = 0; r < gridLength; r++)
		{
			for(int c = 0; c < gridLength; c++)
			{
				if(r >= 0 && r <= 2)
				{
					if(c >= 0 && c <= 2)
					{
						grid[r][c].setBackground(darkBlue);
						grid[r][c].setForeground(Color.WHITE);
					}
					if(c >= 3 && c <= 5)
					{
						grid[r][c].setBackground(lightBlue);
						grid[r][c].setForeground(Color.BLACK);
					}
					if(c >= 6 && c <= 8)
					{
						grid[r][c].setBackground(darkBlue);
						grid[r][c].setForeground(Color.WHITE);
					}
				}
				
				if(r >= 3 && r <= 5)
				{
					if(c >= 0 && c <= 2)
					{
						grid[r][c].setBackground(lightBlue);
						grid[r][c].setForeground(Color.BLACK);
						
					}
					if(c >= 3 && c <= 5)
					{
						grid[r][c].setBackground(darkBlue);
						grid[r][c].setForeground(Color.WHITE);				
					}
					if(c >= 6 && c <= 8)
					{
						grid[r][c].setBackground(lightBlue);
						grid[r][c].setForeground(Color.BLACK);
					}
				}
				
				if(r >= 6 && r <= 8)
				{
					if(c >= 0 && c <= 2)
					{
						grid[r][c].setBackground(darkBlue);
						grid[r][c].setForeground(Color.WHITE);
					}
					if(c >= 3 && c <= 5)
					{
						grid[r][c].setBackground(lightBlue);
						grid[r][c].setForeground(Color.BLACK);
					}
					if(c >= 6 && c <= 8)
					{
						grid[r][c].setBackground(darkBlue);
						grid[r][c].setForeground(Color.WHITE);
					}
				}
			}
		}
		
		// Menu
		solve = new JButton("Solve");
		row = new JLabel("Row:");
		rowText = new JTextField("");
		rowText.setColumns(5);
		col = new JLabel("Colum:");
		colText = new JTextField("");
		colText.setColumns(5);
		value = new JLabel("Value:");
		valText = new JTextField("");
		valText.setColumns(5);
		guess = new JButton("Guess");
		miss = new JLabel("Strikes: " + numMiss);
		
		solve.addActionListener(this);
		guess.addActionListener(this);
		
		mPanel.add(solve);
		mPanel.add(row);
		mPanel.add(rowText);
		mPanel.add(col);
		mPanel.add(colText);
		mPanel.add(value);
		mPanel.add(valText);
		mPanel.add(guess);
		mPanel.add(miss);
		
		add(gPanel, BorderLayout.CENTER);
		add(mPanel, BorderLayout.SOUTH);
	}
	
	public static boolean solve(int[][] board)
	{
		int row = -1;
		int col = -1;
		boolean empty = true; 
		for (int r = 0; r < gridLength; r++)
		{
			for (int c = 0; c < gridLength; c++)
		    { 
				if (board[r][c] == 0)
		        { 
					row = r;
		            col = c; 
	                empty = false;  
		            break; 
		        } 
		    }		        
		    if (!empty)
		    { 
		    	break; 
		    } 
		} 
		
		if (empty)
		{ 
	        return true; 
		}   
		for (int i = 1; i <= gridLength; i++)
		{ 
			if (valid(board, row, col, i))
		    { 
				board[row][col] = i; 
		            
				if (solve(board))
		        { 
					return true; 
		        }  
		        else
		        { 
		            board[row][col] = 0;
		        } 
		    } 
		} 
		return false;
	}
	
	public static boolean valid (int[][] board, int row, int col, int num)
	{ 
		for (int c = 0; c < gridLength; c++)  // Loops through all rows in each column
		{  
			if (board[row][c] == num)  // Checks if numbers are the same
			{ 
				return false; 
			} 
		}
		
		for (int r = 0; r < gridLength; r++) 	// Loops through all columns in each row
		{ 
			if (board[r][col] == num) 	// Checks if numbers are the same
			{ 
				return false; 
			} 
		} 
		
		int sqrt = (int) Math.sqrt(gridLength); // Find the Size of the sections
		int rowStart = row - row % sqrt; 	// Starting point for each row in a section
		int colStart = col - col % sqrt; 	// Starting point for each column in a section

		for (int r = rowStart; r < rowStart + sqrt; r++)  // Loops through the rows
		{ 
			for (int c = colStart;  c < colStart + sqrt; c++)  // Loops through the columns
			{ 
				if (board[r][c] == num)  // Checks if grid slot is the given number
				{ 
					return false; 
				} 
			} 
		} 
		
		return true; 
	} 
	
	public static void update(int[][] board)
	{
	    if ((board.length != gridLength) || (board[0].length != gridLength))
	    {
	      throw new IllegalArgumentException("Sudoku size does not match squares size");
	    }
	    
	    miss.setText("Strikes: " + numMiss);
	    
	    for (int r = 0; r < gridLength; r++)
	    {
	      for (int c = 0; c < gridLength; c++)
	      {
	    	String num = Integer.toString(board[r][c]);
	    	if(num.contentEquals("0"))
	    	{
	    		// We want it to do nothing because graphics
	    	}
	    	else
	    	{
	    		grid[r][c].setText(num);
	    	}
	      }
	    }
	  }

	public static void guess(int[][] board)
	{
		int gRow = Integer.parseInt(rowText.getText());
		int gCol = Integer.parseInt(colText.getText());
		int val = Integer.parseInt(valText.getText());
		
		if(val > 0 || val < 10)
		{
			if(valid(board, gRow, gCol, val))
			{
				if(sBoard[gRow][gCol] == val)
				{
					setValue(board, gRow, gCol, val);
					System.out.println("Value Updated");
				}
				else
				{
					numMiss += 1;
				}
			}
			else
			{
				return;
			}
		}
		else
		{
			return;
		}
	}
	
	public static void setValue(int[][] board, int r, int c, int val)
	{
		board[r][c] = val;
	}
	
	public static void readDoc()
	{	
		try
		{
			FileReader fr =new FileReader("puzzles.txt");
			@SuppressWarnings("resource")
			BufferedReader br =new BufferedReader(fr);
			
			String fileWord = "";
			while((fileWord = br.readLine()) != null)
			{
				puzzles.add(fileWord);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setBoard()
	{
		readDoc();
		
		int prev = puzzleNum;
		
		Random rand = new Random();
		
		do
		{
			puzzleNum = rand.nextInt(puzzles.size());
		}
		while (prev == puzzleNum);
		
		int count = 0;
		
		System.out.println(puzzles.get(puzzleNum) + "\n");
		String puzzle = puzzles.get(puzzleNum);
		int[] nums = new int[puzzle.length()];
		for(int i = 0; i < puzzle.length(); i++)
		{
			nums[i] = Character.getNumericValue(puzzle.charAt(i));
		}
		
		for(int r = 0; r < gridLength; r++)
		{
			for(int c = 0; c < gridLength; c++)
			{
				gBoard[r][c] = nums[count];
				sBoard[r][c] = nums[count];
				count++;
			}
		}
		
		print(gBoard);
		System.out.println("\n");
		print(sBoard);
	}
	
	public static void resetBoard()
	{
		for(int r = 0; r < gridLength; r++)
		{
			for(int c = 0; c < gridLength; c++)
			{
				gBoard[r][c] = 0;
				sBoard[r][c] = 0;
				grid[r][c].setText("");
				update(gBoard);
			}
		}
	}
	
	public static JMenuBar createMenu()
	{
		JMenuBar menu = new JMenuBar();
		
		JMenu help = new JMenu("Help");
		JMenu game = new JMenu("Game");
		
		JMenuItem guide = new JMenuItem("Guide");
		guide.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String instructions = "Row and Column number start from the top left (top left box is r: 1, c: 1)     \n\n1.) Input the number of the row\n"
								+ "2.) Input the number of the column\n3.) Input the value of guess\n4.) Click the 'Guess' button";
						JOptionPane.showMessageDialog(null, instructions);
					}
				});
		
		JMenuItem newPuzzle = new JMenuItem("New Puzzle");
		newPuzzle.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						resetBoard();
						setBoard();
						update(gBoard);
						solve(sBoard);
					}
				});
		
		game.add(newPuzzle);
		help.add(guide);		
		
		menu.add(game);		
		menu.add(help);	
		
		return menu;
	}
	
	public static void print(int[][] board)
	{
		for(int[] row: board)
		{
			System.out.println(Arrays.toString(row));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton source = (JButton) e.getSource();
		
		if(source == solve)
		{
			solve(gBoard);
			update(gBoard);
		}
		if(source == guess)
		{
			guess(gBoard);
			update(gBoard);
		}
	}
	
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable()
		{
		      public void run()
		      {    	
		    	
		    	JFrame frame = new JFrame("Sudoku");
		  	    frame.add(new Sudoku(), BorderLayout.CENTER);
		  	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  	    frame.pack();
		  	    frame.setJMenuBar(createMenu());
		  	    frame.setLocationRelativeTo(null);
		  	    frame.setResizable(false);
		  	    frame.setVisible(true);
		      }
		    });	
	}
}