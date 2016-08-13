package net.generalcraft.subplugins.economy.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import net.generalcraft.database.ConnectionDAO;

public class SysEconomy {

	public static double getMoney(String player) {
		Double amount = 0.0;
		try {
			ConnectionDAO dao = new ConnectionDAO();
			Connection conn = (Connection) dao.getConnection();
			String sql = "SELECT * FROM economy WHERE user_name=?;";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			amount = rs.getDouble("money");
			dao.closeConnection(conn, pstm, rs);
			return amount;
		} catch (SQLException e) {
			return 0;
		}
	}

	// Caso passe um player
	public static double getMoney(Player player) {
		String playerS = player.getName();
		System.out.println(playerS);
		Double amount = 0.0;
		try {
			ConnectionDAO dao = new ConnectionDAO();
			Connection conn = (Connection) dao.getConnection();
			PreparedStatement pstm = conn.prepareStatement("SELECT * FROM economy WHERE user_name=?;");
			pstm.setString(1, playerS);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			amount = rs.getDouble("money");

			dao.closeConnection(conn, pstm, rs);
		} catch (SQLException e) {

		}
		return amount;
	}

	public static void setMoney(String player, Double amount) {
		try {
			ConnectionDAO dao = new ConnectionDAO();
			Connection conn = (Connection) dao.getConnection();
			String sql = "UPDATE economy WHERE user_name=? SET money=?;";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player);
			pstm.setDouble(2, amount);
			pstm.execute();
			dao.closeConnection(conn, pstm);
		} catch (SQLException e) {
		}
	}

	// Caso passe um player
	public static void setMoney(Player player, Double amount) {
		String playerS = player.getName();
		try {
			ConnectionDAO dao = new ConnectionDAO();
			Connection conn = (Connection) dao.getConnection();
			String sql = "UPDATE economy WHERE user_name=? SET money=?;";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, playerS);
			pstm.setDouble(2, amount);
			pstm.execute();
			dao.closeConnection(conn, pstm);
		} catch (SQLException e) {
		}
	}

	public static boolean exists(String player) {
		try {
			ConnectionDAO dao = new ConnectionDAO();
			Connection conn = (Connection) dao.getConnection();
			String sql = "SELECT * FROM economy WHERE user_name=?;";
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql);

			pstm.setString(1, player);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				dao.closeConnection(conn, pstm, rs);
				return true;
			}
			dao.closeConnection(conn, pstm, rs);
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	// Caso passe um player
	public static boolean exists(Player player) {
		String playerS = player.getName();
		try {
			ConnectionDAO dao = new ConnectionDAO();
			Connection conn = (Connection) dao.getConnection();
			String sql = "SELECT * FROM economy WHERE user_name=?;";
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql);

			pstm.setString(1, playerS);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				dao.closeConnection(conn, pstm, rs);
				return true;
			}
			dao.closeConnection(conn, pstm, rs);
			return false;
		} catch (SQLException e) {
			return false;
		}
	}
}
