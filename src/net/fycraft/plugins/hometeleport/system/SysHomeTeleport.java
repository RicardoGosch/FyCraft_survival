package net.fycraft.plugins.hometeleport.system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

import com.mysql.jdbc.Connection;

import net.fycraft.database.ConnectionDAO;
import net.fycraft.plugins.hometeleport.model.ModelHome;

public class SysHomeTeleport {
	// ****************
	// * Atributes
	// ****************
	Plugin taxiTeleport = null;
	ModelHome taxi = new ModelHome();
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;
	HashMap<Integer, ModelHome> mapTaxi = new HashMap<>();

	// ****************
	// * Functions
	// ****************
	public SysHomeTeleport(Plugin main) {
		taxiTeleport = main;
	}

	public void setTaxi(ModelHome instaceTaxi) throws CloneNotSupportedException {
		taxi = instaceTaxi.clone();
	}

	public ModelHome getTeleport() throws SQLException, CloneNotSupportedException {
		conn = (Connection) ConnectionDAO.getConnection();
		if (taxi.getHouse_id() != 0) {
			pstm = conn.prepareStatement("SELECT * FROM home WHERE home_id=?;");
			pstm.setInt(1, taxi.getHouse_id());
		} else {
			pstm = conn.prepareStatement("SELECT * FROM home WHERE home_name=? AND user_name=?;");
			pstm.setString(1, taxi.getHouse_name());
			pstm.setString(2, taxi.getUser_name());
		}
		rs = pstm.executeQuery();
		if (rs.next()) {
			taxi.setHouse_id(rs.getInt("house_id"));
			taxi.setHouse_name(rs.getString("house_name"));
			taxi.setHouse_mode(rs.getInt("house_mode"));
			taxi.setHouse_world(rs.getString("house_world"));
			taxi.setHouse_x(rs.getString("house_x"));
			taxi.setHouse_y(rs.getString("house_y"));
			taxi.setHouse_z(rs.getString("house_z"));
			taxi.setHouse_yaw(rs.getString("house_yaw"));
			taxi.setHouse_pitch(rs.getString("house_pitch"));
			taxi.setUser_name(rs.getString("user_name"));
		} else {
			ConnectionDAO.closeConnection(conn, pstm, rs);
			return null;
		}
		// Seta os valores no TAXI
		ConnectionDAO.closeConnection(conn, pstm, rs);
		return taxi;
	}

	public HashMap<Integer, ModelHome> getListTeleport() throws SQLException, CloneNotSupportedException {
		mapTaxi.clear();
		conn = (Connection) ConnectionDAO.getConnection();
		pstm = conn.prepareStatement("SELECT * FROM home WHERE user_name= ? ;");
		pstm.setString(1, taxi.getUser_name());
		rs = pstm.executeQuery();
		Integer count = 0;
		while (rs.next()) {
			taxi.setHouse_id(rs.getInt("house_id"));
			taxi.setHouse_name(rs.getString("house_name"));
			taxi.setHouse_mode(rs.getInt("house_mode"));
			taxi.setHouse_world(rs.getString("house_world"));
			taxi.setHouse_x(rs.getString("house_x"));
			taxi.setHouse_y(rs.getString("house_y"));
			taxi.setHouse_z(rs.getString("house_z"));
			taxi.setHouse_yaw(rs.getString("house_yaw"));
			taxi.setHouse_pitch(rs.getString("house_pitch"));
			mapTaxi.put(count, taxi.clone());
			count++;
		}
		ConnectionDAO.closeConnection(conn, pstm, rs);
		return mapTaxi;
	}

	public boolean delTeleport() throws SQLException {
		conn = (Connection) ConnectionDAO.getConnection();
		pstm = conn.prepareStatement("DELETE FROM home WHERE home_id=?;");
		pstm.setInt(1, taxi.getHouse_id());
		pstm.execute();
		ConnectionDAO.closeConnection(conn, pstm, rs);
		return true;
	}

	public boolean setTeleport() throws SQLException {
		conn = (Connection) ConnectionDAO.getConnection();
		pstm = conn.prepareStatement("INSERT INTO home VALUES(?, null, ?, ?, ?, ?, ?, ?, ?, ?);");
		pstm.setString(1, taxi.getUser_name());
		pstm.setString(2, taxi.getHouse_name());
		pstm.setInt(3, taxi.getHouse_mode());
		pstm.setString(4, taxi.getHouse_world());
		pstm.setString(5, taxi.getHouse_x());
		pstm.setString(6, taxi.getHouse_y());
		pstm.setString(7, taxi.getHouse_z());
		pstm.setString(8, taxi.getHouse_yaw());
		pstm.setString(9, taxi.getHouse_pitch());
		pstm.execute();
		ConnectionDAO.closeConnection(conn, pstm, rs);
		return true;
	}
}
