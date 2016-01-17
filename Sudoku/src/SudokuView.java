/**
 * The SudokuView class will create a sudoku board graphic in a window.
 * 
 * @author Minhhue H. Khuu
 * @version Assignment 7: Sudoku Final NORMAL VERSION
 */
public class SudokuView extends javax.swing.JPanel implements java.util.Observer {
  
  // fields
  private int row;
  private int col;
  private int size;
  private int srow;
  private int scol;
  private javax.swing.JPanel sudoku;
  public javax.swing.JPanel[] cells;
  private javax.swing.JLabel[] labels;
  private boolean selected;
  private boolean symbols;
  private SudokuBase s;
  private Symbols[] sym;
  
  /**
   * The SudokuView constructor will create a panel that contains the information of the SudokuBase and displays it 
   * graphically.
   * @param sb The sudoku base.
   */
  public SudokuView(SudokuBase sb) {

    // initialize fields
    s = sb;
    row = s.rows; 
    col = s.columns;
    size = s.size;
    cells = new javax.swing.JPanel[size*size];
    labels = new javax.swing.JLabel[size*size];
    symbols = true;
    sym = new Symbols[size*size];
      
    // make the window the preferred size
    this.setPreferredSize(new java.awt.Dimension(100 + size * 52 , 100 + size * 52));
    sudoku = new javax.swing.JPanel(new java.awt.GridLayout(size,size, 2, 2));
    sudoku.setLocation(52, 52);
    sudoku.setSize(52*size - 1, 52*size -1);
    sudoku.setOpaque(false);
    
    // goes through every cell
    for( int i = 0; i < size*size; i++ ) {
      
      // creates a new panel for each cell in the cells array
      cells[i] = new javax.swing.JPanel();
      cells[i].setPreferredSize(new java.awt.Dimension(50, 50));
      cells[i].setOpaque(false);                                        // transparent
      cells[i].setVisible(true);
      sym[i] = new Symbols(0);
      cells[i].add(sym[i]);
      
      // creates a new label for each cell in the label rray
      labels[i] = new javax.swing.JLabel();
      labels[i].setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 24));
      labels[i].setHorizontalAlignment( javax.swing.JLabel.CENTER );
      labels[i].setVerticalAlignment( javax.swing.JLabel.CENTER );
      
      // adds a mouse listener to each cell
      // creates an anonymouse inner class to add to each cell in the cells array
      cells[i].addMouseListener( new java.awt.event.MouseAdapter() {
        
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
          
          // determines row and col based on the position of the event.
          // gets the location of the sudoku board and subtracts it from the mouse location to fix the scaling
          int rowy = (e.getYOnScreen() - (int)sudoku.getLocationOnScreen().getY())/52 ;
          int colx = (e.getXOnScreen() - (int)sudoku.getLocationOnScreen().getX())/52 ;
          
          // calls set selected to the cell that was selected through mouse pressing.
          setSelected(rowy,colx);
        }
      });
      
      // adds the cells to the panel sudoku.
      sudoku.add(cells[i]);
    } // end of cells loop
    
    // draws the board into the sudoku panel
    drawBoard();
    
    // adds the sudoku panel to the instance.
    add(sudoku);
    
  } // end of constructor
  
  /**
   * The setSelected() method will set the selected row and col int numbers that has the specified cell and repaints the
   * board.
   * @param rows The row that has the selected cell.
   * @param cols The col that has the selected cell.
   */
  public void setSelected(int rows, int cols) {
    selected = true;
    srow = rows;
    scol = cols;
    repaint();                           
  }
  
  /**
   * The getSelectedRow() method will return the row number that contains the selected cell.
   * @return The row number that contains the selected cell.
   */
  public int getSelectedRow() {
    return srow;
  }
  
  /**
   * The getSelectedRow() method will return the col number that contains the selected cell.
   * @return The col number that contains the selected cell.
   */
  public int getSelectedColumn() {
    return scol;
  }
  
  /**
   * The paintComponent() method will paint the sudoku board graphics into the panel and highlights a selected cell.
   * @param g The graphic component that will be painted on in the panel.
   */
  public void paintComponent(java.awt.Graphics g) {
    
    // paint the background
    super.paintComponent(g);

    // draws a filled rectangle that is the whole sudoku board
    g.setColor(java.awt.Color.WHITE);
    g.fillRect(50,50, size * 52 + 4, size * 52 + 4);
    
    // draws the outline for the whole sudoku board
    g.setColor(java.awt.Color.BLACK);
    g.drawRect(49, 49, size  * 52 + 3, size * 52 + 3);
    
    // variables for getting checkered pattern
    boolean region = false;
    boolean reg = false;
    int wait = 1;
    
    // creates the board graphics
    for(int n = 0; n < size; n++) {
      // marks if a region is grey for odd columns in a sudoku board
      if( row % 2 == 1 ) {
        if( n % 2 == 1) {
          region = true;
        }
      } 
      // marks if a region is grey for even columns in a sudoku board.
      if( row % 2 == 0 ) {
        if( n % 2 == 1 && reg == false) {
          region = true;
          if( wait == 0 ) {
            region = false;
          }
          if( n % row == row - 1) {
            wait++;
            if( wait == 2) {
              reg = true;
            }
          } 
        }
        if(n % 2 == 0 && reg == true) {
          region = true;
          if( n % row == row - 2) {
            reg = false;
            wait = 0;
          }
        }
      }
      // draws a grey rectangle on regions that are marked as true.
      if(region) {
        g.setColor(java.awt.Color.lightGray);
        g.fillRect(51 + col * (n%row)  * 52, 51 + row * (n/row) * 52, col * 52 , row * 52 );
        region = false;
      }
      // draws individual square boxes
      for(int rows = row * (n/row); rows < row * ( 1 + n/row ); rows++) {               
        for(int cols = col * (n%row); cols < col * ( 1 + n%row); cols++) {           
          g.setColor(java.awt.Color.BLACK);
          g.drawRect(52 + cols  * 52, 52 + rows * 52, 49, 49);
          // colors selected cells
          if(selected) {
            g.setColor(java.awt.Color.YELLOW);
            g.fillRect(52 + scol*52 +1, 52 + srow * 52 +1, 48,48);
          }
        }
      }
    } // end of loop
    
    // updates the board
    drawBoard(java.awt.Color.BLUE);
    
  } // end of board graphics
  
  /**
   * The drawBoard() method draws each individual cell of the sudoku board.
   */
  private void drawBoard(java.awt.Color c){    
    for( int i = 0; i < size*size; i++ ) {
    
      // gets rows and columns through int i.
      int rows = i/size ; 
      int cols = i%size;
      sym[i].setC(c);
      
      if( s.isGiven(rows,cols) ) {
        sym[i].setC(java.awt.Color.BLACK);
      }
      
      // displays only given values
      if(symbols) {
        cells[i].remove(labels[i]);
        // switches a value with the corresponding symbols
        if(s.getValue(rows, cols)  == 1 ) {
          sym[i].setN(1);
        } else if( s.getValue(rows,cols) == 2) {
          sym[i].setN(2);
        } else if( s.getValue(rows,cols) == 3) {
          sym[i].setN(3);
        } else if( s.getValue(rows,cols) == 4) {
          sym[i].setN(4);
        } else if( s.getValue(rows,cols) == 5) {
          sym[i].setN(5);
        } else if( s.getValue(rows,cols) == 6) {
          sym[i].setN(6);
        } else if( s.getValue(rows,cols) == 7) {
          sym[i].setN(7);
        } else if( s.getValue(rows,cols) == 8) {
          sym[i].setN(8);
        } else if( s.getValue(rows,cols) == 9) {
          sym[i].setN(9);
        } else if( s.getValue(rows,cols) == 10) {
          sym[i].setN(10);
        } else if( s.getValue(rows,cols) == 11) {
          sym[i].setN(11);
        } else if( s.getValue(rows,cols) == 12) {
          sym[i].setN(12);
        } else if( s.getValue(rows,cols) == 0) {
          sym[i].setN(0);
        }
        cells[i].add(sym[i]);
        cells[i].revalidate();
        cells[i].repaint();

        // non symbols
      } else {
        if( s.getValue(rows, cols)  == 0 ) {
          cells[i].removeAll();
          labels[i].setText("");
          cells[i].add(labels[i]);
        } else {
          cells[i].removeAll();
          labels[i].setText(s.getValue(rows, cols) + "");
          cells[i].add(labels[i]);
        }
      }
      
    } // end of loop

    // update sudoku board
    sudoku.setLocation(52,52);
    sudoku.revalidate();
  } // end of drawBoard
  
  /**
   * The drawBoard() method draws each individual cell of the sudoku board.
   */
  private void drawBoard(){    
    drawBoard(java.awt.Color.BLACK);
  }
    
  
  @Override
  public void update(java.util.Observable obs, Object x) {
  }
  
  /**
   * The setSymbols() method will set symbols to a desired boolean value.
   * @param b The desired boolean value for symbols.
   */
  public void setSymbols(boolean b) {
    symbols = b;
  }
  
  /**
   * The getSymbols() method will return if there are symbols or not.
   * @return If there are symbols or not.
   */
  public boolean getSymbols() {
    return symbols;
  }
  
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///                                 INNER CLASSES                                              
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * The Symbols class will draw a symbol corresonding to its field n.
   */
  class Symbols extends javax.swing.JComponent {
    
    // the number
    int n;
    java.awt.Color c;
    
    /**
     * Constructs a symbol class and initialize n's value with the param num, default size is 40 x 40.
     * @param num The number that will be draw correspondingly.
     */
    public Symbols(int num) {
      n = num;
      setPreferredSize(new java.awt.Dimension(40, 40));
    }
    
    /**
     * The setN() method will set n as a value based on the given param.
     * @param num The set number.
     */
    public void setN(int num) {
      n = num;
    }
    
    /**
     * The setC() method will set a color for the Symbols class.
     * @param col The color that will be set.
     */
    public void setC(java.awt.Color col) {
      c = col;
    }
    
    @Override
    public void paintComponent(java.awt.Graphics g) {
      super.paintComponent(g);
      g.setColor(c);
      java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
      g2.setStroke(new java.awt.BasicStroke(4));
      
      // draws according to n's value
      if( n == 0 ) {
      } else if(n == 1) {
        g.fillRect(5, 18, 30, 4 );
      } else if(n == 2 ) {
        g.fillRect(10, 12, 20, 4 );
        g.fillRect(5, 22, 30, 4 );
      } else if(n == 3) {
        g.fillRect(8, 10, 24, 4 );
        g.fillRect(12, 18, 16, 4 );
        g.fillRect(8, 26, 24, 4 );
      } else if(n == 4) {
        g.fillRect(4, 4, 4, 28 );
        g.fillRect(4, 32, 28, 4);
        g.fillRect(8, 4, 28, 4 );
        g.fillRect(32, 8, 4, 28 );
        g2.drawLine(16, 8, 12, 16);
        g2.drawLine(24, 8, 24, 16);
        g2.drawLine(24, 16, 32, 16); 
      } else if(n == 5) {
        g2.drawLine(8,6,32,6);
        g2.drawLine(8,18,34,18);
        g2.drawLine(4,34,34,34);
        g2.drawLine(24,8,10,32);
        g2.drawLine(28,20,20,32);
      } else if(n == 6) {
        g2.drawLine(20,4,20,10);
        g2.drawLine(8,16,32,16);
        g2.drawLine(14,22,10,30);
        g2.drawLine(26,22,30,30);
      } else if(n == 7) {
        g2.drawLine(6,18,34,14);
        g2.drawLine(16,6,12,32);
        g2.drawLine(12,32,30,32);
      } else if(n == 8) {
        g2.drawLine(16,16,10,32);
        g2.drawLine(22,6,30,32);
      } else if(n == 9) {
        g2.drawLine(5,20,24,20);
        g2.drawLine(24,20,18,34);
        g2.drawLine(18,34,32,34);
        g2.drawLine(32,34,32,26);
        g2.drawLine(16,6,6,34);
        g2.drawLine(6,34,4,34);
      } else if(n == 10) {
        g2.drawLine(20,6,20,34);
        g2.drawLine(6,20,34,20);
      } else if(n == 11) {
        g2.drawLine(12,12,12,28);
        g2.drawLine(4,20,20,20);
        g2.drawLine(26,20,38,20);
      } else if(n == 12) {
        g2.drawLine(12,12,12,28);
        g2.drawLine(4,20,20,20);
        g2.drawLine(26,16,32,16);
        g2.drawLine(22,25,36,25);
      }
    }  // end of paintComponent() method
    
  } // end of Symbols class
  
  /**
   * The RowStatus class will display the status for each row in the sudoku game.
   * Yellow for incomplete, red for error, and green for complete.
   */
  class RowStatus extends javax.swing.JComponent { 
    
    // base for reference
    SudokuBase sb;
    
    /**
     * The constructor of RowStatus will initialize the base and set the preferred size.
     * @param b The SudokuBase.
     */
    public RowStatus(SudokuBase b) {
      sb = b;
      setPreferredSize(new java.awt.Dimension(sb.size * 5 + 10, sb.size * 5 + 10));
    }
    
    @Override
    public void paintComponent(java.awt.Graphics g) {
      super.paintComponent(g);
      g.setColor(java.awt.Color.BLACK);
      g.drawRect(5,5,sb.size * 5 + 1 ,sb.size * 5 + 1);
      
      SudokuBoard board = (SudokuBoard)sb;
      
      for(int row = 0; row < sb.size; row++) {
        if( board.getRowState(row).toString().equals("INCOMPLETE")) {
          g.setColor(java.awt.Color.YELLOW);
        } else if ( board.getRowState(row).toString().equals("ERROR")) { 
          g.setColor(java.awt.Color.RED);
        } else if ( board.getRowState(row).toString().equals("COMPLETE")) { 
          g.setColor(java.awt.Color.GREEN);
        }
        g.fillRect(5 + 1, 5 + 1 + row * 5, sb.size * 5 , 5 );
        g.setColor(java.awt.Color.BLACK);
        g.drawRect(5 , 5  + row * 5, sb.size * 5 + 1, 5 + 1 );
      }
    }
  } // end of RowStatus inner class.
  
  /**
   * The ColumnStatus class will display the status for each column in the sudoku game.
   * Yellow for incomplete, red for error, and green for complete.
   */
  class ColumnStatus extends javax.swing.JComponent { 
    
    // base for reference
    SudokuBase sb;
    
    /**
     * The constructor of ColumnStatus will initialize the base and set the preferred size.
     * @param b The SudokuBase.
     */
    public ColumnStatus(SudokuBase b) {
      sb = b;
      setPreferredSize(new java.awt.Dimension(50,50));
    }
    
    @Override
    public void paintComponent(java.awt.Graphics g) {
      super.paintComponent(g);
      g.setColor(java.awt.Color.BLACK);
      g.drawRect(5,5,sb.size * 5 + 1 ,sb.size * 5 + 1);
      
      // casting
      SudokuBoard board = (SudokuBoard)sb;
      
      for(int col = 0; col < sb.size; col++) {
        if( board.getColumnState(col).toString().equals("INCOMPLETE")) {
          g.setColor(java.awt.Color.YELLOW);
        } else if ( board.getColumnState(col).toString().equals("ERROR")) { 
          g.setColor(java.awt.Color.RED);
        } else if ( board.getColumnState(col).toString().equals("COMPLETE")) { 
          g.setColor(java.awt.Color.GREEN);
        }
        g.fillRect(5 + 1 + col * 5, 5 + 1, 5 , sb.size * 5);
        g.setColor(java.awt.Color.BLACK);
        g.drawRect(5 + col * 5, 5, 5 + 1, sb.size * 5 + 1 );
      }
    }
  } // end of ColumnStatus inner class.
  
  /**
   * The RegionStatus class will display the status for each column in the sudoku game.
   * Yellow for incomplete, red for error, and green for complete.
   */
  class RegionStatus extends javax.swing.JComponent { 
    
    // base for reference
    SudokuBase sb;
    
    /**
     * The constructor of RegionStatus will initialize the base and set the preferred size.
     * @param b The SudokuBase.
     */
    public RegionStatus(SudokuBase b) {
      sb = b;
      setPreferredSize(new java.awt.Dimension(50,50));
    }
    
    @Override
    public void paintComponent(java.awt.Graphics g) {
      super.paintComponent(g);
      g.setColor(java.awt.Color.BLACK);
      g.drawRect(5,5,sb.size * 5 + 1 ,sb.size * 5 + 1);
      
      // casting
      SudokuBoard board = (SudokuBoard)sb;
      
      for(int reg = 0; reg < sb.size; reg++) {
        if( board.getRegionState(reg).toString().equals("INCOMPLETE")) {
          g.setColor(java.awt.Color.YELLOW);
        } else if ( board.getRegionState(reg).toString().equals("ERROR")) { 
          g.setColor(java.awt.Color.RED);
        } else if ( board.getRegionState(reg).toString().equals("COMPLETE")) { 
          g.setColor(java.awt.Color.GREEN);
        }
        g.fillRect(5 + 1 + sb.columns * (reg%sb.rows) * 5, 5  + 1+ sb.rows * (reg/sb.rows) * 5, sb.columns * 5, sb.rows * 5);
        g.setColor(java.awt.Color.BLACK);
        g.drawRect(5  + sb.columns * (reg%sb.rows) * 5, 5 + sb.rows * (reg/sb.rows) * 5, sb.columns * 5  + 1, sb.rows * 5 + 1);
      }
    }
  } // end of RegionStatus inner class.

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///                                 INNER CLASSES                                         
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
} // end of SudokuView class.
      