import java.lang.reflect.Array;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.JTextComponent;
import java.awt.Font;
import java.util.Enumeration;

public class WhackAMole extends Observable {
	
	public int brow;
	public int bcol;
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
		this.setLocationRelativeTo(null);
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
		
		Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, new FontUIResource("본고딕", Font.BOLD, 15));
        }
        Font font1 = new Font("본고딕", Font.BOLD, 13) ;       
    
        String lvTemp;
        String mdTemp;
		
		JFrame frm = new JFrame("Whack A Mole");
		 
		frm.getContentPane().setBackground(Color.ORANGE);
        // 프레임 크기 설정
        frm.setSize(500, 500);
 
        // 프레임을 화면 가운데에 배치
        frm.setLocationRelativeTo(null);
        
        // 프레임을 닫았을 때 메모리에서 제거되도록 설정
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frm.getContentPane().setLayout(null);
        
        // 버튼 생성
        JButton btn1 = new JButton("Easy");
        JButton btn2 = new JButton("Normal");
        JButton btn3 = new JButton("Hard");
        JButton btn4 = new JButton("Classic");
        JButton btn5 = new JButton("Challenge");
        btn1.setBackground(Color.BLACK);
        btn2.setBackground(Color.BLACK);
        btn3.setBackground(Color.BLACK);
        btn4.setBackground(Color.BLACK);
        btn5.setBackground(Color.BLACK);
        btn1.setForeground(Color.WHITE);
        btn2.setForeground(Color.WHITE);
        btn3.setForeground(Color.WHITE);
        btn4.setForeground(Color.WHITE);
        btn5.setForeground(Color.WHITE);
        btn1.setFont(font1);
        btn2.setFont(font1);
        btn3.setFont(font1);
        btn4.setFont(font1);
        btn5.setFont(font1);
       
        
        // ★ 버튼 위치와 크기 설정
        btn1.setBounds(200, 300, 100, 20);
        btn2.setBounds(200, 330, 100, 20);
        btn3.setBounds(200, 360, 100, 20);
        btn4.setBounds(200, 310, 100, 20);
        btn5.setBounds(200, 340, 100, 20);
        
        // ★ 프레임에다가 버튼 추가
        frm.getContentPane().add(btn1);
        frm.getContentPane().add(btn2);
        frm.getContentPane().add(btn3);
        frm.getContentPane().add(btn4);
        frm.getContentPane().add(btn5);
        btn4.setVisible(false);
        btn5.setVisible(false);
        
        // 라벨
        JLabel lbl = new JLabel();
        lbl.setBounds(200, 260, 100, 30);
        lbl.setText("Select Level");
        lbl.setHorizontalAlignment(JLabel.CENTER); // 수평 가운데 정렬
        frm.getContentPane().add(lbl);
        
        JLabel lbl2 = new JLabel();         
        lbl2.setBounds(150, 150, 200, 50);
        lbl2.setText("Whack - A - Mole ! !");
        lbl2.setHorizontalAlignment(JLabel.CENTER); // 수평 가운데 정렬
        frm.getContentPane().add(lbl2);
        
        JLabel imgLbl = new JLabel();      
        // ★ 라벨에 넣을 아이콘 생성
        ImageIcon bsImg = new ImageIcon(WhackAMole.class.getResource("icons/Mole7.png"));      
        // ★ 라벨에 아이콘 설정
        imgLbl.setIcon(bsImg);       
        // ★ 기타 설정
        imgLbl.setBounds(190, 20, 120, 130);
        imgLbl.setHorizontalAlignment(JLabel.CENTER);
        frm.getContentPane().add(imgLbl);
        
        
        
        
        btn1.addActionListener(event -> {
            lbl.setText("Select Mode");
            btn1.setVisible(false);
            btn2.setVisible(false);
            btn3.setVisible(false);
            btn4.setVisible(true);
            btn5.setVisible(true);
                         
        });       
        btn2.addActionListener(event -> {
        	lbl.setText("Select Mode");
            btn1.setVisible(false);
            btn2.setVisible(false);
            btn3.setVisible(false);
            btn4.setVisible(true);
            btn5.setVisible(true);
            
        });
        btn3.addActionListener(event -> {
        	lbl.setText("Select Mode");
            btn1.setVisible(false);
            btn2.setVisible(false);
            btn3.setVisible(false);          
            btn4.setVisible(true);
            btn5.setVisible(true);
           
        });
        // 프레임이 보이도록 설정
        frm.setVisible(true);
        btn4.addActionListener(event -> {
        	frm.setVisible(false);       	
        	WhackAMole wamm = new WhackAMole();
    		WhackAMoleGUI gui = new WhackAMoleGUI(wamm);
    		gui.setVisible(true);
        });
        btn5.addActionListener(event -> {
        	frm.setVisible(false);      
        	WhackAMole wamm = new WhackAMole();
    		WhackAMoleGUI gui = new WhackAMoleGUI(wamm);
    		gui.setVisible(true);
        });
        System.out.println("hello");
	}
}

