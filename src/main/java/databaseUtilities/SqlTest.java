package databaseUtilities;

import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlTest {
	public static void main(String[] args) throws SQLException {
		Connection connection = MysqlUtility.getConnection(
				"jdbc:mysql://localhost:3306/lianjia?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false", 
				"root", "");
		//MysqlUtility.inserct(connection, "people", 7,"lj");
		
		//MysqlUtility.delete(connection, "people", "WHERE people_id=7");
		//MysqlUtility.createDatabase(connection, "lianjia4");
//		Statement statement = connection.createStatement();
//		statement.executeQuery("USE music");
//		ResultSet resultSet = statement.executeQuery("SHOW TABLES");
//		while(resultSet.next()) {
//			System.out.println(resultSet.getString(1));
//		}
		String[] columns_setup = new String[2];
		
		columns_setup[0]=	MysqlUtility.columsBuilder("city", "VARCHAR(45)", false);
		columns_setup[1] =	MysqlUtility.columsBuilder("community", "VARCHAR(45)", false);
		
		MysqlUtility.createTable(connection, "test", columns_setup, "InnoDB", "utf8");
//		String sql = "CREATE DATABASE IF NOT EXISTS lianjia2";
//		Statement statement = connection.createStatement();
//		statement.executeUpdate(sql);
//		
//    	
//    	statement.close();
//    	connection.close();
//    	
//    	System.out.println(connection);
	}

}
