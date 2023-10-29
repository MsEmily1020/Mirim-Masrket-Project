package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;	
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import base.BaseFrame;

public class MainFrame extends BaseFrame {

	public static JLabel favoriteLb;

	public MainFrame() {
		super("메인", 1000, 700);
		mainCls = this;
		try {
			main.add(setBounds(btn[2] = new JButton("로그인/회원가입"), 860, 10, 105, 25));
			if(u_no > 0) btn[2].setText("로그아웃");

			btn[2].addActionListener(e -> {
				if(btn[2].getText().equals("로그아웃")) {
					u_no = 0;
					changeFrame(new MainFrame());
				}
				else changeFrame(new LoginFrame());
			});

			main.add(setBounds(btn[5] = new JButton("미림장터", getIcon("datafiles/logo.png", 30, 30)), 10, 45, 150, 30));
			main.add(setBounds(btn[6] = new JButton("판매하기", getIcon("datafiles/image/icon/sell.png", 20, 20)), 715, 45, 90, 25));
			main.add(setBounds(btn[7] = new JButton("내상점", getIcon("datafiles/image/icon/store.png", 20, 20)), 810, 45, 95, 25));
			main.add(setBounds(btn[8] = new JButton("차트", getIcon("datafiles/image/icon/chart.png", 20, 20)), 905, 48, 65, 25));
			main.add(setBounds(btn[9] = actbtn("", e -> search()), 600, 46, 28, 28));

			btn[5].addActionListener(e -> changeFrame(new MainFrame()));
			btn[6].addActionListener(e -> changePage(new SaleFrame().main));
			btn[7].addActionListener(e -> { favoritePage = false; s_no = u_no; changePage(new StoreFrame().main); });
			btn[8].addActionListener(e -> changeFrame(new ChartFrame()));

			main.add(setBounds(tf[0] = new JTextField("상품명, 지역명, @상점명 입력"), 171, 46, 430, 28));

			main.add(setBounds(lb[0] = new JLabel(), 165, 45, 465, 30));
			main.add(setBounds(lb[1] = new JLabel("찜한상품", 0), 910, 120, 60, 15));
			main.add(setBounds(favoriteLb = new JLabel("♥ 0", 0), 910, 140, 60, 15)); 
			main.add(setBounds(lb[3] = new JLabel("최근본상품", 0), 900, 175, 80, 15));

			main.add(setBounds(jp[0] = new JPanel(null), 165, 80, 430, 350));
			main.add(setBounds(jp[1] = new JPanel(), 900, 110, 80, 55));
			main.add(setBounds(jp[2] = new JPanel(), 900, 170, 80, 440));

			main.add(setBounds(jsp = new JScrollPane(), 10, 110, 885, 545));

			jp[0].add(setBounds(btn[10] = actbtn("최근검색어", e -> search(e.getSource())), 5, 5, 205, 35));
			jp[0].add(setBounds(btn[11] = actbtn("인기검색어", e -> search(e.getSource())), 215, 5, 205, 35));
			jp[0].add(setBounds(btn[12] = actbtn("검색어 전체삭제", e -> deleteHistory()), 5, 320, 110, 20));
			jp[0].add(setBounds(btn[13] = actbtn("닫기", e -> jsp.requestFocus()), 380, 320, 40, 20));
			jp[0].add(setBounds(jp[3] = new JPanel(new FlowLayout(0, 0, 0)), 5, 45, 415, 265));
			jp[0].add(setBounds(jp[4] = new JPanel(new FlowLayout(0, 0, 0)), 5, 45, 415, 265));
			jp[0].add(setBounds(jp[5] = new JPanel(), 1, 310, 428, 39));

			jp[4].setVisible(false);

			rs = getResult("select no, content, count(*) cnt from history group by content order by cnt desc, no desc limit 20;");

			for (int i = 0; rs.next(); i++) {
				jp[4].add(setBounds(btn[20] = actbtn((i % 2) * 10 + i / 2 + 1 + "", e -> search()), (int) (jp[3].getWidth() * 0.05), jp[3].getHeight() / 10));
				jp[4].add(setBounds(btn[21] = actbtn(rs.getString("content"), e -> search()), (int) (jp[3].getWidth() * 0.45), jp[3].getHeight() / 10));
				btn[20].setForeground(new Color(0, 128, 0));
				btn[21].setHorizontalAlignment(2);
			}

			tf[0].addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
					jp[0].setVisible(false);
					main.repaint();
				}

				@Override
				public void focusGained(FocusEvent e) {
					jp[0].setVisible(true);
					main.repaint();
				}
			});

			try {
				rs = getResult("select distinct content from history where user_no = ?", u_no);

				while(rs.next()) {
					jp[3].add(setBounds(actbtn("X", e -> {
						update("delete from history where content = ? and user_no = ?", jp[3].getComponentAt(0, ((JButton) e.getSource()).getY()).getName(), u_no);
						jp[3].remove(((JButton) e.getSource()));
						jp[3].remove(jp[3].getComponentAt(0, ((JButton) e.getSource()).getY()));
						jp[3].revalidate();
						jp[3].repaint();
					}), (int) (jp[3].getWidth() * 0.05), jp[3].getHeight() / 10), 0);
					jp[3].add(btn[20] = setBounds(actbtn(rs.getString("content"), e -> tf[0].setText(e.getActionCommand())), (int) (jp[3].getWidth() * 0.95), jp[3].getHeight() / 10), 0);
					btn[20].setHorizontalAlignment(2);
					btn[20].setName(rs.getString("content"));

					setComponent(jp[3]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			setComponent(main);
			setComponent(jp[0]);	
			setComponent(jp[4]);

			tf[0].setForeground(Color.GRAY);

			lb[0].setBorder(new LineBorder(new Color(0, 128, 0)));
			favoriteLb.setForeground(Color.LIGHT_GRAY);

			jp[0].setVisible(false);
			jp[0].setBorder(new LineBorder(Color.GRAY));
			jp[1].setBorder(new LineBorder(Color.BLACK));
			jp[2].setName("1");
			jp[2].setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(30, 0, 0, 0)));
			jp[5].setBackground(getBackground());

			btn[5].setForeground(new Color(0, 128, 0));
			btn[9].setIcon(new ImageIcon("./datafiles/image/icon/search.png"));
			btn[10].setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 128, 0)));
			btn[11].setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
			btn[10].setForeground(new Color(0, 128, 0));
			btn[11].setForeground(Color.GRAY);
			btn[12].setBackground(new Color(238, 238, 238));
			btn[13].setBackground(new Color(238, 238, 238));

			jsp.getViewport().setView(new BackgroundFrame().main);

			recentProduct();
			favoriteList();
			
			tf[0].addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) search();
				};
			});
			
			favoriteLb.addMouseListener(new MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					favoritePage = true;
					changePage(new StoreFrame().main);
				};
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void favoriteList() {
		try {
			rs = getResult("select count(*) as cnt from favorite where user = ?", u_no);
			rs.next();
			if(rs.getInt("cnt") > 0) {
				favoriteLb.setText("♥ " + rs.getInt("cnt"));
				favoriteLb.setForeground(Color.red);
			}

			else {
				favoriteLb.setText("♥ 0");
				favoriteLb.setForeground(Color.lightGray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void deleteHistory() {                                                                                  
		update("delete from history where user_no = ?", u_no);
		jp[3].removeAll();
	}

	public void recentProduct() throws Exception {
		jp[2].removeAll();
		showProductList(jp[2], getResult("select distinct(no), title, price from recent where user_no = ? order by sort desc limit 5", u_no));
	}

	private void search() {
		try {
			if(tf[0].getText().equals("상품명, 지역명, @상점명 입력") || tf[0].getText().length() == 0) { showErr("검색어를 입력해주세요."); return; }
			
			jp[3].removeAll();

			update("insert into history values(null, ?, ?)", u_no, tf[0].getText());
			rs = getResult("select distinct content from history where user_no = ?", u_no);

			while(rs.next()) {
				jp[3].add(setBounds(actbtn("X", e -> {
					jp[3].remove(((JButton) e.getSource()));
					jp[3].remove(jp[3].getComponentAt(0, ((JButton) e.getSource()).getY()));
					jp[3].revalidate();
					jp[3].repaint();
				}), (int) (jp[3].getWidth() * 0.05), jp[3].getHeight() / 10), 0);
				jp[3].add(btn[20] = setBounds(actbtn(rs.getString("content"), e -> tf[0].setText(e.getActionCommand())), (int) (jp[3].getWidth() * 0.95), jp[3].getHeight() / 10), 0);
				btn[20].setHorizontalAlignment(2);

				setComponent(jp[3]);
			}
			rs = getResult("select * from history where user_no = ? order by no desc", u_no);
			rs.next();

			rs = getResult("select * from post where title like '%" + rs.getString("content") + "%'");
			if(!rs.next()) { showErr("검색 결과가 없습니다."); return; }

		} catch (Exception e) {
			e.printStackTrace();
		}

		changePage(new SearchFrame().main);
	}

	public void search(Object obj) {
		if(((JButton) obj).getForeground() == new Color(0, 128, 0)) return;
		btn[10].setBorder(new MatteBorder(0, 0, 2, 0, !jp[3].isShowing() ? new Color(0, 128, 0) : Color.GRAY));
		btn[11].setBorder(new MatteBorder(0, 0, 2, 0, jp[3].isShowing() ? new Color(0, 128, 0) : Color.GRAY));
		btn[10].setForeground(!jp[3].isShowing() ? new Color(0, 128, 0) : Color.GRAY);
		btn[11].setForeground(jp[3].isShowing() ? new Color(0, 128, 0) : Color.GRAY);
		jp[3].setVisible(!jp[3].isShowing());
		jp[4].setVisible(!jp[4].isShowing());
		btn[12].setVisible(jp[3].isShowing());
	}

	public static void main(String[] args) {
		try {
			new MainFrame().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}