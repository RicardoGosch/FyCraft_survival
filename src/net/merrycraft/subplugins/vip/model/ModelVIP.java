package net.merrycraft.subplugins.vip.model;

import java.util.Date;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;

public class ModelVIP {
	private Player player;
	private PermissionUser pexPlayer;
	private String type;
	private Integer temp;
	private String key;
	private String paymentType;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PermissionUser getPexPlayer() {
		return pexPlayer;
	}

	public void setPexPlayer(PermissionUser pexPlayer) {
		this.pexPlayer = pexPlayer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
