package net.fycraft.plugins.hometeleport.controller;

import org.bukkit.entity.Player;

public class SysAssistant {

	public static boolean verifyCaracteres(String arg) {
		if (arg.contains(":") || arg.contains(";") || arg.contains("?") || arg.contains("/") || arg.contains(".")
				|| arg.contains("-") || arg.contains("_") || arg.contains("=") || arg.contains("§") || arg.contains(",")
				|| arg.contains("[") || arg.contains("]") || arg.contains("{") || arg.contains("}") || arg.contains("@")
				|| arg.contains("#") || arg.contains("*") || arg.contains("(") || arg.contains(")") || arg.contains("ª")
				|| arg.contains("º") || arg.contains("^") || arg.contains("~") || arg.contains("&") || arg.contains("!")
				|| arg.contains("'") || arg.contains("¨") || arg.contains("£") || arg.contains("%") || arg.contains("¢")
				|| arg.contains("¬") || arg.contains(".") || arg.contains("°")) {
			return false;
		}

		return true;
	}

	public static boolean hasPermission(Player player, Integer size) {
		if (player.hasPermission("rank.staff.dono") || player.isOp()) {
			return true;
		} else if (player.hasPermission("rank.normal")) {
			if (size < 2) {
				return true;
			}
			return false;
		} else if (player.hasPermission("rank.carl")) {
			if (size < 5) {
				return true;
			}
			return false;
		} else if (player.hasPermission("rank.maggie")) {
			if (size < 10) {
				return true;
			}
			return false;
		} else if (player.hasPermission("rank.gleen")) {
			if (size < 15) {
				return true;
			}
			return false;
		} else if (player.hasPermission("rank.michonne")) {
			if (size < 20) {
				return true;
			}
			return false;
		} else if (player.hasPermission("rank.rick")) {
			if (size < 30) {
				return true;
			}
			return false;
		} else if (player.hasPermission("rank.staff")) {
			if (size < 10) {
				return true;
			}
			return false;
		}

		return false;

	}
}
