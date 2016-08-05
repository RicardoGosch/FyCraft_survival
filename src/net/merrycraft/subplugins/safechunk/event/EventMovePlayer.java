package net.merrycraft.subplugins.safechunk.event;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.merrycraft.MerrycraftAPI;
import net.merrycraft.subplugins.safechunk.cmd.CmdVer;

public class EventMovePlayer implements Listener {

	MerrycraftAPI plugin;
	FileConfiguration config;

	public EventMovePlayer(MerrycraftAPI main) {
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
				//new CmdVer(plugin, player);
			}

		}
	}
}