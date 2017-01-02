package net.fycraft.system.spawn.cmd;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fycraft.AuthGosch;

public class CmdSpawn implements CommandExecutor {

	public CmdSpawn() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*
		 * 
		 * TODO Comando responsável por teleportar player para o spawn
		 * 
		 * USO: /<command>
		 * 
		 * <command> = [spawn]
		 * 
		 */

		// Verificando se o sender é PLAYER
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return false;
		}
		Player player = (Player) sender;
		player.teleport(AuthGosch.getSpawn());
		player.playSound(AuthGosch.getSpawn(), Sound.SUCCESSFUL_HIT, 2L, 5L);

		return false;
	}

}
