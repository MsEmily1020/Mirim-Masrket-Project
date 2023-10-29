package main;

import base.BaseFrame;

public class ChartFrame extends BaseFrame {
	public ChartFrame() {
		super("차트", 1000, 700);
	}
	
	public static void main(String[] args) {
		new ChartFrame().setVisible(true);
	}
}
