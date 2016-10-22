package net.fycraft.plugins.hometeleport.cmd;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.fycraft.plugins.hometeleport.model.ModelHome;
import net.fycraft.plugins.hometeleport.system.SysHomeTeleport;

public class CmdDelHome implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	Plugin main;
	ModelHome taxi = new ModelHome();
	SysHomeTeleport sysHomeTeleport = new SysHomeTeleport(main);

	public CmdDelHome(Plugin instanceMain) {
		main = instanceMain;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return false;
		}

		switch (args.length) {
		case 1:
			try {

				String arg = args[0];
				taxi.setUser_name(sender.getName());
				taxi.setHouse_name(arg);
				sysHomeTeleport.setTaxi(taxi);

				if (sysHomeTeleport.getTeleport() == null) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte não encontrado!");
					return true;
				}
				sysHomeTeleport.delTeleport();
				sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte '" + taxi.getHouse_name()
						+ "' deletado com sucesso!");
				return true;

			} catch (SQLException | CloneNotSupportedException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

		return false;
	}

}
