package net.fycraft.plugins.hometeleport.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.fycraft.FyCraft;
import net.fycraft.plugins.hometeleport.controller.ControllerHome;

public class CmdDelHome implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	FyCraft plugin;
	FileConfiguration messages;
	String prefix;

	public CmdDelHome(FyCraft plugin) {
		this.plugin = plugin;
		this.messages = plugin.getMessages();
		this.prefix = messages.getString("PrefixHome") + " ";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*
		 * 
		 * TODO Comando responsável pela exclusão de uma HOME
		 * 
		 * USO: /<command> [name]
		 * 
		 * <command> = [delhome]
		 * 
		 */

		// Verifica se sender é PLAYER
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return true;
		}

		// Verifica se o usuário usou algum argumento e informa como deve usar
		if (args.length != 1) {
			sender.sendMessage(prefix + messages.getString("UsoDelHome"));
			return true;
		}
		Player player = (Player) sender;

		String name = args[0];

		if (ControllerHome.remove(player.getName(), name)) {
			// Home deletada com sucesso
			player.sendMessage(prefix + messages.getString("HomeDeletada").replace("%home%", name));
		} else {
			// Não foi possível deletar a home
			player.sendMessage(prefix + messages.getString("HomeNaoDeletada").replace("%home%", name));
		}

		return true;
	}

}
