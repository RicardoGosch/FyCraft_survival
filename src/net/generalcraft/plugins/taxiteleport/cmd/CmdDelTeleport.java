package net.generalcraft.plugins.taxiteleport.cmd;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.generalcraft.plugins.taxiteleport.model.ModelTaxi;
import net.generalcraft.plugins.taxiteleport.system.SysTaxiTeleport;

public class CmdDelTeleport implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	Plugin main;
	ModelTaxi taxi = new ModelTaxi();
	SysTaxiTeleport sysTaxiTeleport = new SysTaxiTeleport(main);

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
				sysTaxiTeleport.setTaxi(taxi);

				if (sysTaxiTeleport.getTeleport() == null) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte não encontrado!");
					return true;
				}
				sysTaxiTeleport.delTeleport();
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
