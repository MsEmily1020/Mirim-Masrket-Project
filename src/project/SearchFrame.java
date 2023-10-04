package project;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class SearchFrame extends BaseFrame {
	public SearchFrame() {

		super("메인", 880, 640);
		try {
			main.add(setBounds(lb[0] = new JLabel("홈"), 10, 5, 20, 25));
			main.add(setBounds(lb[1] = new JLabel(" > ", 0), 20, 5, 20, 25));
			main.add(setBounds(lb[2] = new JLabel(" > ", 0), 170, 5, 40, 25));
			main.add(setBounds(lb[3] = new JLabel(" > ", 0), 310, 5, 40, 25));
			main.add(setBounds(cbx[0] = new JComboBox(), 45, 5, 130, 25));
			main.add(setBounds(cbx[1] = new JComboBox(), 200, 5, 120, 25));
			main.add(setBounds(cbx[2] = new JComboBox(), 345, 5, 120, 25));
			
			main.add(setBounds(btn[0] = new JButton("최신순"), 650, 60, 50, 20));
			main.add(setBounds(btn[1] = new JButton("인기순"), 700, 60, 50, 20));
			main.add(setBounds(btn[2] = new JButton("저가순"), 750, 60, 50, 20));
			main.add(setBounds(btn[3] = new JButton("고가순"), 800, 60, 50, 20));
			
			main.add(setBounds(jp[0] = new JPanel(new FlowLayout(0, 0, 0)), 5, 60, 460, 30));
			main.add(setBounds(page = new JPanel(new FlowLayout(0)), 0, 90, 860, 10000));
			
			jp[0].add(lb[4] = new JLabel("샤넬"));
			jp[0].add(lb[5] = new JLabel("의 검색결과  "));
			jp[0].add(lb[6] = new JLabel("10개"));
			
			page.setName("5");
			
			상품리스트(page, getResult("select * from post where title like '%샤넬%'"));
			
			setComponent(main);
			setComponent(jp[0]);
			
			lb[1].setFont(new Font("HY견고딕", 1, 12));
			lb[2].setFont(new Font("HY견고딕", 1, 12));
			lb[3].setFont(new Font("HY견고딕", 1, 12));
			lb[4].setForeground(new Color(0, 128, 0));
			lb[6].setForeground(Color.GRAY);
			
			btn[0].setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
			btn[1].setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
			btn[2].setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
			btn[0].setForeground(new Color(0, 128, 0));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SearchFrame().setVisible(true);
	}
}
