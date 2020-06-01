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
	
	public int getRows() {
		return brow;
	}
	public int getCols() {
		return bcol;
	}
	public int getMoleRow() {
		return molRow;
	}
	public int getMoleCol() {
		return molCol;
	}
	public int getScore() {
		return score;
	}

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
	
	public JLabel score() {
		l1 = new JLabel("Score: " + myModel.getScore() );
		return l1;
	}

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

	
	public static void main(String[] args) {
		WhackAMole wamm = new WhackAMole();
		WhackAMoleGUI gui = new WhackAMoleGUI(wamm);
		gui.setVisible(true);
	}
}

