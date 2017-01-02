package net.fycraft.plugins.hometeleport.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

import net.fycraft.database.ConnectionDAO;
import net.fycraft.plugins.hometeleport.model.ModelHome;

public class ControllerHome {
	// ****************
	// * Atributes
	// ****************
	Plugin taxiTeleport = null;

	// ****************
	// * Functions
	// ****************
	public ControllerHome(Plugin main) {
		taxiTeleport = main;
	}

	public static Boolean exists(String player, String name) {
		Connection conn = ConnectionDAO.getConnection();
		String sql = "SELECT home_id FROM home WHERE user_name=? AND home_name=? ;";
		PreparedStatement pstm = null;
		Boolean result = false;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, player);
			pstm.setString(2, name);
			ResultSet rs = pstm.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		ConnectionDAO.closeConnection(conn, pstm);
		return result;
	}

	public static Boolean exists(Integer id) {
		Connection conn = ConnectionDAO.getConnection();
		String sql = "SELECT home_id FROM home WHERE home_id=? ;";
		PreparedStatement pstm = null;
		Boolean result = false;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		ConnectionDAO.closeConnection(conn, pstm);
		return result;
	}

	public static ModelHome select(String player, String name) {
		Connection conn = ConnectionDAO.getConnection();
		String sql = "SELECT * FROM home WHERE user_name=? AND home_name=? ;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, player);
			pstm.setString(2, name);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				ModelHome home = new ModelHome(rs);
				ConnectionDAO.closeConnection(conn, pstm, rs);
				return home;
			} else {
				ConnectionDAO.closeConnection(conn, pstm, rs);
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	public static ArrayList<ModelHome> selectAll(String player) {
		ArrayList<ModelHome> list = new ArrayList<>();
		Connection conn = ConnectionDAO.getConnection();
		String sql = "SELECT * FROM home WHERE user_name=? ;";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, player);
			rs = pstm.executeQuery();

			while (rs.next()) {
				ModelHome home = new ModelHome(rs);
				list.add(home.clone());
			}
		} catch (SQLException | CloneNotSupportedException e) {
			System.err.println(e.getMessage());
		}
		return list;
	}

	public static boolean remove(Integer id) {
		if(!exists(id)) return false;
		Connection conn = ConnectionDAO.getConnection();
		String sql = "DELETE FROM home WHERE home_id=?;";
		PreparedStatement pstm;
		Boolean result = false;
		try {
			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, id);
			result = !pstm.execute();
			ConnectionDAO.closeConnection(conn, pstm);
		} catch (SQLException e) {
			result = false;
			System.err.println(e.getMessage());
		}
		return result;
	}

	public static boolean remove(String player, String name) {
		if(!exists(player, name)) return false;
		Connection conn = ConnectionDAO.getConnection();
		String sql = "DELETE FROM home WHERE home_name=? AND user_name=?;";
		PreparedStatement pstm;
		Boolean result = false;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, name);
			pstm.setString(2, player);
			result = !pstm.execute();
			ConnectionDAO.closeConnection(conn, pstm);
		} catch (SQLException e) {
			result = false;
			System.err.println(e.getMessage());
		}
		return result;
	}

	public static boolean insert(ModelHome home) {
		Connection conn = ConnectionDAO.getConnection();
		String sql = "INSERT INTO home VALUES(null,?,?,?,?,?,?,?,?,?);";
		PreparedStatement pstm;
		Boolean result = false;

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, home.getUser());
			pstm.setString(2, home.getName());
			pstm.setInt(3, home.getMode());
			pstm.setString(4, home.getWorld());
			pstm.setString(5, home.getX());
			pstm.setString(6, home.getY());
			pstm.setString(7, home.getZ());
			pstm.setString(8, home.getYaw());
			pstm.setString(9, home.getPitch());
			result = !pstm.execute();
			ConnectionDAO.closeConnection(conn, pstm);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}

		return result;
	}
}
