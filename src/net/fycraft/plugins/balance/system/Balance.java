package net.fycraft.plugins.balance.system;

import org.bukkit.entity.Player;

public class Balance {

	public static void remove(Double amount, Player player) {
		SysInternal.setMoney(player, SysInternal.getMoney(player) - amount);
	}

	public static void remove(Double amount, String player) {
		SysInternal.setMoney(player, SysInternal.getMoney(player) - amount);
	}

	public static void add(Double amount, Player player) {
		SysInternal.setMoney(player, SysInternal.getMoney(player) + amount);
	}

	public static void add(Double amount, String player) {
		SysInternal.setMoney(player, SysInternal.getMoney(player) + amount);
	}

	public static void set(Double amount, Player player) {
		SysInternal.setMoney(player, amount);
	}

	public static void set(Double amount, String player) {
		SysInternal.setMoney(player, amount);
	}

	public static Double get(Player player) {
		return SysInternal.getMoney(player);
	}

	public static Double get(String player) {
		return SysInternal.getMoney(player);
	}

	public static boolean exists(Player player) {
		return SysInternal.exists(player);
	}

	public static boolean exists(String player) {
		return SysInternal.exists(player);
	}

	public static boolean has(Double amount, Player player) {
		return SysInternal.hasMoney(player, amount);
	}

	public static boolean has(Double amount, String player) {
		return SysInternal.hasMoney(player, amount);
	}

}
