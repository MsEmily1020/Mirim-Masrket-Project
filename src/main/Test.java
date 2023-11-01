package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {
	public static void main(String[] args) {
		for(int i = 1; i <= 100; i++) {
			try {
				if(Files.list(Paths.get("datafiles/image/post/" + i + "/")).count() == 0) 
				System.out.println(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
