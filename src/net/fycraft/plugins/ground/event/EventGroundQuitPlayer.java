package net.fycraft.plugins.ground.event;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.fycraft.FyCraft;

public class EventGroundQuitPlayer implements Listener {
	FyCraft plugin;
	FileConfiguration config;

	public EventGroundQuitPlayer(FyCraft main) {
		plugin = main;
		config = plugin.getSafeChunkConfig();
	}

	@EventHandler
	public void onQuitPlayer(PlayerQuitEvent e) {
		if (!playerEmpty(e.getPlayer())) {
			config.set(e.getPlayer().getName(), false);
			plugin.saveSafeChunkConfig();
		}
	}

	public boolean playerEmpty(Player player) {
		if (config.isSet(player.getName())) {
			return false;
		}
		return true;
	}
}