import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GUI application for ConnectFour
 *
 * @author Mukul Sauhta
 * @author Raven Llarina
 * @author Ryan Foster
 * @author Mustafa Irfan
 */
public class ConnectFourGUI extends JFrame implements ActionListener {
    /**
     * Declare a panel for the application window
     */
    private JPanel panel;

    /**
     * Declare a frame for the application window
     */
    private JFrame frame;

    /**
     * Declare a grid layout for the application window
     */
    private GridLayout connectGrid;

    /**
     * Declare a 2d array of JButtons that holds each spot that the user could click
     */
    private JButton[][] spots;

    /**
     * Declare a spot holder that holds all possible spots in a connect four grid
     */
    private Spot connectFourGrid[][];

    /**
     * Declare an image icon for a red connect four icon
     */
    private ImageIcon redIcon = new ImageIcon("test-files/redconnectfour.png");

    /**
     * Declare an image icon for a yellow connect four icon
     */
    private ImageIcon yellowIcon = new ImageIcon("test-files/yellowconnectfour.png");

    /**
     * Declare an image icon for an empty connect four icon
     */
    private ImageIcon emptyIcon = new ImageIcon("test-files/emptyconnectfour.png");

    /**
     * This holds the number of pieces that have been played, which will start at 0.
     */
    private int piecesPlayed = 0;

    /**
     * This holds the longest sequence
     */
    private int longestSequence = 0;

    /**
     * This holds the number of times that player 1 has won.
     */
    private int playerOneWins = 0;

    /**
     * This holds the number of times that player 2 has won
     */
    private int playerTwoWins = 0;

    /**
     * Label to show wins for Player 1
     */
    private JLabel playerOneLabel;

    /**
     * Label to show wins for Player 2
     */
    private JLabel playerTwoLabel;

    /**
     * Label to show number of pieces played
     */
    private JLabel piecesPlayedLabel;

    /**
     * Label to show longest sequence
     */
    private JLabel longestSequenceLabel;

    /**
     * This holds the amount in a row that the user needs to win, user input
     * size of the board is based on this, double the value.
     */
    private int inARow;

    /**
     * Constructor for ConnectFour
     */
    public ConnectFourGUI() {
        //make sure that it exits when closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //make a new panel for the application
        this.panel = new JPanel();
        //this will display in the upper area of the window.
        this.frame = new JFrame("Connect Four");

        //here we ask the user for input, basing the amount to win off of their answer,
        //and using info to set the grid as well.
        String toWin = JOptionPane.showInputDialog("How many do you want to connect to win?");
        int toWinInt = Integer.parseInt(toWin);

        //per the instructions, we must make sure that the amount of pieces connected is
        //greater than or equal to 4.
        if (!(toWinInt >= 4)) {
            JOptionPane.showMessageDialog(null, "Please enter a board size that is >= 4.");
            System.exit(1);
        }

        //set thisinarow to the integer of how many pieces in a row to win.
        this.inARow = toWinInt;

        int dimension = inARow * 2;
        //the grid is double the amount in a row to win.
        connectFourGrid = new Spot[dimension][dimension];

        //set up a for loop to now create a grid of spots based on userinput.
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                connectFourGrid[i][j] = new Spot();
            }
        }

        //now make the grid that will be used by Jpanel to create a panel for connect four.
        this.connectGrid = new GridLayout(dimension, dimension);


        //create an array of spots that matches up with the grid.
        this.spots = new JButton[dimension][dimension];

        //now make the panel using the connect four grid.
        panel.setLayout(connectGrid);

        //now we must construct the board to have empty spots with no color, red or yellow.
        int emptyNumber = 0;

        //important to note that we use >= 0 so that the array can utilize the smallest possible value.
        for (int i = dimension - 2; i >= 0; --i) {
            for (int k = dimension - 1; k >= 0; k--) {
                //by setting it to neither player 1 or 2, it makes the cell empty.
                connectFourGrid[i][k].setSpotColor(0);
            }
        }

        //now we can construct the entire grid with the images we made of connect four.
        Container pane = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // create the status bar panel and shove it down the bottom of the frame
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(1, 8));
        statusPanel.add(new JLabel("Player 1 : "));
        this.playerOneLabel = new JLabel("" + playerOneWins);
        statusPanel.add(playerOneLabel);
        statusPanel.add(new JLabel("Player 2 : "));
        this.playerTwoLabel = new JLabel("" + playerTwoWins);
        statusPanel.add(playerTwoLabel);
        statusPanel.add(new JLabel("Pieces Played : "));
        this.piecesPlayedLabel = new JLabel("" + piecesPlayed);
        statusPanel.add(piecesPlayedLabel);
        statusPanel.add(new JLabel("Longest Sequence : "));
        this.longestSequenceLabel = new JLabel("" + 0);
        statusPanel.add(longestSequenceLabel);

        pane.add(statusPanel, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);

        fillBoard();
        pane.add(panel, BorderLayout.SOUTH);


        frame.pack();
        frame.setVisible(true);

    }

    /**
     * This method is designed fill the board using the image that our group made
     * for the empty connect four grid.
     */
    public void fillBoard() {
        for (int i = 0; i < inARow * 2; i++) {
            for (int k = 0; k < inARow * 2; k++) {
                //here we fill in the fill in all of the grid with empty buttons.
                spots[i][k] = new JButton(emptyIcon);
                //next we must check whether or not the button has been clicked
                spots[i][k].addActionListener(this);
                //now we must add these buttons which have the image attached to it
                //depending on whether or not they are clicked to the panel on the window.
                panel.add(spots[i][k]);
            }

        }
    }

    /**
     * This method ensures that
     * the action listener will check if a button changes from empty to red or yellow.
     * By making a bunch of buttons "spots" that hold the ability to be clicked,
     * we must make sure that depending on the turn the player is in, their spot
     * is updated by changing to their respective color.
     */
    public void actionPerformed(ActionEvent e) {
        //we need to make sure that the cell the player is trying to click is empty,
        //so in order to check this, we can utilize try catch for this entire method.
        try {
            //keep track if valid button was clicked
            boolean buttonClicked = false;

            //here we create an integer that ensures that only one source is gotten
            //for each time a button is clicked, onePlayer. Once we iterate through
            //the board and confirm that a spot is already updated, we need to make
            //sure that it doesn't do the same for the second player.
            //If pieces play is even, it's player 1 else player 2
            int player = 1 + (piecesPlayed % 2);
            int boardSize = inARow * 2;
            int selectedCol = 0;
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (e.getSource() == spots[row][col]) {
                        //player selected empty cell
                        if (connectFourGrid[row][col].getSpotColor() == 0) {
                            buttonClicked = true;
                            selectedCol = col;
                        }
                        break;
                    }
                }
            }
            if (!buttonClicked) {
                throw new RuntimeException("Filled CELL was clicked");
            } else {
                //valid button was clicked
                piecesPlayed++;
                this.piecesPlayedLabel.setText("" + piecesPlayed);
                //now found the lowest row
                int lowestRow = boardSize - 1;
                while (connectFourGrid[lowestRow][selectedCol].getSpotColor() != 0)
                    lowestRow--;
                connectFourGrid[lowestRow][selectedCol].setSpotColor(player);
                if (player == 1)
                    spots[lowestRow][selectedCol].setIcon(yellowIcon);
                else
                    spots[lowestRow][selectedCol].setIcon(redIcon);

                checkWin(player);
            }
        } catch (Exception h) {
            //display that the cell is already filled.
            cellAlreadyFilled();
        }
    }

    /**
     * This method checks to see if a player has won the game.
     */
    public void checkWin(int player) {
        //iterate through the entire grid using basic syntax
        boolean playerWon = false;
        int boardSize = inARow * 2;
        for (int row = 0; !playerWon && row < inARow * 2; row++) {
            for (int col = 0; !playerWon && col < inARow * 2; col++) {
                if (connectFourGrid[row][col].getSpotColor() == player) {
                    //check vertical win
                    for (int z = 1; z < inARow; z++) {
                        if ((col + z) >= boardSize) break;
                        else if (connectFourGrid[row][col + z].getSpotColor() != player) break;
                        else if ((z + 1) > longestSequence) longestSequence = (z + 1);
                    }
                    if (longestSequence == inARow) break;
                    //check left to right
                    for (int z = 1; z < inARow; z++) {
                        if ((row + z) >= boardSize) break;
                        else if (connectFourGrid[row + z][col].getSpotColor() != player) break;
                        else if ((z + 1) > longestSequence) longestSequence = (z + 1);
                    }
                    if (longestSequence == inARow) break;

                    //check diagonal left to right
                    for (int z = 1; z < inARow; z++) {
                        if ((row + z) >= boardSize || (col + z) >= boardSize) break;
                        else if (connectFourGrid[row + z][col + z].getSpotColor() != player) break;
                        else if ((z + 1) > longestSequence) longestSequence = (z + 1);
                    }
                    if (longestSequence == inARow) break;
                    //check diagonal right to left
                    for (int z = 1; z < inARow; z++) {
                        if ((row + z) >= boardSize || (col - z) < 0) break;
                        else if (connectFourGrid[row + z][col - z].getSpotColor() != player) break;
                        else if ((z + 1) > longestSequence) longestSequence = (z + 1);
                    }
                }
            }
            playerWon = (longestSequence == inARow);
        }
        longestSequenceLabel.setText("" + longestSequence);
        if (playerWon) {
            display(player);
        }
    }

    /**
     * This is the method used when a player wins, which displays the winner.
     */
    public void display(int player) {
        if (player == 1) {
            this.playerOneWins++;
            this.playerOneLabel.setText("" + playerOneWins);
            JOptionPane.showMessageDialog(new JFrame(), "Player 1 has won " + playerOneWins +
                    " times!", "", JOptionPane.INFORMATION_MESSAGE);
            continu();
        } else {
            this.playerTwoWins++;
            this.playerTwoLabel.setText("" + playerTwoWins);
            JOptionPane.showMessageDialog(new JFrame(), "Player 2 has won " + playerTwoWins +
                    " times!", "", JOptionPane.INFORMATION_MESSAGE);
            continu();
        }
    }

    /**
     * This is the message that displays when the user tries to click on a cell that
     * is already filled.
     */
    public void cellAlreadyFilled() {

        JOptionPane.showMessageDialog(new JFrame(), "Space already filled!", "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This method allows the players to keep playing once a winner has been decided.
     */
    public void continu() {
        for (int i = 0; i < inARow * 2; i++) {
            for (int k = 0; k < inARow * 2; k++) {
                //by setting it to neither player 1 or 2, it makes the cell empty.
                connectFourGrid[i][k].setSpotColor(0);
                //make the spots empty once again
                spots[i][k].setIcon(emptyIcon);
            }
        }
        piecesPlayed = 0;
        this.piecesPlayedLabel.setText("" + piecesPlayed);
        longestSequence = 0;
        this.longestSequenceLabel.setText("" + longestSequence);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * By utilizing the main method, we have no need for an external java file to
     * start this class. Instead, this method will automatically start the java file
     * and display the connect four gameboard.
     *
     * @param args string array of command line arguments.
     */
    public static void main(String[] args) {
        new ConnectFourGUI();
    }
}
