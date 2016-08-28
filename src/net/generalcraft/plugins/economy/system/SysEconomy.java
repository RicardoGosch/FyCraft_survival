package net.generalcraft.plugins.economy.system;

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
			Connection conn = (Connection) ConnectionDAO.getConnection();
			String sql = "SELECT * FROM economy WHERE user_name=?;";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			amount = rs.getDouble("money");
			ConnectionDAO.closeConnection(conn, pstm, rs);
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
			Connection conn = (Connection) ConnectionDAO.getConnection();
			PreparedStatement pstm = conn.prepareStatement("SELECT * FROM economy WHERE user_name=?;");
			pstm.setString(1, playerS);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			amount = rs.getDouble("money");

			ConnectionDAO.closeConnection(conn, pstm, rs);
		} catch (SQLException e) {

		}
		return amount;
	}

	public static void setMoney(String player, Double amount) {
		try {
			Connection conn = (Connection) ConnectionDAO.getConnection();
			String sql = "UPDATE economy SET money=? WHERE user_name=?;";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, amount);
			pstm.setString(2, player);
			pstm.execute();
			ConnectionDAO.closeConnection(conn, pstm);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Caso passe um player
	public static void setMoney(Player player, Double amount) {
		String playerS = player.getName();
		try {
			Connection conn = (Connection) ConnectionDAO.getConnection();
			String sql = "UPDATE economy WHERE user_name=? SET money=?;";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, playerS);
			pstm.setDouble(2, amount);
			pstm.execute();
			ConnectionDAO.closeConnection(conn, pstm);
		} catch (SQLException e) {
		}
	}

	public static boolean exists(String player) {
		try {
			Connection conn = (Connection) ConnectionDAO.getConnection();
			String sql = "SELECT * FROM economy WHERE user_name=?;";
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql);

			pstm.setString(1, player);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				ConnectionDAO.closeConnection(conn, pstm, rs);
				return true;
			}
			ConnectionDAO.closeConnection(conn, pstm, rs);
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	// Caso passe um player
	public static boolean exists(Player player) {
		String playerS = player.getName();
		try {
			Connection conn = (Connection) ConnectionDAO.getConnection();
			String sql = "SELECT * FROM economy WHERE user_name=?;";
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql);

			pstm.setString(1, playerS);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				ConnectionDAO.closeConnection(conn, pstm, rs);
				return true;
			}
			ConnectionDAO.closeConnection(conn, pstm, rs);
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public static boolean hasMoney(Player player, Double amount) {
		if (getMoney(player) >= amount)
			return true;
		return false;
	}

	public static boolean hasMoney(String player, Double amount) {
		if (getMoney(player) >= amount)
			return true;
		return false;
	}
}
