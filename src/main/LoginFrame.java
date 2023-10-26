package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import base.BaseFrame;

public class LoginFrame extends BaseFrame {
	public LoginFrame() {
		super("로그인", 500, 600);
		setUndecorated(true);
		main.setBackground(getBackground());

		main.add(setBounds(lb[0] = new JLabel(getIcon("./datafiles/logo.png", 40, 40), 0), 220, 80, 50, 50));
		main.add(setBounds(lb[1] = new JLabel("미림장터로 중고거래 시작하기", 0), 0, 150, getWidth(), 30));
		main.add(setBounds(lb[2] = new JLabel("간편하게 가입하고 상품을 확인하세요", 0), 0, 200, getWidth(), 30));
		main.add(setBounds(jp[0] = new JPanel(new GridLayout(4, 1, 10, 20)), 50, 280, getWidth() - 100, 250));
		main.add(setBounds(btn[0] = actbtn("", e -> dispose()), 450, 15, 30, 30));
		
		String[] str = "아이디로 이용하기,전화번호로 이용하기,이메일로 이용하기,회원가입".split(",");
		String[] str1 = "id,phone,email,join".split(",");
		for (int i = 0; i < str.length; i++) {
			jp[0].add(btn[i + 1] = new JButton(str[i], getIcon("datafiles/image/icon/" + str1[i] + ".png", 30, 30)));
		}
		
		setComponent(main);
		setComponent(jp[0]);
		
		jp[0].setBackground(getBackground());
		lb[1].setFont(new Font("나눔고딕 Light", 1, 24));
		btn[0].setIcon(getIcon("./datafiles/image/icon/cancel.png", 30, 30));
		btn[0].setBackground(new Color(238, 238, 238));
	
		btn[1].addActionListener(e -> { dispose(); new IdLogin().setVisible(true); });
		btn[2].addActionListener(e -> { dispose(); new PhoneLogin().setVisible(true); });
		btn[3].addActionListener(e -> { dispose(); new EmailLogin().setVisible(true); });
		btn[4].addActionListener(e -> { dispose(); new SignUpFrame().setVisible(true); });
	}
	
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
}