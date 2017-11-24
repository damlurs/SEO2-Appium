package com.ntt.acoe.framework.selenium.util;

import java.sql.*;
import java.util.ArrayList;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class DBUtil {

	/**
	 * Verifying Table Exist Or Not, If Not Return False Else True
	 * 
	 * @param jdbcDriver
	 *            : JDBC Driver For JDBC Connection
	 * @param dbURL
	 *            : Database URL
	 * @param username
	 *            : UserName For Login Into DataBase
	 * @param password
	 *            : Password For Login Into Database
	 * @param tableName
	 *            : Existing TableName into DB
	 * @return
	 */
	public static boolean isTableExist(String jdbcDriver, String dbURL, String username, String password, String tableName) {
		Connection conn = null;
		boolean isTableExist = false;
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, username, password);
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getTables(null, null, tableName, null);
			while (resultSet.next()) {
				resultSet.getString("TABLE_NAME");
			}

			if (resultSet.next()) {
				isTableExist = true;
			}
			resultSet.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Verifying Column Exist Or Not, If Not Return False Else True
	 * 
	 * @param jdbcDriver
	 *            : JDBC Driver For JDBC Connection
	 * @param dbURL
	 *            : Database URL
	 * @param username
	 *            : UserName For Login Into DataBase
	 * @param password
	 *            : Password For Login Into Database
	 * @param tableName
	 *            : Existing TableName into DB
	 * @param columnName
	 *            : Existing ColumnName into DB
	 * @return
	 */
	public static boolean isColumnExist(String jdbcDriver, String dbURL, String username, String password, String tableName, String columnName) {
		Connection conn = null;
		boolean isColumnExist = false;

		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, username, password);
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getColumns(null, null, tableName, columnName);
			if (resultSet.next()) {
				isColumnExist = true;
			}
			resultSet.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Getting Size Of Data Existing In Table
	 * 
	 * @param jdbcDriver
	 *            : JDBC Driver For JDBC Connection
	 * @param dbURL
	 *            : Database URL
	 * @param username
	 *            : UserName For Login Into DataBase
	 * @param password
	 *            : Password For Login Into Database
	 * @param sqlQuery
	 *            : SQl Query To Insert/Read Values From DataBase.
	 * @return
	 */
	public static int size(String jdbcDriver, String dbURL, String username, String password, String sqlQuery) {
		Connection conn = null;
		int resultSetSize = 0;

		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, username, password);
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sqlQuery);
			resultSetSize = resultSet.getFetchSize();
			resultSet.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultSetSize;
	}

	/**
	 * Getting Data From The DB
	 * 
	 * @param jdbcDriver
	 *            : JDBC Driver For JDBC Connection
	 * @param dbURL
	 *            : Database URL
	 * @param username
	 *            : UserName For Login Into DataBase
	 * @param password
	 *            : Password For Login Into Database
	 * @param sqlQuery
	 *            : SQl Query To Insert/Read Values From DataBase.
	 * @return
	 */
	public static ResultSet getResultSet(String jdbcDriver, String dbURL, String username, String password, String sqlQuery) {
		Connection conn = null;
		ResultSet resultSet = null;

		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, username, password);
			Statement stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sqlQuery);
			// conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static ArrayList<ArrayList<String>> getResultArrayList(String jdbcDriver, String dbURL, String username, String password, String sqlQuery) {
		Connection conn = null;
		ResultSet resultSet = null;
		ArrayList<ArrayList<String>> resultArrayList = null;

		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, username, password);
			Statement stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sqlQuery);

			int columnCount = resultSet.getMetaData().getColumnCount();

			if (resultSet.getType() == ResultSet.TYPE_FORWARD_ONLY)
				resultArrayList = new ArrayList<ArrayList<String>>();
			else {
				resultSet.last();
				resultArrayList = new ArrayList<ArrayList<String>>(resultSet.getRow());
				resultSet.beforeFirst();
			}

			for (ArrayList<String> row; resultSet.next(); resultArrayList.add(row)) {
				row = new ArrayList<String>(columnCount);

				for (int c = 1; c <= columnCount; ++c)
					row.add(resultSet.getString(c).intern());
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultArrayList;
	}

	/**
	 * Getting message for exist message
	 * 
	 * @param item
	 *            : Item Exist In Database
	 * @param isExist
	 *            : Condition To Check Where Item Exist Or Not
	 * @return isExistMessage : IsExistMessage To Print message Item Exist Or
	 *         Not
	 */
	public static String getIsExistMessage(String item, boolean isExist) {
		String isExistMessage = "";
		if (isExist) {
			isExistMessage = item + " " + " exist";
		} else {
			isExistMessage = item + " " + " does not exist";
		}

		return isExistMessage;
	}
	
	/**
	 * Getting Data From The DB
	 * 
	 * @param jdbcDriver
	 *            : JDBC Driver For JDBC Connection
	 * @param dbURL
	 *            : Database URL
	 * @param username
	 *            : UserName For Login Into DataBase
	 * @param password
	 *            : Password For Login Into Database
	 * @param sqlQuery
	 *            : SQl Query To Insert/Read Values From DataBase.
	 * @return
	 */
	public static Connection getConnection(String jdbcDriver, String dbURL, String username, String password) {
		Connection conn = null;

		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}
	
}