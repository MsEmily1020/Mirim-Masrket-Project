package project;

public class MainFrame extends BaseFrame {
	public MainFrame() {
		super("메인", 1200, 800);
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
