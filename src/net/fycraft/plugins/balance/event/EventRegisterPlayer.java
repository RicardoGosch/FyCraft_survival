package net.fycraft.plugins.balance.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.fycraft.database.ConnectionDAO;
import net.fycraft.plugins.balance.system.Balance;

public class EventRegisterPlayer implements Listener {

	@EventHandler
	public void registerPlayer(PlayerJoinEvent e) {
		if (!Balance.exists(e.getPlayer())) {
			try {
				Connection conn = ConnectionDAO.getConnection();
				// Cadastrando na tabela USER
				PreparedStatement pstm = conn.prepareStatement("INSERT INTO user VALUES(?);");
				pstm.setString(1, e.getPlayer().getName());
				pstm.execute();
				
//				// Cadastrando na tabela Balance
				pstm = conn.prepareStatement("INSERT INTO economy VALUES(0, ?);");
				pstm.setString(1, e.getPlayer().getName());
				pstm.execute();
				ConnectionDAO.closeConnection(conn, pstm);
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}

}
