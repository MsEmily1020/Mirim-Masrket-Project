package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import base.BaseFrame;

public class StoreFrame extends BaseFrame {

	JTextArea area;
	String[] btnText = "상품,상점후기,팔로잉".split(","); 

	public StoreFrame() {
		super("미림장터", 1000, 1000);

		try {
			rs = getResult("select * from user where no = ?", s_no);
			rs.next();

			main.add(setBounds(lb[0] = new JLabel(), 30, 33, 805, 1));

			main.add(setBounds(jp[0] = new JPanel(), 30, 35, 210, 215));
			main.add(setBounds(area = new JTextArea(), 260, 50, 545, 170));
			main.add(setBounds(btn[0] = new JButton("소개글 수정"), 260, 220, 105, 30));
			
			main.add(setBounds(jp[1] = new JPanel(null), 30, 290, 805, 1000));
			jp[1].add(setBounds(jp[2] = new JPanel(new GridLayout()), 0, 0, 835, 35));
			
			// 버튼 클릭 후 panel
			jp[1].add(setBounds(jp[3] = new JPanel(null), 0, 35, 805, 1000));
			jp[1].add(setBounds(jp[4] = new JPanel(null), 0, 35, 805, 305));
			jp[1].add(setBounds(jp[5] = new JPanel(null), 0, 35, 805, 305));
			jp[1].add(setBounds(jp[6] = new JPanel(null), 0, 35, 805, 305));
			
			if(s_no == u_no) btnText = "상품,상점후기,찜,팔로잉".split(",");
			
			setComponent(main);
			
			jp[3].setBackground(Color.white);
			jp[4].setBackground(Color.white);
			jp[5].setBackground(Color.white);
			jp[6].setBackground(Color.white);
			
			for(int i = 1; i <= btnText.length; i++) {
				jp[2].add(setBounds(btn[i] = new JButton(btnText[i - 1]), (btnText.length % 2) * 60 + 200, 50));
				btn[i].setBackground(Color.white);
				btn[i].setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
			}
			
			jp[4].setVisible(false);
			jp[5].setVisible(false);
			jp[6].setVisible(false);

			if(favoritePage) {
				jp[5].setVisible(true);
				jp[3].setVisible(false);
				btn[2].setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
			}
			else btn[1].setBorder(new MatteBorder(1, 1, 0, 1, Color.black));

			area.setText(rs.getString("explain") == null ? "" : rs.getString("explain"));
			area.setEnabled(false);
			area.setLineWrap(true);
			area.setDisabledTextColor(Color.black);

			jp[0].setBackground(getBackground());
			btn[0].setBackground(new Color(0, 128, 0));
			btn[0].setForeground(Color.white);
			
			lb[0].setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

			btn[0].addActionListener(e -> {
				if(btn[0].getText().equals("소개글 수정")) {
					area.setEnabled(true);
					area.requestFocus();
					btn[0].setBackground(Color.black);
					btn[0].setText("완료");
				}
				
				else {
					if(area.getText().length() != 0) {
						update("update user set explain = ? where no = ?", area.getText(), s_no);
						showInfo("수정이 완료되었습니다.");
					} 
					area.setEnabled(false);
					btn[0].setBackground(new Color(0, 128, 0));
					btn[0].setText("소개글 수정");
				}
			});
			
			for(int i = 1; i <= btnText.length; i++) {
				btn[i].addActionListener(e -> {
					for(int j = 1; j <= btnText.length; j++) {
						if(e.getSource() == btn[j]) {
							if(btn[j].getText().equals(btnText[j - 1])) jp[j + 2].setVisible(true);
							btn[j].setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
						}
						
						else {
							jp[j + 2].setVisible(false);
							btn[j].setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
						}
					}
				});
			}
			
			rs = getResult("select count(*) from post where user = ?", s_no);
			rs.next();
			int cnt = rs.getInt("count(*)");
			jp[3].add(setBounds(new JLabel("<html>상품" + " <font color='red'>" + cnt + "</font></html>"),20, 20, 80, 20));
			jp[3].add(setBounds(new JLabel(), 20, 55, 750, 1));
			jp[3].add(setBounds(new JLabel("<html>전체" + " <font color='gray'>" + cnt + "개</font></html>"), 20, 70, 80, 20));
			jp[3].add(setBounds(page = new JPanel(new FlowLayout(0)), 0, 100, 805, ((cnt % 5 == 0) ? cnt / 5 : cnt / 5 + 1) * 200 + 60));
			page.setName("5");
			page.setBackground(Color.white);
			showProductList(page, getResult("select * from post where user = ?", s_no));

			((JLabel)jp[3].getComponent(1)).setBorder(new LineBorder(Color.lightGray, 2));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new StoreFrame().setVisible(true);
	}
}
