package setting;

import java.sql.DriverManager;

public class Setting {
	public Setting() throws Exception {
		var con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/?serverTimezone=UTC", "root", "1234");
		var stmt = con.createStatement();
		
		stmt.execute("set global local_infile = 1");
		stmt.execute("drop schema if exists `mirim-market`");
		stmt.execute("CREATE SCHEMA `mirim-market` DEFAULT CHARACTER SET utf8");
		
	}
	
	public static void main(String[] args) {
		try {
			new Setting();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
