package net.merrycraft.subplugins.taxiteleport.system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

import com.mysql.jdbc.Connection;

import net.merrycraft.database.ConnectionDAO;
import net.merrycraft.subplugins.taxiteleport.model.ModelTaxi;

public class SystemTaxiTeleport {
	// ****************
	// * Atributes
	// ****************
	Plugin taxiTeleport = null;
	ModelTaxi taxi = new ModelTaxi();
	Connection connection = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;
	HashMap<Integer, ModelTaxi> mapTaxi = new HashMap<>();
	// ****************
	// * Instances
	// ****************
	ConnectionDAO dao = new ConnectionDAO();

	// ****************
	// * Functions
	// ****************
	public SystemTaxiTeleport(Plugin main) {
		taxiTeleport = main;
	}

	public void setTaxi(ModelTaxi instaceTaxi) throws CloneNotSupportedException {
		taxi = instaceTaxi.clone();
	}

	public ModelTaxi getTeleport() throws SQLException, CloneNotSupportedException {
		connection = (Connection) dao.getConnection();
		if (taxi.getHouse_id() != 0) {
			pstm = connection.prepareStatement("SELECT * FROM house WHERE house_id=?;");
			pstm.setInt(1, taxi.getHouse_id());
		} else {
			pstm = connection.prepareStatement("SELECT * FROM house WHERE house_name=? AND user_name=?;");
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
			dao.closeConnection(connection, pstm, rs);
			return null;
		}
		// Seta os valores no TAXI
		dao.closeConnection(connection, pstm, rs);
		return taxi;
	}

	public HashMap<Integer, ModelTaxi> getListTeleport() throws SQLException, CloneNotSupportedException {
		mapTaxi.clear();
		connection = (Connection) dao.getConnection();
		pstm = connection.prepareStatement("SELECT * FROM house WHERE user_name= ? ;");
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
		return mapTaxi;
	}

	public boolean delTeleport() throws SQLException {
		connection = (Connection) dao.getConnection();
		pstm = connection.prepareStatement("DELETE FROM house WHERE house_id=?;");
		pstm.setInt(1, taxi.getHouse_id());
		pstm.execute();
		return true;
	}

	public boolean setTeleport() throws SQLException {
		connection = (Connection) dao.getConnection();
		pstm = connection.prepareStatement("INSERT INTO house VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		pstm.setInt(1, 0);
		pstm.setString(2, taxi.getHouse_name());
		pstm.setInt(3, taxi.getHouse_mode());
		pstm.setString(4, taxi.getHouse_world());
		pstm.setString(5, taxi.getHouse_x());
		pstm.setString(6, taxi.getHouse_y());
		pstm.setString(7, taxi.getHouse_z());
		pstm.setString(8, taxi.getHouse_yaw());
		pstm.setString(9, taxi.getHouse_pitch());
		pstm.setString(10, taxi.getUser_name());
		pstm.execute();
		return true;
	}
}
