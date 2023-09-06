package project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * 공통된 부분을 메소드로 작업하는 클래스입니다.
 * 
 * @author MsEmily1020
 */
public class BaseFrame extends JFrame {
	static Connection con;
	static Statement stmt;
	
	static int user_no = 1;

	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/mirim-market?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	JPanel main;
	public JScrollPane jps;
	JPanel[] pn = new JPanel[30];
	JLabel[] lb = new JLabel[30];
	JButton[] btn = new JButton[30];
	JTextField[] tf = new JTextField[30];

	JComponent comp;
	int startX, startY, endX, endY, width, height;

	String[] jcompArr = ",JLabel,JButton,JTextField".split(",");
	String[] valArr = ",lb[],btn[],tf[]".split(",");

	String jcomp, val;

	public static HashMap<String, JFrame> frameList = new HashMap<>();

	public BaseFrame(String title, int w, int h) {
		setTitle(title);
		setSize(w, h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		try {
			setIconImage(ImageIO.read(new File("datafiles/logo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setCursor(new Cursor(CROSSHAIR_CURSOR));
		add(main = setBounds(new JPanel(null), 0, 0, w, h));
		
		main.setBackground(Color.white);
		
		main.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				try {
					jcomp = jcompArr[e.getKeyChar() - 48];
					val = valArr[e.getKeyChar() - 48];

					comp = (JComponent) Class.forName("javax.swing." + jcomp).newInstance();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				startX = e.getX() - (e.getX() % 5);
				startY = e.getY() - (e.getY() % 5);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				endX = e.getX() - (e.getX() % 5);
				endY = e.getY() - (e.getY() % 5);
				
				width = Math.abs(startX - endX);
				height = Math.abs(endX - endY);
				
				comp.setBounds(startX -= 5, startY -= 30, width, height);
				
				main.add(comp);
				
				if(jcomp.equals("JLabel") || jcomp.equals("JPanel"))
					comp.setBorder(new LineBorder(Color.black));
				
				main.revalidate();
				main.repaint();

				main.setFocusable(true);
				main.requestFocus();
				
				System.out.println("main.add(setBounds(" + val + " = new " + jcomp + "(), " + startX + ", " + startY + ", " + endX + ", " + endY + "));");
			}
		});
	}

	/**
	 * DB의 값을 read하는 메소드입니다.
	 * 
	 * @param String sql
	 * @param Object ... p
	 * @return ResultSet
	 * @throws Exception
	 */
	public static ResultSet getResult(String sql, Object... p) throws Exception {
		var pst = con.prepareStatement(sql);
		for (int i = 0; i < p.length; i++)
			pst.setObject(i + 1, p[i]);
		return pst.executeQuery();
	}

	/**
	 * DB의 값을 추가 또는 업데이트, 삭제하는 메소드입니다.
	 * 
	 * @param String sql
	 * @param Object ... p
	 * @throws Exception
	 */
	public static void update(String sql, Object... p) throws Exception {
		var pst = con.prepareStatement(sql);
		for (int i = 0; i < p.length; i++)
			pst.setObject(i + 1, p[i]);
		pst.executeUpdate();
	}

	/**
	 * JOptionPane.showMessage 정보 메소드입니다.
	 * @param message
	 */
	public void showInfo(String message) {
		JOptionPane.showMessageDialog(null, message, "정보", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * JOptionPane.showMessage 에러 메소드입니다.
	 * @param message
	 */
	public void showErr(String message) {
		JOptionPane.showMessageDialog(null, message, "에러", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * component의 setBounds를 시켜주는 메소드입니다.
	 * @param <T>
	 * @param comp
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return JComponent
	 */
	public <T extends JComponent> T setBounds(T comp, int x, int y, int w, int h) {
		comp.setBounds(x, y, w, h);
		return comp;
	}

	/**
	 * component의 size를 변경시켜주는 메소드입니다.
	 * @param <T>
	 * @param comp
	 * @param w
	 * @param h
	 * @return JComponent
	 */
	public <T extends JComponent> T setPreferredSize(T comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w, h));
		return comp;
	}
	
	/**
	 * 버튼에 텍스트와 리스너를 추가시켜주는 메소드입니다.
	 * @param text
	 * @param action
	 * @return JButton
	 */
	public JButton actionButton(String text, ActionListener action) {
		JButton btn = new JButton(text);
		btn.addActionListener(action);
		return btn;
	}
	
	/**
	 * 해당 jpanel component의 공통 부분 메소드입니다.
	 * @param jp
	 */
	public void setComponent(JPanel jp) {
		for (Component comp : jp.getComponents()) {
			if (comp instanceof JButton) {
				comp.setCursor(new Cursor(HAND_CURSOR));
			}
			
			if(comp instanceof JTextField) {
				JTextField tf = (JTextField) comp;
				tf.setName(tf.getText());
				tf.setForeground(Color.gray);
				tf.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
				tf.addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent e) {
						tf.setForeground(tf.getText().isEmpty() ? Color.GRAY : Color.BLACK);
						tf.setText(tf.getText().isEmpty() ? tf.getName() : tf.getText());
					}
					@Override
					public void focusGained(FocusEvent e) {
						tf.setText("");
						tf.setForeground(Color.BLACK);
					}
				});
			}
			
			comp.setBounds(null);
			comp.setBackground(Color.white);
			comp.setFont(new Font("맑은 고딕", 1, 12));
		}
	}
	
}
