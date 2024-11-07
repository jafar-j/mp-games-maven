package edu.grinnell.csc207.util;

public class boardUtils {

    /**
   * The "game board" to play on. It is a 3x3 matrix that is filled with characters depending on
   * input as the game is played.
   */
  public static MatrixV0<Character> values;

  /**
   * 3x3 Matrix carrying the positions to choose from to guide players of the
   * game.
   */
  public static MatrixV0<Integer> defaultValues;

  /**
   * Initialized and fills the default matrix with the position integers using the
   * matrix
   * operations.
   */
  public static void setDefaultValues() {
    defaultValues = new MatrixV0<Integer>(3, 0);
    try {
      defaultValues.insertRow(0, new Integer[] { 0, 1, 2 });
      defaultValues.insertRow(1, new Integer[] { 3, 4, 5 });
      defaultValues.insertRow(2, new Integer[] { 6, 7, 8 });
    } catch (Exception e) {
      // Does nothing.
    } // try/catch
  } // setDefaultValues()

  /**
   * Sets value at position given by user to 'X' or 'O' based on the turn.
   * 
   * @param position Value inputted by user that is converted to separate
   *                 horizontal and vertical
   *                 matrix coordinates.
   * @param letter   X or O depending on the turn.
   */
  public static void setValue(int position, char letter) {
    int rowPosition = position / 3;
    int colPosition = position % 3;
    values.set(rowPosition, colPosition, letter);
  } // setValue(int)

  /**
   * Gets the character value at the position to help with determining the winner.
   * 
   * @param position int that the character is to be retrieved from.
   * @return The character at the given position.
   */
  public static char getValue(int position) {
    int rowPosition = position / 3;
    int colPosition = position % 3;
    return values.get(rowPosition, colPosition);
  } // getValue(int, int)
}
