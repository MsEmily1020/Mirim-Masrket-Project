package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import base.BaseFrame;

public class SearchFrame extends BaseFrame {
	
	public SearchFrame() {
		super("미림장터", 880, 640);
		try {
			rs = getResult("select * from history where user_no = ? order by no desc", u_no);
			rs.next();
			main.add(setBounds(lb[0] = new JLabel("홈"), 10, 5, 20, 25));
			main.add(setBounds(lb[1] = new JLabel(" > ", 0), 20, 5, 20, 25));
			main.add(setBounds(lb[2] = new JLabel(" > ", 0), 170, 5, 40, 25));
			main.add(setBounds(lb[3] = new JLabel(" > ", 0), 310, 5, 40, 25));
			main.add(setBounds(cbx[0] = new JComboBox(), 45, 5, 130, 25));
			main.add(setBounds(cbx[1] = new JComboBox(), 200, 5, 120, 25));
			main.add(setBounds(cbx[2] = new JComboBox(), 345, 5, 120, 25));

			main.add(setBounds(jp[0] = new JPanel(new FlowLayout(0, 0, 0)), 5, 60, 460, 30));
			main.add(setBounds(page = new JPanel(new FlowLayout(0)), 0, 90, 860, 10000));

			jp[0].add(lb[4] = new JLabel(rs.getString("content")));
			jp[0].add(lb[5] = new JLabel("의 검색결과  "));

			rs = getResult("select count(*) from history where user_no = ?", u_no);
			rs.next();

			jp[0].add(lb[6] = new JLabel(rs.getInt("count(*)") + ""));

			page.setName("5");

			rs = getResult("select * from history where user_no = ? order by no desc", u_no);
			rs.next();

			showProductList(page, getResult("select * from post where title like '%" + rs.getString("content") + "%'"));

			setComponent(main);
			setComponent(jp[0]);

			lb[1].setFont(new Font("HY견고딕", 1, 12));
			lb[2].setFont(new Font("HY견고딕", 1, 12));
			lb[3].setFont(new Font("HY견고딕", 1, 12));
			lb[4].setForeground(new Color(0, 128, 0));
			lb[6].setForeground(Color.GRAY);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
