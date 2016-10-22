package net.fycraft.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDAO {
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/fycraft_survival";
	private final static String USER = "root";
	private final static String PASS = "";

	public static final Connection getConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASS);

		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	public static final void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public static final void closeConnection(Connection conn, PreparedStatement pstm) {
		closeConnection(conn);
		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
			}
		}
	}

	public static final void closeConnection(Connection conn, PreparedStatement pstm, ResultSet rs) {
		closeConnection(conn, pstm);
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}
}
