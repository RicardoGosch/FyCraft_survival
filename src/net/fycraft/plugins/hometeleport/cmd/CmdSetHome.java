package net.fycraft.plugins.hometeleport.cmd;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.fycraft.plugins.hometeleport.model.ModelHome;
import net.fycraft.plugins.hometeleport.system.SysHomeTeleport;
import net.fycraft.plugins.hometeleport.system.SysHomeTeleportAssistant;

public class CmdSetHome implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	Plugin taxiTeleport;
	ModelHome taxi = new ModelHome();
	SysHomeTeleportAssistant system = new SysHomeTeleportAssistant();
	SysHomeTeleport sysHomeTeleport = new SysHomeTeleport(taxiTeleport);

	public CmdSetHome(Plugin instanceMain) {
		taxiTeleport = instanceMain;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Comando somente para Players!");
			return true;
		}
		switch (args.length) {
		case 1:
			try {
				String arg = args[0];
				if (!system.verifyCaracteres(arg)) {
					sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Caracteres inválidos!");
					return false;
				}
				Player player = (Player) sender;
				taxi.setUser_name(sender.getName());
				taxi.setHouse_name(arg);
				sysHomeTeleport.setTaxi(taxi);
				if (!system.hasPermission(player, sysHomeTeleport.getListTeleport().size())) {
					sender.sendMessage(
							ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Você alcançou o limite de teleportes.");
					return true;
				}
				setValues(player, arg);
				sysHomeTeleport.setTaxi(taxi);
				ModelHome taxiReturn = sysHomeTeleport.getTeleport();
				if (taxiReturn != null) {
					sysHomeTeleport.delTeleport();
					sysHomeTeleport.setTaxi(taxi);
				}
				sysHomeTeleport.setTeleport();
				sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Teleporte '" + taxi.getHouse_name()
						+ "' criado com sucesso!");

			} catch (SQLException | CloneNotSupportedException e) {
				e.printStackTrace();
			}
			break;
		}

		return false;
	}

	private void setValues(Player player, String arg) {
		Location locationPlayer = player.getLocation();
		taxi.setHouse_mode(0);
		taxi.setHouse_name(arg);
		taxi.setUser_name(player.getName());
		taxi.setHouse_world(locationPlayer.getWorld().getName());
		Double x = locationPlayer.getX();
		Double y = locationPlayer.getY();
		Double z = locationPlayer.getZ();
		Float pitch = locationPlayer.getPitch();
		Float yaw = locationPlayer.getYaw();
		taxi.setHouse_x(x.toString());
		taxi.setHouse_y(y.toString());
		taxi.setHouse_z(z.toString());
		taxi.setHouse_yaw(yaw.toString());
		taxi.setHouse_pitch(pitch.toString());
	}

}
