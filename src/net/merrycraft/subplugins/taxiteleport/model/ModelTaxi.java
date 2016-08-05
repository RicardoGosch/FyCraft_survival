package net.merrycraft.subplugins.taxiteleport.model;

public class ModelTaxi implements Cloneable {

	@Override
	public ModelTaxi clone() throws CloneNotSupportedException {
		return (ModelTaxi) super.clone();
	}

	int house_id = 0, house_mode = 0;
	String house_name = null, house_world = null, house_x = null, house_y = null, house_z = null, house_yaw = null,
			house_pitch = null, user_name = null;

	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	public int getHouse_mode() {
		return house_mode;
	}

	public void setHouse_mode(int house_mode) {
		this.house_mode = house_mode;
	}

	public String getHouse_name() {
		return house_name;
	}

	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}

	public String getHouse_world() {
		return house_world;
	}

	public void setHouse_world(String house_world) {
		this.house_world = house_world;
	}

	public String getHouse_x() {
		return house_x;
	}

	public void setHouse_x(String house_x) {
		this.house_x = house_x;
	}

	public String getHouse_y() {
		return house_y;
	}

	public void setHouse_y(String house_y) {
		this.house_y = house_y;
	}

	public String getHouse_z() {
		return house_z;
	}

	public void setHouse_z(String house_z) {
		this.house_z = house_z;
	}

	public String getHouse_yaw() {
		return house_yaw;
	}

	public void setHouse_yaw(String house_yaw) {
		this.house_yaw = house_yaw;
	}

	public String getHouse_pitch() {
		return house_pitch;
	}

	public void setHouse_pitch(String house_pitch) {
		this.house_pitch = house_pitch;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
