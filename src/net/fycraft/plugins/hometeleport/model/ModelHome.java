package net.fycraft.plugins.hometeleport.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class ModelHome implements Cloneable {

	@Override
	public ModelHome clone() throws CloneNotSupportedException {
		return (ModelHome) super.clone();
	}

	public ModelHome() {

	}

	public ModelHome(ResultSet rs) {
		try {
			this.setId(rs.getInt("home_id"));
			this.setMode(rs.getInt("home_mode"));
			this.setName(rs.getString("home_name"));
			this.setWorld(rs.getString("home_world"));
			this.setX(rs.getString("home_x"));
			this.setY(rs.getString("home_y"));
			this.setZ(rs.getString("home_z"));
			this.setYaw(rs.getString("home_yaw"));
			this.setPitch(rs.getString("home_pitch"));
			this.setUser(rs.getString("user_name"));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public ModelHome(Player player, String name) {
		this.setName(name);
		this.setUser(player.getName());
		this.setWorld(player.getLocation().getWorld().getName());
		this.setX(((Integer) player.getLocation().getBlockX()).toString());
		this.setY(((Integer) player.getLocation().getBlockY()).toString());
		this.setZ(((Integer) player.getLocation().getBlockZ()).toString());
		this.setYaw(((Float) player.getLocation().getYaw()).toString());
		this.setPitch(((Float) player.getLocation().getPitch()).toString());

	}

	int id = 0;
	int mode = 0;
	String name = null;
	String world = null;
	String x = null;
	String y = null;
	String z = null;
	String yaw = null;
	String pitch = null;
	String user = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public String getYaw() {
		return yaw;
	}

	public void setYaw(String yaw) {
		this.yaw = yaw;
	}

	public String getPitch() {
		return pitch;
	}

	public void setPitch(String pitch) {
		this.pitch = pitch;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
