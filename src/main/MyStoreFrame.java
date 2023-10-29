package main;

import base.BaseFrame;

public class MyStoreFrame extends BaseFrame {
	public MyStoreFrame() {
		super("미림장터", 1000, 1000);
		
	}
	
	public static void main(String[] args) {
		new MyStoreFrame().setVisible(true);
	}
}
