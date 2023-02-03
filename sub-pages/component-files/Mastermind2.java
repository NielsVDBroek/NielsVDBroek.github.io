package Mastermind2;

import java.util.Scanner;
import java.util.Random;

public class Mastermind2 {

	// Welcome text with instructions.
	public static void text() {
		System.out.print("\u001b[1;37m");
		System.out.println("Welcome to Mastermind!");
		System.out.println("Try guessing the secret code.");
		System.out.println("For each guess you have to type a color.");
		System.out.println("The colors are: Red, Blue, Green, Yellow, Orange and Purple.");
		System.out.println("When you guess a correct color in the correct position, you will get a hint saying: Black.");
		System.out.println("When you guess a correct color but not in the correct position, you will get a hint saying: White.");
		System.out.println("Guess the 4 colors in the correct positions to win!");
		System.out.println("Good Luck!");
		System.out.println("");
	}

	// Making scanner.
	public static Scanner scan = new Scanner(System.in);

	// The method for playing the game.
	public static boolean playGame() {
		// Making random.
		Random random = new Random();
		// Declaring variables and arrays.
		String arrayColors[] = { "red", "blue", "green", "yellow", "orange", "purple" }; // The colors. (easy for checking if the user input is a valid color)
		String arrayCodeColors[] = new String[4]; // The array with the generated colors code.
		String arrayGuesses[] = new String[4]; // The array where the guess will be stored.

		// When the guessed colors are not in the correct position, they will be put in this array so it can check if they are somewhere else in the code.
		String arrayCollectOtherColors[] = new String[4];
		String arrayCheckForWhite[] = new String[4]; // In this array the colors of the code that have not been guessed are placed here.
		String arrayCheckingBoxSolution[] = { "black", "black", "black", "black" }; // The array with what colors the pins need to be to win.
		String arrayCheckingBox[] = { "none", "none", "none", "none" }; // The hints will be placed within this array.

		String guess;
		int randomNumber = 0;
		int row = 1;
		int maxRounds = 10;
		boolean gameEndWon = false;
		boolean gameEndLost = false;
		boolean gameEndBreak = false;
		String askMultiplayer = "none";
		boolean multiplayer = false;
		String codeInput;

		String arrayGuessesAndCheckingBox[][] = new String[maxRounds][9];
		String colorCircleGuesses = "none";
		String colorCircleCheckingBox = " ";

		// Asking for Mutliplayer or Singleplayer.
		while (askMultiplayer.equals("none")) {
			System.out.println("Do you want to play this game in singleplayer or multiplayer");
			System.out.println("Type singleplayer or multiplayer");
			askMultiplayer = scan.nextLine().toLowerCase();
			if (askMultiplayer.equals("multiplayer")) {
				multiplayer = true;
				System.out.println("This game is now multiplayer.");
			} else if (askMultiplayer.equals("singleplayer")) {
				multiplayer = false;
				System.out.println("This game is now singleplayer.");
				System.out.println("");
			} else
				askMultiplayer = "none";
		}

		// The code for when player wants Multiplayer.
		// One person will make the code.
		if (multiplayer) {
			System.out.println("Player 1 will now make a secret code without player 2 looking.");
			System.out.println("");
			for (int i = 0; i < 4;) {
				System.out.print("Code place " + (i + 1) + ":");
				codeInput = scan.nextLine().toLowerCase();
				// If player input is break, the game ends immediately
				if (codeInput.equals("break")) {
					gameEndBreak = true;
					i = 4;
				}
				if (codeInput.equals("help")) {
					text();
				}
				// Checking if the input is valid
				for (int j = 0; j < 6; j++) {
					if (codeInput.equals(arrayColors[j])) {
						arrayCodeColors[i] = codeInput;
						i++;
						break;
					}

					// Print a line which says which colors to use.
					else if (j == 5 && !gameEndBreak) {
						System.out.println("Please enter one of the following colors: red, blue, green, yellow, orange or purple!");
						System.out.println("If you want to read the instructions again type: help");
						System.out.println("Or if you want to stop the game, type: break");
					}
				}
			}
			// Prints 50 blank rows to make sure the other player can't see the code made by the other person.
			for (int i = 0; i < 75; ++i) {
				System.out.println();
			}
			System.out.println("Player 2 will now try to guess the secret code!");

			// If the game is singleplayer, a random code will be generated.
		} else if (!multiplayer) {
			// Generating a random code.
			for (int i = 0; i < 4; i++) {
				randomNumber = random.nextInt(6);
				arrayCodeColors[i] = arrayColors[randomNumber];
			}
			System.out.println("A random code has been generated.");
			System.out.println("Try guessing the code!");
		}

		// The loop for the game which goes on until the player wins or the set amount of rounds has been reached
		while (!gameEndWon && !gameEndLost && !gameEndBreak) {
			System.out.println();
			// Making the text for which guess it is
			System.out.println("Row " + row);
			for (int i = 0; i < 4;) {
				System.out.print("Guess" + (i + 1) + ":");
				guess = scan.nextLine().toLowerCase();
				// If player input is break, the game ends immediately
				if (guess.equals("break")) {
					gameEndBreak = true;
					i = 4;
				}
				if (guess.equals("help")) {
					text();
				}
				// Checking if the input is valid
				for (int j = 0; j < 6; j++) {
					if (guess.equals(arrayColors[j])) {
						arrayGuesses[i] = guess;
						i++;
						break;
					}

					// Print a line which says which colors to use.
					else if (j == 5 && !gameEndBreak) {
						System.out.println("Please enter one of the following colors: red, blue, green, yellow, orange or purple!");
						System.out.println("If you want to read the instructions again type: help");
						System.out.println("Or if you want to stop the game, enter: break");
					}
				}
			}
			System.out.println("");

			// If the player had typed break, the game will end.
			if (gameEndBreak) {
				break;
			}
			// The guesses will be entered into the 2D array each round.
			// The colors will be turned in to colored circles in the array.
			for (int i = 0; i < 4; i++) {
				switch (arrayGuesses[i]) {
				case "red":
					colorCircleGuesses = "\u001B[31m \u26AB \u001b[1;37m";
					break;
				case "blue":
					colorCircleGuesses = "\u001b[1;34m \u26AB \u001b[1;37m";
					break;
				case "green":
					colorCircleGuesses = "\u001b[1;32m \u26AB \u001b[1;37m";
					break;
				case "yellow":
					colorCircleGuesses = "\u001b[1;33m \u26AB \u001b[1;37m";
					break;
				case "orange":
					colorCircleGuesses = "\u001b[38;5;214m \u26AB \u001b[1;37m";
					break;
				case "purple":
					colorCircleGuesses = "\u001b[1;35m \u26AB \u001b[1;37m";
					break;
				}
				arrayGuessesAndCheckingBox[(row - 1)][i] = colorCircleGuesses;
			}

			// Making another array where the code colors will be placed(for making it easy to check for white).
			for (int i = 0; i < 4; i++) {
				arrayCheckForWhite[i] = arrayCodeColors[i];
			}

			// Emptying the checking box each round.
			for (int i = 0; i < 4; i++) {
				arrayCheckingBox[i] = "none";
			}

			int x = 0;
			// Checking if a guess is correct, else the other colors will be taken to check if the color is somewhere else in the code.
			for (int i = 0; i < 4; i++) {
				if (arrayGuesses[i].equals(arrayCodeColors[i])) {
					arrayCheckingBox[x] = "black";
					x++;
					arrayCheckForWhite[i] = "none";
					arrayCollectOtherColors[i] = "none";
				} else {
					arrayCollectOtherColors[i] = arrayGuesses[i];
				}
			}
			// Checking for white.(if the colors are correct but not in the correct place)
			for (int i = 0; i < 4; i++) {
				for (int y = 0; y < 4; y++) {
					if (arrayCollectOtherColors[i].equals("none")) {
						// Nothing happens, move on to the next.
					} else if (arrayCollectOtherColors[i].equals(arrayCheckForWhite[y])) {
						arrayCheckingBox[x] = "white";
						x++;
						arrayCollectOtherColors[i] = "none";
						arrayCheckForWhite[y] = "none";
					}
				}
			}

			// The hints from the checking box will be placed into the 2D array
			for (int i = 0; i < 4; i++) {
				switch (arrayCheckingBox[i]) {
				case "none":
					colorCircleCheckingBox = " ";
					break;
				case "black":
					colorCircleCheckingBox = "\u001b[1;30m \u26AB \u001b[1;37m";
					break;
				case "white":
					colorCircleCheckingBox = "\u001b[1;37m \u26AB \u001b[1;37m";
					break;
				}
				arrayGuessesAndCheckingBox[(row - 1)][(i + 5)] = colorCircleCheckingBox;
			}

			// The 2D array with the guesses and checkingBox will be printed here.
			for (int i = 1; i < (row + 1); i++) {
				if (i == 10) {
					System.out.print("Row " + (i) + " | ");
				} else {
					System.out.print("Row " + (i) + "  | ");	
				}
				for (int y = 0; y < 9; y++) {
					if (arrayGuessesAndCheckingBox[(i - 1)][y] == null) {
						System.out.print("| ");
					} else {
						System.out.print(arrayGuessesAndCheckingBox[(i - 1)][y] + " ");
					}
				}
				System.out.println("");
			}

			// Checking if the player has 4 black pins.
			int blackPins = 0;
			for (int i = 0; i < 4; i++) {
				if (arrayCheckingBox[i].equals(arrayCheckingBoxSolution[i])) {
					blackPins++;
				}
			}

			// If the player doesn't have 4 black pins, the game continues and the array of the hints gets cleared.
			if (blackPins == 4) {
				gameEndWon = true;
			}

			// If the player has played the max amount of rounds, the game ends
			if (row == maxRounds && !gameEndWon) {
				gameEndLost = true;
			} else {
				row++;
			}
		}

		// Player wins text
		if (gameEndWon) {
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("CONGRATULATIONS, YOU WON!");
		}

		// Player loses text
		if (gameEndLost) {
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("YOU LOST!");
		}

		// Showing the player(s) the code after the game
		if (gameEndWon || gameEndLost) {
			System.out.println(" ");
			System.out.println("THE CODE WAS:");
			for (int i = 0; i < 4; i++) {
				switch (arrayCodeColors[i]) {
				case "red":
					colorCircleGuesses = "\u001B[31m \u26AB \u001b[1;37m";
					break;
				case "blue":
					colorCircleGuesses = "\u001b[1;34m \u26AB \u001b[1;37m";
					break;
				case "green":
					colorCircleGuesses = "\u001b[1;32m \u26AB \u001b[1;37m";
					break;
				case "yellow":
					colorCircleGuesses = "\u001b[1;33m \u26AB \u001b[1;37m";
					break;
				case "orange":
					colorCircleGuesses = "\u001b[38;5;214m \u26AB \u001b[1;37m";
					break;
				case "purple":
					colorCircleGuesses = "\u001b[1;35m \u26AB \u001b[1;37m";
					break;
				}
				System.out.print(colorCircleGuesses + " ");
			}
		}

		// Player break text
		if (gameEndBreak) {
			System.out.println(" ");
			System.out.println("THE GAME HAS STOPPED!");
			System.out.println("");
		}

		if (gameEndWon) {
			return true;
		} else {
			return false;
		}
	}

	// The game will be played here.
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean playAgain = true;
		String askToPlayAgain;
		int totalGamesPlayed = 0;
		int totalWins = 0;

		// The instructions will be placed here.
		text();

		// After each game the player will be asked if they want to play again.
		// If they want to play again, the game will restart but keep the scores.
		while (playAgain) {
			boolean won = playGame();
			totalGamesPlayed++;
			if (won) {
				totalWins++;
			}
			// Asking if the player(s) want to play again.
			askToPlayAgain = "No Answer";
			while (askToPlayAgain.equals("No Answer")) {
				System.out.println("");
				System.out.println("Do you want to play again?");
				System.out.println("Yes or No");
				System.out.println("");
				System.out.print("Play again: ");
				askToPlayAgain = scan.nextLine().toLowerCase();
				if (askToPlayAgain.equals("yes")) {
					playAgain = true;
				}
				// Prints the scores after the player(s) dont want to play again.
				else if (askToPlayAgain.equals("no")) {
					playAgain = false;
					System.out.println("GAME HAS ENDED");
					System.out.println("");
					System.out.println("Games Played:" + totalGamesPlayed);
					System.out.println("Games Won: " + totalWins);
					System.out.println("Games Lost: " + (totalGamesPlayed - totalWins));
				} else {
					askToPlayAgain = "No Answer";
				}
				System.out.println("");
			}
		}
		scan.close();
	}
}