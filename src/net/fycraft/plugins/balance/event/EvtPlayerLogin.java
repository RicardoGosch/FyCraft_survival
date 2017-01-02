package net.fycraft.plugins.balance.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import net.fycraft.FyCraft;
import net.fycraft.database.ConnectionDAO;
import net.fycraft.eventhandler.PlayerLoginEvent;

public class EvtPlayerLogin implements Listener {

	// private FyCraft plugin;

	public EvtPlayerLogin(FyCraft plugin) {
		// this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent e) {
		if (!isBalance(e.getPlayer())) {
			EvtPlayerRegister.registerBalance(e.getPlayer());
		}
	}

	private static boolean isBalance(Player player) {
		Boolean result = false;
		try {
			Connection conn = ConnectionDAO.getConnection();
			String sql = "SELECT * FROM balance WHERE user_name=?;";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());

			ResultSet rs = pstm.executeQuery();
			result = rs.next();
			ConnectionDAO.closeConnection(conn, pstm, rs);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
}
