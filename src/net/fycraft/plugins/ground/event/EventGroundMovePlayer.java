package net.fycraft.plugins.ground.event;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.fycraft.FyCraft;

public class EventGroundMovePlayer implements Listener {

	FyCraft plugin;
	FileConfiguration config;

	public EventGroundMovePlayer(FyCraft main) {
		plugin = main;
		config = plugin.getSafeChunkConfig();
	}

	@EventHandler
	public void onMovePlayer(PlayerMoveEvent e) {
		Location from = e.getFrom();
		Location to = e.getTo();
		if (from.getBlockX() - to.getBlockX() > 0.1 || from.getBlockX() - to.getBlockX() < -0.1
				|| from.getBlockY() - to.getBlockY() > 0.1 || from.getBlockY() - to.getBlockY() < -0.1
				|| from.getBlockZ() - to.getBlockZ() > 0.1 || from.getBlockZ() - to.getBlockZ() < -0.1) {
			Player player = e.getPlayer();
			String playerName = player.getName();
			Boolean playerFile = false;
			playerFile = config.getBoolean(playerName);
			if (playerFile) {
				//new SysGroundSeeBlock(plugin, player);
			}

		}
	}
}