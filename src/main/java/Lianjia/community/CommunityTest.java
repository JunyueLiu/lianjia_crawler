package Lianjia.community;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class CommunityTest {
	public static void main(String [] args) throws SQLException {
//		Connection connection = MysqlUtility.getConnection(
//				"jdbc:mysql://localhost:3306/lianjia?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
//				"root", "815165");
//
//
//		CommunityCrawl.crawlCommunity(connection, "community","北京", "https://bj.lianjia.com/xiaoqu/");
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("src/main/java/config.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommunityCrawl communityCrawl = new CommunityCrawl(properties,"深圳");
		communityCrawl.crawlCommunity();


	}

}
