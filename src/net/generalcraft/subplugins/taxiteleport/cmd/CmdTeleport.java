package net.generalcraft.subplugins.taxiteleport.cmd;

import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.generalcraft.subplugins.taxiteleport.model.ModelTaxi;
import net.generalcraft.subplugins.taxiteleport.system.SysMain;

public class CmdTeleport implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	Plugin main;
	ModelTaxi taxi = new ModelTaxi();
	SysMain sysMain = new SysMain(main);

	public CmdTeleport(Plugin instanceMain) {
		main = instanceMain;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return false;
		}
		switch (args.length) {
		case 0:
			try {
				taxi.setUser_name(sender.getName());
				sysMain.setTaxi(taxi);
				HashMap<Integer, ModelTaxi> mapTaxi = sysMain.getListTeleport();
				if (mapTaxi.size() == 0) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Nenhum teleporte encontrado!");
					return true;
				}
				String msg = "";
				for (int a = 0; mapTaxi.size() > a; a++) {
					msg += ", " + mapTaxi.get(a).getHouse_name();
				}
				sender.sendMessage("");
				sender.sendMessage(
						ChatColor.GOLD + "Teleportes Privados: " + ChatColor.RESET + msg.replaceFirst(", ", ""));
				sender.sendMessage("");
			} catch (CloneNotSupportedException | SQLException e) {
				e.printStackTrace();
			}
			break;

		case 1:
			try {
				String arg = args[0];
				if (arg.contains(":")) {
					// Comando será utilizado pela staff para ver a home de
					// outro player
					return true;
				}
				taxi.setHouse_name(arg);
				taxi.setUser_name(sender.getName());
				sysMain.setTaxi(taxi);
				ModelTaxi taxiReturn = sysMain.getTeleport();
				if (taxiReturn == null) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte não encontrado!");
				} else {
					Player player = (Player) sender;
					World world = Bukkit.getWorld(taxiReturn.getHouse_world());
					Double x = Double.parseDouble(taxiReturn.getHouse_x());
					Double y = Double.parseDouble(taxiReturn.getHouse_y());
					Double z = Double.parseDouble(taxiReturn.getHouse_z());
					Float yaw = Float.parseFloat(taxiReturn.getHouse_yaw());
					Float pitch = Float.parseFloat(taxiReturn.getHouse_pitch());
					Location loc = new Location(world, x, y, z, yaw, pitch);
					world.loadChunk(loc.getChunk());
					main.getLogger().info("Chunk of " + player.getName() + " is loadded");
					if (!(player.getLocation().getChunk().getEntities().length > 1)) {
						player.getWorld().unloadChunk(player.getLocation().getChunk());
						main.getLogger().info("Chunk of " + player.getName() + " is unloadded");
					}
					player.teleport(loc);
					player.playSound(loc, Sound.SUCCESSFUL_HIT, 2, 3);
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleportado para '"
							+ taxiReturn.getHouse_name() + "'!");
				}

			} catch (CloneNotSupportedException | SQLException e) {
				e.printStackTrace();
			}
			break;
		}

		return false;
	}

}
