/**
 * The SudokuMain class will create a SudokuView using a SudokuBase into a window and will create a control panel
 * next to the SudokuView, the user has the option of entering a "set up" mode or plays a premade board, the Main class
 * also support serialization.
 * 
 * @author Minhhue H. Khuu
 * @version Assignment 7: Sudoku Final NORMAL VERSION
 */
public class SudokuMain extends javax.swing.JPanel {
  
  // fields
  private javax.swing.JPanel[] btns;
  private javax.swing.JLabel[] labels;
  private SudokuBase b;
  private SudokuView v;
  private javax.swing.JPanel controlPanel;
  private javax.swing.JPanel btnPanel;
  private javax.swing.JPanel pnl;
  private java.io.File tempFile;
  private javax.swing.JPanel status;
  
  /**
   * The SudokuMain constructor will create a controller next to the sudoku view.
   * @param sb The Sudoku Base.
   * @param sv The Sudoku View.
   */
  public SudokuMain(SudokuBase sb, SudokuView sv) {
    
    // initialize fields
    b = sb;
    v = sv;
    btns = new javax.swing.JPanel[sb.size + 1];
    labels = new javax.swing.JLabel[sb.size + 1];

    tempFile = null;

    // causes the size of a contrcutor SudokuMain object to be this size
    this.setPreferredSize(new java.awt.Dimension(100 + 52 + 50, 100 + sb.size * 52));
   
    // create panels to place into the SudokuMain object
    controlPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
    btnPanel = new javax.swing.JPanel(new java.awt.GridLayout(sb.size, 1, 2, 2));
    
    // set prefered size of btn panel
    btnPanel.setPreferredSize(new java.awt.Dimension(52, sb.size * 52));

    // creates buttons for values excluding blanks
    for(int i = 1; i < sb.size + 1; i++) { 
      final int ii = i;
      
      // intialize btns array
      btns[i] = new javax.swing.JPanel();
      btns[i].setSize(50,50);
      btns[i].setBackground(java.awt.Color.GRAY);
      btns[i].setForeground(java.awt.Color.BLACK);
      
      // intialize labels array
      labels[i] = new javax.swing.JLabel();
      labels[i].setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 24));
      labels[i].setHorizontalAlignment( javax.swing.JLabel.CENTER );
      labels[i].setVerticalAlignment( javax.swing.JLabel.CENTER );
      
      // add each button in the btns array into the btnPanel
      btnPanel.add(btns[i]);
      
      // add mouse click functionality to the buttons
      btns[i].addMouseListener( new java.awt.event.MouseListener() {
        
        // overriding methods
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
          btns[ii].setBackground(java.awt.Color.RED);
        }
        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
          btns[ii].setBackground(java.awt.Color.GRAY);
        }
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
          btns[ii].setBackground(java.awt.Color.YELLOW);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
          btns[ii].setBackground(java.awt.Color.GRAY);
        }
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
          if(!b.isGiven(v.getSelectedRow(),v.getSelectedColumn())) {
            b.setRawValue(v.getSelectedRow(),v.getSelectedColumn(), ii);

            // repaints status after a value is given
            status.repaint();
            
          } else {
            // plays a beep if user tries to input a value to a selected cell that already has a value
            java.awt.Toolkit.getDefaultToolkit().beep();
          }
        }
      });
      
    }  // end of loop
    
    // adds the symbols or text to the buttons
    makeControl();
    
    // adds a button for blank values
    btns[0] = new javax.swing.JPanel();
    btns[0].setSize(50,50);
    btns[0].setBackground(java.awt.Color.WHITE);
    
    // creates a label that is empty for the empty button
    javax.swing.JLabel ll = new javax.swing.JLabel();
    ll.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 20));
    ll.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    ll.setText("Empty");
    
    // add a label to the empty button
    btns[0].add(ll);
    btns[0].setVisible(true);
    btns[0].addMouseListener( new java.awt.event.MouseListener() {
      
      // overriding methods
      @Override
      public void mousePressed(java.awt.event.MouseEvent e) {
        btns[0].setBackground(java.awt.Color.RED);
      }
      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {
        btns[0].setBackground(java.awt.Color.WHITE);
      }
      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        btns[0].setBackground(java.awt.Color.YELLOW);
      }
      @Override
      public void mouseExited(java.awt.event.MouseEvent e) {
        btns[0].setBackground(java.awt.Color.WHITE);
      }
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        if(!b.isGiven(v.getSelectedRow(),v.getSelectedColumn())) {
          b.setValue(v.getSelectedRow(),v.getSelectedColumn(), 0);
          // repaints status after a value is given
          status.repaint();
        } else {
          // plays a beep if user tries to input a value to a selected cell that already has a value
          java.awt.Toolkit.getDefaultToolkit().beep();
        }
      } 
    });

    // label for displaying the control panel
    javax.swing.JLabel label = new javax.swing.JLabel();
    label.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 20));
    label.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    label.setText("Control");

    // add label, btn panel, and empty button into the control panel
    controlPanel.add(label, java.awt.BorderLayout.NORTH);
    controlPanel.add(btnPanel, java.awt.BorderLayout.CENTER);
    controlPanel.add(btns[0], java.awt.BorderLayout.SOUTH);
    
    // status bar
    status = new javax.swing.JPanel(new java.awt.GridLayout(3,1));
    status.setPreferredSize(new java.awt.Dimension(b.size * 5 + 10 ,3 * (b.size * 5 + 10) ));
    
    // intialize status instances
    SudokuView.RowStatus rowStatus = v.new RowStatus(b);
    SudokuView.ColumnStatus colStatus = v.new ColumnStatus(b);
    SudokuView.RegionStatus regStatus = v.new RegionStatus(b);
    
    // add to status panel
    status.add(rowStatus);
    status.add(colStatus);
    status.add(regStatus);
    
    // create a new panel to add both the control panel and status panel
    java.awt.FlowLayout f = new java.awt.FlowLayout();
    f.setHgap( 50 );
    pnl = new javax.swing.JPanel(f);
    
    // adds control panel and status panel
    pnl.add(controlPanel);
    pnl.add(status);
    
    // make visible and add
    btnPanel.setVisible(true);
    controlPanel.setVisible(true);
    add(pnl);

  } // end of constructor
  
  /**
   * The paintComponent() will update the control panel if symbols are toggled.
   * @param g The graphic component that will be painted on in the panel.
   */
  public void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);
    makeControl();
  }
  
  /**
   * The setUpMain() method will make the background of the controler and status panel black for set up mode.
   */
  public void setUpMain() {
    pnl.setBackground(java.awt.Color.BLACK);
    status.setBackground(java.awt.Color.BLACK);
  }
  
  /**
   * The setFile() method will set a temporary file for the SudokuMain class.
   * @param f The file that will be set as the tempFile.
   */
  public void setFile(java.io.File f) {
    tempFile = f;
  }
  
  /**
   * The getFile() will return the temporary file.
   * @return The temporary file.
   */
  public java.io.File getFile() {
    return tempFile;
  }
  
  /**
   * The makeControl() method will add symbols or numeral characters into each button.
   */
  private void makeControl() {
    // creates buttons for values excluding blanks
    for(int i = 1; i < b.size + 1; i++) { 
      final int ii = i;
      if(v.getSymbols()) {
        btns[i].remove(labels[i]);
        // switches a value with the corresponding symbols
        if(i == 1 ) {
          btns[i].add(v.new Symbols(1)); 
        } else if( i == 2) {
          btns[i].add(v.new Symbols(2));  
        } else if( i == 3) {
          btns[i].add(v.new Symbols(3)); 
        } else if( i == 4) {
          btns[i].add(v.new Symbols(4)); 
        } else if( i == 5) {
          btns[i].add(v.new Symbols(5)); 
        } else if( i == 6) {
          btns[i].add(v.new Symbols(6)); 
        } else if( i == 7) {
          btns[i].add(v.new Symbols(7)); 
        } else if( i == 8) {
          btns[i].add(v.new Symbols(8)); 
        } else if( i == 9) {
          btns[i].add(v.new Symbols(9)); 
        } else if( i == 10) {
          btns[i].add(v.new Symbols(10)); 
        } else if( i == 11) {
          btns[i].add(v.new Symbols(11)); 
        } else if( i == 12) {
          btns[i].add(v.new Symbols(12)); 
        }
        // non symbols
      } else {
        btns[i].removeAll();
        labels[i].setText(ii + "");
        btns[i].add(labels[i]);
      }
    }
    btnPanel.revalidate();
    controlPanel.revalidate();
  }
  
  /**
   * The getBase() will return the sudoku base that the SudokuMain is dealing with.
   * @return The SudokuBase SudokuMain is using.
   */
  public SudokuBase getBase() {
    return b;
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////// STATIC METHODS ///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * The makeDefaultBoard() method will make a default sudoku board.
   * @return The sudoku base that will be used for the sudoku main.
   */
  public static SudokuBase makeDefaultBoard() {
    SudokuBase board = new SudokuBoard(2, 3);
    board.setValue(0, 3, 6);
    board.setValue(0, 5, 1);
    board.setValue(1, 2, 4);
    board.setValue(1, 4, 5);
    board.setValue(1, 5, 3);
    board.setValue(2, 3, 3);
    board.setValue(3, 2, 6);
    board.setValue(4, 0, 2);
    board.setValue(4, 1, 3);
    board.setValue(4, 3, 1);
    board.setValue(5, 0, 6);
    board.setValue(5, 2, 1);
    board.fixGivens();
    return board;
  }
  
  /**
   * The isInt() method will determine if a given string is an Int or not.
   * @param str The given string that will be checked if it is an int.
   * @return The result if the string is an int or not.
   */
  private static boolean isInt(String str) {
    try  {
      int i = Integer.parseInt(str);  
    }  
    catch(NumberFormatException nfe)  {
      return false;  
    }  
    return true;  
  }
  
  /**
   * The menuUI() method will create a menuUI for a JFrame window.
   * @param win The JFrame that will get the menuUI.
   * @param sv The SudokuView to extract view information.
   * @param sm The SudokuMain to extract main information.
   */
  private static void menuUI(javax.swing.JFrame win, SudokuView sv, SudokuMain sm, SudokuBase sb) {
    
    // finalize params so the anonymouse inner class may use it.
    final SudokuView s = sv;
    final SudokuMain m = sm;
    final SudokuBase b = sb;
    final javax.swing.JFrame w = win;
    
    // create menu bar
    javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
    win.setJMenuBar(menuBar);
    // create option menu and file menu
    javax.swing.JMenu fileMenu = new javax.swing.JMenu("File");
    javax.swing.JMenu optionMenu = new javax.swing.JMenu("Options");
    menuBar.add(fileMenu);
    menuBar.add(optionMenu);
    
    // add fixGivens
    javax.swing.JMenuItem fixGivens = new javax.swing.JMenuItem("Fix Givens");
    optionMenu.add(fixGivens);
    
    fixGivens.addActionListener( new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        b.fixGivens();
      }
    });
        
    // add save and load menu items
    javax.swing.JMenuItem save = new javax.swing.JMenuItem("Save");
    javax.swing.JMenuItem saveAs = new javax.swing.JMenuItem("Save As...");
    javax.swing.JMenuItem load = new javax.swing.JMenuItem("Load");
    fileMenu.add(save);
    fileMenu.add(saveAs);
    fileMenu.add(load);
    
    // adds save menu item fuctionallity
    save.addActionListener( new java.awt.event.ActionListener(){
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        if(m.getFile() == null) {
          saveFile(s, m, b);
        } else {
          new Save(b, m.getFile());
        }
      }
    });
    
    // adds save as menu item functionallity
    saveAs.addActionListener( new java.awt.event.ActionListener()  {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        saveFile(s, m, b);
      }
    });
    
    // adds load menu item functionallity
    load.addActionListener( new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        loadFile(s, m, w);
      }
    });
    
    // create toggle switch in option menu
    javax.swing.JRadioButtonMenuItem toggleSymbol = new javax.swing.JRadioButtonMenuItem("Toggle Symbols", true);
    optionMenu.add(toggleSymbol);
    // adds functionality to the toggle symbol
    toggleSymbol.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        // switches the boolean value of symbols opisitely
        s.setSymbols(!s.getSymbols());
        w.repaint();
      }
    });
    // adds a restart game menu
    javax.swing.JMenuItem restartGame = new javax.swing.JMenuItem("Restart Game");
    optionMenu.add(restartGame);
    restartGame.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        if (javax.swing.JOptionPane.showConfirmDialog(w, 
                                                      "All play values will be set to zero and all given values will remain. Confirm?",
                                                      "Restart Game?", 
                                                      javax.swing.JOptionPane.YES_NO_OPTION,
                                                      javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION){
          for( int i = 0; i < b.size * b.size; i++) {
            if(!b.isGiven(i/b.size, i%b.size)) {
              b.setRawValue(i/b.size, i%b.size, 0);
            }
          }
          makeSudoku(b, null);      
          w.dispose();
        } else {
        }
      }
    });
    // adds a reset menu
    javax.swing.JMenuItem reset = new javax.swing.JMenuItem("Return to Main Menu");
    optionMenu.add(reset);
    reset.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        w.dispose();
        main(new String[0]);
      }
    });
    
  } // end of menuUI method
  
  /**
   * The setUp() method will create a set up game so the user can set his/her own sudoku game.
   * @param b The SudokuBase that has a specified size, rows, and columns.
   */
  private static void setUp(SudokuBase b) {
    
    // create window
    final javax.swing.JFrame win = new javax.swing.JFrame("SET UP MODE - CLICK DONE WHEN FINISHED TO BEGIN GAME");
    win.setLayout(new java.awt.BorderLayout());
    
    // create a SudokuView and SudokuMain set play to false to allow user to change selected values
    SudokuView sudoku = new SudokuView(b);
    SudokuMain main = new SudokuMain(b, sudoku);
 
    // make the back ground of the window black to notify the user that it is set up mod
    win.getContentPane().setBackground(java.awt.Color.BLACK);
    sudoku.setBackground(java.awt.Color.BLACK);
    main.setBackground(java.awt.Color.BLACK);
    main.setUpMain();
    
    menuUI(win, sudoku, main, b);
    
    // creates a button so the user can press when done with set up mode and precede to game mode
    javax.swing.JButton btn = new javax.swing.JButton("Done");
    // create a final SudokuBase variable to hold the information of what the user set up
    final SudokuBase done = main.getBase();
    // makes the button do create a new game using what the user inputted
    btn.addActionListener( new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        done.fixGivens();
        makeSudoku(done, null);
        win.dispose();
      }
    });
    
    // adds sudoku into window and adds the controls next to it
    win.add(sudoku, java.awt.BorderLayout.WEST);
    win.add(main,java.awt.BorderLayout.EAST);
    win.add(btn, java.awt.BorderLayout.SOUTH);
    
    // make window unresizable and visible
    win.setLocation(100,100);
    win.setResizable(false);
    win.setVisible(true);
    win.pack();
    
  } // end of set up method
  
  /**
   * The makeSudoku() makes a new sudoku game in a new window and uses a file to refer to a loaded game.
   * @param board The SudokuBase that will be used to get information.
   * @file The file location of a loaded game if any.
   */
  private static void makeSudoku(SudokuBase board, java.io.File file) {
    // creates a final win to hold the game
    final javax.swing.JFrame win = new javax.swing.JFrame("Sudoku by Minh");

    // create a sudoku view and main and finalize them
    final SudokuView sudoku = new SudokuView(board);
    final SudokuMain main = new SudokuMain(board, sudoku);
    final SudokuBase base = board;
    main.setFile(file);
    
    // adds menuUI to win
    menuUI(win, sudoku, main, base);
    
    // adds panel to contain controller and game status
    javax.swing.JPanel pnl = new javax.swing.JPanel(new java.awt.FlowLayout());   
    // adds sudoku into window and adds the controls next to it
    pnl.add(sudoku);
    pnl.add(main);
    win.add(pnl);
    
    // on closing window will ask if want to save file
    win.addWindowListener( new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        if (javax.swing.JOptionPane.showConfirmDialog(win, 
                                                      "Exit without saving?", "Really Closing?", 
                                                      javax.swing.JOptionPane.YES_NO_OPTION,
                                                      javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION){
          win.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        } else {
          saveFile(sudoku, main, win, base);
        }
      }
    });
    
    // make window unresizable and visible
    win.setLocation(100,100);
    win.setResizable(false);
    win.setVisible(true);
    win.pack();
  
  } // end of makeSudoku method
    
  /**
   * The saveFile() method saves a file.
   * @param s SudokuView to extract view information.
   * @param m SudokuMain to extract main information.
   */
  private static void saveFile(SudokuView s, SudokuMain m, SudokuBase b) {
    // user chooses save directory
    final javax.swing.JFileChooser fc = new javax.swing.JFileChooser(System.getProperty("user.dir"));
    javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Sudoku Save Files", "dat"); 
    fc.setFileFilter(filter);
    int returnVal = fc.showSaveDialog(null); 
    // if save button pressed
    if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
      // checks if last 4 character is ".dat" extension if not it will be apended
      String st = fc.getSelectedFile().toString();
      if(st.charAt(st.length() - 4) == '.' && st.charAt(st.length() - 3) == 'd' && st.charAt(st.length() - 2) == 'a'
           && st.charAt(st.length() - 1) == 't') {
        java.io.File file = fc.getSelectedFile();
        new Save(b, file);
        m.setFile(file);
      } else {
        java.io.File file = new java.io.File(fc.getSelectedFile() + ".dat");
        new Save(b, file);
        m.setFile(file);
      } 
    }
  }
  
  /**
   * The saveFile() method is a overloaded method that takes an additional param of win for closing when a window is disposed.
   * @param s SudokuView to extract view information.
   * @param m SudokuMain to extract main information.
   * @param win The JFrame that will be told what to do when the close operation is commanded.
   */
  private static void saveFile(SudokuView s, SudokuMain m, javax.swing.JFrame win, SudokuBase b) {
    // user chooses directory
    final javax.swing.JFileChooser fc = new javax.swing.JFileChooser(System.getProperty("user.dir"));
    javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Sudoku Save Files", "dat"); 
    fc.setFileFilter(filter);
    int returnVal = fc.showSaveDialog(null); 
    // if save button is pressed
    if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
      // checks if last 4 character is ".dat" extension if not it will be apended
      String st = fc.getSelectedFile().toString();
      if(st.charAt(st.length() - 4) == '.' && st.charAt(st.length() - 3) == 'd' && st.charAt(st.length() - 2) == 'a'
           && st.charAt(st.length() - 1) == 't') {
        java.io.File file = fc.getSelectedFile();
        m.setFile(file);
        new Save(b, file);
      } else {
        java.io.File file = new java.io.File(fc.getSelectedFile() + ".dat");
        new Save(b, file);
        m.setFile(file);
      } 
    } else {
        win.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }
  }
  
  /**
   * The loadFile() method loads a file.
   * @param s SudokuView to extract view information.
   * @param m SudokuMain to extract main information.
   */
  private static void loadFile(SudokuView s, SudokuMain m, javax.swing.JFrame win) {
    // uses file chooser to select a fil to load
    final javax.swing.JFileChooser fc = new javax.swing.JFileChooser(System.getProperty("user.dir"));
    javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Sudoku Save Files", "dat"); 
    fc.setFileFilter(filter);
    int returnVal = fc.showOpenDialog(null); 
    // what happens when open is pressed
    if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
      java.io.File file = fc.getSelectedFile();
      Object obj = new Load(file).getObject();
      // creates a new game based off of the load file
      if( obj instanceof SudokuBase ) {
        SudokuBase newBase = (SudokuBase)obj;
        win.dispose();
        makeSudoku(newBase, file);
      }
    }
  }

  /**
   * The main() method will run the program and create a window that contains the sudoku board in the Sudoku View and 
   * a sudoku controller in the Sudoku Main next to it, prior to this the user will be asked to enter "set up" mode or normal
   * play.
   * @param args The command-line arguments.
   */
  public static void main(String[] args) {
    
    // creates a window that will give the user the option of choosing a set up mode or normal play
    final javax.swing.JFrame win = new javax.swing.JFrame("SUDOKU");
    win.setLocation(100,100);
    win.setSize(300,400);
    
    // label for instructions
    javax.swing.JLabel jlabel = new javax.swing.JLabel();
    jlabel.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 48));
    jlabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    jlabel.setText("Sudoku Main Menu");
    
    // panel for buttons
    javax.swing.JPanel pan = new javax.swing.JPanel(new java.awt.GridLayout(1,2));
    pan.setPreferredSize(new java.awt.Dimension(200,200));
    
    // buttons for the user to choose which mode
    javax.swing.JButton setUp = new javax.swing.JButton("Create");
    javax.swing.JButton normal = new javax.swing.JButton("Play");
    javax.swing.JButton load = new javax.swing.JButton("Load");
    
    // add buttons
    pan.add(setUp);
    pan.add(normal);
    pan.add(load);
    
    // add functionallity to buttons
    setUp.addActionListener( new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        
        // final variables to be edited by action performed events in the buttons
        final javax.swing.JLabel row = new javax.swing.JLabel("");
        final javax.swing.JLabel col = new javax.swing.JLabel("");
        final javax.swing.JLabel direction = new javax.swing.JLabel("Rows for Sudoku Board: ");
        final javax.swing.JTextField txt = new javax.swing.JTextField("", 15);
        final javax.swing.JDialog dia = new javax.swing.JDialog(win, "Set Up Dialog", true);
        dia.setSize(375,125);
        
        // a new panel to hold directions and user input
        javax.swing.JPanel panl = new javax.swing.JPanel();
        panl.add(direction);      
        
        // buttons to confirm user input
        javax.swing.JButton ok = new javax.swing.JButton("OK");
        javax.swing.JButton cancel = new javax.swing.JButton("Cancel");
        
        // button functionallity
        ok.addActionListener( new java.awt.event.ActionListener() {
          @Override
          public void actionPerformed(java.awt.event.ActionEvent e) {
            String text = txt.getText();
            if( row.getText().equals("") ) {
              if( !isInt(text) ) {
                direction.setText("Please Enter an Integer for your Sudoku Board Row!");
              } else {
                int num = Integer.parseInt(text);
                if(!(num > 0 && num <= 12)) {
                  direction.setText("Please Enter an Integer that is greater than 0 and less than 12!");
                } else {
                  direction.setText("Columns for Sudoku Board: ");
                  row.setText(num + ""); 
                }
              }
            } else {
              if( !isInt(text) ) {
                direction.setText("Please Enter an Integer for your Sudoku Board Column!");
              } else {
                int num = Integer.parseInt(text);
                if(!(num > 0 && num <= 12)) {
                  direction.setText("Please Enter an Integer that is greater than 0 and less than 12!");
                } else if(!(Integer.parseInt(row.getText()) * num <= 12) ) {
                  direction.setText("Enter an integer so that your row, " + row.getText() + ", mutiplied by the entered"
                                      + " value is less than or equal to 12 for your column!"); 
                  dia.setSize(700,125);
                 } else {
                  col.setText(num + ""); 
                  SudokuBase b = new SudokuBoard(Integer.parseInt(row.getText()), Integer.parseInt(col.getText()));
                  setUp(b);
                  dia.dispose();
                }
              }            
            }
          }
        });
        
        // button functionallity
        cancel.addActionListener( new java.awt.event.ActionListener() {
          @Override
          public void actionPerformed(java.awt.event.ActionEvent e) {
            System.out.println(row.getText());
            dia.dispose();
            main(new String[0]);
          }
        });
        
        // add buttons the panel
        javax.swing.JPanel panl2 = new javax.swing.JPanel();
        panl2.add(ok);
        panl2.add(cancel);
        
        // add panels to the dialogue window
        dia.add(panl);
        dia.add(panl2, java.awt.BorderLayout.SOUTH);
        panl.add(txt);
        
        // make dialogue window visible and dispose the menu window
        dia.setVisible(true);
        win.dispose();
      }
    });
    
    // button functionallity, sets up a normal game
    normal.addActionListener( new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        try {
          java.io.FileInputStream fis = new java.io.FileInputStream(new java.io.File("temp.sudokurc"));
          makeSudoku((SudokuBase)new Load(new java.io.File("temp.sudokurc")).getObject(),null);
          win.dispose();
        } catch(java.io.FileNotFoundException ex) {
          makeSudoku(makeDefaultBoard(), null);
          win.dispose();  
        } 
      }
    });
    
    // button functionality, sets up a loaded game
    load.addActionListener( new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        // default directory is current directory
        final javax.swing.JFileChooser fc = new javax.swing.JFileChooser(System.getProperty("user.dir"));
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Sudoku Save Files", "dat"); 
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(null); 
        // when open button is pressed
        if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
          java.io.File file = fc.getSelectedFile();
          Object obj = new Load(file).getObject();
          // creates a new sudoku game
          if( obj instanceof SudokuBase ) {
            SudokuBase newBase = (SudokuBase)obj;
            win.dispose();
            makeSudoku(newBase, null);
          }
        }
      }
    });
    
    // adds to window
    win.add(pan);
    win.add(jlabel, java.awt.BorderLayout.NORTH);
    win.setVisible(true);
    win.setResizable(false);
    win.pack();

  } // end of main method

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///                                 INNER CLASSES                                                       
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  

  
  /**
   * The Save class will save an object to a given file location.
   */
  static class Save {
    
    /**
     * Saves data into given file location.
     * @param obj The given data.
     * @param file The given file location.
     */
    public Save(Object obj, java.io.File file) {
      
      try{    
        java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
        java.io.FileOutputStream fos2 = new java.io.FileOutputStream(new java.io.File("temp.sudokurc"));
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos = new java.io.ObjectOutputStream(fos2);
        oos.writeObject(obj);
        oos.close();
      } catch( java.io.FileNotFoundException ex) {
      } catch( java.io.IOException ex ) {
        ex.printStackTrace();
      }
    }
  }
  
  /**
   * The Load class will load a data file from a chosen location.
   */
  static class Load {
    
    // object field
    Object obj;
    
    /**
     * Loads data from given file location.
     * @param file The given file location.
     */
    public Load(java.io.File file) {
      
      try{
        java.io.FileInputStream fis = new java.io.FileInputStream(file);
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);
        obj = ois.readObject();
        ois.close();
      } catch ( java.io.FileNotFoundException ex ) {
        javax.swing.JOptionPane.showMessageDialog(null, "Invalid File!", "ERROR!", javax.swing.JOptionPane.ERROR_MESSAGE);
      } catch( ClassNotFoundException ex) {
        javax.swing.JOptionPane.showMessageDialog(null, "Invalid File!", "ERROR!", javax.swing.JOptionPane.ERROR_MESSAGE);
      } catch( java.io.IOException ex ) {
        javax.swing.JOptionPane.showMessageDialog(null, "Invalid File!", "ERROR!", javax.swing.JOptionPane.ERROR_MESSAGE);
      }
    }
    
    /**
     * The getObject() returns the object that was loaded.
     * @return The loaded object from the class.
     */
    public Object getObject() {
      return obj;
    }
  }
  
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///                                 INNER CLASSES                                                      
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
} // end of class