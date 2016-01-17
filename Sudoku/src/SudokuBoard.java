/**
 * The SudokuBoard class will create a sudoku board, and checks the state of the rows, columns, and regions;
 * it extends from the sudoku base.
 * 
 * @author Minhhue H. Khuu
 * @version Assignment 7: Sudoku Final NORMAL VERSION
 */
public class SudokuBoard extends SudokuBase {
  
  /**
   * The contructor for the SudokuBoard class which uses the SudokuBase's constructor as its own, it constructs a sudoku board.
   * 
   * @param r The amount of rows desired for the newly created Sudoku Board.
   * @param c The amount of columns dersired for the newly created Sudoku Board.
   */
   public SudokuBoard(int r, int c) {
      super(r, c);
   }  // end of constructor.
   
   /**
    * The getRowState() method will check what the row for the referred sudoku board is, Complete, Incomplete, or Error.
    * 
    * @param n The row that will be checked.
    * @return The state the row is in.
    */
   public State getRowState(int n) { 
     
     // intialize a new int array to place values in.
     int[] nums = new int[size];
     
     // intialize i value for indexing to put values of a region into an int array.
     int i = 0;  
     
     // goes through the whole column.
     // finds duplicates.
     for(int col = 0; col < size; col++) {
       nums[i++] = getValue(n, col);
     }
     
     // uses the check method to see if complete, incomplete, or error and returns the status.
     return check(nums);
     
   } // end of getRowState() method.
   
   /**
    * The getColumnState() method will check what the row for the referred sudoku board is, Complete, Incomplete, or Error.
    * 
    * @param n The column that will be checked.
    * @return The state the column is in.
    */
   public State getColumnState(int n) { 
     
     // intialize a new int array to place values in.
     int[] nums = new int[size];
     
     // intialize i value for indexing to put values of a region into an int array.
     int i = 0;  
     
     // goes through the whole column.
     // finds duplicates.
     for(int row = 0; row < size; row++) {
       nums[i++] = getValue(row, n);
     }
     
     // uses the check method to see if complete, incomplete, or error and returns the status.
     return check(nums);
     
   }  // end of getColumnState() method.
   
   
   /**
    * The getRegionState() method will check what the row for the referred sudoku board is, Complete, Incomplete, or Error.
    * 
    * @param n The region that will be checked.
    * @return The state the region is in.
    */
   public State getRegionState(int n) { 
     
     // intialize a new int array to place values in.
     int[] nums = new int[size];
     
     // intialize i value for indexing to put values of a region into an int array.
     int i = 0;
     
     // put values in region into one int array.
     for(int row = rows * (n/rows); row < rows * ( 1 + n/rows ); row++) {                  // integer division
       for(int col = columns * (n%rows); col < columns * ( 1 + n%rows); col++) {           // modulo operator
         nums[i++] = getValue(row, col);
       }
     }
     
     // uses the check method to see if complete, incomplete, or error and returns the status.
     return check(nums);
     
   }  // end of getRegionState() method.

   /**
    * The check method will check an int array to see if there are duplicates or any zeroes, this method is geared towards
    * the get***State() methods in the Sudoku Board Class; this is for the CHALLENGE VERSION.
    * 
    * @nums The int array of values of a row, column, or region which should be created from the get***State() methods 
    * in the SudokuBoard class.
    * @return The state of the nums int array, it is Error if there are duplicates, it is Incomplete if there are no errors
    * and there are zeroes, and it is Complete if it does not have duplicates or zeroes.
    */
   private State check(int[] nums) {
     
     // intialize value to check if error or incomplete.
     // 0 = complete.
     // 1 = incomplete.
     // 2 = error.
     int state = 0;
     
     // check for duplicates.
     for(int j = 0; j < nums.length; j++) {
       for(int k = j + 1; k < nums.length; k++) {
         if(nums[j] == nums[k] && nums[j] != 0) {
           state = 2;
         }
       }
     }
     
     // check for zeroes.
     if(state != 2) {
       for(int j = 0; j < nums.length; j++) {
         if(nums[j] == 0) {
           state = 1;
         }
       }
     }
     
     // returns thes state of the int array nums.
     if(state == 0) {  
       return State.COMPLETE;
     } else if(state == 1) {
       return State.INCOMPLETE;
     } else {
       return State.ERROR;
     }
     
   }  // end of check method.
   
}  // end of SudokuBoard class.