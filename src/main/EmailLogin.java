package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import base.BaseFrame;

public class EmailLogin extends BaseFrame{
	public EmailLogin() {
		super("로그인", 1000, 800);
		main.setBackground(getBackground());

		main.add(setBounds(lb[0] = new JLabel(getIcon("./datafiles/image/icon/email.png", 30, 30), 0), 460, 220, 60, 30));
		main.add(setBounds(lb[1] = new JLabel("이메일로 로그인", 0), 280, 260, 425, 45));
		
		main.add(setBounds(tf[0] = new JTextField("이메일"), 300, 335, 385, 40));
		main.add(setBounds(tf[1] = new JTextField("비밀번호"), 300, 405, 385, 40));
		
		main.add(setBounds(btn[0] = new JButton("로그인"), 300, 470, 190, 35));
		main.add(setBounds(btn[1] = actbtn("닫기", e -> changeFrame(new LoginFrame())), 495, 470, 190, 35));
		
		main.add(setBounds(jp[0] = new JPanel(), 280, 215, 425, 310));
		
		setComponent(main);
		
		lb[1].setFont(new Font("맑은 고딕", 1, 18));
		tf[0].setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		tf[1].setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		btn[0].setForeground(getBackground());
		btn[1].setForeground(getBackground());
		btn[0].setBackground(new Color(0, 128, 0));
		btn[1].setBackground(Color.BLACK);
		jp[0].setBorder(new LineBorder(Color.LIGHT_GRAY));
		jp[0].setBackground(Color.WHITE);
		
		btn[0].addActionListener(e -> {
			if(tf[0].getText().equals("이메일") || tf[1].getText().equals("비밀번호")) {
				showErr("빈칸이 존재합니다.");
				return;
			}
			
			try {
				var rs = getResult("select * from user where email = ? and pw = ?", tf[0].getText(), tf[1].getText());
				
				if(rs.next()) {
					BaseFrame.u_no = rs.getInt("no");
					showInfo(rs.getString("name") + "님 환영합니다.");
					changeFrame(new MainFrame());
				}
				
				else {
					showErr("정보가 일치하는 회원이 없습니다.");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new MainFrame().setVisible(true);
			};
		});
	}
	
	public static void main(String[] args) {
		new EmailLogin().setVisible(true);
	}
}
