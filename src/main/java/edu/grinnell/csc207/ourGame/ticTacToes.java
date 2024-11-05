package edu.grinnell.csc207.ourGame;

import java.io.IOException;

import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;
import java.io.PrintWriter;
import java.lang.Integer;
import java.util.Scanner;


public class ticTacToes {

  
  private static MatrixV0<Integer> defaultValues;

  private static MatrixV0<Character> values;

  private static boolean playerTurn;

  private static int turnsPlayed;

  public ticTacToes () {
    values = new MatrixV0<>(3, 3);
  } // ticTacToes()

  public static void setDefaultValues() throws Exception{
    defaultValues = new MatrixV0<Integer>(3, 0);
    defaultValues.insertRow(0, new Integer[] {0, 1, 2});
    defaultValues.insertRow(1, new Integer[] {3, 4, 5});
    defaultValues.insertRow(2, new Integer[] {6, 7, 8});
  } // setDefaultValues()

  public static void setValue (int position, char letter) {
    int rowPosition = position / 3;
    int colPosition = position % 3;
    values.set(rowPosition, colPosition, letter);
  } // setValue(int)

  public static char getValue (int position) {
    int rowPosition = position / 3;
    int colPosition = position % 3;
    return values.get(rowPosition, colPosition);
  } // getValue(int, int)

  public boolean checkInput (char letter) {
    if (letter == 'x' || letter =='o') {
      return true;
    } // if
    return false;
  } // checkInput(char)

  public static char winnerCalculator () {
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

  public static void main(String[] args) throws Exception {
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
