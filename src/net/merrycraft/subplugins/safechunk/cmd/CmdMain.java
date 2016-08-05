package net.merrycraft.subplugins.safechunk.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.merrycraft.MerrycraftAPI;

public class CmdMain implements CommandExecutor {
	MerrycraftAPI plugin;
	FileConfiguration messages;
	Integer length;
	Player player;

	public CmdMain(MerrycraftAPI main) {
		plugin = main;
		messages = plugin.getMessages();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(messages.getString("ComandoSomentePlayer"));
			return true;
		}
		player = (Player) sender;
		if (args.length < 1) {
			sender.sendMessage(ChatColor.GOLD + "Use:");
			sender.sendMessage("   /terreno ver");
			sender.sendMessage("   /terreno comprar");
			sender.sendMessage("   /terreno deletar");
			return true;
		}
		switch (args[0].toLowerCase()) {
		case "ver":
			if(args.length == 1){
				// Abre o menu GUI
				sender.sendMessage("Menu aberto!");
				
			} else if(args.length == 2) {
				// Mostra o terreno no tamanho do args[1]
				try {
					length = Integer.parseInt(args[1]);
					new CmdVer(player, plugin, length);
				} catch (NumberFormatException e) {
					sender.sendMessage("Informe um valor numérico!");
				}
				
			}
			break;

		case "comprar":
				
			break;

		case "deletar":

			break;

		default:
			sender.sendMessage(ChatColor.GOLD + "Use:");
			sender.sendMessage("   /terreno ver");
			sender.sendMessage("   /terreno comprar");
			sender.sendMessage("   /terreno deletar");
			break;
		}

		return false;
	}

}
