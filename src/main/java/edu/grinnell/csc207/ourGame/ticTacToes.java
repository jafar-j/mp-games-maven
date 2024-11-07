package edu.grinnell.csc207.ourGame;

import java.io.IOException;
import java.util.ArrayList;

import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
import edu.grinnell.csc207.util.boardUtils;
import edu.grinnell.csc207.util.ticTacUtils;
import java.util.Random;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;
import java.io.PrintWriter;
import java.lang.Integer;
import java.util.Scanner;

/**
 * An implementation of the classic tic tac toe game.
 * 
 * @author Jafar Jarrar
 * @author Leonardo Alves Nunes
 */
public class ticTacToes {

  /**
   * Carries value indicating whether it is player X's turn or player O's turn. The value is
   * initialized based on user input in beginning of execution.
   */
  private static boolean playerTurn;

  /**
   * Set the mode to play agains the computer or player vs player.
   */
  private static boolean gameMode;

  /**
   * Value showing how many rounds of the game have been played.
   */
  private static int turnsPlayed;

  private static ArrayList<Integer> valuesAvailable;


  /**
   * Checks if the starting character chosen is either X or O.
   * 
   * @param letter The character inputted by the user to be checked.
   * @return True if the character is X or O, false otherwise.
   */
  public boolean checkInput(char letter) {
    if (letter == 'x' || letter == 'o') {
      return true;
    } // if
    return false;
  } // checkInput(char)

  /**
   * Calculates if the winner is X or O by checking if any of the possible winning combinations are
   * met in the matrix of values.
   * 
   * @return 'X' if X is the winner, 'O' if O is the winner, 'n' if it is a tie.
   */
  public static char winnerCalculator() {
    // Array containing sequences of positions in values matrix that must contain
    // all X's or all O's for a winner to be declared.
    int[][] possibilities =
        {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};
    for (int i = 0; i < possibilities.length; i++) {
      // Sets temporary array equal to one of the possible sequences,
      // retrieves the values at the given positions, and checks if they all
      // contain the same character to determine the winner.
      int[] winCase = possibilities[i];
      char pos1 = boardUtils.getValue(winCase[0]);
      char pos2 = boardUtils.getValue(winCase[1]);
      char pos3 = boardUtils.getValue(winCase[2]);
      if (pos1 == pos2 && pos2 == pos3) {
        return pos1;
      } // if
    } // for
    return 'n'; // If no winner, returns 'n'
  } // winnerCalculator()

  /**
   * Sets characters in the values matrix based on the turn and position that the user selects.
   * @param position The position that the user wants to fill with 'X' or 'O'.
   * @throws Exception If the user tries to choose a position on the board that has already been
   *         taken.
   */
  public static boolean playRound (int position) throws Exception {
    if (boardUtils.getValue(position) != ' ') {
      throw new Exception(); // if there is already a value in the position
    } // if
    if (playerTurn) {
      boardUtils.setValue(position, 'O');
      return false;
    } else {
      boardUtils.setValue(position, 'X');
      return true;
    } // if
  } // playRound(int)

  /**
   * Prints out the game guide for the user(s) and runs the game until a winner is determined or the
   * board is filled (it is a tie).
   * 
   * @param args Command-line arguments (ignored).
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    Scanner eyes = new Scanner(System.in);
    char winner;
    boardUtils.values = new MatrixV0<>(3, 3, ' ');
    turnsPlayed = 0;
    boardUtils.setDefaultValues();

    pen.println("Let's play X-O!");

    // Asks user to select game mode.
    while (true) {
      pen.println(
          "If you want to play with a friend, type 0. If you want to play against the computer, type 1:");
      String starter = eyes.nextLine();
      if (starter.charAt(0) == '0') {
        gameMode = false;
        break;
      } else if (starter.charAt(0) == '1') {
        gameMode = true;
        break;
      } else {
        System.err.println("Error: Invalid input. Please try again.");
      } // if
    } // while

    // Normal two-player game mode code execution.
    if (!gameMode) {
      while (true) {
        pen.println(
            "Who will start the game? X or O? Please use lowercase letters when entering X or O.");
        String starter = eyes.nextLine();
        if (starter.charAt(0) == 'x') {
          playerTurn = false;
          break;
        } else if (starter.charAt(0) == 'o') {
          playerTurn = true;
          break;
        } else {
          System.err.println("Error: Invalid input. Please try again.");
        } // if
      } // while

      pen.println("Please enter the position you want based on the matrix below:");
      Matrix.print(pen, boardUtils.defaultValues, false);
      while (turnsPlayed != 9) {
        String input = eyes.nextLine();
        int position = input.charAt(0) - 48;
        if (position < 0 || position > 8) {
          System.err.println("Error: Invalid input. Please enter a valid position.");
          continue;
        } // if
        try {
          playerTurn = playRound(position);
        } catch (Exception e) {
          System.err.println("Position already taken! Choose another position.");
          continue;
        } // try/catch
        turnsPlayed++;
        Matrix.print(pen, boardUtils.values, false);
        winner = winnerCalculator();
        if (winner == 'X') {
          pen.println("X has won the game!");
          return;
        } else if (winner == 'O') {
          pen.println("O has won the game!");
          return;
        } // if
      } // while
      pen.println("It is a tie! Thank you for playing.");

    } else {
      // Code execution for computer vs user game mode.
      Random rand = new Random();
      ArrayList<Integer> availableNumbers = new ArrayList<>();
      for (int i = 0; i < 9; i++) {
        availableNumbers.add(i);
      } // for
      pen.println("PLAY AGAINST THE COMPUTER! BE PREPARED!!!");
      while (true) {
        pen.println(
            "Who will start the game? Type 0 if you want to start. Type 1 if you want the computer to start.");
        String starter = eyes.nextLine();
        if (starter.charAt(0) == '0') {
          playerTurn = false;
          break;
        } else if (starter.charAt(0) == '1') {
          playerTurn = true;
          break;
        } else {
          System.err.println("Error: Invalid input. Please try again.");
        } // if
      } // while
      
      pen.println("Please enter the position you want based on the matrix below:");
      Matrix.print(pen, boardUtils.defaultValues, false);
      while (turnsPlayed != 9) {
        // Execution if it is the user's turn.
        if (!playerTurn) {
          String input = eyes.nextLine();
          int position = input.charAt(0) - 48;
          if (position < 0 || position > 8) {
            System.err.println("Error: Invalid input. Please enter a valid position.");
            continue;
          } // if
          try {
            playerTurn = playRound(position);
            availableNumbers.remove(availableNumbers.indexOf(position));
          } catch (Exception e) {
            System.err.println("Position already taken! Choose another position.");
            continue;
          } // try/catch
        } else {
          // Execution if it is the computer's turn.
          int randomIndex = rand.nextInt(availableNumbers.size());
          int randomPos = availableNumbers.get(randomIndex);

          try {
            playerTurn = playRound(randomPos);
            availableNumbers.remove(availableNumbers.indexOf(randomPos));
          } catch (Exception e) {
            continue;
          } // try
        } // else

        turnsPlayed++;
        Matrix.print(pen, boardUtils.values, false);
        winner = winnerCalculator();
        if (winner == 'X') {
          pen.println("X has won the game!");
          return;
        } else if (winner == 'O') {
          pen.println("O has won the game!");
          return;
        } // if
      } // while
      pen.println("It is a tie! Thank you for playing.");
    } // else
  } // main(String[])
} // class ticTacToes
