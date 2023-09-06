package project;

public class LoginFrame extends BaseFrame {
	public LoginFrame() {
		super("로그인", 500, 600);
	}
	
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}
}
