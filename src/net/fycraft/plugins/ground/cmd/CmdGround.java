package net.fycraft.plugins.ground.cmd;

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

import net.fycraft.FyCraft;
import net.fycraft.plugins.ground.system.SysGroundBuy;
import net.fycraft.plugins.ground.system.SysGroundSeeBlock;

public class CmdGround implements CommandExecutor {
	FyCraft plugin;
	FileConfiguration messages;
	Integer length;
	Player player;
	private String prefix;

	public CmdGround(FyCraft main) {
		plugin = main;
		messages = plugin.getMessages();
		this.prefix = messages.getString("PrefixTerreno") + " ";
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + messages.getString("ComandoSomentePlayer"));
			return true;
		}
		player = (Player) sender;

		if (args.length < 2) {
			if (!player.getLocation().getWorld().getName().equalsIgnoreCase("world_build")) {
				player.sendMessage(prefix + messages.getString("TerrenoErrado"));
				return true;
			}
			player.openInventory(getVer());
			return true;
		}
		switch (args[0].toLowerCase()) {
		case "ver":
			if (!player.getLocation().getWorld().getName().equalsIgnoreCase("world_build")) {
				player.sendMessage(prefix + messages.getString("TerrenoErrado"));
				return true;
			}
			if (args.length == 1) {
				// Abre o menu GUI

			} else if (args.length == 2) {
				// Mostra o terreno no tamanho do args[1]
				try {
					length = Integer.parseInt(args[1]);
					if (!(length == 10 || length == 20 || length == 30)) {
						return true;
					}
					new SysGroundSeeBlock(player, plugin, length);
				} catch (NumberFormatException e) {
					sender.sendMessage(prefix + messages.getString("ValorNumerico"));
				}

			}
			break;

		case "comprar":
			// Mostra o terreno no tamanho do args[1]
			if (!player.getLocation().getWorld().getName().equalsIgnoreCase("world_build")) {
				player.sendMessage(prefix + messages.getString("TerrenoErrado"));
				return true;
			}
			try {
				length = Integer.parseInt(args[1]);
				if (!(length == 10 || length == 20 || length == 30)) {
					return true;
				}
				new SysGroundBuy(plugin, player, length);
			} catch (NumberFormatException e) {
				sender.sendMessage(prefix + messages.getString("ValorNumerico"));
			}
			break;

		case "deletar":

			break;

		default:
			if (!player.getLocation().getWorld().getName().equalsIgnoreCase("world_build")) {
				player.sendMessage(prefix + messages.getString("TerrenoErrado"));
				return true;
			}
			sender.sendMessage(ChatColor.GOLD + "Use:");
			sender.sendMessage(" /terreno ver [10/20/30]");
			sender.sendMessage(" /terreno comprar [10/20/30]");
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
