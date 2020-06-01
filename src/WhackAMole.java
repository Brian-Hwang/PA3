import java.lang.reflect.Array;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class WhackAMole extends Observable {

	private int brow;
	private int bcol;
	private int molRow;
	private int molCol;
	private static Random rnd;
	private int score;

	/**
	 * Construct a new 4x4 Whack-a-Mole board. The mole is initially at a randomly
	 * selected location. The score is initially 0.
	 */
	public WhackAMole() {
		brow = 4;
		bcol = 4;
		score = 0;
		rnd = new Random();
		molRow = rnd.nextInt(brow);
		molCol = rnd.nextInt(bcol);

	}

	/**
	 * Get the number of rows on the board.
	 * 
	 * @return the number of rows.
	 */
	public int getRows() {
		return brow;
	}

	/**
	 * Get the number of columns on the board.
	 * 
	 * @return the number of columns.
	 */
	public int getCols() {
		return bcol;
	}

	/**
	 * Get the row containing the mole.
	 * 
	 * @return the row containing the mole.
	 */
	public int getMoleRow() {
		return molRow;
	}

	/**
	 * Get the column containing the mole.
	 * 
	 * @return the column containing the mole.
	 */
	public int getMoleCol() {
		return molCol;
	}

	/**
	 * Get the current score.
	 * 
	 * @return the score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Whack the hole at the specified row and column. When a hole is whacked the
	 * score is increased if there was a mole at that location and decreased if
	 * there was not. Either way the location of the mole is changed and any
	 * observers are notified of the change.
	 * 
	 * @param row the row to whack
	 * @param col the column to whack
	 */
	public void whack(int row, int col) {
		if (col == molCol && row == molRow) {
			score = score + 10;
		} else {
			score = score - 5;
		}
		molRow = rnd.nextInt(brow);
		molCol = rnd.nextInt(bcol);
		setChanged();
		notifyObservers();
	}
	
	public void setMol(int molRow, int molCol) {
		this.molRow = molRow;
		this.molCol = molCol;
	}
	
	
}


class WhackAMoleGUI extends JFrame implements Observer {

	private JButton[][] board;
	private WhackAMole myModel;
	private ImageIcon hole, mole;
	private JLabel l1;

	/**
	 * Construct a new WhackAMoleGUI for the specified model.
	 * 
	 * @param myModel the model for this GUI.
	 */
	public WhackAMoleGUI(WhackAMole myModel) {
		super("Whack A Mol");
		this.myModel = myModel;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new JButton[myModel.getRows()][myModel.getCols()];
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.add(mainPanel);
		mainPanel.add(score());
		mole = new ImageIcon(WhackAMoleGUI.class.getResource("icons/gopher.jpg"));
		hole = new ImageIcon(WhackAMoleGUI.class.getResource("icons/hole.jpg"));
		for (int i = 0; i < myModel.getRows(); i++) {
			mainPanel.add(getJPanel(i));
		}
		board[myModel.getMoleRow()][myModel.getMoleCol()].setIcon(mole);
		myModel.addObserver(this);
		
		this.pack();
		for(int row = 0; row <myModel.getRows(); row++){
			for(int col = 0; col <myModel.getRows(); col++){
				board[row][col].addActionListener(new ButtonListener(row, col));
			}
		}
	}
	/**
	 * returns a JLabel with a score.
	 */
	
	public JLabel score() {
		l1 = new JLabel("Score: " + myModel.getScore() );
		return l1;
	}
	
	/**
	 * returns a JPanel with a 4x4 board full of buttons.
	 */

	public JPanel getJPanel(int row) {
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));

		for (int i = 0; i < myModel.getCols(); i++) {

			JButton button = new JButton(hole);
			rowPanel.add(button);
			board[row][i] = button;
		}
		return rowPanel;
	}
	
	/**
	 * An inner class that defines an ActionListener for the buttons.
	 */

	private class ButtonListener implements ActionListener {
		private int row;
		private int col;

		public ButtonListener(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public void actionPerformed(ActionEvent e) {
			board[myModel.getMoleRow()][myModel.getMoleCol()].setIcon(hole);
			myModel.whack(row, col);
		}
	}

	/**
	 * Update the GUI to reflect the state of the model. This method repaints all of
	 * the buttons with a hole and then repaints the button with the mole on it.
	 */

	@Override
	public void update(Observable o, Object arg) {
		l1.setText("Score: " + myModel.getScore());
		board[myModel.getMoleRow()][myModel.getMoleCol()].setIcon(mole);

		
	}

	
	
	/**
	 * Run the WhackAMole game.
	 * 
	 * @param args none
	 */
	public static void main(String[] args) {
		WhackAMole wamm = new WhackAMole();
		WhackAMoleGUI gui = new WhackAMoleGUI(wamm);
		gui.setVisible(true);
	}
}

