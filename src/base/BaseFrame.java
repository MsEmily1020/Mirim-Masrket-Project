package base;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import main.MainFrame;
import main.ProductFrame;
import main.SaleFrame;

/**
 * 공통된 부분을 메소드로 작업하는 클래스입니다.
 * 
 * @author MsEmily1020
 */
public class BaseFrame extends JFrame {
	static Connection con;
	static Statement stmt;
	public static ResultSet rs;

	public static MainFrame mainCls;

	public static int u_no = 1;
	public static int p_no = 1;
	public static int s_no = 1;
	public static boolean favoritePage = false;

	public JTextArea jta;
	public JScrollPane jsp;
	public JPanel p, page, main;
	public JPanel[] jp = new JPanel[1000];
	public JLabel[] lb = new JLabel[1000];
	public JButton[] btn = new JButton[1000];
	public JTextField[] tf = new JTextField[1000];
	public JComboBox[] cbx = new JComboBox[1000];
	public static JCheckBox[] ch = new JCheckBox[1000];
	public JRadioButton[] rb =new JRadioButton[1000];

	public static String aaa;
	public static String jop;
	public static JComponent comp;

	public static HashMap<Integer, String> category = new HashMap();
	public static HashMap<Integer, ArrayList> filter = new HashMap();
	public static HashMap<Component, Component> list = new HashMap();

	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/mirim-market?serverTimezone=UTC", "root", "1234");
			stmt = con.createStatement();

			rs = getResult("select * from post");
			for (int i = 1; rs.next(); i++) {
				filter.put(i, new ArrayList(Arrays.asList(rs.getInt("category"), rs.getInt("category_sub"), rs.getInt("category_datail"))));
			}

			rs = getResult("select * from category");
			for (int i = 1; rs.next(); i++) {
				category.put(i, rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BaseFrame(String title, int w, int h) {
		setTitle(title);
		setSize(w, h);
		setLayout(null);
		setDefaultCloseOperation(2);
		setLocationRelativeTo(null);

		try {
			setIconImage(ImageIO.read(new File("datafiles/logo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		add(main = setBounds(new JPanel(null), 0, 0, w, h));

		main.setBackground(Color.WHITE);

		main.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				try {
					String[] asdf = ",JLabel,JButton,JTextField,JComboBox,JPanel".split(",");
					String[] as = ",lb[],btn[],tf[],cbx[],jp[]".split(",");

					jop = asdf[Integer.parseInt(e.getKeyChar()+"")];
					aaa = as[Integer.parseInt(e.getKeyChar()+"")];

					Class c = Class.forName("javax.swing." + jop);
					comp = (JComponent) c.newInstance();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			int startX, endX;
			int startY, endY;


			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);

				startX = e.getX() - (e.getX() % 5);
				startY = e.getY() - (e.getY() % 5);
			}

			@Override
			public void mouseReleased(MouseEvent e){
				super.mouseReleased(e);

				endX = e.getX() - (e.getX() % 5);
				endY = e.getY() - (e.getY() %  5);

				int width = Math.abs(startX - endX);
				int height = Math.abs(startY - endY);

				comp.setBounds(startX - 5, startY - 30, width, height);

				main.add(comp);

				if (jop.equals("JLabel") || jop.equals("JPanel")) {
					comp.setBorder(new LineBorder(Color.BLACK));
				}

				main.revalidate();
				main.repaint();

				main.setFocusable(true);
				main.requestFocus();

				System.out.println("main.add(setBounds(" + aaa + " = new " + jop + "(), " + (startX - 5) + ", " + (startY - 30) + ", " + width + ", " + height + "));");
			}

		});
		main.setFocusable(true);
		main.requestFocus();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!SaleFrame.isRegistration) {
					try {
						rs = getResult("select * from post order by no desc");
						rs.next();
						int deleteFolderNo = Integer.parseInt(rs.getString("no")) + 1;
						File deleteFolder = new File("datafiles/image/post/" + deleteFolderNo + "/" );
						File[] files = deleteFolder.listFiles();
						for(File f: files) f.delete();
						deleteFolder.delete();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	public <T extends JComponent> T setBounds(T comp, int x, int y, int w, int h) {
		comp.setBounds(x, y, w, h); 
		return comp;
	}
	public <T extends JComponent> T setBounds(T comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w, h)); 
		return comp;
	}

	public Component createLB(Component comp, Font font) {
		comp.setFont(font);
		return comp;
	}

	public static void showInfo(String m) {
		JOptionPane.showMessageDialog(null, m, "정보", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showErr(String m) {
		JOptionPane.showMessageDialog(null, m, "에러", JOptionPane.ERROR_MESSAGE);
	}

	public JButton actbtn(String text, ActionListener action) {
		JButton jb = new JButton(text);
		jb.addActionListener(action);
		return jb;
	}

	public static ResultSet getResult(String sql, Object...p) throws Exception {
		var pst = con.prepareStatement(sql);
		for (int i = 0; i < p.length; i++) 
			pst.setObject(i + 1, p[i]);

		return pst.executeQuery();
	}

	public static void update(String sql, Object...p) {
		try {
			var pst = con.prepareStatement(sql);
			for (int i = 0; i < p.length; i++)
				pst.setObject(i + 1, p[i]);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ImageIcon getIcon(String path, int w, int h) {
		return new ImageIcon(Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	public void changePage(Component page) {
		list.put(page, mainCls.jsp.getViewport().getView());
		mainCls.jsp.getViewport().setView(page);

	}

	public void setComponent(JPanel jp) {
		for (Component comp : jp.getComponents()) {
			if (comp instanceof JButton) {
				((JButton) comp).setMargin(new Insets(0, 0, 0, 0));
				((JButton) comp).setCursor(new Cursor(HAND_CURSOR));

			}
			if (comp instanceof JTextField) {
				JTextField tf = (JTextField) comp;
				tf.setName(tf.getText());
				tf.setForeground(Color.GRAY);
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
			((JComponent) comp).setBorder(null);
			comp.setBackground(Color.WHITE);
			comp.setFont(new Font("맑은 고딕", 1, 12));
		}
	}

	public void changeFrame(JFrame frame) {
		dispose();
		frame.setVisible(true);
		main.requestFocus();
	}

	public void showProductList(JPanel page, ResultSet rs) {
		int w = (int) (page.getWidth() * (page.getName().equals("5") ? 0.2 : page.getName().equals("6") ? 0.17 : 1)) - 7;
		try {
			for (int i = 1; rs.next(); i++) {
				page.add(p = setBounds(new JPanel(new FlowLayout(0, 0, 0)), w, w + (page.getName().equals("5") ? 40 : page.getName().equals("6") ? 150 : 0)));
				p.add(new JLabel(getIcon("./datafiles/image/post/" + rs.getInt("no") + "/1.jpg", w - 2, w - 2)));
				p.add(new JLabel(rs.getString("title") + "                                     "));
				p.add(new JLabel(String.format("%,d원", rs.getInt("price"))));
				p.getComponent(1).setFont(new Font("맑은 고딕", 0, 12));
				p.getComponent(2).setFont(new Font("맑은 고딕", 1, 12));
				p.setBackground(Color.white);
				p.setBorder(new LineBorder(Color.BLACK));
				p.setName(rs.getString("no"));
				p.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						try {
							p_no = Integer.parseInt(((JPanel) e.getSource()).getName());
							var rs1 = getResult("select * from post where no = ?", p_no);
							rs1.next();
							if(rs1.getInt("state") == 2) { showErr("해당 상품은 예약상품입니다."); return; }
							if(rs1.getInt("state") == 3) { showErr("해당 상품은 이미 판매완료되었습니다."); return; }
							
							var rs = getResult("select * from post where no = ?", p_no);
							rs.next();
							update("update post set view = view + 1 where no = ?", p_no);
							update("insert into recent values(null,?,?,?,?)",u_no, rs.getString("title"), rs.getInt("price"), p_no);
							mainCls.recentProduct();
							changePage(new ProductFrame().main);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					};
				});
				mainCls.main.repaint();
				page.repaint();
				page.getParent().repaint();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}