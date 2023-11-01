package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import base.BaseFrame;

public class SearchFrame extends BaseFrame {

	public SearchFrame() {
		super("미림장터", 880, 640);
		try {
			rs = getResult("select * from history where user_no = ? order by no desc", u_no);
			rs.next();

			main.add(setBounds(btn[0] = new JButton("최신순"), 650, 60, 50, 20));
			main.add(setBounds(btn[1] = new JButton("인기순"), 700, 60, 50, 20));
			main.add(setBounds(btn[2] = new JButton("저가순"), 750, 60, 50, 20));
			main.add(setBounds(btn[3] = new JButton("고가순"), 800, 60, 50, 20));

			main.add(setBounds(jp[0] = new JPanel(new FlowLayout(0, 0, 0)), 5, 60, 460, 30));
			main.add(setBounds(page = new JPanel(new FlowLayout(0)), 0, 90, 860, 10000));

			jp[0].add(lb[4] = new JLabel(rs.getString("content")));
			jp[0].add(lb[5] = new JLabel("의 검색결과  "));


			page.setName("5");

			showProductList(page, getResult("select * from post where title like '%" + rs.getString("content") + "%'"));

			setComponent(main);
			setComponent(jp[0]);

			lb[4].setForeground(new Color(0, 128, 0));

			btn[0].addActionListener(e -> {
				try {
					btn[0].setForeground(new Color(0, 128, 0));
					for(int i = 1; i <= 3; i++) btn[i].setForeground(Color.gray);
					page.removeAll();
					showProductList(page, getResult("select * from post where title like '%" + rs.getString("content") + "%' order by no desc"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});

			btn[1].addActionListener(e -> {
				try {
					btn[1].setForeground(new Color(0, 128, 0));
					for(int i = 0; i <= 3 && i != 1; i++) btn[i].setForeground(Color.gray);
					page.removeAll();
					showProductList(page, getResult("select * from post where title like '%" + rs.getString("content") + "%' order by view desc"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});

			btn[2].addActionListener(e -> {
				try {
					btn[2].setForeground(new Color(0, 128, 0));
					for(int i = 0; i <= 3 && i != 2; i++) btn[i].setForeground(Color.gray);
					page.removeAll();
					showProductList(page, getResult("select * from post where title like '%" + rs.getString("content") + "%' order by price"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});

			btn[3].addActionListener(e -> {
				try {
					btn[3].setForeground(new Color(0, 128, 0));
					for(int i = 0; i <= 2; i++) btn[i].setForeground(Color.gray);
					page.removeAll();
					showProductList(page, getResult("select * from post where title like '%" + rs.getString("content") + "%' order by price desc"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});

			rs = getResult("select count(*) as cnt from post where title like '%" + rs.getString("content") + "%'");
			rs.next();
			
			jp[0].add(lb[6] = new JLabel(rs.getInt("cnt") + "개"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchFrame(String content) {
		super("미림장터", 880, 640);

		try {

			main.add(setBounds(btn[0] = new JButton("최신순"), 650, 60, 50, 20));
			main.add(setBounds(btn[1] = new JButton("인기순"), 700, 60, 50, 20));
			main.add(setBounds(btn[2] = new JButton("저가순"), 750, 60, 50, 20));
			main.add(setBounds(btn[3] = new JButton("고가순"), 800, 60, 50, 20));

			main.add(setBounds(jp[0] = new JPanel(new FlowLayout(0, 0, 0)), 5, 60, 460, 30));
			main.add(setBounds(page = new JPanel(new FlowLayout(0)), 0, 90, 860, 10000));

			jp[0].add(lb[4] = new JLabel(content));
			jp[0].add(lb[5] = new JLabel("의 검색결과  "));

			rs = getResult("select count(*) as cnt from post where title like '%" + content + "%'");
			rs.next();

			jp[0].add(lb[6] = new JLabel(rs.getInt("cnt") + "개"));

			page.setName("5");

			showProductList(page, getResult("select * from post where title like '%" + content + "%'"));

			setComponent(main);
			setComponent(jp[0]);

			lb[4].setForeground(new Color(0, 128, 0));
			
			for(int i = 0 ; i < 4; i++) btn[i].setForeground(Color.gray); 

			btn[0].addActionListener(e -> {
				try {
					btn[0].setForeground(new Color(0, 128, 0));
					for(int i = 1; i <= 3; i++) btn[i].setForeground(Color.gray);
					page.removeAll();
					showProductList(page, getResult("select * from post where title like '%" + content + "%' order by no desc"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});

			btn[1].addActionListener(e -> {
				try {
					btn[1].setForeground(new Color(0, 128, 0));
					for(int i = 0; i <= 3 && i != 1; i++) btn[i].setForeground(Color.gray);
					page.removeAll();
					showProductList(page, getResult("select * from post where title like '%" + content + "%' order by view desc"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});

			btn[2].addActionListener(e -> {
				try {
					btn[2].setForeground(new Color(0, 128, 0));
					for(int i = 0; i <= 3 && i != 2; i++) btn[i].setForeground(Color.gray);
					page.removeAll();
					showProductList(page, getResult("select * from post where title like '%" + content + "%' order by price"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});

			btn[3].addActionListener(e -> {
				try {
					btn[3].setForeground(new Color(0, 128, 0));
					for(int i = 0; i <= 2; i++) btn[i].setForeground(Color.gray);
					page.removeAll();
					showProductList(page, getResult("select * from post where title like '%" + content + "%' order by price desc"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
