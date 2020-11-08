package Lianjia.deal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import databaseUtilities.MysqlUtility;

public class DealTest {
	public static void main(String [] args) {
//		Connection connection;
//		try {
//			connection = MysqlUtility.getConnection(
//					"jdbc:mysql://localhost:3306/lianjia?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
//					"root", "815165");
//			//CommunityCrawl.crawlCommunity(connection, tableName, city, xiaoquUrl);
////			DealCrawl.crawlAndWriteMySQL("北京", true);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("src/main/java/config.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		DealCrawl dealCrawl = new DealCrawl(properties,"深圳");
		dealCrawl.crawlAndWriteCSV();
//		dealCrawl.getDealInfo2("https://sz.lianjia.com/chengjiao/105100372181.html");

	}

}
