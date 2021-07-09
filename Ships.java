public class Ships {
	
	// declare and initialize variables
	private String[] ships;
	private String shipName;
	private char piece;
	private int shipLength = 0;
	private int shipsSunk = 0;
	
	public Ships()
	/* 
	 * Function: constructs the five ships within the class
	 */
	{
		ships = new String[5];
		ships[0] = "Carrier";
		ships[1] = "Battleship";
		ships[2] = "Cruiser";
		ships[3] = "Submarine";
		ships[4] = "Destroyer";
	}
	
	public String getShip(int i)
	/* 
	 * Function: returns the ship name to the user
	 */
	{
		return ships[i];
	}
	
	public void printShips()
	/* 
	 * Function: prints the list of ships in the class
	 */
	{
		System.out.println("\nThese are your ships: ");
		System.out.println("Carrier: #####");
		System.out.println("Battleship: $$$$");
		System.out.println("Cruiser: %%%");
		System.out.println("Submarine: &&&");
		System.out.println("Destroyer: @@");
	}
	
	public int getShipLength(int i)
	/* 
	 * Function: returns length of ship depending on the ship's location in the array 
	 */
	{
		
		if (ships[i] == "Carrier")
		{
			shipLength = 5;
		}
		else if (ships[i] == "Battleship")
		{
			shipLength = 4;
		}
		else if (ships[i] == "Cruiser")
		{
			shipLength = 3;
		}
		else if (ships[i] == "Submarine")
		{
			shipLength = 3;
		}
		else
		{
			shipLength = 2;
		}
		
		return shipLength;
	}
	
	public int getShipLength(String shipname)
	/* 
	 * Function: return ship's length depending on the ship's name 
	 */
	{
		if (shipName == "Carrier")
		{
			shipLength = 5;
		}
		else if (shipName == "Battleship")
		{
			shipLength = 4;
		}
		else if (shipName == "Cruiser")
		{
			shipLength = 3;
		}
		else if (shipName == "Submarine")
		{
			shipLength = 3;
		}
		else
		{
			shipLength = 2;
		}
		
		return shipLength;
	}
	
	public char getPiece(String shipName)
	/* 
	 * Function: returns the character that creates a given ship
	 */
	{
		if (shipName == ships[0])
		{
			piece = '#';
		}
		else if (shipName == ships[1])
		{
			piece = '$';
		}
		else if (shipName == ships[2])
		{
			piece = '%';
		}
		else if (shipName == ships[3])
		{
			piece = '&';
		}
		else
		{
			piece = '@';
		}
		
		return piece;
	}
	
	public String detectShip(Board board, int row, int column)
	/* 
	 * Function: determine what ship is present on the board based on its character
	 */
	{
		
		if (board.getBoard(row, column) == '#')
		{
			shipName = "Carrier";
		}
		else if (board.getBoard(row, column) == '$')
		{
			shipName = "Battleship";
		}
		else if (board.getBoard(row, column) == '%')
		{
			shipName = "Cruiser";
		}
		else if (board.getBoard(row, column) == '&')
		{
			shipName = "Submarine";
		}
		else if (board.getBoard(row, column) == '@')
		{
			shipName = "Destroyer";
		}
		
		return shipName;
	}

	public void updateShipsSunk()
	/* 
	 * Function: adds number of ships sunk to the running total
	 */
	{
		shipsSunk += 1;
	}
	
	public int getShipsSunk()
	/* 
	 * Function: access how many ships have been sunk throughout the game 
	 */
	{
		return shipsSunk;
	}
}
	