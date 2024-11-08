package edu.grinnell.csc207.util;

/**
 * Contains helper methods used to run ticTacToe game for both pvp and
 * computer game modes.
 * @author Jafar Jarrar
 * @author Leonardo Alves Nunes
 */
public class GameUtils {

  /**
   * Carries value indicating whether it is player X's turn or player O's turn.
   * The value is
   * initialized based on user input in beginning of execution.
   */
  private static boolean playerTurn;

  /**
   * Indicates what turn is it to play (x or o).
   * @return the player turn.
   */
  public static boolean getPlayerTurn() {
    return playerTurn;
  } // getPlayerTurn()

  /**
   * Sets whose turn it is to play.
   * @param turn
   * Indicates whose turn it is (false for x, true for o).
   */
  public static void setPlayerTurn(boolean turn) {
    playerTurn = turn;
  } // setPlayerTurn(boolean)
  /**
   * Checks if the starting character chosen is either X or O.
   *
   * @param letter
   *               The character inputted by the user to be checked.
   * @return
   *         True if the character is X or O, false otherwise.
   */
  public boolean checkInput(char letter) {
    if (letter == 'x' || letter == 'o') {
      return true;
    } // if
    return false;
  } // checkInput(char)

  /**
   * Calculates if the winner is X or O by checking if any of the possible winning
   * combinations are
   * met in the matrix of values.
   *
   * @return
   *         'X' if X is the winner, 'O' if O is the winner, 'n' if it is a tie.
   */
  public static char winnerCalculator() {
    // Array containing sequences of positions in values matrix that must contain
    // all X's or all O's for a winner to be declared.
    int[][] possibilities = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8}, {2, 4, 6}, {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8}};
    for (int i = 0; i < possibilities.length; i++) {
      // Sets temporary array equal to one of the possible sequences,
      // retrieves the values at the given positions, and checks if they all
      // contain the same character to determine the winner.
      int[] winCase = possibilities[i];
      char pos1 = BoardUtils.getValue(winCase[0]);
      char pos2 = BoardUtils.getValue(winCase[1]);
      char pos3 = BoardUtils.getValue(winCase[2]);
      if (pos1 == pos2 && pos2 == pos3) {
        return pos1;
      } // if
    } // for
    return 'n'; // If no winner, returns 'n'
  } // winnerCalculator()

  /**
   * Sets characters in the values matrix based on the turn and position that the
   * user selects.
   *
   * @param position
   *                 The position that the user wants to fill with 'X' or 'O'.
   * @throws Exception
   *                   If the user tries to choose a position on the board that
   *                   has already been
   *                   taken.
   * @return boolean.
   */
  public static boolean playRound(int position) throws Exception {
    if (BoardUtils.getValue(position) != ' ') {
      throw new Exception(); // if there is already a value in the position
    } // if
    if (playerTurn) {
      BoardUtils.setValue(position, 'O');
      return false;
    } else {
      BoardUtils.setValue(position, 'X');
      return true;
    } // if
  } // playRound(int)
} // class GameUtils
