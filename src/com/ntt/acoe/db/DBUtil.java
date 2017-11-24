
package com.ntt.acoe.db;
import java.sql.*;

/**  * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class DBUtil {
	static String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static String DB_URL = "jdbc:oracle:thin:@165.136.7.158:1521/ccasit3.cca.pri";
	static String USER = "shalini";
	static String PASS = "shalini";

	public static void set(String jdbcDriver, String dbURL, String username, String password) {
		JDBC_DRIVER = jdbcDriver;
		DB_URL = dbURL;
		USER = username;
		PASS = password;
	}

	public static boolean isTableExist(String tableName) {
		return isTableExist(JDBC_DRIVER, DB_URL, USER, PASS, tableName);
	}

	public static boolean isTableExist(String jdbcDriver, String dbURL, String username, String password, String tableName) {
		Connection conn = null;
		boolean isTableExist = false;

		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, username, password);
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = metadata.getTables(null, null, tableName, null);
			if (resultSet.next()) {
				isTableExist = true;
			}
			resultSet.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isTableExist;
	}

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

		return isColumnExist;
	}

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

	public static ResultSet getResultSet(String sqlQuery) {
		return getResultSet(JDBC_DRIVER, DB_URL, USER, PASS, sqlQuery);
	}

	public static ResultSet getResultSet(String jdbcDriver, String dbURL, String username, String password, String sqlQuery) {
		Connection conn = null;
		ResultSet resultSet = null;

		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, username, password);
			Statement stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sqlQuery);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static void main(String args[]) {
		String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
		String DB_URL = "jdbc:oracle:thin:@165.136.7.158:1521/ccasit3.cca.pri";
		String USER = "shalini";
		String PASS = "shalini";
		DBUtil.set(JDBC_DRIVER, DB_URL, USER, PASS);
		ResultSet rs = DBUtil.getResultSet("Select * from DORIS.MEMBER_MASTER where SUBSCRIBER_ID like '1410361076170%'");
		try {
			System.out.println("RS Size:" + rs.getFetchSize());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
