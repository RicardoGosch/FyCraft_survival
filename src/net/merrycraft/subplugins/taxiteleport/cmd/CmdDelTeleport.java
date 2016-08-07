package net.merrycraft.subplugins.taxiteleport.cmd;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.merrycraft.subplugins.taxiteleport.model.ModelTaxi;
import net.merrycraft.subplugins.taxiteleport.system.SysMain;

public class CmdDelTeleport implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	Plugin main;
	ModelTaxi taxi = new ModelTaxi();
	SysMain sysMain = new SysMain(main);

	public CmdDelTeleport(Plugin instanceMain) {
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
				sysMain.setTaxi(taxi);

				if (sysMain.getTeleport() == null) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte não encontrado!");
					return true;
				}
				sysMain.delTeleport();
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
