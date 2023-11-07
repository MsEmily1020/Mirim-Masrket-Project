package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import base.BaseFrame;

public class StoreFrame extends BaseFrame {

	JTextArea area, area1;
	String[] btnText = "상품,상점후기,팔로잉".split(","); 
	int cnt;

	public StoreFrame() {
		super("미림장터", 1000, 1000);

		u_no = 3;
		s_no = 3;

		try {
			rs = getResult("select * from user where no = ?", s_no);
			rs.next();

			main.add(setBounds(lb[0] = new JLabel(), 30, 33, 805, 1));

			main.add(setBounds(jp[0] = new JPanel(), 30, 35, 210, 215));
			main.add(setBounds(area = new JTextArea(), 260, 50, 545, 170));
			main.add(setBounds(btn[0] = new JButton("소개글 수정"), 260, 220, 105, 30));
			if(u_no != s_no) btn[0].setVisible(false);

			main.add(setBounds(jp[1] = new JPanel(null), 30, 290, 805, 1000));
			jp[1].add(setBounds(jp[2] = new JPanel(new GridLayout()), 0, 0, 805, 35));

			// 버튼 클릭 후 panel
			jp[1].add(setBounds(jp[3] = new JPanel(null), 0, 35, 805, 1000));
			jp[1].add(setBounds(jp[4] = new JPanel(new GridLayout(0, 1)), 0, 45, 805, 405));
			jp[1].add(setBounds(jp[5] = new JPanel(null), 0, 35, 805, 1000));
			jp[1].add(setBounds(jp[6] = new JPanel(null), 0, 35, 805, 305));

			if(s_no == u_no) { 
				btnText = "상품,상점후기,찜,팔로잉".split(",");
				isCorrectionProduct = true;
			}

			setComponent(main);

			jp[3].setBackground(Color.white);
			jp[4].setBackground(Color.white);
			jp[5].setBackground(Color.white);
			jp[6].setBackground(Color.white);

			for(int i = 1; i <= btnText.length; i++) {
				jp[2].add(setBounds(btn[i] = new JButton(btnText[i - 1]), (btnText.length % 2) * 60 + 190, 50));
				btn[i].setBackground(Color.white);
				btn[i].setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
			}

			jp[4].setVisible(false);
			jp[5].setVisible(false);
			jp[6].setVisible(false);

			if(favoritePage) {
				jp[5].setVisible(true);
				jp[3].setVisible(false);
				btn[2].setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
			}
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
						update("update user set user.explain = ? where no = ?", area.getText(), s_no);
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
							if(btn[j].getText().equals(btnText[j - 1])) jp[j + 2].setVisible(true);
							btn[j].setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
						}

						else {
							jp[j + 2].setVisible(false);
							btn[j].setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
						}
					}
				});
			}
			rs = getResult("select count(*) as cnt from review where user = ?", s_no);
			rs.next();

			cnt = rs.getInt("cnt");
			if(cnt == 0) {
				jp[4].setSize(new Dimension(jp[4].getWidth(), 130));
				jp[4].add(setBounds(new JLabel("상점후기가 없습니다.", 0), 0, 0, 805, 20));
			}
			if(cnt != 0) {
				jp[4].setSize(new Dimension(jp[4].getWidth(), rs.getInt("cnt") * 180));

				rs = getResult("select * from review r inner join post p on r.post = p.no inner join user u on p.user = u.no where r.user = ?", s_no);

				while(rs.next()) {
					jp[4].add(setBounds(jp[7] = new JPanel(null), jp[4].getWidth(), 40));
					jp[7].setBackground(Color.white);
					jp[7].setBorder(new MatteBorder(1, 0, 1, 0, Color.lightGray));
					jp[7].add(setBounds(lb[1] = new JLabel(rs.getString("name")),30, 10, 50, 30));
					for (int i = 1; i <= 5; i++) {
						jp[7].add(setBounds(new JLabel("★"), 18 + i * 10, 30, 20, 30));
						jp[7].getComponent(i).setFont(new Font("맑은 고딕", 1, 13));
					}
					for(int i = 1; i <= rs.getInt("score"); i++) jp[7].getComponent(i).setForeground(Color.orange); 
					jp[7].add(setBounds(lb[2] = new JLabel(rs.getString("title") + " >", 0), 30, 70, rs.getString("title").length() * 15, 20));
					lb[2].setName(rs.getString("title"));

					lb[2].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							try {
								rs = getResult("select * from post where title = ?", ((JLabel)e.getSource()).getName());
								rs.next();
								p_no = rs.getInt("no");
								changePage(new ProductFrame().main);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});
					lb[2].setBorder(new LineBorder(Color.black));
					jp[7].add(setBounds(area1 = new JTextArea(), 30, 100, 750, 50));
					area1.setText(rs.getString("content"));
					area1.setLineWrap(true);
					area1.setEnabled(false);
					area1.setDisabledTextColor(Color.black);
					if(u_no == s_no) {
						jp[7].add(setBounds(btn[rs.getRow() + 9] = actbtn("수정하기", e -> {
							if(((JButton)e.getSource()).getText().equals("수정하기")) {
								jp[4].getComponent( Integer.parseInt(((JButton)e.getSource()).getName()) - 1 ).getComponentAt(((JButton)e.getSource()).getX(), ((JButton) e.getSource()).getY() - 40).setEnabled(true);
								jp[4].getComponent( Integer.parseInt(((JButton)e.getSource()).getName()) - 1 ).getComponentAt(((JButton)e.getSource()).getX(), ((JButton) e.getSource()).getY() - 40).requestFocus();
								((JButton)e.getSource()).setText("완료");
								return;
							}
							if(((JTextArea) jp[4].getComponent( Integer.parseInt(((JButton)e.getSource()).getName()) - 1 ).getComponentAt(((JButton)e.getSource()).getX(), ((JButton) e.getSource()).getY() - 40)).getText().length() == 0) {
								showErr("빈칸입니다.");
								return;
							}

							try {
								String content = ((JTextArea) jp[4].getComponent( Integer.parseInt(((JButton)e.getSource()).getName()) - 1 ).getComponentAt(((JButton)e.getSource()).getX(), ((JButton) e.getSource()).getY() - 40)).getText();
								rs = getResult("select * from review where user = ?", s_no);
								while(rs.next()) {
									if(rs.getInt("no") == Integer.parseInt(((JButton)e.getSource()).getName())) {
										update("update review set content = ? where no = ?", content, rs.getInt("no"));
										jp[4].getComponent( Integer.parseInt(((JButton)e.getSource()).getName()) - 1 ).getComponentAt(((JButton)e.getSource()).getX(), ((JButton) e.getSource()).getY() - 40).setEnabled(false);
										((JButton)e.getSource()).setText("수정하기");
									}
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}), 30, 140, 50, 30));
						btn[rs.getRow() + 9].setBackground(Color.white);
						btn[rs.getRow() + 9].setBorder(null);
						btn[rs.getRow() + 9].setForeground(Color.LIGHT_GRAY);
						btn[rs.getRow() + 9].setName(rs.getInt("no") + "");

						jp[7].add(setBounds(btn[11] = actbtn("삭제하기", e -> {
							try {
								rs = getResult("select * from review where user = ?", s_no);
								while(rs.next()) {
									if(rs.getInt("no") == Integer.parseInt(((JButton)e.getSource()).getName())) {
										update("delete from review where no = ?", rs.getInt("no"));
									}
								}
								rs = getResult("select count(*) as cnt from review where user = ?", s_no);
								rs.next();

								cnt = rs.getInt("cnt");

								jp[4].setSize(new Dimension(jp[4].getWidth(), cnt * 180));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							jp[4].remove(jp[4].getComponentAt(((JButton)e.getSource()).getX(), ((JButton) e.getSource()).getY()));
							jp[4].revalidate();
							jp[4].repaint();
						}), 90, 140, 50, 30));
						btn[11].setBackground(Color.white);
						btn[11].setBorder(null);
						btn[11].setForeground(Color.LIGHT_GRAY);
						btn[11].setName(rs.getInt("no") + "");
					}
				}
			}

			rs = getResult("select count(*) from favorite where user = ?", s_no);
			rs.next();
			cnt = rs.getInt("count(*)");
			if(cnt == 0) {
				jp[5].add(setBounds(new JLabel("찜한 상품이 없습니다.", 0), 30, 130, 805, 20));
			}

			else {
				jp[5].add(setBounds(new JLabel("<html>찜 <font color='red'>" + cnt + "</font></html>"), 20, 20, 80, 20));
				jp[5].add(setBounds(new JLabel(), 20, 55, 750, 1));
				((JLabel)jp[5].getComponent(1)).setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
				jp[5].add(setBounds(ch[0] = new JCheckBox(), 20, 70, 20, 20));
				ch[0].setBackground(Color.white);
				jp[5].add(setBounds(btn[190] = new JButton("선택삭제"), 60, 70, 90, 20));
				btn[190].setBackground(Color.white);
				btn[190].setBorder(new LineBorder(Color.black));

				jp[5].add(setBounds(jp[8] = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20)), 0, 100, jp[5].getWidth(), ((cnt % 2 == 0) ? (cnt / 2) : (cnt / 2 + 1)) * 140));
				jp[8].setBackground(Color.white);

				rs = getResult("select * from favorite f join post p on p.no = f.post where f.user = ?", s_no);
				while(rs.next()) {
					jp[8].add(setBounds(jp[9] = new JPanel(null), jp[8].getWidth() / 2 - 30, 100));
					jp[9].add(setBounds(ch[1] = new JCheckBox(), 340, 15, 20, 20));
					jp[9].setBorder(new LineBorder(Color.black));
					jp[9].setBackground(Color.white);
					jp[9].add(setBounds(new JLabel(getIcon("datafiles/image/post/" + rs.getInt("no") + "/1.jpg", 100, 92)), 3, 3, 100, 92));
					jp[9].add(setBounds(new JLabel(rs.getString("title")), 120, 10, 200, 30));
					ch[1].setBackground(Color.white);
					jp[9].add(setBounds(new JLabel(String.format("%,d원", rs.getInt("price"))), 120, 60, 300, 40));
				}

				for(Component comp : jp[8].getComponents()) {
					((JCheckBox)((JPanel) comp).getComponent(0)).addActionListener(e -> {
						if(!((JCheckBox)((JPanel) comp).getComponent(0)).isSelected()) {
							ch[0].setSelected(false);
						}
					});
				}

				ch[0].addActionListener(e -> {
					if(ch[0].isSelected()) {
						for(Component comp : jp[8].getComponents())
							((JCheckBox)((JPanel) comp).getComponent(0)).setSelected(true);
						return;
					}

					for(Component comp : jp[8].getComponents())
						((JCheckBox)((JPanel) comp).getComponent(0)).setSelected(false);
				});

				btn[190].addActionListener(e -> {
					for(Component comp : jp[8].getComponents()) {
						if(((JCheckBox)((JPanel) comp).getComponent(0)).isSelected()) {
							jp[8].remove(comp);
							jp[8].revalidate();
							jp[8].repaint();
						}
					}
				});
			}

			rs = getResult("select count(*) from post where user = ?", s_no);
			rs.next();
			cnt = rs.getInt("count(*)");

			jp[3].add(setBounds(new JLabel("<html>상품" + " <font color='red'>" + cnt + "</font></html>"),20, 20, 80, 20));
			jp[3].add(setBounds(new JLabel(), 20, 55, 750, 1));
			jp[3].add(setBounds(new JLabel("<html>전체" + " <font color='gray'>" + cnt + "개</font></html>"), 20, 70, 80, 20));
			if (cnt == 0) {
				jp[3].add(setBounds(new JLabel("등록한 상품이 존재하지 않습니다.", 0), 30, 130, 805, 20));
				return;
			}
			jp[3].add(setBounds(page = new JPanel(new FlowLayout(0)), 0, 100, 805, ((cnt % 5 == 0) ? cnt / 5 : cnt / 5 + 1) * 200 + 60));
			page.setName("5");
			page.setBackground(Color.white);
			showProductList(page, getResult("select * from post where user = ?", s_no));

			((JLabel)jp[3].getComponent(1)).setBorder(new LineBorder(Color.lightGray, 2));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new StoreFrame().setVisible(true);
	}
}
