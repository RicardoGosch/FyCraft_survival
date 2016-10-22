package net.fycraft.plugins.vip.cmd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Connection;

import net.fycraft.GeneralcraftAPI;
import net.fycraft.database.ConnectionDAO;
import net.fycraft.plugins.vip.api.pagseguro.exception.PagSeguroServiceException;
import net.fycraft.plugins.vip.model.ModelVIP;

public class CmdVip implements CommandExecutor {

	private GeneralcraftAPI plugin;
	public FileConfiguration messages;
	private ModelVIP vip;
	private String dateString;
	private DateFormat dateFormat;
	private Calendar calendar;

	public CmdVip(GeneralcraftAPI pluginInstance) {
		plugin = pluginInstance;
		messages = plugin.getMessages();
		vip = new ModelVIP();
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		calendar = Calendar.getInstance();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (args.length) {
		case 0:
			if (!(sender instanceof Player)) {
				sender.sendMessage(messages.getString("ComandoSomentePlayer"));
				return false;
			}
			// Teleporta player para a área VIP
			break;
		case 2:
			switch (args[0].toLowerCase()) {
			case "ativar":
				if (!(sender instanceof Player)) {
					sender.sendMessage(messages.getString("ComandoSomentePlayer"));
					return true;
				}
				try {
					vip.setKey(args[1].toUpperCase());
					vip.setPlayer((Player) sender);
					vip.setDate(calendar.getTime());

					if (isOnDatabase()) {
						sender.sendMessage(messages.getString("KeyVipInvalida"));
						return true;
					}
					if (!new CmdVipActivate().run(plugin, vip)) {
						sender.sendMessage(messages.getString("KeyVipInvalida"));
						return true;
					}
					plugin.getLogger().info("Antes: " + calendar.getTime());
					dateString = dateFormat.format(vip.getDate());
					registerDatabase();
					vip.getPlayer().sendTitle(
							messages.getString("Todos-ativou-l1").replace("%player%", vip.getPlayer().getName()),
							messages.getString("Todos-ativou-l2").replace("%vip%", "VIP"));
				} catch (PagSeguroServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case "tempo":
				// Verifica o tempo do vip do player
				break;

			case "novo":
				// Gerar uma key nova
				break;

			default:
				return true;
			}
			break;

		default:
			// Mensagem de modo de uso do comando /vip
			break;
		}
		return false;
	}

	public boolean isOnDatabase() {
		try {

			Connection conn = (Connection) ConnectionDAO.getConnection();
			PreparedStatement pstm = conn.prepareStatement("SELECT * FROM vip_activated WHERE vip_key=?");
			pstm.setString(1, vip.getKey());
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				ConnectionDAO.closeConnection(conn, pstm, rs);
				return true;
			}
			ConnectionDAO.closeConnection(conn, pstm, rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void registerDatabase() {
		try {
			Connection conn = (Connection) ConnectionDAO.getConnection();

			PreparedStatement pstm = conn.prepareStatement("INSERT INTO vip_activated VALUES(?, ?, ?, ?, ?);");
			pstm.setString(1, vip.getKey());
			pstm.setString(2, vip.getPlayer().getName());
			pstm.setInt(3, 1);
			pstm.setString(4, dateString);
			pstm.setInt(5, 1);
			pstm.execute();
			ConnectionDAO.closeConnection(conn, pstm);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
