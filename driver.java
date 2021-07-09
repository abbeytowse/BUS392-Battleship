import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class driver {

	public static void main(String[] args) {
		
		// create objects from classes
		Scanner keyboard = new Scanner(System.in);
		Ships shipsPlayer1 = new Ships();
		Ships shipsPlayer2 = new Ships();
		Board boardPlayer1 = new Board();
		Board boardPlayer2 = new Board();
		Board guessingBoardPlayer1 = new Board();
		Board guessingBoardPlayer2 = new Board();
		
		// create variables
		int playerCount = 0;
		int turnCount = 0;
		boolean isNumber = true;
		String confirmation;
		
		// get number of players 
		do{
			// ensures program doesn't crash is non-number is entered 
			do { 
				System.out.print("Enter how many players are playing (1 or 2): ");
				try {
					playerCount = Integer.parseInt(keyboard.nextLine());
					isNumber = true;
				}
				catch (Exception NumberFormatException) {
					System.out.println("A non-number was inputted. Try again.\n");
					isNumber = false;
				}
			} while (isNumber == false);
			
			if (playerCount > 2 || playerCount < 1)    // player count not 1 or 2
			{
				System.out.println("Invalid player count. Try again\n.");
			}
		} while (playerCount > 2 || playerCount < 1);    // player count not 1 or 2
	
		// make sure only player 1 is looking at the screen
		do {
			if (playerCount == 2)
			{
				System.out.println("\nIt is Player 1's turn to set the board.");
				System.out.print("Press 'K' and hit enter when Player 2 has left: ");
			}
			else
			{
				System.out.println("\nIt is Player 1's turn to set the board.");
				System.out.print("Press 'K' and hit enter to begin: ");
			}
			confirmation = keyboard.nextLine();
		} while (!confirmation.equalsIgnoreCase("K"));
			
		// player 1 sets the board
		System.out.print("\n");    // blank line for formating 
		turnCount++;
		boardPlayer1.printBoard();
		shipsPlayer1.printShips();
		setLocations(boardPlayer1, shipsPlayer1, playerCount, turnCount);
			
		//player 1 finishes setting their board
		System.out.println("\nPlayer 1: You have finished setting your board!");
			
		// player 1 hides board after completion 
		do { 
			if (playerCount == 2)
			{
				System.out.print("Press 'K' and hit enter to hide your board and show blank Player 2 board: ");
			}
			else
			{
				System.out.print("Press 'K' and hit enter to let The Computer set its board: ");
			}
			confirmation = keyboard.nextLine();
		} while (!confirmation.equalsIgnoreCase("K"));
			
		// Player 2 or The Computer sets the board
		if (playerCount == 2)
		{
			turnCount++;
			boardPlayer2.printBoard();
			shipsPlayer2.printShips();
			setLocations(boardPlayer2, shipsPlayer2, playerCount, turnCount);
		}
		else
		{
			turnCount++;
			setLocations(boardPlayer2, shipsPlayer2, playerCount, turnCount);
		}
			
		// player 2 finishes setting their board
		if (playerCount == 2)
		{
			System.out.println("Player 2: You have finished setting your board!");
		}
		else
		{
			System.out.println("\nThe Computer has finished setting its board!");
		}
		System.out.println("\nTime to start playing! It is Player 1's turn.");
			
		// player 2 hides board after completion
		do {
			if (playerCount == 2)
			{
				System.out.print("Player 2, press 'K' and hit enter to hide your board: ");
			}
			else
			{
				System.out.print("Press 'K' and hit enter to begin: ");
			}
			confirmation = keyboard.nextLine();
		} while (!confirmation.equalsIgnoreCase("K")); 
			
		// players guess the location of each other's ships 
		do {
			turnCount += 1;   // tracks turn count 
			if (turnCount % 2 == 0)    // player 2 or the computer makes a guess on the location of player 1's ships
			{
				if (playerCount == 2)    // player 2
				{
					// print board for player 2 guesses
					System.out.println("\nPlayer 2 Guessing Board:\n");
					guessingBoardPlayer2.printBoard();
					
					//player 2 makes guess
					playerMakeGuess(guessingBoardPlayer2, boardPlayer1, shipsPlayer2, turnCount, playerCount);
					
					// check for winner 
					if (isWinner(shipsPlayer1, shipsPlayer2, playerCount))
					{
						System.exit(0);    // game ends if someone wins 
					}
					
					// player 2 passes play after seeing result 
					do {
						System.out.print("\nEnd of Player 2's turn. Press 'K' and hit enter to pass play to Player 1: ");
						confirmation = keyboard.nextLine();
					} while (!confirmation.equalsIgnoreCase("K"));
				}
				else    // the computer 
				{
					// the computer makes guess 
					computerMakeGuess(guessingBoardPlayer2, boardPlayer1, shipsPlayer2, playerCount, turnCount);
					
					// check for winner
					if (isWinner(shipsPlayer1, shipsPlayer2, playerCount))
					{
						System.exit(0);    // game ends if someone wins
					}
					
					// player 1 reviews results of computer's turn 
					do {
						System.out.print("\nEnd of Computer's turn. Press 'K' and hit enter to pass play to Player 1: ");
						confirmation = keyboard.nextLine();
					} while (!confirmation.equalsIgnoreCase("K"));
				}
			}
			else    // player 1 makes a guess on the location of player 2's ships
			{
				// print board for player 2 guesses
				System.out.println("\nPlayer 1 Guessing Board:\n");
				guessingBoardPlayer1.printBoard();
				playerMakeGuess(guessingBoardPlayer1, boardPlayer2, shipsPlayer1, turnCount, playerCount);
				
				// check for winner 
				if (isWinner(shipsPlayer1, shipsPlayer2, playerCount))
				{
					System.exit(0);    // game ends if someone ends
				}
					
				// player 1 hides board after making guess and seeing result 
				do {
					if (playerCount == 2)
					{
						System.out.print("\nEnd of Player 1's turn. Press 'K' and hit enter to pass play to Player 2: ");
					}
					else 
					{
						System.out.print("\nEnd of Player 1's turn. Press 'K' and hit enter to pass play to The Computer: ");
					}
					confirmation = keyboard.nextLine();
				} while (!confirmation.equalsIgnoreCase("K"));
			}
		} while (isWinner(shipsPlayer1, shipsPlayer2, playerCount) == false);

	}
	
	public static String horizontalOrVertical(Ships ships, int i, int playerCount, int turnCount)
	/* 
	 * Function: asks user if they want to place their ship vertically or horizontally 
	 */
	{	
		// create objects 
		Scanner keyboard = new Scanner(System.in);
		Random random = new Random();
		
		// declare and initiate variables
		String direction;
		String shipName = ships.getShip(i);
		String dir[] = {"H", "V"};
		int shipLength = ships.getShipLength(i);
		
		// ask user the orientation they want to place their ship 
		do {
			if ((playerCount == 2) || (turnCount % 2 != 0))    // if player is not a computer
			{
				System.out.print("\nDo you want to place the " + shipName + " (length " + shipLength + ") horizontally or vertically (H or V): ");
				direction = keyboard.nextLine();
				if (!direction.equalsIgnoreCase("H") && !direction.equalsIgnoreCase("V"))    // the direction isn't horizontal or vertical
				{
					System.out.print("Invalid direction. Try again.");
				}
			}
			else    // if player is the computer 
			{
				direction = dir[random.nextInt(2)];    // computer randomly selects "H" or "V"
			}
		} while(!direction.equalsIgnoreCase("H") && !direction.equalsIgnoreCase("V"));    // the direction isn't horizontal or vertical 
		
		return direction;
	}
	
	public static int convertLetterToNum(String letter)
	/* 
	 * Function: Converts a letter A - J to a number 
	 */
	{
		// declare and initialize variable number
		int number = -1; 
		
		if (letter.equalsIgnoreCase("A"))    // A to 0
		{
			number = 0;
		}
		else if (letter.equalsIgnoreCase("B"))    // B to 1
		{
			number = 1;
		}
		else if (letter.equalsIgnoreCase("C"))    // C to 2
		{
			number = 2;
		}
		else if (letter.equalsIgnoreCase("D"))    // D to 3
		{
			number = 3;
		}
		else if (letter.equalsIgnoreCase("E"))    // E to 4
		{
			number = 4;
		}
		else if (letter.equalsIgnoreCase("F"))    // F to 5
		{
			number = 5;
		}
		else if (letter.equalsIgnoreCase("G"))    // G to 6
		{
			number = 6;
		}
		else if (letter.equalsIgnoreCase("H"))    // H to 7
		{
			number = 7;
		}
		else if (letter.equalsIgnoreCase("I"))    // I to 8
		{
			number = 8;
		}
		else if (letter.equalsIgnoreCase("J"))    // J to 9
		{
			number = 9;
		}
		
		return number;
	}
	
	public static String convertNumToLetter(int number)
	/* 
	 * Function: Converts a letter A - J to a number 
	 */
	{
		// declare and initialize variable number
		String letter = ""; 
		
		if (number == 0)    // 0 to A
		{
			letter = "A";
		}
		else if (number == 1)    // 1 to B
		{
			letter = "B";
		}
		else if (number == 2)    // 2 to C
		{
			letter = "C";
		}
		else if (number == 3)    // 3 to D
		{
			letter = "D";
		}
		else if (number == 4)    // 4 to E
		{
			letter = "E";
		}
		else if (number == 5)    // 5 to F
		{
			letter = "F";
		}
		else if (number == 6)    // 6 to G
		{
			letter = "G";
		}
		else if (number == 7)    // 7 to H
		{
			letter = "H";
		}
		else if (number == 8)    // 8 to I
		{
			letter = "I";
		}
		else if (number == 9)    // 9 to J
		{
			letter = "J";
		}
		
		return letter;
	}
	
	public static int chooseCol(String direction, Ships ships, Board locationBoard, int i, int playerCount, int turnCount)
	/* 
	 * Function: gets user input for column to place the first unit of the ship 
	 */
	{
		// create objects 
		Scanner keyboard = new Scanner(System.in);
		Random random = new Random();
		
		// declare and initialize variables
		int column = 0; 
		int count = 0;
		int validity = 0;
		int shipLength = ships.getShipLength(i);
		String shipName = ships.getShip(i);
		String letter;
		String letters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		
		// get the column number to place the first unit of the ship
		do { 
			
			// ask for column letter and convert to number 
			do {
				if ((playerCount == 2) || (turnCount % 2 != 0))    // the player is not the computer
				{
					System.out.print("\nChoose board column (A - J): ");
					letter = keyboard.nextLine();
					column = convertLetterToNum(letter);
					if (column == -1)    // if letter entered that is not A - J
					{
					System.out.println("Invalid column letter. Try again.");
					}
				}
				else    // the player is the computer 
				{
					letter = letters[random.nextInt(10)];
					column = convertLetterToNum(letter);
				}
			} while (column == -1);    // letter is not A - J
			
			// check if ship will go off board if placed in column 
			if (direction.equalsIgnoreCase("H") && (column + shipLength > 10))
			{
				if ((playerCount == 2) || (turnCount % 2 != 0))    // give error message is player is not the computer
				{
					System.out.print("Not a valid column for the " + shipName + ". Try again.");
				}
			}
			// check if a least one location in a column has a ship in it 
			else if (locationBoard.getBoard(0, column) != '\u0000' || locationBoard.getBoard(1, column) != '\u0000' 
					|| locationBoard.getBoard(2, column) != '\u0000' || locationBoard.getBoard(3, column) != '\u0000' 
					|| locationBoard.getBoard(4, column) != '\u0000' || locationBoard.getBoard(5, column) != '\u0000' 
					|| locationBoard.getBoard(6, column) != '\u0000' || locationBoard.getBoard(7, column) != '\u0000' 
					|| locationBoard.getBoard(8, column) != '\u0000' || locationBoard.getBoard(9, column) != '\u0000')
			{
				if (direction.equalsIgnoreCase("V"))    // vertical orientation 
				{
					// check if column has space to place a ship 
					for (int j = 0; j < 10; j++)
					{
						if (locationBoard.getBoard(j, column) == '\u0000')    // if spot on board is blank 
						{
							count += 1;
							if (count >= shipLength)    // if there are equal amount or more continuous blank spots than the length of the ship
							{
								validity = 1;    // column is valid for placing ships 
								break;    // stop checking 
							}
							else
							{
								continue;    // keep checking, don't yet know if valid 
							}
						}
						else
						{
							count = 0;    // the blank spots are not continuous, reset count to 0 
							validity = 0;    // column is not valid for placing ships 
						}
						
					}
					if (count < shipLength)    // if there are less continuous blank spots than the length of the ship 
					{
						if ((playerCount == 2) || (turnCount % 2 != 0))    // give error message if player is not the computer
						{
							System.out.print("This column is too full. Try again.");
						}
						validity = 0;    // column is not valid for placing ships 
					}
				}
				else    // horizontal orientation 
				{
					// check if a space in the column is blank 
					for (int k = 0; k < 10; k++)
					{
						if (locationBoard.getBoard(k, column) == '\u0000')    // if any space in column is blank 
						{
							validity = 1;    // column is valid for placing ships 
						}
					}
				}
			}
			else    // if column is completely blank and ship will not go off the board
			{
				validity = 1;    // column is valid for placing ships 
			}
		} while((direction.equalsIgnoreCase("H") && column + shipLength > 10) || validity == 0);    // ship will not go off the board or column is not valid for placing ships 
		
		return column;
	}
	
	public static int chooseRow(String direction, Ships ships, Board locationBoard, int i, int column, int playerCount, int turnCount)
	/* 
	 * Function: gets user input for row to place the first unit of the ship 
	 */
	{
		// create objects 
		Scanner keyboard = new Scanner(System.in);
		Random random = new Random();
		
		// declare and initialize variables
		int row = 0; 
		int validity = 0;
		int shipLength = ships.getShipLength(i);
		boolean isNumber = true;
		String shipName = ships.getShip(i);
		
		
		// get the row number to place the first unit of the ship 
		do {
			
			// get user input for row number
			do {
				if ((playerCount == 2) || (turnCount % 2 != 0))    // if the player is not the computer 
				{
					// ensures program doesn't crash is non-number is entered 
					do { 
						System.out.print("Choose board row (1 - 10): ");
						try {
							row = Integer.parseInt(keyboard.nextLine());
							isNumber = true;
						}
						catch (Exception NumberFormatException)
						{
							System.out.println("A non-number was inputted. Try again.\n");
							isNumber = false;
						}
					} while (isNumber == false);
					if (row < 0 || row > 10)    // row number is greater than 0 and less than 10
					{
						System.out.println("Invalid row number. Try again.\n");
					}
				}
				else    // if the player is the computer 
				{
					row = 1 + random.nextInt(10);
				}
			} while (row < 0 || row > 10);    // row number is greater than 0 and less than 10 
			
			// checks if ship will go off the board during vertical placement 
			if (direction.equalsIgnoreCase("V") && (row - 1) + shipLength > 10)    // (row - 1) allows row number to work with array values (0 - 9)
			{
				if ((playerCount == 2) || (turnCount % 2 != 0))    // if the player is not the computer 
				{
					System.out.println("Not a valid row for the " + shipName + ". Try again.");
			
				}
			}
			
			// if orientation is horizontal 
			else if (direction.equalsIgnoreCase("H"))
			{
				// check if each column is in row is valid 
				for (int j = 0; j < shipLength; j++)
				{
					if (locationBoard.getBoard((row - 1), column + j) != '\u0000')    // if row/col location not blank 
					{
						if ((playerCount == 2) || (turnCount % 2 != 0))    // if the player is not the computer 
						{
							System.out.println("Not a valid row for the " + shipName + ". Will hit another boat. Try again.");
						}
						validity = 0;    // not a valid row to place ship 
						break;    // stop checking 
					}
					else    // row/col location is blank 
					{
						validity = 1;    // valid row to place ship 
					}
				}
			}
			
			// if orientation is vertical 
			else if (direction.equalsIgnoreCase("V"))
			{
				// check if each row in column is valid
				for (int k = 0; k < shipLength; k++)
				{
					if (locationBoard.getBoard((row - 1) + k, column) != '\u0000')    // if row/col location not blank 
					{
						if ((playerCount == 2) || (turnCount % 2 != 0))    // if the player is not the computer 
						{
							System.out.println("Not a valid row for the " + shipName + ". Will hit another boat. Try again.");
						}
						validity = 0;     // not a valid row to place ship 
						break;    // stop checking
					}
					else    // row/col location is blank 
					{
						validity = 1;    // valid row to place ship 
					}
				}
			}
		} while ((direction.equalsIgnoreCase("V") && (row - 1) + shipLength > 10) || validity == 0);    // ship goes off the board during placement or row is not valid for placing ships 
	
		return (row - 1);    // (row - 1) allows row number to work with array values (0 - 9)
	}
	
	public static void placePiece(String direction, Ships ships, Board locationBoard, int i, int row, int column)
	/*
	 * Function: replaces characters in board array with characters that correspond to the ship at that location
	 */
	{
		// declare and initialize variables 
		int shipLength = ships.getShipLength(i);
		String shipName = ships.getShip(i);
				
				
		// horizontal orientation 
		if (direction.equalsIgnoreCase("H"))
		{
			// replaces corresponding character in board based on location and ship name 
			for (int j = 0; j < shipLength; j++)
			{
						
				if (shipName.equals("Carrier"))
				{
					locationBoard.updateBoard(row, column + j, '#');
				}
				else if (shipName.equals("Battleship"))
				{
					locationBoard.updateBoard(row, column + j, '$');
				}
				else if (shipName.equals("Cruiser"))
				{
					locationBoard.updateBoard(row, column + j, '%');
				}
				else if (shipName.equals("Submarine"))
				{
					locationBoard.updateBoard(row, column + j, '&');
				}
				else
				{
					locationBoard.updateBoard(row, column + j, '@');
				}
			}
		}
		else
		{
			// replaces corresponding character in board based on location and ship name 
			for (int k = 0; k < shipLength; k++)
			{
				
				if (shipName.equals("Carrier"))
				{
					locationBoard.updateBoard(row + k, column, '#');
				}
				else if (shipName.equals("Battleship"))
				{
					locationBoard.updateBoard(row + k, column, '$');
				}
				else if (shipName.equals("Cruiser"))
				{
					locationBoard.updateBoard(row + k, column, '%');
				}
				else if (shipName.equals("Submarine"))
				{
					locationBoard.updateBoard(row + k, column, '&');
				}
				else
				{
					locationBoard.updateBoard(row + k, column, '@');
				}
			}
		}
	}
	
	public static void setLocations(Board locationBoard, Ships ships, int playerCount, int turnCount)
	/*
	 * Function: give each ship a location on the board 
	 */
	{
		// create objects 
		Scanner keyboard = new Scanner(System.in);
		Random random = new Random();
		
		// declare and initialize variables
		String direction;
		int row = 0;
		int column = 0;
		
		// place each ship 
		for (int i = 0; i < 5; i++)
		{
			// declare and initialize ships and their lengths 
			String shipName = ships.getShip(i);
			int shipLength = ships.getShipLength(i);
			
			direction = horizontalOrVertical(ships, i, playerCount, turnCount);    // get user input for ship orientation 
			
			if ((playerCount == 2) || (turnCount % 2 != 0))    // if the player is not the computer 
			{
				// give different placement information based on orientation 
				if (direction.equalsIgnoreCase("H"))
				{
					System.out.println("\nShips are placed from left to right.");
				}
				else
				{
					System.out.println("\nShips are placed downward");
				}
				System.out.println("Choose the location of the first unit of " + shipName + " (length " + shipLength + "):");
			}
			
			column = chooseCol(direction, ships, locationBoard, i, playerCount, turnCount);    // get user input/random num for column 
			row = chooseRow(direction, ships, locationBoard, i, column, playerCount, turnCount);    // get user input/ random num for row 
			
			placePiece(direction, ships, locationBoard, i, row, column);    // place ship on the board 
			
			if ((playerCount == 2) || (turnCount % 2 != 0))    // if the player is not the computer 
			{
				System.out.print("\n");    // blank line for formatting
				locationBoard.printBoard();    // print updated board
				ships.printShips();    // print ships list
			}
		}
	}  
	
	public static boolean isGuessValid(Board guessingBoard, int row, int column, int playerCount, int turnCount)
	/* 
	 * Function: check is user's guess is a valid 
	 */
	{
		// check if location on the board is blank 
		if (guessingBoard.getBoard(row, column) == '\u0000')
		{
			return true;
		}
		else if (playerCount == 2)   // if location on the board is not blank & 2 people are playing
		{
			System.out.println("Already guessed this location. Try again.");
			return false;
		}
		else    // if location on the board is not blank & 1 person is playing 
		{
			if (turnCount % 2 != 0)     // the player is not the computer
			{
				System.out.println("Already guessed this location. Try again.");
				return false;
			}
			else    // the player is the computer 
			{
				return false;
			}
		}
		
	}
		
	public static boolean isShipSunk(Board guessingBoard, Board locationBoard, Ships ships, int row, int column)
	/* 
	 * Function: checks if a given ship has been sunk after being hit 
	 */
	{
		// declare and initialize variables 
		String shipName = ships.detectShip(locationBoard, row, column);
		char piece = ships.getPiece(shipName);
		int hits = ships.getShipLength(shipName);
		int horizontalCount = 0;
		int verticalCount = 0;
		
		// check the row for a sunk ship 
		for (int r = 0; r < 10; r++)
			{
			if (locationBoard.getBoard(r, column) == piece)
			{
				if (guessingBoard.getBoard(r, column) == 'X')
				{
					verticalCount += 1;    // count how many 'X's are in a column
					if (verticalCount == hits)
					{
						// for a sunk ship show the ship's characters 
						for (int i = 0; i < 10; i++)
						{
							if ((guessingBoard.getBoard(i, column) == 'X') && (locationBoard.getBoard(i, column) == piece))
							{
								guessingBoard.updateBoard(i, column, piece);
							}
						}
						return true;
					}
				}
			}
		}
		
		// check the column for a sunk ship 
		for (int c = 0; c < 10; c++)
		{
			if (locationBoard.getBoard(row, c) == piece)
			{
				if (guessingBoard.getBoard(row, c) == 'X')
				{
					horizontalCount += 1;    // count how many 'X's are in a row 
					if (horizontalCount == hits)
					{
						// for the sunk ship show the ship's characters 
						for (int j = 0; j < 10; j++)
						{
							if ((guessingBoard.getBoard(row, j) == 'X') && (locationBoard.getBoard(row, j) == piece))
							{
								guessingBoard.updateBoard(row, j, piece);
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isShipAtGuess(Board guessingBoard, Board locationBoard, Ships ships, int row, int column, int turnCount, int playerCount)
	/* 
	 * Function: checks if a user hits a ship when making their guess 
	 */
	{
		// determine what ship is being hit 
		String shipName = ships.detectShip(locationBoard, row, column);
		
		if ((turnCount % 2 == 0) && (playerCount == 2))    // it is player 2's turn 
		{
			// check if location on the board is blank 
			if (locationBoard.getBoard(row, column) == '\u0000')
			{
				System.out.println("\nYou Missed!");
				return false;
			}
			else    // location on the board is not blank 
			{
				System.out.println("\nPlayer 2 hit the " + shipName + "!\n");
				return true;
			}
		}
		else if ((turnCount % 2 == 0) && (playerCount == 1)) // it is the computer's turn 
		{
			// check if location on the board is blank 
			if (locationBoard.getBoard(row, column) == '\u0000')
			{
				System.out.println("\nThe Computer Missed!");
				return false;
			}
			else    // location on the board is not blank 
			{
				System.out.println("\nThe Computer hit the " + shipName + "!\n");
				return true;
			}
		}
		else    // it is player 1's turn 
		{
			// check if location on the board is blank 
			if (locationBoard.getBoard(row, column) == '\u0000')
			{
				System.out.println("\nYou missed!");
				return false;
			}
			else    // location on the board is not blank 
			{
				System.out.println("\nPlayer 1 hit the " + shipName + "!\n");
				return true;
			}
		}
			
	}
	
	public static void playerMakeGuess(Board guessingBoard, Board locationBoard, Ships ships, int turnCount, int playerCount)
	/* 
	 * Function: user makes a guess for where their opponents ships may be 
	 */
	{
		// create object 
		Scanner keyboard = new Scanner(System.in);
		
		// declare and initialize variables
		int column = 0;
		int row = 0;
		boolean isNumber = true;
		String columnLetter;
		
		if (turnCount % 2 == 0)    // it's player 2's turn 
		{
			System.out.println("\nPlayer 2 it is your turn to make a guess.");
		}
		else    // it's player 1's turn 
		{
			System.out.println("\nPlayer 1 it is your turn to make a guess.");
		}
		
		// prompt player to guess where their opponents ship is 
		do {
			System.out.println("\nGuess location of opponents ship: ");
			
			// guess column of opponent's ship 
			do {
				System.out.print("\nColumn (A - J): ");
				columnLetter = keyboard.nextLine();
				column = convertLetterToNum(columnLetter);
				if (column == -1)    // if letter entered that is not A - J
				{
					System.out.println("Invalid column letter. Try again.");
				}
			} while (column == -1);    // letter is not A - J
			
			// guess row of opponent's ship 
			do {
				// ensures program doesn't crash is non-number is entered 
				do {
					System.out.print("Row (1 - 10): ");
					try {
						row = Integer.parseInt(keyboard.nextLine()) - 1;
						isNumber = true;
					} 
					catch (Exception NumberFormatException) {
						System.out.println("A non-number was inputted. Try again.\n");
						isNumber = false;
					}
				} while (isNumber == false);
				if (row < 0 || row > 10)
				{
					System.out.println("Invalid row number. Try again");
				}
			} while (row < 0 || row > 10);    // row number is greater than 0 and less than 10 
		} while (isGuessValid(guessingBoard, row, column, playerCount, turnCount) == false);
		
		// check if guess hits the opponent's ship 
		if (isShipAtGuess(guessingBoard, locationBoard, ships, row, column, turnCount, playerCount) == true)
		{
			guessingBoard.updateBoard(row, column, 'X');    // place an 'X' on the board 
			// check if ship was sunk by the most recent hit 
			if (isShipSunk(guessingBoard, locationBoard, ships, row, column) == true)
			{
				if (turnCount % 2 == 0)    // it's player 2's turn 
				{
					String shipName = ships.detectShip(locationBoard, row, column);
					ships.updateShipsSunk();
					System.out.println("\nPlayer 2 you have sunk the " + shipName + "!\n");
				}
				else    // it's player 1's turn 
				{
					String shipName = ships.detectShip(locationBoard, row, column);
					ships.updateShipsSunk();
					System.out.println("\nPlayer 1 you have sunk the " + shipName + "!\n");
				}
			}
		}
		else    // opponent's ship is not hit 
		{
			guessingBoard.updateBoard(row, column, 'O');    // place a 'O' on the board
		}
		
		// print the updated board 
		guessingBoard.printBoard();
		
	}
		
	public static void computerMakeGuess(Board guessingBoard, Board locationBoard, Ships ships, int playerCount, int turnCount)
	/*
	 * Function: Have computer make a guess about where the opponents ships are located 
	 */
	{
		// create objects
		Random random = new Random();
		String[] rowCol = new String[2];
		List<String> guessSpaces = new ArrayList<String>();
		
		// declare variables 
		int row;
		int column;
		int index;
		int length;
		String space;
		
		// search the board for hits 
		for (int r = 0; r < 10; r++)
		{
			for (int c = 0; c < 10; c++)
			{
				// if hit is found add the surrounding valid spaces to the ArrayList
				if (guessingBoard.getBoard(r, c) == 'X')
				{
					if (( r - 1 > -1 ) && isGuessValid(guessingBoard, (r - 1), c, playerCount, turnCount))
					{
						// if location is not already in ArrayList, add location to ArrayList
						if (guessSpaces.contains((r - 1) + " " + c) == false) 
						{
							guessSpaces.add((r - 1) + " " + c);
						}
					}
					if ((r + 1 < 10) && isGuessValid(guessingBoard, (r + 1), c, playerCount, turnCount))
					{
						// if location is not already in ArrayList, add location to ArrayList
						if (guessSpaces.contains((r + 1) + " " + c) == false)
						{
							guessSpaces.add((r + 1) + " " + c);
						}
					}
					if ((c + 1 < 10) && isGuessValid(guessingBoard, r, (c + 1), playerCount, turnCount))
					{
						// if location is not already in ArrayList, add location to ArrayList
						if (guessSpaces.contains(r + " " + (c + 1)) == false)
						{
							guessSpaces.add(r + " " + (c + 1));
						}
					}
					if ((c - 1 > -1) && isGuessValid(guessingBoard, r, (c -1), playerCount, turnCount))
					{
						// if location is not already in ArrayList, add location to ArrayList
						if (guessSpaces.contains(r + " " + (c - 1)) == false)
						{
							guessSpaces.add(r + " " + (c -1));
						}
					}
				}
			}
		}
		
		if (guessSpaces.isEmpty() == false)    // if the ArrayList contains values
		{
			length = guessSpaces.size();     // get the num of guess options in ArrayList
			index = random.nextInt(length);    // get index of a random guess option 
			space = guessSpaces.get(index);    // retrieve guess option string
			guessSpaces.remove(index);    // remove guess option from the ArrayList
			// separate guess option into row and col values 
			rowCol= space.split(" ");
			row = Integer.parseInt(rowCol[0]);
			column = Integer.parseInt(rowCol[1]);
		}
		else    // if the ArrayList does not contain values 
		{
			// random number generate location to guess until location is valid 
			do {
				row = random.nextInt(10);
				column = random.nextInt(10);
			} while (isGuessValid(guessingBoard, row, column, playerCount, turnCount) == false);
		}
		
		System.out.println("\nComputer Guessed: " + convertNumToLetter(column) + (row + 1));
		
		// check if guess hits the opponent's ship
		if (isShipAtGuess(guessingBoard, locationBoard, ships, row, column, turnCount, playerCount) == true)
		{
			guessingBoard.updateBoard(row, column, 'X');    // place an 'X' on the board
			// check if ship was sunk by the most recent hit
			if (isShipSunk(guessingBoard, locationBoard, ships, row, column) == true)
			{
				String shipName = ships.detectShip(locationBoard, row, column);
				ships.updateShipsSunk();
				System.out.println("The Computer sunk the " + shipName + "!\n");
			}
		}
		else    // opponent's ship is not hit
		{
			guessingBoard.updateBoard(row, column, 'O');    // place a 'O' on the board
		}

		// print the updated board
		guessingBoard.printBoard();
		return;
	}
	
	public static boolean isWinner(Ships player1Ships, Ships player2Ships, int playerCount)
	/* 
	 * Function: checks if a user wins the game after sinking a ship  
	 */
	{
		if (player1Ships.getShipsSunk() == 5)    // checks if player 1 has sunk 5 ships 
		{
			System.out.println("\nPlayer 1 wins the game! Congrats!");
			return true;
		}
		else if (player2Ships.getShipsSunk() == 5)   // checks if player 2 (or computer) has sunk 5 ships 
		{
			if (playerCount == 2)    // player 2
			{
			System.out.println("\nPlayer 2 wins the game! Congrats!");
			}
			else    // computer
			{
				System.out.print("\nYou lose. The Computer has won the game.");
			}
			return true;
		}
		
		return false;    // no player has won the game 
	}
}

