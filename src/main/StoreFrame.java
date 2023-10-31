package main;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
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
			
			main.add(setBounds(jp[1] = new JPanel(), 30, 290, 805, 410));
			jp[1].add(setBounds(jp[2] = new JPanel(new GridLayout()), 0, 0, 835, 35));
			
			if(s_no == u_no) btnText = "상품,상점후기,찜,팔로잉".split(",");

			setComponent(main);
			
			for(int i = 1; i <= btnText.length; i++) {
				jp[2].add(setBounds(btn[i] = new JButton(btnText[i - 1]), (btnText.length % 2) * 60 + 200, 50));
				btn[i].setBackground(Color.white);
				btn[i].setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
			}

			if(favoritePage) btn[2].setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
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
							btn[j].setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
						}
						
						else {
							btn[j].setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
						}
					}
				});
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new StoreFrame().setVisible(true);
	}
}
