package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import base.BaseFrame;

public class ProductFrame extends BaseFrame {

	String str = "", lbTf = "";

	public ProductFrame() {
		super("미림장터", 880, 640);
		try {
			rs = getResult("select *, \r\n"
					+ "(select count(*) from follower f where f.post = ?) as fwer, \r\n"
					+ "(select count(case when user = (select user from post where no = ?) then 1 end) from post p) as pcnt\r\n"
					+ "from post p \r\n"
					+ "where no = ?"
					+ ";", p_no, p_no, p_no);
			
			rs.next();
			
			for (int i = 0; i < 3; i++) {
				str += category.get(filter.get(p_no).get(i)) == null ? ""
						: " > " + category.get(filter.get(p_no).get(i));
			}

			main.add(setBounds(lb[0] = new JLabel("홈" + str), 5, 5, 855, 20));
			main.add(setBounds(lb[1] = new JLabel(), 5, 30, 290, 260));
			main.add(setBounds(lb[2] = new JLabel(rs.getString("title")), 305, 30, 550, 30));
			main.add(setBounds(lb[3] = new JLabel(String.format("%,d원", rs.getInt("price"))), 305, 75, 550, 40));
			main.add(setBounds(lb[5] = new JLabel("연관상품"), 5, 305, 70, 15));
			main.add(setBounds(lb[6] = new JLabel("연관상품"), 5, 305, 70, 15));
			main.add(setBounds(lb[7] = new JLabel("1/4", 4), 815, 300, 40, 20));
			main.add(setBounds(lb[8] = new JLabel("상품정보"), 10, 510, 615, 30));
			main.add(setBounds(lb[9] = new JLabel("유저정보"), 630, 510, 225, 30));

			main.add(setBounds(lb[10] = new JLabel("<HTML><p>" + rs.getString("explanation") + "</p></HTML>"), 10, 450, 615, 300));
			
			main.add(setBounds(lb[11] = new JLabel(getIcon("datafiles/image/user/" + u_no + ".jpg", 45, 45)), 630, 550, 45, 45));
			main.add(setBounds(lb[13] = new JLabel("상품 " + rs.getInt("pcnt") + " | 팔로워 " + rs.getString("fwer")), 675, 580, 180, 20));
			
			lbTf += " | 조회수: " + rs.getString("view");
			lbTf += " | 배송비: " + (rs.getInt("deliveryfee") == 0 ? "무료" : "있음"); 

			rs = getResult("select count(*) as cnt from favorite where post = ?", p_no);
			rs.next();
			
			lbTf = "찜: " + rs.getInt("cnt") + lbTf;
			main.add(setBounds(btn[0] = new JButton("♥찜 " + rs.getInt("cnt")), 305, 240, 275, 50));

			rs = getResult("SELECT * \r\n"
					+ "FROM post p\r\n"
					+ "inner join user u on p.user = u.no\r\n"
					+ "where p.no = ?\r\n"
					+ ";", p_no);
			rs.next();
			main.add(setBounds(lb[12] = new JLabel(rs.getString("name")), 675, 550, 180, 20));	
			s_no = rs.getInt("user");

			main.add(setBounds(lb[4] = new JLabel(lbTf),305, 130, 550, 20));
			
			main.add(setBounds(btn[1] = new JButton("바로구매"), 590, 240, 265, 50));
			
			main.add(setBounds(btn[2] = new JButton("팔로우", getIcon("datafiles/image/icon/unfollow.png", 20, 10)), 630, 610, 225, 35));

			main.add(setBounds(btn[3] = actbtn("◀", e -> paging(-1)), 5, 370, 40, 40));
			main.add(setBounds(btn[4] = actbtn("▶", e -> paging(1)), 815, 370, 40, 40));
			main.add(setBounds(jp[0] = new JPanel(new FlowLayout(0, 4, 0)), 5, 325, 860, 170));

			jp[0].setName("6");

			showProductList(jp[0],
					getResult("select * from post where no != ? and category = ? order by rand() limit 24", p_no,
							filter.get(p_no).get(0)));

			new Thread() {
				public void run() {
					try {
						while (true) {
							for (String file : new File("./datafiles/image/post/" + p_no).list()) {
								lb[1].setIcon(getIcon("./datafiles/image/post/" + p_no + "/" + file, 290, 260));
								Thread.sleep(1000);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();

			setComponent(main);
			setComponent(jp[0]);

			lb[1].setBorder(new LineBorder(Color.BLACK));
			lb[2].setFont(new Font("맑은 고딕", 1, 18));
			lb[3].setFont(new Font("맑은 고딕", 1, 24));
			lb[3].setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
			lb[8].setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
			lb[9].setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
			btn[0].setBackground(Color.GRAY);
			btn[1].setBackground(new Color(0, 128, 0));
			btn[0].setForeground(getBackground());
			btn[1].setForeground(getBackground());
			btn[2].setBorder(new LineBorder(Color.GRAY));
			btn[3].setOpaque(false);
			btn[4].setOpaque(false);
			btn[3].setFont(new Font("맑은 고딕", 1, 20));
			btn[4].setFont(new Font("맑은 고딕", 1, 20));
			btn[3].setForeground(Color.black);
			btn[4].setForeground(Color.black);
			jp[0].setBackground(Color.WHITE);
			jp[0].setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK), new EmptyBorder(0, 0, 30, 0)));

			paging(0);

			main.setPreferredSize(new Dimension(850, 660));
			
			rs = getResult("select * from follower where user = ? and post = ?", u_no, p_no);
			if(rs.next()) {
				btn[2].setBackground(new Color(0, 128, 0));
				btn[2].setIcon(getIcon("datafiles/image/icon/follow.png", 20, 10));
				btn[2].setForeground(Color.white);
			}
			
			
			btn[0].addActionListener(e -> {
				try {
					rs = getResult("select * from favorite where user = ? and post = ?", u_no, p_no);
					
					if(rs.next()) {
						update("delete from favorite where user = ? and post = ?", u_no, p_no);
						btn[0].setText("♥찜 " + (Integer.parseInt(btn[0].getText().replace("<html><font color='red'>♥</font>찜 ", "").replace("</html>", "")) - 1));
						btn[0].setBackground(Color.gray);
						MainFrame.favoriteList();
					}
					
					else {
						update("insert into favorite(user, post) values (?, ?)", u_no, p_no);
						btn[0].setText("<html><font color='red'>♥</font>찜 " + (Integer.parseInt(btn[0].getText().replace("♥찜 ", "")) + 1) + "</html>");
						btn[0].setBackground(Color.black);
						MainFrame.favoriteList();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
			btn[1].addActionListener(e -> {
				int yes = JOptionPane.showConfirmDialog(null, "정말 이 상품을 구매하시겠습니까?", "구매 확인", JOptionPane.YES_NO_OPTION);
				if(yes == JOptionPane.YES_OPTION) {
					update("update post set state = 3 where no = ?");
					changeFrame(new ReviewFrame());
				}
			});
			
			btn[2].addActionListener(e -> {
				try {
					rs = getResult("select * from follower where user = ? and post = ?", u_no, p_no);
					if(rs.next()) {
						update("delete from follower where user = ? and post = ?", u_no, p_no);
						btn[2].setBackground(Color.white);
						btn[2].setIcon(getIcon("datafiles/image/icon/unfollow.png", 20, 10));
						btn[2].setForeground(Color.black);
					}
					
					else {
						update("insert into follower(user, post) values (?, ?)", u_no, p_no);
						btn[2].setBackground(new Color(0, 128, 0));
						btn[2].setIcon(getIcon("datafiles/image/icon/follow.png", 20, 10));
						btn[2].setForeground(Color.white);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
			lb[12].addMouseListener(new MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					changePage(new MyStoreFrame().main);
				};
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void paging(int page) {
		lb[7].setText(Integer.parseInt(lb[7].getText().split("/")[0]) + page + "/4");
		int a = (Integer.parseInt(lb[7].getText().split("/")[0]) - 1) * 6;
		btn[3].setEnabled(!lb[7].getText().equals("1/4"));
		btn[4].setEnabled(!lb[7].getText().equals("4/4"));
		for (int i = 0; i < jp[0].getComponentCount(); i++) {
			jp[0].getComponent(i).setVisible(a <= i && a + 6 > i);
		}
		main.repaint();
		jp[0].repaint();
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}