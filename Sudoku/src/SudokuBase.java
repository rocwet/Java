/**
 * The SudokuBase class is designed to hold the basic structure for a sudoku game and contains some abstract
 * methods.
 * 
 * @author Dan Jinugji
 * @author Minhhue H. Khuu
 * @version Assignment 7: Sudoku Final NORMAL VERSION
 * 
 */
public abstract class SudokuBase extends java.util.Observable implements java.io.Serializable {
  
  /** Rows contain the amount of rows in the sudoku board. */
  public final int rows;
  
  /** Columns contain the amount of columns in the the sudoku board. */
  public final int columns;
  
  /** Size contains the size of the sudoku board, which is rows * columns. */
  public final int size;
  
  /** Grid is an int array that contains a 1 D array that contains the values of the sudoku board. */
  private final int[] grid;
  
  // fixed constants
  private static final int GIVEN_MASK = 0x00000100;
  private static final int GIVEN_UNMASK = ~ GIVEN_MASK;
  
  /** The State of the Sudoku Board */
  public enum State {COMPLETE, INCOMPLETE, ERROR};
  
  /**
   * The constructor for the SudokuBase class, it will create intialize the rows, columns, size,
   * and create a grid based off the parameters.
   * 
   * @param layoutRows The amount rows for the sudoku board.
   * @param layoutColumns The amount of columns for the sudoku board.
   */
  public SudokuBase(int layoutRows, int layoutColumns) {
    rows = layoutRows;
    columns = layoutColumns;
    size = columns * rows;
    grid = new int[size*size];
  }
  
  /**
   * The getIndex() method will return the index number that is in the 1 D array using row and col as information.
   * 
   * @param row The row from the sudoku board.
   * @param col The column from the sudoku board.
   * @return The index of the 1 D array that contains the sudoku board.
   */
  private int getIndex(int row, int col) {
    if(row < 0 || row >= size || col < 0 || col >= size) {
      String msg = "Error in location";
      throw new IllegalArgumentException(msg);
    }
    return row * size + col;
  }
  
  /*
   * The getValue() method will return the number that is specifically assigned to the row and col that was specified.
   * 
   * @param row The row from the sudoku board.
   * @param col The column from the sudoku board.
   * @return The value of the number in the specified row and col from the sudoku board.
   */
  public int getValue(int row, int col) {
    return grid[getIndex(row, col)] & GIVEN_UNMASK;
  }
  
  /**
   * The setValue() method will set an int number at the specified row and col in the sudoku board.
   * 
   * @param row The row from the sudoku board.
   * @param col The columm from the sudoku board.
   * @param value The int number that will be set in the specified row and col in the sudoku board.
   */
  public void setValue(int row, int col, int value) {
    if(value < 0 || value > size) {
      String msg = "Value out of range: " + value;
      throw new IllegalArgumentException(msg);
    }
    if(isGiven(row, col)) {
      String msg = "Cannot set given location: " + row + ", " + col;
      throw new IllegalStateException(msg);
    }
    grid[getIndex(row, col)] = value;
    setChanged();
    notifyObservers();
  }
  
  /**
   * The isGiven() method will return if there is a value in a specified row and column in the sudoku board.
   * 
   * @param row The row from the sudoku board.
   * @param col The column from the sudoku board.
   * @return A boolean value that will determine if there is a value or not in the specified row and col.
   */
  public boolean isGiven(int row, int col) {
    return (grid[getIndex(row, col)] & GIVEN_MASK) == GIVEN_MASK;
  }
  
  /**
   * The fixGivens() method will cause an value that is not eqaul to 0 to be marked as given.
   */
  public void fixGivens() {
    for(int i = 0; i < grid.length; i++)
      if(grid[i] != 0) 
      grid[i] |= GIVEN_MASK;
    setChanged();
    notifyObservers();
  }
  
  // abstract methods that will be written by Minh
  
  /**
   * The getRowState() method will check what the row for the referred sudoku board is, Complete, Incomplete, or Error.
   * 
   * @param n The row that will be checked.
   * @return The state the row is in.
   */
  public abstract State getRowState(int n);
  
  /**
   * The getColumnState() method will check what the row for the referred sudoku board is, Complete, Incomplete, or Error.
   * 
   * @param n The column that will be checked.
   * @return The state the column is in.
   */
  public abstract State getColumnState(int n);
  
  /**
   * The getRegionState() method will check what the row for the referred sudoku board is, Complete, Incomplete, or Error.
   * 
   * @param n The region that will be checked.
   * @return The state the region is in.
   */
  public abstract State getRegionState(int n);
  
  /**
   * The toString() method will return a string that contains the information of the sudoku board in the sudoku board
   * format.
   * 
   * @return The value of the sudoku board in string format.
   */
  public String toString() {
    String board = "";
    for(int i = 0; i < size; i ++) {
      for(int j = 0; j < size; j ++)
        board += charFor(i, j) + " ";
      board += "\n";
    }
    return board;
  }
  
  /**
   * The charFor() method will return a String that represents an alphanumeric char based on the specified location
   * on the sudoku board.
   * 
   * @param i The row of the sudoku board.
   * @param j The colum of the sudoku board.
   * @return The string value that represents an alphanumeric char based on the specified location i and j.
   */
  private String charFor(int i, int j) {
    int v = getValue(i, j);
    if(v < 0) {
      return "?";
    } else if(v == 0) {
      return " ";
    } else if(v < 36) {
      return Character.toString(Character.forDigit(v, 36)).toUpperCase();
    } else {
      return "?";
    }
  }
  
  /**
   * The readFromStream() method will read from the input stream.
   * 
   * @param is The input stream.
   */
  protected void readFromStream(java.io.InputStream is) {
  }
  
  /**
   * The writeToStream() method will write byte to the output stream.
   * 
   * @param os The output stream.
   */
  protected void writeToStream(java.io.OutputStream os) {
  }
  
  /**
   * The getRawValue() will return a value and indirectly return a GIVEN MASk value based on the sudoku board location.
   *
   * @param row The row of the Sudoku Board.
   * @param col The column of the Sudoku Board.
   * @return The value and given mask value based on the specified row and column in the Sudoku Board.
   */
  protected int getRawValue(int row, int col) {
    return grid[getIndex(row, col)];
  }
  
  /**
   * The setRawValue() method will set a value and also indirectly set a GIVEN MASK value to a sudoku board location.
   * 
   * @param row The row of the Sudoku Board.
   * @param col The column of the Sudoku Board.
   * @param value The value to be associated with the specified row and column in the Sudoku Board.
   */
  protected void setRawValue(int row, int col, int value) {
    grid[getIndex(row, col)] = value;
  }
  
}
