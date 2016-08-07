package net.generalcraft.subplugins.terrenos.cmd;

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

import net.generalcraft.MerrycraftAPI;
import net.generalcraft.subplugins.terrenos.system.SysVer;

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
		if (args.length < 2) {
			sender.sendMessage(ChatColor.GOLD + "Use também:");
			sender.sendMessage(" /terreno ver [tamanho]");
			sender.sendMessage(" /terreno comprar [tamanho]");
			sender.sendMessage(" /terreno deletar");
			player.openInventory(getVer());
			return true;
		}
		switch (args[0].toLowerCase()) {
		case "ver":
			if (args.length == 1) {
				// Abre o menu GUI

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
			sender.sendMessage(ChatColor.GOLD + "Use também:");
			sender.sendMessage(" /terreno ver [tamanho]");
			sender.sendMessage(" /terreno comprar [tamanho]");
			sender.sendMessage(" /terreno deletar");
			break;
		}

		return false;
	}

	private Inventory getVer() {
		// Pequeno
		ItemStack pequeno = new ItemStack(Material.LOG);
		ItemMeta pequenoMeta = pequeno.getItemMeta();
		pequenoMeta.setDisplayName(ChatColor.RED + "Terreno Pequeno");
		ArrayList<String> pequenoMetaLore = new ArrayList<String>();
		pequenoMetaLore.add(" §710x10");
		pequenoMetaLore.add(" §7100 coins");
		pequenoMeta.setLore(pequenoMetaLore);
		pequeno.setItemMeta(pequenoMeta);

		// Médio
		ItemStack medio = new ItemStack(Material.STAINED_CLAY);
		ItemMeta medioMeta = medio.getItemMeta();
		medioMeta.setDisplayName(ChatColor.RED + "Terreno Médio");
		ArrayList<String> medioMetaLore = new ArrayList<String>();
		medioMetaLore.add(" §720x20");
		medioMetaLore.add(" §7500 coins");
		medioMeta.setLore(medioMetaLore);
		medio.setItemMeta(medioMeta);

		// Grande
		ItemStack grande = new ItemStack(Material.BEDROCK);
		ItemMeta grandeMeta = grande.getItemMeta();
		grandeMeta.setDisplayName(ChatColor.RED + "Terreno Grande");
		ArrayList<String> grandeMetaLore = new ArrayList<String>();
		grandeMetaLore.add(" §730x30");
		grandeMetaLore.add(" §71.000 coins");
		grandeMeta.setLore(grandeMetaLore);
		grande.setItemMeta(grandeMeta);

		// Expandir
		ItemStack expandir = new ItemStack(Material.DIODE);
		ItemMeta expandirMeta = expandir.getItemMeta();
		expandirMeta.setDisplayName(ChatColor.GOLD + "Expandir Terreno");
		expandir.setItemMeta(expandirMeta);

		Inventory inv = Bukkit.createInventory(null, 27, "Terreno");
		inv.setItem(11, pequeno);
		inv.setItem(13, medio);
		inv.setItem(15, grande);
		inv.setItem(26, expandir);

		return inv;
	}

}
