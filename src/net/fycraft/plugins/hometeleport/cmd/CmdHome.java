package net.fycraft.plugins.hometeleport.cmd;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.fycraft.FyCraft;
import net.fycraft.plugins.hometeleport.controller.ControllerHome;
import net.fycraft.plugins.hometeleport.model.ModelHome;

public class CmdHome implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	FyCraft plugin;
	FileConfiguration messages;
	String prefix;

	public CmdHome(FyCraft plugin) {
		this.plugin = plugin;
		this.messages = plugin.getMessages();
		this.prefix = messages.getString("PrefixHome") + " ";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*
		 * 
		 * TODO Comando responsável por listar todas as homes, além de
		 * teleportar para elas
		 * 
		 * USO: /<command> [name]
		 * 
		 * <command> = [home]
		 * 
		 */
		
		// Verificando se o sender é PLAYER
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return false;
		}
		Player player = (Player) sender;
		
		if (args.length == 0) {
			// Mostra todas as homes para o player
			ArrayList<ModelHome> list = ControllerHome.selectAll(player.getName());
			String msg = "";
			if(list.size() > 0){
				for (ModelHome modelHome : list) {
					msg += ", " + modelHome.getName();
				}
			} else {
				msg += "Nenhuma encontrada.";
			}
			
			player.sendMessage(prefix + messages.getString("TodasHomes").replace("%msg%", msg.replaceFirst(", ", "")));

		}
		
		if (args.length == 1) {
			// Teleporta o player para a home
			String name = args[0];
			if(!ControllerHome.exists(player.getName(), name)){
				player.sendMessage(prefix + messages.getString("NaoTeleportadoHome").replace("%home%", name));
				return true;
			}
			ModelHome home = ControllerHome.select(player.getName(), name);
			
			Location loc = new Location(
					Bukkit.getWorld(home.getWorld()), 
					Double.parseDouble(home.getX()), 
					Double.parseDouble(home.getY()), 
					Double.parseDouble(home.getZ()), 
					Float.parseFloat(home.getYaw()), 
					Float.parseFloat(home.getPitch()));
			loc.getWorld().loadChunk(loc.getChunk());
			player.teleport(loc);
			player.sendMessage(prefix + messages.getString("TeleportadoHome").replace("%home%", name));
			player.playSound(loc, Sound.SUCCESSFUL_HIT, 2L, 5L);
		}

		return true;
	}

}
