import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Comp.ChessType;

public class Board extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel label;
	ArrayList<Image> chesses;
	ArrayList<ChessType> types;
	ArrayList<Point> positions;
	boolean[][] flag;
	ArrayList<Point> click;
	static int num;
	static int devide;
	boolean round;
	JButton start;
	boolean startOrNot;
	
	public Board() {
		this.setLayout(null);
		this.addMouseListener(this);
		label = new JLabel("Red Round");
		start = new JButton("Start");
		startOrNot = false;
		label.setBounds(200, 50, 100, 20);
		start.setBounds(200, 100, 100, 50);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!startOrNot) {
					startOrNot = true;
				}
			}
			
		});
		label.setHorizontalAlignment(JLabel.CENTER);
		this.add(label);
		this.add(start);
		chesses = new ArrayList<Image>();
		types = new ArrayList<ChessType>();
		positions = new ArrayList<Point>();
		click = new ArrayList<Point>();
		flag = new boolean[10][9];
		for(int i=0; i<flag.length; i++) {
			for(int j=0; j<flag[i].length; j++) {
				flag[i][j] = false;
			}
		}
		devide = 16;
		round = true;
		for(int i=0; i<10; i++) {
			if(i==0 || i == 9) {
				for(int j=0; j<9; j++) {
					chesses.add((new ImageIcon("src/"+(i/9+1)+"-"+(j<4?(j+1):9-j)+".png")).getImage());
					types.add(ChessType.getType(j<4?j+1:9-j));
					positions.add(new Point(i, j));
					flag[i][j] = true;
				}
			} else if(i == 2 || i == 7) {
				chesses.add((new ImageIcon("src/"+(i/7+1)+"-6.png")).getImage());
				chesses.add((new ImageIcon("src/"+(i/7+1)+"-6.png")).getImage());
				types.add(ChessType.getType(6));
				types.add(ChessType.getType(6));
				positions.add(new Point(i>2?7:2, 1));
				positions.add(new Point(i>2?7:2, 7));
				flag[i>2?7:2][1] = true;
				flag[i>2?7:2][7] = true;
			} else if(i == 3 || i == 6) {
				for(int j=0; j<9; j++) {
					if(j % 2 == 0) {
						chesses.add((new ImageIcon("src/"+(i/3)+"-7.png")).getImage());
						types.add(ChessType.getType(7));					
						positions.add(new Point(i>3?6:3, j));
						flag[i>3?6:3][j] = true;
					}
				}
			}
		}
	}

	public void paint(Graphics g1) {
		super.paint(g1);
		Graphics2D g = (Graphics2D) g1;
		g.drawImage(new ImageIcon("src/back.png").getImage(), Interface.STARTX-40, Interface.STARTY-40, null);
		for (int i = 0; i < 10; i++) {
			g.drawLine(Interface.STARTX, Interface.STARTY + Interface.GRIDH * i, Interface.STARTX + Interface.GRIDW * 8,
					Interface.STARTY + Interface.GRIDH * i);
		}
		for (int i = 0; i < 9; i++) {
			if (i == 0 || i == 8) {
				g.drawLine(Interface.STARTX + Interface.GRIDW * i, Interface.STARTY,
						Interface.STARTX + Interface.GRIDW * i, Interface.STARTY + Interface.GRIDH * 9);
			} else {
				g.drawLine(Interface.STARTX + Interface.GRIDW * i, Interface.STARTY,
						Interface.STARTX + Interface.GRIDW * i, Interface.STARTY + Interface.GRIDH * 4);
				g.drawLine(Interface.STARTX + Interface.GRIDW * i, Interface.STARTY + Interface.GRIDH * 5,
						Interface.STARTX + Interface.GRIDW * i, Interface.STARTY + Interface.GRIDH * 9);
			}
			if (i == 3) {
				g.drawLine(Interface.STARTX + Interface.GRIDW * 3, Interface.STARTY,
						Interface.STARTX + Interface.GRIDW * 5, Interface.STARTY + Interface.GRIDH * 2);
				g.drawLine(Interface.STARTX + Interface.GRIDW * 3, Interface.STARTY + Interface.GRIDH * 7,
						Interface.STARTX + Interface.GRIDW * 5, Interface.STARTY + Interface.GRIDH * 9);
			} else if (i == 5) {
				g.drawLine(Interface.STARTX + Interface.GRIDW * 5, Interface.STARTY,
						Interface.STARTX + Interface.GRIDW * 3, Interface.STARTY + Interface.GRIDH * 2);
				g.drawLine(Interface.STARTX + Interface.GRIDW * 5, Interface.STARTY + Interface.GRIDH * 7,
						Interface.STARTX + Interface.GRIDW * 3, Interface.STARTY + Interface.GRIDH * 9);
			}
		}
		for(int i=0; i<positions.size(); i++) {
			g.drawImage(chesses.get(i), (int) (Interface.STARTX+positions.get(i).getY()*Interface.GRIDW-30), (int) (Interface.STARTY+positions.get(i).getX()*Interface.GRIDH-30), null);
		}
		this.add(label);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(startOrNot) {
			int j = (e.getX()-Interface.STARTX)/Interface.GRIDW+((e.getX()-Interface.STARTX)%Interface.GRIDW>Interface.GRIDW/2?1:0);
			int i = (e.getY()-Interface.STARTY)/Interface.GRIDH+((e.getY()-Interface.STARTY)%Interface.GRIDH>Interface.GRIDH/2?1:0);
			if(new Rectangle(Interface.STARTX, Interface.STARTY, Interface.GRIDW*8, Interface.GRIDH*9).contains(e.getPoint())) {
				if(click.size() == 0) {
					if(flag[i][j]) {
						for(int k=0; k<positions.size(); k++) {
							if(positions.get(k).equals(new Point(i, j))) {
								if(round && k < devide) {
									click.add(new Point(i, j));
									num=k;
								} else if (!round && k >= devide) {
									click.add(new Point(i, j));
									num=k;
								}
							}
						}
					}
				} else {
					if(!flag[i][j]) {
						if(!click.contains(new Point(i, j))) {
							click.add(new Point(i, j));
							boolean result = move(click);
							if(result) {
								if(round) {
									round = false;
									label.setText("Black Round");
								} else {
									round = true;
									label.setText("Red Round");
								}
							}
							click.remove(0);
							click.remove(0);
						}
					} else {
						for(int k=0; k<positions.size(); k++) {
							if(positions.get(k).equals(new Point(i, j))) {
								if(num < devide && k >= devide) {
									click.add(new Point(i, j));
									boolean result = move(click);
									int isGameOver = gameOver();
									if(isGameOver == 1) {
										int option = JOptionPane.showOptionDialog(this, "Red wins", "Congraduaiton", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Continue", "Exit"}, new String("Continue"));
										if(option == JOptionPane.YES_OPTION) {
											Interface.main.dispose();
											new Interface();
										} else if (option == JOptionPane.NO_OPTION) {
											System.exit(0);
										}
									} else if (isGameOver == 2) {
										int option = JOptionPane.showOptionDialog(this, "Black wins", "Congraduaiton", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Continue", "Exit"}, new String("Continue"));
										if(option == JOptionPane.YES_OPTION) {
											Interface.main.dispose();
											new Interface();
										} else if (option == JOptionPane.NO_OPTION) {
											System.exit(0);
										}
									}
									if(result) {
										if(round) {
											round = false;
											label.setText("Black Round");
										} else {
											round = true;
											label.setText("Red Round");
										}
									}
									click.remove(0);
									click.remove(0);
								} else if (num >= devide && k < devide) {
									click.add(new Point(i, j));
									boolean result = move(click);
									int isGameOver = gameOver();
									if(isGameOver == 1) {
										int option = JOptionPane.showOptionDialog(this, "Red wins", "Congraduaiton", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Continue", "Exit"}, new String("Continue"));
										if(option == JOptionPane.YES_OPTION) {
											Interface.main.dispose();
											new Interface();
										} else if (option == JOptionPane.NO_OPTION) {
											System.exit(0);
										}
									} else if (isGameOver == 2) {
										int option = JOptionPane.showOptionDialog(this, "Black wins", "Congraduaiton", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Continue", "Exit"}, new String("Continue"));
										if(option == JOptionPane.YES_OPTION) {
											Interface.main.dispose();
											new Interface();
										} else if (option == JOptionPane.NO_OPTION) {
											System.exit(0);
										}
									}
									if(result) {
										if(round) {
											round = false;
											label.setText("Black Round");
										} else {
											round = true;
											label.setText("Red Round");
										}
									}
									click.remove(0);
									click.remove(0); 
									devide--;
								}
							}
						}
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(Interface.main, "Start First!");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0; i<10; i++) {
			for(int j=0; j<9; j++) {
//				if(e.getSource() == chesses[i][j]) {
//					chesses[i][j].setBackground(Color.RED);
//				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0; i<10; i++) {
			for(int j=0; j<9; j++) {
//				if(e.getSource() == chesses[i][j]) {
//					chesses[i][j].setBackground(null);
//				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean move(ArrayList<Point> click) {
		int x1 = (int) click.get(0).getX();
		int y1 = (int) click.get(0).getY();
		int x2 = (int) click.get(1).getX();
		int y2 = (int) click.get(1).getY();
		if(types.get(num) == ChessType.ROOKS) {
			boolean flag = false;
			if(x1 == x2 && y1 != y2) {
				for(int i=1; i<=Math.abs(y2-y1)-1; i++) {
					if(positions.contains(new Point(x1, y2>y1?y1+i:y1-i))) {
						flag = true;
						break;
					}
				}
				if(!flag) {
					for(int k=0; k<positions.size(); k++) {
						if(positions.get(k).equals(new Point(x1, y2>y1?y1+Math.abs(y2-y1):y1-Math.abs(y2-y1)))) {
							positions.remove(k);
							chesses.remove(k);
							types.remove(k);
							if(!round)
								num--;
							break;
						}
					}
				}
			}else if(y1 == y2 && x1 != x2) {
				for(int i=1; i<=Math.abs(x2-x1)-1; i++) {
					if(positions.contains(new Point(x2>x1?x1+i:x1-i, y1))) {
						flag = true;
						break;
					}
				}
				if(!flag) {
					for(int k=0; k<positions.size(); k++) {
						if(positions.get(k).equals(new Point(x2>x1?x1+Math.abs(x2-x1):x1-Math.abs(x2-x1), y1))) {
							positions.remove(k);
							chesses.remove(k);
							types.remove(k);
							if(!round)
								num--;
							break;
						}
					}
				}
			}
			if (!flag) {
				change(x1, y1, x2, y2);
				return true;
			}
		} else if(types.get(num) == ChessType.KNIGHTS) {
			if(Math.abs(x2-x1) == 2 && Math.abs(y2-y1) == 1) {
				if(!positions.contains(new Point(x2>x1?x1+1:x1-1, y1))) {
					defeat(click.get(1));
					change(x1, y1, x2, y2);
					return true;
				}
			} else if (Math.abs(x2-x1) == 1 && Math.abs(y2-y1) == 2) {
				if(!positions.contains(new Point(x1, y2>y1?y1+1:y1-1))) {
					defeat(click.get(1));
					change(x1, y1, x2, y2);
					return true;
				}
			}
		} else if(types.get(num) == ChessType.ELEPHANTS) {
			if(Math.abs(x2-x1) == 2 && Math.abs(y2-y1) == 2 && ((x1 < 5 && x2 < 5) || (x1 > 4 && x2 > 4))) {
				if(!positions.contains(new Point(x2>x1?x1+1:x1-1, y2>y1?y1+1:y1-1))) {
					defeat(click.get(1));
					change(x1, y1, x2, y2);
					return true;
				}
			}
		} else if(types.get(num) == ChessType.MANDARINS) {
			if (Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 1 && 
					(y1 >= 3 && y1 <= 5) && (y2 >= 3 && y2 <= 5) 
					&& ((x1 < 3 && x2 < 3)||(x1>6 && x2 > 6))) {
				defeat(click.get(1));
				change(x1, y1, x2, y2);
				return true;
			}
		} else if(types.get(num) == ChessType.KING) {
			if (((Math.abs(x2 - x1) == 1 && Math.abs(y2 - y1) == 0)
					||(Math.abs(x2 - x1) == 0 && Math.abs(y2 - y1) == 1)) 
					&& (y1 >= 3 && y1 <= 5) && (y2 >= 3 && y2 <= 5) 
					&& ((x1 < 3 && x2 < 3)||(x1>6 && x2 > 6))) {
				defeat(click.get(1));
				change(x1, y1, x2, y2);
				return true;
			}
		} else if(types.get(num) == ChessType.CANNONS) {
			int count = 0;
			boolean flag = false;
			if(x1 == x2 && y1 != y2) {
				for(int i=1; i<=Math.abs(y2-y1); i++) {
					if(positions.contains(new Point(x1, y2>y1?y1+i:y1-i))) {
						flag = true;
						count++;
					}
				}
				if(count == 2) {
					if(positions.contains(click.get(1))) {
						for(int k=0; k<positions.size(); k++) {
							if(positions.get(k).equals(click.get(1))) {
								positions.remove(k);
								chesses.remove(k);
								types.remove(k);
								if(!round)
									num--;
								flag = false;
								break;
							}
						}
					}
				}
			}else if(y1 == y2 && x1 != x2) {
				for(int i=1; i<=Math.abs(x2-x1); i++) {
					if(positions.contains(new Point(x2>x1?x1+i:x1-i, y1))) {
						flag = true;
						count++;
					}
				}
				if(count == 2) {
					if(positions.contains(click.get(1))) {
						for(int k=0; k<positions.size(); k++) {
							if(positions.get(k).equals(click.get(1))) {
								positions.remove(k);
								chesses.remove(k);
								types.remove(k);
								if(!round)
									num--;
								flag = false;
								break;
							}
						}
					}
				}
			}
			if (!flag) {
				change(x1, y1, x2, y2);
				return true;
			}
		} else if(types.get(num) == ChessType.PAWNS) {
			if(num < devide ? x1 > 4 : x1 < 5) {
				if((x2 - x1 == (num < devide ? 1 : -1) && y1 == y2)|| (Math.abs(y2-y1) == 1 && x1 == x2)) {
					defeat(click.get(1));
					change(x1, y1, x2, y2);
					return true;
				}
			} else {
				if(x2 - x1 == (num < devide ? 1 : -1) && y1 == y2) {
					defeat(click.get(1));
					change(x1, y1, x2, y2);
					return true;
				}
			}
		}
		return false;
	}
	
	public void change(int x1, int y1, int x2, int y2) {
		positions.remove(num);
		this.flag[x1][y1] = false;
		positions.add(num, new Point(click.get(1)));
		this.flag[x2][y2] = true;
		this.repaint();
		this.setVisible(true);
	}
	
	public void defeat(Point point) {
		if(positions.contains(point)) {
			for(int k=0; k<positions.size(); k++) {
				if(positions.get(k).equals(point)) {
					positions.remove(k);
					chesses.remove(k);
					types.remove(k);
					if(!round)
						num--;
					break;
				}
			}
		}
	}
	
	public int gameOver() {
		int count = 0;
		for(int i=0; i<types.size(); i++) {
			if(types.get(i) == ChessType.KING) {
				count++;
			}
		}
		if(count == 2) {
			return 0;
		} else {
			if(round) {
				return 1;
			} else
				return 2;
		}
	}
}
