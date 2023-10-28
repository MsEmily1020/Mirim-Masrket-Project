package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import base.BaseFrame;

public class ReviewFrame extends BaseFrame {
	public ReviewFrame() {
		super("리뷰등록", 300, 230);
		try {
			rs = getResult("select * from review where user = ? and post = ?", u_no, p_no);

			main.add(setBounds(jp[0] = new JPanel(), 0, 10, 285, 55));
			main.add(setBounds(jta = new JTextArea(rs.next() ? rs.getString("content") : ""), 5, 70, 275, 70));
			if(rs.isFirst()) this.setTitle("리뷰수정");  
			main.add(setBounds(btn[0] = actbtn("취소", e -> dispose()), 5, 145, 140, 40));
			main.add(setBounds(btn[1] = actbtn(rs.getRow() == 0 ? "등록" : "수정", e -> {
				try {
					if(!jp[0].getComponent(0).isEnabled()) { showErr("점수는 최소 1점 이상 입력해주세요."); return; }
					if(jta.getText().length() == 0) { showErr("내용을 입력해주세요."); return; }
					
					int cnt = 0;
					for (Component comp : jp[0].getComponents()) if(comp.isEnabled()) ++cnt;

					if (e.getActionCommand().equals("등록")) {
						update("insert into review(user, post, content, score) values(?,?,?,?)", u_no, p_no, jta.getText(), cnt);
						showInfo("등록이 완료되었습니다.");
					}
					
					else if (e.getActionCommand().equals("수정")) {
						update("update review set content = ?, score = ? where user = ? and post = ?", jta.getText(), cnt, u_no, p_no);
						showInfo("수정이 완료되었습니다.");
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}), 150, 145, 130, 40));


			for (int i = 0; i < 5; i++) {
				final int a = i;
				jp[0].add(lb[0] = new JLabel("★"));
				lb[0].setFont(new Font("맑은 고딕", 1, 32));
				lb[0].setForeground(Color.orange);
				lb[0].setEnabled(false);
				lb[0].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Arrays.stream(jp[0].getComponents()).forEach(comp -> comp.setEnabled(false));
						for (int i = 0; i <= a; i++) {
							jp[0].getComponent(i).setEnabled(true);
						}
					};
				});
			}

			setComponent(main);

			for (int i = 0; i < rs.getInt("score"); i++) {
				jp[0].getComponent(i).setEnabled(true);
			}
			
			jta.setBorder(new LineBorder(Color.BLACK));
			jta.setLineWrap(true);

			btn[0].setBackground(Color.BLACK);
			btn[0].setForeground(Color.WHITE);
			btn[1].setBackground(new Color(0, 128, 0));
			btn[1].setForeground(Color.WHITE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ReviewFrame().setVisible(true);
	}
}
