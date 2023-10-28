package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import base.BaseFrame;

public class SignUpFrame extends BaseFrame {
	
	String[] str;
	
	public SignUpFrame() {
		super("회원가입", 1000, 800);
		main.setBackground(getBackground());
		
		main.add(setBounds(lb[0] = new JLabel("본인 정보를 입력해주세요", 0), 260, 20, 495, 45));
		
		str = "이름,아이디,비밀번호,휴대폰번호,이메일,주소".split(","); 
		
		for (int i = 0; i < 6; i++) {
			main.add(setBounds(tf[i] = new JTextField(str[i]), 290, 110 + 50 * i, 435, 40));
		}
		
		str = ("전체동의,기능장터 이용약관 (필수),개인정보 수집 이용 동의 (필수),"
				+ "휴면 개인정보 분리보관 동의 (필수),위치정보 이용약관 동의 (필수),"
				+ "마케팅 수신 동의,개인정보 광고활용 동의").split(",");
		
		for (int i = 0; i < 7; i++) {
			main.add(setBounds(ch[i] = new JCheckBox(str[i]), 300, 420 + 40 * i, 400, 30));
		}
		main.add(setBounds(jp[0] = new JPanel(), 290, 415, 435, 40));
		main.add(setBounds(btn[0] = new JButton("회원가입"), 295, 700, 205, 40));
		main.add(setBounds(btn[1] = actbtn("닫기", e -> changeFrame(new LoginFrame())), 505, 700, 225, 40));
		
		main.add(setBounds(page = new JPanel(), 260, 10, 495, 745));
		
		setComponent(main);
		
		lb[0].setFont(new Font("맑은 고딕", 1, 24));
		btn[0].setForeground(getBackground());
		btn[1].setForeground(getBackground());
		btn[0].setBackground(new Color(0, 128, 0));
		btn[1].setBackground(Color.BLACK);
		page.setBackground(Color.WHITE);
		jp[0].setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		for (int i = 0; i < 6; i++) {
			tf[i].setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		}
		
		ch[0].addActionListener(e -> {
			if(ch[0].isSelected()) for(int i = 1; i < 7; i++) ch[i].setSelected(true);
			else for(int i = 1; i < 7; i++) ch[i].setSelected(false);
		});

		for (int i = 1; i < 7; i++) {
			ch[i].addActionListener(e -> {
				ch[0].setSelected(false);
			});
		}
		
		btn[0].addActionListener(e -> {
			for(int i = 0; i < 6; i++) if(tf[i].getText().equals(str[i])) { showErr("빈칸이 존재합니다."); return; }
			if(!tf[2].getText().matches(".*[A-Za-z0-9!@#$") || tf[2].getText().length() < 4) { showErr("비밀번호 형식이 일치하지 않습니다."); return; }
			if(!tf[3].getText().matches("^\\010-\\d{4}-\\d{4}$")) { showErr("전화번호 형식이 일치하지 않습니다."); return; }
			if(!tf[4].getText().matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")) { showErr("이메일 형식이 일치하지 않습니다."); return; }
			
			for(int i = 1; i < 7; i++) if(!ch[i].isSelected()) { showErr("필수 항목은 모두 동의해주세요."); return; }
			
			showInfo("회원가입이 완료되었습니다.");
			
			update("insert into user (name, id, pw, phone, email) values (?, ?, ?, ?, ?)", tf[0].getText(), tf[1].getText(), tf[2].getText(), tf[3].getText(), tf[4].getText());
			
			changeFrame(new MainFrame());
		});
	}

	public static void main(String[] args) {
		new SignUpFrame().setVisible(true);
	}
}
