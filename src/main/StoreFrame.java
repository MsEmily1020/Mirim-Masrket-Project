package main;

import base.BaseFrame;

public class StoreFrame extends BaseFrame {
	public StoreFrame() {
		super("미림장터", 1000, 1000);
		
	}
	
	public static void main(String[] args) {
		new StoreFrame().setVisible(true);
	}
}
