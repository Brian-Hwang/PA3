import java.lang.reflect.Array;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.JTextComponent;
import java.awt.Font;

public class WhackAMole extends Observable {
	
	public int brow;
	public int bcol;
	private int molRow;
	private int molCol;
	private static Random rnd;
	private int score;
	private boolean isnorm;
	private double timing;

	public WhackAMole() {
		score = 0;
	}
	
	void setLevel(int level){
		brow = 3+level;
		bcol = 3+level;
		timing=level*0.1;
		rnd = new Random();
		molRow = rnd.nextInt(brow);
		molCol = rnd.nextInt(bcol);
	}
	void setMode(boolean isnorm) {
		this.isnorm=isnorm;
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
	public double getTiming() {
		return timing;
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
	private ImageIcon moleunscaled, holeunscaled, hole, mole;
	private JLabel l1,timer;	

	public WhackAMoleGUI(WhackAMole myModel) {
		super("Whack A Mol");
		this.myModel = myModel;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		board = new JButton[myModel.getRows()][myModel.getCols()];
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.add(mainPanel);
		timer= new JLabel();
		timer.setBounds(100, 100, 200, 30);
		Timer time = new Timer();
		mainPanel.add(timer);
		mainPanel.add(score());
		time.scheduleAtFixedRate(new TimerTask() {
			int i= 30;
			@Override
			public void run() {
				timer.setText("Timer: "+(i--));
				if(i<0) {
					time.cancel();// end game!
				}
			}
			
		}, 0, 1000);
		moleunscaled = new ImageIcon(WhackAMoleGUI.class.getResource("icons/Moleup.png"));
		holeunscaled = new ImageIcon(WhackAMoleGUI.class.getResource("icons/Moledown.png"));
		mole=new ImageIcon(moleunscaled.getImage().getScaledInstance(moleunscaled.getIconHeight()/5,moleunscaled.getIconHeight()/5,Image.SCALE_SMOOTH));
		hole=new ImageIcon(holeunscaled.getImage().getScaledInstance(moleunscaled.getIconHeight()/5,moleunscaled.getIconHeight()/5,Image.SCALE_SMOOTH));
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
			button.setBackground(new Color(255, 214, 66));
			button.setOpaque(true);
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
                UIManager.put(key, new FontUIResource("�����", Font.BOLD, 15));
        }
    	WhackAMole wamm = new WhackAMole();
		
		
        Font font1 = new Font("�����", Font.BOLD, 13) ;       
    
        String lvTemp;
        String mdTemp;
		
		JFrame frm = new JFrame("Whack A Mole");
		 
		frm.getContentPane().setBackground(Color.ORANGE);
        // ������ ũ�� ����
        frm.setSize(500, 500);
 
        // �������� ȭ�� ����� ��ġ
        frm.setLocationRelativeTo(null);
        
        // �������� �ݾ��� �� �޸𸮿��� ���ŵǵ��� ����
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frm.getContentPane().setLayout(null);
        
        // ��ư ����
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
       
        
        // �� ��ư ��ġ�� ũ�� ����
        btn1.setBounds(200, 300, 100, 20);
        btn2.setBounds(200, 330, 100, 20);
        btn3.setBounds(200, 360, 100, 20);
        btn4.setBounds(200, 310, 100, 20);
        btn5.setBounds(200, 340, 100, 20);
        
        // �� �����ӿ��ٰ� ��ư �߰�
        frm.getContentPane().add(btn1);
        frm.getContentPane().add(btn2);
        frm.getContentPane().add(btn3);
        frm.getContentPane().add(btn4);
        frm.getContentPane().add(btn5);
        btn4.setVisible(false);
        btn5.setVisible(false);
        
        // ��
        JLabel lbl = new JLabel();
        lbl.setBounds(200, 260, 100, 30);
        lbl.setText("Select Level");
        lbl.setHorizontalAlignment(JLabel.CENTER); // ���� ��� ����
        frm.getContentPane().add(lbl);
        
        JLabel lbl2 = new JLabel();         
        lbl2.setBounds(150, 150, 200, 50);
        lbl2.setText("Whack - A - Mole ! !");
        lbl2.setHorizontalAlignment(JLabel.CENTER); // ���� ��� ����
        frm.getContentPane().add(lbl2);
        
        JLabel imgLbl = new JLabel();      
        // �� �󺧿� ���� ������ ����
        ImageIcon bsImg = new ImageIcon(WhackAMole.class.getResource("icons/Mole7.png"));      
        // �� �󺧿� ������ ����
        imgLbl.setIcon(bsImg);       
        // �� ��Ÿ ����
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
            wamm.setLevel(1);
                         
        });       
        btn2.addActionListener(event -> {
        	lbl.setText("Select Mode");
            btn1.setVisible(false);
            btn2.setVisible(false);
            btn3.setVisible(false);
            btn4.setVisible(true);
            btn5.setVisible(true);
            wamm.setLevel(2);
        });
        btn3.addActionListener(event -> {
        	lbl.setText("Select Mode");
            btn1.setVisible(false);
            btn2.setVisible(false);
            btn3.setVisible(false);          
            btn4.setVisible(true);
            btn5.setVisible(true);
            wamm.setLevel(3);
           
        });
        // �������� ���̵��� ����
        frm.setVisible(true);
        btn4.addActionListener(event -> {
        	frm.setVisible(false);
        	WhackAMoleGUI gui = new WhackAMoleGUI(wamm);
    		gui.setVisible(true);
        });
        btn5.addActionListener(event -> {
        	frm.setVisible(false);
        	WhackAMoleGUI gui = new WhackAMoleGUI(wamm);
    		gui.setVisible(true);
        });
	}
}

