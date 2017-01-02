package net.fycraft.plugins.balance.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import net.fycraft.eventhandler.PlayerRegisterEvent;
import net.fycraft.FyCraft;
import net.fycraft.database.ConnectionDAO;

public class EvtPlayerRegister implements Listener {

	// private FyCraft plugin;

	public EvtPlayerRegister(FyCraft plugin) {
		// this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRegister(PlayerRegisterEvent e) {
		registerBalance(e.getPlayer());
	}
	
	public static void registerBalance(Player player){
		try {
			Connection conn = ConnectionDAO.getConnection();
			String sql = "INSERT INTO balance VALUES(?, 0);";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, player.getName());

			pstm.execute();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
}
