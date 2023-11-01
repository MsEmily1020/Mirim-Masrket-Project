package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import base.BaseFrame;

public class BackgroundFrame extends BaseFrame {
	
	public BackgroundFrame() {
		super("미림장터", 880, 940);
		
		try {
			main.add(setBounds(lb[0] = new JLabel("오늘의 상품 추천"), 5, 290, 155, 35));
			main.add(setBounds(btn[0] = actbtn("<", e -> movePicture(1)), 10, 100, 30, 50));
			main.add(setBounds(btn[1] = actbtn(">", e -> movePicture(-1)), 820, 100, 30, 50));
			main.add(setBounds(jp[0] = new JPanel(new FlowLayout(0, 0, 0)), 0, 0, 860 * 60, 275));
			main.add(setBounds(page = new JPanel(new FlowLayout(0)), 0, 330, 860, 50000));
			
			for (int i = 0; i < 60; i++) {
				jp[0].add(new JLabel(getIcon("./datafiles/image/background/" + ((i % 7) + 1) + ".jpg", 860, 275)));
			}
			
			new Thread() {
				public void run() {
					try {
						while (true) {
							Thread.sleep(3000);
							movePicture(-1);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
			
			setComponent(main);
			
			btn[0].setOpaque(false);
			btn[1].setOpaque(false);
			btn[0].setForeground(Color.white);
			btn[1].setForeground(Color.white);
			btn[0].setFont(new Font("맑은 고딕", 1, 20));
			btn[1].setFont(new Font("맑은 고딕", 1, 20));
			
			lb[0].setFont(new Font("맑은 고딕", 1, 18));
			
			page.setName("5");
			
			showProductList(page, getResult("select * from post order by rand()"));
			
			main.setPreferredSize(new Dimension(860, 330 + (205 + 5) * 4));
			
			mainCls.jsp.getVerticalScrollBar().addAdjustmentListener(e -> {
				try {
					JPanel comp = (JPanel) mainCls.jsp.getViewport().getView();
					if (e.getAdjustable().getMaximum() - e.getAdjustable().getVisibleAmount() == e.getValue()) {
						comp.setPreferredSize(new Dimension(860, comp.getPreferredSize().height + 210 * 4));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void movePicture(int a) {
		if (jp[0].getLocation().x % 860 != 0) return;
		var th = new Thread() {
			public void run() {
				try {
					int x = jp[0].getLocation().x;
					for (int i = 0; i < 861; i++) {
						jp[0].setLocation(x + i * a, 0);
						Thread.sleep(1);
						mainCls.main.repaint();
						main.repaint();
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		};
		th.start();
	}
}
