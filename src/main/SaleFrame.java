package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import base.BaseFrame;

public class SaleFrame extends BaseFrame {

	static int cnt = 1;
	static int productCnt = 1;
	static int productSubCnt = 1;
	public static boolean isRegistration = false;

	public static int categoryIndex;
	public static int categorySubIndex;
	public static int categoryDetailIndex;

	public SaleFrame() {
		super("미림장터", 1000, 1600);

		main.add(setBounds(lb[0] = new JLabel("기본 정보"), 20, 20, 180, 35));
		main.add(setBounds(lb[1] = new JLabel(), 20, 65, 950, 2));
		main.add(setBounds(lb[2] = new JLabel("*필수항목"), 100, 20, 65, 35));
		main.add(setBounds(lb[3] = new JLabel("<html>상품이미지<font color='red'>*</font> <font color='gray'>(0/7)</font></html>"), 20, 95, 140, 25));
		main.add(setBounds(jp[0] = new JPanel(null), 140, 105, 765, 350));
		jp[0].add(setBounds(btn[0] = new JButton("이미지 등록", getIcon("datafiles/image/icon/region.png", 50, 50)), 10, 0, 170, 170));
		main.add(setBounds(lb[4] = new JLabel("<html><font color='#87cefac'>상품 이미지는 640x640dp 최적화 되어 있습니다. <br>-이미지는 상품 등록 시 정사각형으로 잘려서 등록됩니다.<br>-큰 이미지일 경우 이미지가 깨지는 경우가 발생할 수 있습니다.<br>최대 지원 사이즈인 640x640으로 리사이즈 해서 올려주세요.(개당 이미지 최대 10M)</font></html>"), 155, 455, 500, 85));
		main.add(setBounds(lb[5] = new JLabel(), 20, 560, 950, 2));

		main.add(setBounds(lb[6] = new JLabel("<html>제목<font color='red'>*</font></html>"), 30, 585, 30, 30));
		main.add(setBounds(tf[0] = new JTextField("상품 제목을 입력해주세요."), 135, 585, 670, 35));
		main.add(setBounds(lb[7] = new JLabel(), 20, 640, 950, 2));

		main.add(setBounds(lb[8] = new JLabel("<html>카테고리<font color='red'>*</font></html>"), 25, 675, 70, 20));
		main.add(setBounds(jp[1] = new JPanel(new FlowLayout(0, 0, 0)), 130, 670, 680, 241));

		JScrollPane pane1 = new JScrollPane(jp[2] = new JPanel(new GridLayout(0, 1)));
		pane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jp[1].add(setBounds(pane1, 225, 239));

		JScrollPane pane2 = new JScrollPane(jp[3] = new JPanel(new GridLayout(0, 1)));
		pane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jp[1].add(setBounds(pane2, 225, 239));

		JScrollPane pane3 = new JScrollPane(jp[4] = new JPanel(new GridLayout(0, 1)));
		pane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jp[1].add(setBounds(pane3, 228, 239));

		main.add(setBounds(lb[9] = new JLabel("선택한 카테고리 : "), 130, 935, 145, 25));

		main.add(setBounds(lb[10] = new JLabel(), 20, 990, 950, 2));

		main.add(setBounds(lb[11] = new JLabel("<html>가격<font color='red'>*</font></html>"), 20, 1025, 55, 30));
		main.add(setBounds(tf[1] = new JTextField("숫자만 입력해주세요."), 130, 1025, 230, 35));
		main.add(setBounds(lb[12] = new JLabel("원"), 375, 1025, 40, 30));
		main.add(setBounds(rb[0] = new JRadioButton("배송비 포함"), 130, 1070, 125, 20));

		main.add(setBounds(lb[13] = new JLabel(), 20, 1125, 950, 2));

		main.add(setBounds(lb[14] = new JLabel("<html>설명<font color='red'>*</font></html>"), 20, 1165, 55, 20));
		main.add(setBounds(tf[2] = new JTextField("여러 장의 상품 사진과 구입 연도, 브랜드, 사용감, 하자 유무 등 구매자에게 필요한 정보를 꼭 포함해 주세요."), 120, 1160, 690, 150));
		main.add(setBounds(btn[1] = new JButton("등록하기"), 680, 1330, 130, 40));

		setComponent(main);
		setComponent(jp[0]);
		setComponent(jp[1]);

		jp[3].setBackground(Color.white);
		jp[4].setBackground(Color.white);

		btn[0].setBackground(new Color(230, 230, 230));
		btn[0].setVerticalTextPosition(JButton.BOTTOM);
		btn[0].setHorizontalTextPosition(JButton.CENTER);
		btn[0].setBorder(new LineBorder(Color.gray));

		lb[0].setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lb[1].setBorder(new LineBorder(Color.lightGray, 2));
		lb[2].setForeground(Color.red);
		lb[5].setBorder(new LineBorder(Color.lightGray, 2));

		tf[0].setBorder(new LineBorder(Color.gray));
		lb[7].setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

		jp[1].setBorder(new LineBorder(Color.gray));

		lb[9].setForeground(Color.red);
		lb[10].setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

		tf[1].setBorder(new LineBorder(Color.gray));

		lb[13].setBorder(new LineBorder(Color.lightGray, 2));

		tf[2].setBorder(new LineBorder(Color.gray));

		btn[1].setBackground(Color.red);
		btn[1].setForeground(Color.white);

		btn[0].addActionListener(e -> {
			if(cnt == 8) { showErr("등록할 수 있는 이미지 수를 초과 하였습니다."); return; }

			var fopen = new FileDialog(this, "이미지 열기", FileDialog.LOAD);
			fopen.setVisible(true);
			fopen.setLocationRelativeTo(null);
			fopen.setSize(400, 300);
			String path = fopen.getDirectory();
			String name = fopen.getFile();
			if (path == null || name == null) return;

			try {
				rs = getResult("select * from post order by no desc");
				rs.next();

				int newFolderNo = Integer.parseInt(rs.getString("no")) + 1;

				File newFolder = new File("datafiles/image/post/" + newFolderNo + "/");
				if(!newFolder.isDirectory()) newFolder.mkdirs();

				ImageIO.write(ImageIO.read(new File(path + name)), "jpg", new File(newFolder, cnt + ".jpg"));

				jp[0].add(setBounds(new JLabel(getIcon("datafiles/image/post/" + newFolderNo + "/" + cnt + ".jpg", 170, 170)), 2, 2, 2, 2));

				lb[3].setText("<html>상품이미지<font color='red'>*</font> <font color='gray'>(" + cnt + "/7)</font></html>");

				jp[0].add(setBounds(btn[cnt + 1] = actbtn("X", e2 -> {
					jp[0].remove(((JButton) e2.getSource()));
					jp[0].remove(jp[0].getComponentAt(((JButton)e2.getSource()).getX(), ((JButton) e2.getSource()).getY()));
					jp[0].revalidate();
					jp[0].repaint();
					System.out.println(((JButton)e2.getSource()).getName());
					new File("datafiles/image/post/" + newFolderNo + "/" + ((JButton)e2.getSource()).getName() + ".jpg").delete();
					File[] fileList = new File("datafiles/image/post/" + newFolderNo + "/").listFiles();
					for (int i = 0; i < fileList.length; i++)
						fileList[i].renameTo(new File("datafiles/image/post/" + newFolderNo + "/" + (i + 1) + ".jpg"));
					cnt = fileList.length + 1;
					changeProductImage(fileList.length);
				}), (cnt % 4) * 180 + 135, (cnt <= 3 ? 0 : 180), 45, 45));
				btn[cnt + 1].requestFocus();
				btn[cnt + 1].setFont(new Font("맑은 고딕", 1, 15));
				btn[cnt + 1].setOpaque(false);
				btn[cnt + 1].setContentAreaFilled(false);
				btn[cnt + 1].setBorderPainted(false);
				btn[cnt + 1].setName(cnt + "");

				jp[0].add(setBounds(new JLabel(getIcon("datafiles/image/post/" + newFolderNo + "/" + cnt + ".jpg", 170, 170)), (cnt % 4) * 180 + 10, (cnt <= 3 ? 0 : 180), 170, 170));
				++cnt;

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		try {
			rs = getResult("select * from category");
			while(rs.next()) {
				if(rs.getString("parent") == null) {
					jp[2].add(setBounds(btn[rs.getRow() + 39] = new JButton(rs.getString("name")), 0, 50));
					btn[rs.getRow() + 39].setBackground(Color.white);
					btn[rs.getRow() + 39].setBorder(null);
					btn[rs.getRow() + 39].setName(rs.getString("name"));
				}
			}

			for(int i = 40; i < 59; i++) {
				btn[i].addActionListener(e -> {
					for(int j = 40; j < 59; j++) {
						if(e.getSource() == btn[j]) {
							btn[j].setBackground(new Color(0, 128, 0));
							btn[j].setForeground(Color.white);
							try {
								rs = getResult("select * from category where name = ?", btn[j].getName());
								rs.next();
								categoryIndex = rs.getInt("no");
							} catch (Exception e1) {
								e1.printStackTrace();
							}

							changeCategorySub(j - 39);
							jp[3].revalidate();
							jp[3].repaint();
						}

						else {
							btn[j].setBackground(Color.white);
							btn[j].setForeground(Color.black);
						}
					}
				});
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}


		btn[1].addActionListener(e -> {
			System.out.println(categoryIndex + " " + categorySubIndex + " " + categoryDetailIndex);
			if(tf[0].getText().length() == 0 || tf[0].getText().equals("상품 제목을 입력해주세요.")) { showErr("빈칸이 존재합니다."); return; }
			if(tf[1].getText().length() == 0 || tf[1].getText().equals("숫자만 입력해주세요.")) { showErr("빈칸이 존재합니다."); return; }
			if(tf[2].getText().length() == 0 || tf[2].getText().equals("여러 장의 상품 사진과 구입 연도, 브랜드, 사용감, 하자 유무 등 구매자에게 필요한 정보를 꼭 포함해 주세요.")) { showErr("빈칸이 존재합니다."); return; }

			if(jp[0].getComponent(1) == null) { showErr("등록이미지는 최소 한 개의 이미지가 등록되어야 합니다."); return; }

			if(!tf[1].getText().matches(".*[0-9].*")) { showErr("가격은 숫자로만 입력해주세요."); return; }

			isRegistration = true;

			showInfo("등록이 완료되었습니다.");

			update("insert into post values(null, ?, ?, ?, 0, ?, ?, ?, ?, 1, ?)", tf[0].getText(), tf[2].getText(), tf[1].getText(), rb[0].isSelected() ? 1 : 0, categoryIndex, categorySubIndex, categoryDetailIndex, u_no);
		});
	}

	public void changeCategorySub(int category) {
		jp[3].removeAll();
		try {
			rs = getResult("select *,(select count(*) from category where parent like '%," + category + ",%') as cnt from category where parent like '%," + category + ",%'");

			while(rs.next()) {
				productCnt = rs.getInt("cnt");
				jp[3].add(setBounds(btn[rs.getRow() + 58] = new JButton(rs.getString("name")), 0, 50));
				btn[rs.getRow() + 58].setBackground(Color.white);
				btn[rs.getRow() + 58].setBorder(null);
				btn[rs.getRow() + 58].setName(rs.getString("name"));
			}

			for(int i = 1; i <= productCnt; i++) {
				btn[i + 58].addActionListener(e -> {
					for(int j = 1; j <= productCnt; j++) {
						if(e.getSource() == btn[j + 58]) {
							btn[j + 58].setBackground(new Color(0, 128, 0));
							btn[j + 58].setForeground(Color.white);
							try {
								rs = getResult("select * from category where name = ?", btn[j + 58].getName());
								if(rs.next()) {
									categorySubIndex = rs.getInt("no");
									changeCategoryDetail(rs.getInt("no"));
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}

							jp[3].revalidate();
							jp[3].repaint();
						}

						else {
							btn[j + 58].setBackground(Color.white);
							btn[j + 58].setForeground(Color.black);
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeCategoryDetail(int categorySub) {
		jp[4].removeAll();
		try {
			rs = getResult("select *, (select count(*) from category where parent like '%," + categorySub + ",%') as cnt from category where parent like '%," + categorySub + ",%'");

			while(rs.next()) {
				productSubCnt = rs.getInt("cnt");
				jp[4].add(setBounds(btn[rs.getRow() + 185] = new JButton(rs.getString("name")), 0, 50));
				btn[rs.getRow() + 185].setBackground(Color.white);
				btn[rs.getRow() + 185].setBorder(null);
				btn[rs.getRow() + 185].setName(rs.getString("name"));
			}

			if(rs.isLast()) {
				for(int i = 1; i <= productSubCnt; i++) {
					btn[i + 185].addActionListener(e -> {
						for(int j = 1; j <= productSubCnt; j++) {
							if(e.getSource() == btn[j + 185]) {
								btn[j + 185].setBackground(new Color(0, 128, 0));
								btn[j + 185].setForeground(Color.white);
								try {
									rs = getResult("select * from category where name = ?", btn[j + 185].getName());
									rs.next();
									categoryDetailIndex = rs.getInt("no");
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}

							else {
								btn[j + 185].setBackground(Color.white);
								btn[j + 185].setForeground(Color.black);
							}
						}

						jp[4].revalidate();
						jp[4].repaint();
					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		jp[4].revalidate();
		jp[4].repaint();
	}

	public void changeProductImage(int fileListSize) {

		for(Component comp : jp[0].getComponents()) {
			if(comp != btn[0]) jp[0].remove(comp);
		}

		try {
			rs = getResult("select * from post order by no desc");
			rs.next();
			int newFolderNo = Integer.parseInt(rs.getString("no")) + 1;

			for(int i = 1; i <= fileListSize; i++) {
				jp[0].add(setBounds(btn[i + 1] = actbtn("X", e2 -> {
					jp[0].remove(((JButton) e2.getSource()));
					jp[0].remove(jp[0].getComponentAt(((JButton)e2.getSource()).getX(), ((JButton) e2.getSource()).getY()));
					jp[0].revalidate();
					jp[0].repaint();
					new File("datafiles/image/post/" + newFolderNo + "/" + ((JButton)e2.getSource()).getName() + ".jpg").delete();
					File[] fileList = new File("datafiles/image/post/" + newFolderNo + "/").listFiles();
					for (int j = 0; j < fileList.length; j++)
						fileList[j].renameTo(new File("datafiles/image/post/" + newFolderNo + "/" + (j + 1) + ".jpg"));
					cnt = fileList.length + 1;
					changeProductImage(fileList.length);
				}), (i % 4) * 180 + 135, (i <= 3 ? 0 : 180), 45, 45));
				btn[i + 1].requestFocus();
				btn[i + 1].setFont(new Font("맑은 고딕", 1, 15));
				btn[i + 1].setOpaque(false);
				btn[i + 1].setContentAreaFilled(false);
				btn[i + 1].setBorderPainted(false);
				btn[i + 1].setName(i + "");

				jp[0].add(setBounds(new JLabel(getIcon("datafiles/image/post/" + newFolderNo + "/" + i + ".jpg", 170, 170)), (i % 4) * 180 + 10, (i <= 3 ? 0 : 180), 170, 170));
			}

			jp[0].revalidate();
			jp[0].repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SaleFrame().setVisible(true);
	}
}
