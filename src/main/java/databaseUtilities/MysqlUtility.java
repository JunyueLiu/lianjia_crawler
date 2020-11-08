package databaseUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

import com.mysql.cj.jdbc.Driver;

public class MysqlUtility {
	public static Connection getConnection(String url, String user, String password) throws SQLException {
		Connection connection = 
				DriverManager.getConnection(url, user, password);
		
		//System.out.println(connection);
		return connection;
		
	}
	
	public static void inserct(Connection connection,String table_name,Object ...ars){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("INSERT INTO "+table_name+" VALUES (");
		for (int i = 0; i < ars.length; i++) {
			sBuilder.append("?,");
			
		}
		sBuilder.deleteCharAt(sBuilder.length()-1);
		sBuilder.append(")");
		
		
		String insertsql = sBuilder.toString();
		//System.out.println(insertsql);
		
		
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement = connection.prepareStatement(insertsql);
		
			for (int i = 0; i < ars.length; i++) {
			
				preparedStatement.setObject(i+1, ars[i]);
			}
			
			preparedStatement.executeUpdate();
			}catch (SQLIntegrityConstraintViolationException e) {
				// TODO: handle exception
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	
	public static void inserct2(Connection connection,String table_name,Object[] array,Object ...ars) throws SQLException {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("INSERT INTO "+table_name+" VALUES (");
		for (int i = 0; i < array.length; i++) {
			sBuilder.append("?,");
		}
		
		
		for (int i = 0; i < ars.length; i++) {
			sBuilder.append("?,");
			
		}
		sBuilder.deleteCharAt(sBuilder.length()-1);
		sBuilder.append(")");
		
		
		String insertsql = sBuilder.toString();
		//System.out.println(insertsql);
		
		
		PreparedStatement preparedStatement = connection.prepareStatement(insertsql);
		for (int i = 0; i < array.length; i++) {
			preparedStatement.setObject(i+1, array[i]);
		}
		
		
		for (int i = 0; i < ars.length; i++) {
			preparedStatement.setObject(i+1+array.length, ars[i]);
			
		}
		
		
		preparedStatement.executeUpdate();
		
		
	}
	
	
	
	
	public static void delete(Connection connection,String table_name,String where_statement) throws SQLException {
		
		String deletetsql = "DELETE FROM "+table_name+" "+where_statement;
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(deletetsql);
		
		
	}
	
	public static void createDatabase(Connection connection,String database_name) {
		
		String createDatabase = "CREATE DATABASE IF NOT EXISTS "+database_name;
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(createDatabase);
			statement.executeQuery("USE "+database_name);
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: fail to create database "+database_name);
			// TODO: handle exception
		}
		//Connection connection2 = DriverManager();
		
	}
	
	public static void createTable(Connection connection,String table_name,String[] columns_setup,String engine,String charset) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("CREATE TABLE IF NOT EXISTS "+table_name+"(");
		for (String string : columns_setup) {
			sBuilder.append(string);
			sBuilder.append(",");
		}
		sBuilder.deleteCharAt(sBuilder.length()-1);
		sBuilder.append(")");
		sBuilder.append(" ENGINE = "+engine+" ");
		sBuilder.append("DEFAULT CHARACTER SET = "+charset);
		
		
		
		String createDatabase = sBuilder.toString();
		try {
			connection.createStatement().executeUpdate(createDatabase);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(createDatabase);
		
		
	}
	
	public static String columsBuilder(String varname, String type, boolean setnull) {
		String nullstring = setnull?"NULL":"NOT NULL";
		
		return varname+" "+type+" "+ nullstring;
		
	}
	
	public static ResultSet getResult(Connection connection,String sql) {
		Statement statement;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
		
	}
	
	

}
