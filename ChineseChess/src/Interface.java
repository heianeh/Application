import java.awt.Toolkit;

import javax.swing.JFrame;

public class Interface {
	final static int X = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final static int Y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40);
	final static int GRIDW = 65;
	final static int GRIDH = 65;
	final static int STARTX = 400;
	final static int STARTY = 50;
	Board board;
	static JFrame main;
	public Interface () {
		main = new JFrame("Chinese Chess");
		board = new Board();
		main.add(board);
		main.setBounds(0, 0, X, Y);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Interface();
	}
}
