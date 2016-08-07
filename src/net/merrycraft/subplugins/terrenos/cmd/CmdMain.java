package net.merrycraft.subplugins.terrenos.cmd;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.merrycraft.MerrycraftAPI;
import net.merrycraft.subplugins.terrenos.system.SysVer;

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
			if (args.length == 1) {
				// Abre o menu GUI
				Inventory inv = Bukkit.createInventory(null, 27, "Terreno");

				// Pequeno
				ItemStack pequeno = new ItemStack(Material.LOG);
				ItemMeta pequenoMeta = pequeno.getItemMeta();
				pequenoMeta.setDisplayName(ChatColor.RED + "Terreno Pequeno");
				pequeno.setItemMeta(pequenoMeta);

				// Médio
				ItemStack medio = new ItemStack(Material.STAINED_CLAY);
				ItemMeta medioMeta = medio.getItemMeta();
				medioMeta.setDisplayName(ChatColor.RED + "Terreno Médio");
				medio.setItemMeta(medioMeta);

				// Grande
				ItemStack grande = new ItemStack(Material.BEDROCK);
				ItemMeta grandeMeta = grande.getItemMeta();
				grandeMeta.setDisplayName(ChatColor.RED + "Terreno Grande");
				ArrayList<String> grandeMetaLore = new ArrayList<String>();
				grandeMetaLore.add("§6Tamanho: 10x10");
				grandeMetaLore.add("§6Valor: $ 100");
				grandeMeta.setLore(grandeMetaLore);
				grande.setItemMeta(grandeMeta);

				inv.setItem(11, pequeno);
				inv.setItem(13, medio);
				inv.setItem(15, grande);
				player.openInventory(inv);

			} else if (args.length == 2) {
				// Mostra o terreno no tamanho do args[1]
				try {
					length = Integer.parseInt(args[1]);
					// if (!(length == 10 || length == 20 || length == 30)) {
					// sender.sendMessage(ChatColor.GOLD + "Use:");
					// sender.sendMessage(" /terreno ver 10");
					// sender.sendMessage(" /terreno ver 20");
					// sender.sendMessage(" /terreno ver 30");
					// return true;
					// }
					new SysVer(player, plugin, length);
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
