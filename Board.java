public class Board {
	
	// declare variable
	private char[][] board;
	
	public Board()
	/* 
	 * Function: constructs the board as a 10 by 10 array
	 */
	{
		board = new char[10][10];
	}
	
	public void printBoard()
	/* 
	 * Function: prints the board to the console 
	 */
	{
		System.out.println("       A     B     C     D     E     F     G     H     I     J   ");     // column labels
		System.out.println("    |-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|");    // first line of the board
		for(int row = 0; row < 10; row++)    // for each board location print element
		{
			if (row < 9)    // what to print for every row except the last
			{
				System.out.println((row + 1) + "   |  " + board[row][0] + "  |  " + board[row][1] + "  |  " 
			+ board[row][2] + "  |  " + board[row][3] + "  |  " + board[row][4] + "  |  "  + board[row][5] 
					+ "  |  "  + board[row][6] + "  |  "  + board[row][7] + "  |  " + board[row][8] + "  |  " 
			+ board[row][9] + "  |  ");
			}
			else    // what to print for the last row 
			{
				System.out.println((row + 1) + "  |  "+ board[row][0] + "  |  " + board[row][1] + "  |  " 
			+ board[row][2] + "  |  " + board[row][3] + "  |  " + board[row][4] + "  |  "  + board[row][5] 
					+ "  |  "  + board[row][6] + "  |  "  + board[row][7] + "  |  " + board[row][8] + "  |  " 
			+ board[row][9] + "  |  ");
			}
			
			System.out.println("    |-----|-----|-----|-----|-----|-----|-----|-----|-----|-----|");    // last line of the board
		}
	}
	
	public char getBoard(int row, int col)
	/* 
	 * Function: gets the value of a given location on the board
	 */
	{
		char value = board[row][col];
		return value;
	}
	
	public void updateBoard(int row, int col, char newChar)
	/* 
	 * Function: updates a location on the board with a new value 
	 */
	{
		board[row][col] = newChar;
	}
}
