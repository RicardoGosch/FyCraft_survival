package net.merrycraft.subplugins.safechunk.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.merrycraft.MerrycraftAPI;

public class CmdSafeChunk implements CommandExecutor {
	MerrycraftAPI plugin;
	FileConfiguration messages;
	
	public CmdSafeChunk(MerrycraftAPI main) {
		plugin = main;
		messages = plugin.getMessages();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			switch (args.length) {
			case 1:
				switch (args[0].toLowerCase()) {
				case "ver":
					if (sender.hasPermission("land.buy")) {
						new CmdSeeChunk(plugin, sender);
					} else {
						sender.sendMessage(messages.getString("SemPermissaoComando"));
					}
					break;

				case "comprar":
					if (sender.hasPermission("land.buy")) {
						new CmdBuyChunk(sender, cmd, label, args);
					} else {
						sender.sendMessage(messages.getString("SemPermissaoComando"));
					}

					break;

				case "buy":
					if (sender.hasPermission("land.buy")) {
						new CmdBuyChunk(sender, cmd, label, args);
					} else {
						sender.sendMessage(messages.getString("SemPermissaoComando"));
					}
					break;
				}
				break;
			default:
				sender.sendMessage("/terreno <comprar/deletar/ver>");
				break;
			}
		} else {
			sender.sendMessage(messages.getString("ComandoSomentePlayer"));
		}
		return false;
	}

}
