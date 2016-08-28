package net.generalcraft.plugins.taxiteleport.cmd;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.generalcraft.plugins.taxiteleport.model.ModelTaxi;
import net.generalcraft.plugins.taxiteleport.system.SysTaxiTeleport;
import net.generalcraft.plugins.taxiteleport.system.SysTaxiTeleportAssistant;

public class CmdSetTeleport implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	Plugin taxiTeleport;
	ModelTaxi taxi = new ModelTaxi();
	SysTaxiTeleportAssistant system = new SysTaxiTeleportAssistant();
	SysTaxiTeleport sysTaxiTeleport = new SysTaxiTeleport(taxiTeleport);

	public CmdSetTeleport(Plugin instanceMain) {
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
				sysTaxiTeleport.setTaxi(taxi);
				if (!system.hasPermission(player, sysTaxiTeleport.getListTeleport().size())) {
					sender.sendMessage(
							ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Você alcançou o limite de teleportes.");
					return true;
				}
				setValues(player, arg);
				sysTaxiTeleport.setTaxi(taxi);
				ModelTaxi taxiReturn = sysTaxiTeleport.getTeleport();
				if (taxiReturn != null) {
					sysTaxiTeleport.delTeleport();
					sysTaxiTeleport.setTaxi(taxi);
				}
				sysTaxiTeleport.setTeleport();
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
