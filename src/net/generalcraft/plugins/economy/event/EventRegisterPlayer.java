package net.generalcraft.plugins.economy.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.generalcraft.database.ConnectionDAO;
import net.generalcraft.plugins.economy.system.SysEconomy;

public class EventRegisterPlayer implements Listener {

	@EventHandler
	public void registerPlayer(PlayerJoinEvent e) {
		if (!SysEconomy.exists(e.getPlayer())) {
			try {
				Connection conn = ConnectionDAO.getConnection();
				// Cadastrando na tabela USER
				PreparedStatement pstm = conn.prepareStatement("INSERT INTO user VALUES(?);");
				pstm.setString(1, e.getPlayer().getName());
				pstm.execute();
				
//				// Cadastrando na tabela Economy
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
