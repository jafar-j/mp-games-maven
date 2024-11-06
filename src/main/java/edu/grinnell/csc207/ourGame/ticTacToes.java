package edu.grinnell.csc207.ourGame;

import java.io.IOException;

import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
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
   * 3x3 Matrix carrying the positions to choose from to guide players of the game.
   */
  private static MatrixV0<Integer> defaultValues;

  /**
   * The "game board" to play on. It is a 3x3 matrix that is filled with
   * characters depending on input as the game is played.
   */
  private static MatrixV0<Character> values;

  /**
   * Carries value indicating whether it is player X's turn or player O's turn.
   * The value is initialized based on user input in beginning of execution.
   */
  private static boolean playerTurn;

  /**
   * Value showing how many rounds of the game have been played.
   */
  private static int turnsPlayed;

  /**
   * Initialized and fills the default matrix with the position integers using the
   * matrix operations. 
   */
  public static void setDefaultValues(){
    defaultValues = new MatrixV0<Integer>(3, 0);
    try {
      defaultValues.insertRow(0, new Integer[] {0, 1, 2});
      defaultValues.insertRow(1, new Integer[] {3, 4, 5});
      defaultValues.insertRow(2, new Integer[] {6, 7, 8});
    } catch (Exception e) {
      // Does nothing.
    } // try/catch
  } // setDefaultValues()

  /**
   * Sets value at position given by user to 'X' or 'O' based on the turn.
   * @param position
   * Value inputted by user that is converted to
   * separate horizontal and vertical matrix coordinates.
   * @param letter
   * X or O depending on the turn.
   */
  public static void setValue (int position, char letter) {
    int rowPosition = position / 3;
    int colPosition = position % 3;
    values.set(rowPosition, colPosition, letter);
  } // setValue(int)

  /**
   * Gets the character value at the position to help with determining the winner.
   * @param position
   * int that the character is to be retrieved from.
   * @return
   * The character at the given position.
   */
  public static char getValue (int position) {
    int rowPosition = position / 3;
    int colPosition = position % 3;
    return values.get(rowPosition, colPosition);
  } // getValue(int, int)

  /**
   * Checks if the starting character chosen is either X or O.
   * @param letter
   * The character inputted by the user to be checked.
   * @return
   * True if the character is X or O, false otherwise.
   */
  public boolean checkInput (char letter) {
    if (letter == 'x' || letter =='o') {
      return true;
    } // if
    return false;
  } // checkInput(char)

  /**
   * Calculates if the winner is X or O by checking if any of the possible
   * winning combinations are met in the matrix of values.
   * @return
   * 'X' if X is the winner, 'O' if O is the winner, 'n' if it is a tie.
   */
  public static char winnerCalculator () {
    // Array containing sequences of positions in values matrix that must contain
    // all X's or all O's for a winner to be declared.
    int[][] possibilities = {
      {0, 1, 2},
      {3, 4, 5},
      {6, 7, 8},
      {0, 4, 8},
      {2, 4, 6},
      {0, 3, 6},
      {1, 4, 7},
      {2, 5, 8}
    };
    for (int i = 0; i < possibilities.length; i++) {
      // Sets temporary array equal to one of the possible sequences,
      // retrieves the values at the given positions, and checks if they all
      // contain the same character to determine the winner.
      int[] winCase = possibilities[i];
      char pos1 = getValue(winCase[0]);
      char pos2 = getValue(winCase[1]);
      char pos3 = getValue(winCase[2]);
      if (pos1 == pos2 && pos2 == pos3) {
        return pos1;
      } // if
    } // for
    return 'n'; // If no winner, returns 'n'
  } // winnerCalculator()

  /**
   * Sets characters in the values matrix based on the turn and
   * position that the user selects.
   * @param position
   * The position that the user wants to fill with 'X' or 'O'.
   * @throws Exception
   * If the user tries to choose a position on the board that has
   * already been taken.
   */
  public static void playRound (int position) throws Exception {
    if (getValue(position) != ' ') {
      throw new Exception(); // if there is already a value in the position
    } // if
    if (playerTurn) {
      setValue(position, 'O');
      playerTurn = false;
    } else {
      setValue(position, 'X');
      playerTurn = true;
    } // if
  } // playGame()

  /**
   * Prints out the game guide for the user(s) and runs the game until a winner is
   * determined or the board is filled (it is a tie).
   * @param args
   * Command-line arguments (ignored).
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    Scanner eyes = new Scanner(System.in);
    char winner;
    values = new MatrixV0<>(3, 3, ' ');
    turnsPlayed = 0;
    setDefaultValues();

    pen.println("Let's play X-O!");
    // Adding interactiveness cause why not.
    while (true) {
      pen.println("Who will start the game? X or O? Please use lowercase letters when entering X or O.");
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
    Matrix.print(pen, defaultValues, false);
    while (turnsPlayed != 9) {
      String input = eyes.nextLine();
      int position = input.charAt(0) - 48;
      if (position < 0 || position > 9) {
        System.err.println("Error: Invalid input. Please enter a valid position.");
        continue;
      } // if
      try {
        playRound(position);
      } catch (Exception e) {
        System.err.println("Position already taken! Choose another position.");
        continue;
      } // try/catch
      turnsPlayed++;
      Matrix.print(pen, values, false);
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
  } // main(String[])

} // class ticTacToes
