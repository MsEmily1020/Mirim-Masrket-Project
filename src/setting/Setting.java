package setting;

import java.sql.DriverManager;

public class Setting {
	public Setting() throws Exception {
		var con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/?serverTimezone=UTC&allowLoadLocalInfile=true", "root", "1234");
		var stmt = con.createStatement();
		
		stmt.execute("SET GLOBAL local_infile = 1");
		stmt.execute("DROP DATABASE IF EXISTS `mirim-market`");
		stmt.execute("CREATE SCHEMA `mirim-market` DEFAULT CHARACTER SET utf8;");

		stmt.execute("CREATE TABLE `mirim-market`.`history` (\r\n"
				+ "  `no` int NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `content` text,\r\n"
				+ "  PRIMARY KEY (`no`)\r\n"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb3;\r\n"
				+ "");
		
		System.out.println("history db 생성");
		
		stmt.execute("CREATE TABLE `mirim-market`.`category` (\r\n"
				+ "  `no` int NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` text,\r\n"
				+ "  PRIMARY KEY (`no`)\r\n"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=569 DEFAULT CHARSET=utf8mb3;\r\n"
				+ "");
		
		System.out.println("category db 생성");
		
		stmt.execute("CREATE TABLE `mirim-market`.`user` (\r\n"
				+ "  `no` int NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` text,\r\n"
				+ "  `id` text,\r\n"
				+ "  `pw` text,\r\n"
				+ "  `phone` text,\r\n"
				+ "  `email` text,\r\n"
				+ "  PRIMARY KEY (`no`)\r\n"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb3;\r\n"
				+ "");
		
		System.out.println("user db 생성");
		
		stmt.execute("CREATE TABLE `mirim-market`.`post` (\r\n"
				+ "  `no` int NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `title` text,\r\n"
				+ "  `explanation` text,\r\n"
				+ "  `price` int DEFAULT NULL,\r\n"
				+ "  `view` int DEFAULT NULL,\r\n"
				+ "  `deliveryfee` int DEFAULT NULL,\r\n"
				+ "  `category` int DEFAULT NULL,\r\n"
				+ "  `category_sub` int DEFAULT NULL,\r\n"
				+ "  `category_datail` int DEFAULT NULL,\r\n"
				+ "  `satae` int DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`no`),\r\n"
				+ "  KEY `fk_post_category_idx` (`category`),\r\n"
				+ "  CONSTRAINT `fk_post_category` FOREIGN KEY (`category`) REFERENCES `category` (`no`) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=1048 DEFAULT CHARSET=utf8mb3;\r\n"
				+ "");
		
		System.out.println("post db 생성");
		
		stmt.execute("CREATE TABLE `mirim-market`.`favorite` (\r\n"
				+ "  `no` int NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `user` int DEFAULT NULL,\r\n"
				+ "  `post` int DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`no`),\r\n"
				+ "  KEY `fk_favorite_user_idx` (`user`),\r\n"
				+ "  KEY `fk_favorite_post_idx` (`post`),\r\n"
				+ "  CONSTRAINT `fk_favorite_post` FOREIGN KEY (`post`) REFERENCES `post` (`no`) ON DELETE CASCADE ON UPDATE CASCADE,\r\n"
				+ "  CONSTRAINT `fk_favorite_user` FOREIGN KEY (`user`) REFERENCES `user` (`no`) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb3;\r\n"
				+ "");
		
		System.out.println("favorite db 생성");
		
		stmt.execute("CREATE TABLE `mirim-market`.`follower` (\r\n"
				+ "  `no` int NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `user` int DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`no`),\r\n"
				+ "  KEY `fk_follower_user_idx` (`user`),\r\n"
				+ "  CONSTRAINT `fk_follower_user` FOREIGN KEY (`user`) REFERENCES `user` (`no`) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
				+ ") ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb3;\r\n"
				+ "");
		
		System.out.println("follower db 생성");
		
		stmt.execute("CREATE TABLE `mirim-market`.`review` (\r\n"
				+ "  `no` int NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `user` int DEFAULT NULL,\r\n"
				+ "  `post` int DEFAULT NULL,\r\n"
				+ "  `content` text,\r\n"
				+ "  `score` int DEFAULT NULL,\r\n"
				+ "  PRIMARY KEY (`no`),\r\n"
				+ "  KEY `fk_review_post_idx` (`post`),\r\n"
				+ "  KEY `fk_review_user_idx` (`user`),\r\n"
				+ "  CONSTRAINT `fk_review_post` FOREIGN KEY (`post`) REFERENCES `post` (`no`) ON DELETE CASCADE ON UPDATE CASCADE,\r\n"
				+ "  CONSTRAINT `fk_review_user` FOREIGN KEY (`user`) REFERENCES `user` (`no`) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;\r\n"
				+ "");
		
		stmt.execute("ALTER TABLE `mirim-market`.`history` \r\n"
				+ "ADD COLUMN `user_no` INT NULL AFTER `content`,\r\n"
				+ "ADD INDEX `fk_history_user_idx` (`user_no` ASC) VISIBLE;\r\n"
				+ ";\r\n");
		
		stmt.execute("ALTER TABLE `mirim-market`.`history` \r\n"
				+ "ADD CONSTRAINT `fk_history_user`\r\n"
				+ "  FOREIGN KEY (`user_no`)\r\n"
				+ "  REFERENCES `mirim-market`.`user` (`no`)\r\n"
				+ "  ON DELETE CASCADE\r\n"
				+ "  ON UPDATE CASCADE;\r\n"
				+ "");
		
		stmt.execute("ALTER TABLE `mirim-market`.`history` \r\n"
				+ "CHANGE COLUMN `user_no` `user_no` INT NULL DEFAULT NULL AFTER `no`;\r\n"
				+ "");
		
		System.out.println("review db 생성");
		
		stmt.execute("CREATE TABLE `mirim-market`.`recent` (\r\n"
				+ "  `no` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `user_no` INT NULL,\r\n"
				+ "  `post_no` INT NULL,\r\n"
				+ "  PRIMARY KEY (`no`),\r\n"
				+ "  INDEX `fk_recent_user_idx` (`user_no` ASC) VISIBLE,\r\n"
				+ "  INDEX `fk_recent_post_idx` (`post_no` ASC) VISIBLE,\r\n"
				+ "  CONSTRAINT `fk_recent_user`\r\n"
				+ "    FOREIGN KEY (`user_no`)\r\n"
				+ "    REFERENCES `mirim-market`.`user` (`no`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE,\r\n"
				+ "  CONSTRAINT `fk_recent_post`\r\n"
				+ "    FOREIGN KEY (`post_no`)\r\n"
				+ "    REFERENCES `mirim-market`.`post` (`no`)\r\n"
				+ "    ON DELETE CASCADE\r\n"
				+ "    ON UPDATE CASCADE);\r\n"
				+ "");
		
		System.out.println("recent db 생성");
		
		stmt.execute("ALTER TABLE `mirim-market`.`recent` \r\n"
				+ "DROP FOREIGN KEY `fk_recent_post`;\r\n");
		
		stmt.execute("ALTER TABLE `mirim-market`.`recent` \r\n"
				+ "DROP COLUMN `post_no`,\r\n"
				+ "ADD COLUMN `title` TEXT NULL AFTER `user_no`,\r\n"
				+ "ADD COLUMN `price` INT NULL AFTER `title`,\r\n"
				+ "CHANGE COLUMN `no` `sort` INT NOT NULL AUTO_INCREMENT ,\r\n"
				+ "DROP INDEX `fk_recent_post_idx` ;\r\n");
		
		stmt.execute("ALTER TABLE `mirim-market`.`recent` \r\n"
				+ "ADD COLUMN `no` INT NULL AFTER `price`;\r\n"
				+ "");
		
		stmt.execute("ALTER TABLE `mirim-market`.`recent` \r\n"
				+ "ADD INDEX `fk_recent_post_idx` (`no` ASC) VISIBLE;\r\n"
				+ ";\r\n");
		
		stmt.execute("ALTER TABLE `mirim-market`.`recent` \r\n"
				+ "ADD CONSTRAINT `fk_recent_post`\r\n"
				+ "  FOREIGN KEY (`no`)\r\n"
				+ "  REFERENCES `mirim-market`.`post` (`no`)\r\n"
				+ "  ON DELETE CASCADE\r\n"
				+ "  ON UPDATE CASCADE;\r\n"
				+ "");
		
		stmt.execute("DROP USER IF EXISTS 'user'@'127.0.0.1'");
		stmt.execute("CREATE USER 'user'@'127.0.0.1' IDENTIFIED BY '1234'");
		stmt.execute("GRANT SELECT, INSERT, DELETE, UPDATE ON `mirim-market`.* TO 'user'@'127.0.0.1'");
		
		System.out.println("user 생성");
		
		stmt.execute("USE `mirim-market`");
		
		String[] tables = "user,category,post,review,favorite,follower,history,recent".split(",");
		
		for(String table : tables) {
			stmt.execute("LOAD DATA LOCAL INFILE 'datafiles/" + table + ".txt'"
					+ " INTO TABLE " + table
					+ " character set 'utf8mb4'"
					+ " fields TERMINATED BY '\\t'"
					+ " LINES TERMINATED BY '\\r\\n'"
					+ " IGNORE 1 LINES");
		}
		
		System.out.println("data txt file 추가");
	}
	
	public static void main(String[] args) {
		try {
			new Setting();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
