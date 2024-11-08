package edu.grinnell.csc207.ourGame;

import edu.grinnell.csc207.util.BoardUtils;
import edu.grinnell.csc207.util.GameUtils;
import java.util.Random;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * An implementation of the classic tic tac toe game.
 *
 * @author Jafar Jarrar
 * @author Leonardo Alves Nunes
 */
public class TicTacToes {
  /**
   * Set the mode to play agains the computer or player vs player.
   */
  private static boolean gameMode;

  /**
   * Value showing how many rounds of the game have been played.
   */
  private static int turnsPlayed;

  /**
   * Swaps element to be "removed" with element at the end of the array.
   * @param arr
   * The array in which the element will be removed.
   * @param length
   * The size of the values that haven't been swapped in the array (to exclude
   * values that have been removed).
   * @param index
   * The index of the value to be shifted to the end of the array.
   * @return
   * The same array but with the value shifted to the end.
   */
  public static int[] shiftElement(int[] arr, int length, int index) {
    for (int i = 0; i < length; i++) {
      if (i == index) {
        int temp = arr[i];
        arr[i] = arr[length - 1];
        arr[length - 1] = temp;
        break;
      } // if
    } // for
    return arr;
  } // shiftElement(in[], int, int)

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
    BoardUtils.values = new MatrixV0<>(3, 3, ' ');
    turnsPlayed = 0;
    BoardUtils.setDefaultValues();

    pen.println("Let's play X-O!");

    // Asks user to select game mode.
    while (true) {
      pen.println(
          "If you want to play with a friend, type 0. To play against the computer, type 1:");
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
          GameUtils.playerTurn = false;
          break;
        } else if (starter.charAt(0) == 'o') {
          GameUtils.playerTurn = true;
          break;
        } else {
          System.err.println("Error: Invalid input. Please try again.");
        } // if
      } // while

      pen.println("Please enter the position you want based on the matrix below:");
      Matrix.print(pen, BoardUtils.defaultValues, false);
      while (turnsPlayed != 9) {
        String input = eyes.nextLine();
        int position = input.charAt(0) - 48;
        if (position < 0 || position > 8) {
          System.err.println("Error: Invalid input. Please enter a valid position.");
          continue;
        } // if
        try {
          GameUtils.playerTurn = GameUtils.playRound(position);
        } catch (Exception e) {
          System.err.println("Position already taken! Choose another position.");
          continue;
        } // try/catch
        turnsPlayed++;
        Matrix.print(pen, BoardUtils.values, false);
        winner = GameUtils.winnerCalculator();
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
      int[] availablePositions = new int[9];
      int positionsLeft = 9;
      for (int i = 0; i < 9; i++) {
        availablePositions[i] = i;
      } // for
      pen.println("PLAY AGAINST THE COMPUTER! BE PREPARED!!!");
      while (true) {
        pen.println(
            "Who will start the game? Type 0 if you want to start. Type 1 so computer starts.");
        String starter = eyes.nextLine();
        if (starter.charAt(0) == '0') {
          GameUtils.playerTurn = false;
          break;
        } else if (starter.charAt(0) == '1') {
          GameUtils.playerTurn = true;
          break;
        } else {
          System.err.println("Error: Invalid input. Please try again.");
        } // if
      } // while

      pen.println("Please enter the position you want based on the matrix below:");
      Matrix.print(pen, BoardUtils.defaultValues, false);
      while (turnsPlayed != 9) {
        // Execution if it is the user's turn.
        if (!GameUtils.playerTurn) {
          String input = eyes.nextLine();
          int position = input.charAt(0) - 48;
          if (position < 0 || position > 8) {
            System.err.println("Error: Invalid input. Please enter a valid position.");
            continue;
          } // if
          try {
            GameUtils.playerTurn = GameUtils.playRound(position);
          } catch (Exception e) {
            System.err.println("Position already taken! Choose another position.");
            continue;
          } // try/catch
        } else {
          int randomIndex = 0;
          int randomPos;
          // Keeps trying ranodm positions from the available ones.
          while (true) {
            try {
              randomIndex = rand.nextInt(positionsLeft);
              randomPos = availablePositions[randomIndex];
              GameUtils.playerTurn = GameUtils.playRound(randomPos);
              shiftElement(availablePositions, positionsLeft--, randomIndex);
              break;
            } catch (Exception e) {
              shiftElement(availablePositions, positionsLeft--, randomIndex);
            } // try
          } // while
        } // else

        turnsPlayed++;
        Matrix.print(pen, BoardUtils.values, false);
        winner = GameUtils.winnerCalculator();
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
