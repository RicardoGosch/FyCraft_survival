package net.merrycraft.subplugins.safechunk.cmd;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.merrycraft.MerrycraftAPI;

public class CmdVer {

	private Player player;
	private MerrycraftAPI plugin;
	private Location locP;
	private Integer length;
	private World world;

	Integer posX;
	Integer posY;
	Integer posZ;

	Location locA;
	Location locB;

	public CmdVer(Player player, MerrycraftAPI plugin, Integer length) {
		this.player = player;
		this.plugin = plugin;
		this.length = length;
		this.locP = player.getLocation();
		this.world = locP.getWorld();
		run();
	}

	private void run() {
		posX = locP.getBlockX() + (length / 2);
		posY = locP.getBlockY();
		posZ = locP.getBlockZ() + (length / 2);

		updatePos();
		if (!verifyBlockTop()) {
			while ((locB.getBlock().getType() != Material.AIR
					|| locB.getBlock().getType() != Material.RED_MUSHROOM
					|| locB.getBlock().getType() != Material.BROWN_MUSHROOM
					|| locB.getBlock().getType() != Material.YELLOW_FLOWER
					|| locB.getBlock().getType() != Material.DOUBLE_PLANT
					|| locB.getBlock().getType() != Material.GRASS) && posY < world.getMaxHeight()) {
				posY++;
				updatePos();
			}
		}
		player.sendBlockChange(locA, Material.CHEST, (byte) 1);

	}

	private void updatePos() {
		locA = new Location(world, posX, posY, posZ);
		locB = new Location(world, posX, posY - 1, posZ);
	}

	private boolean verifyBlockTop() {
		while ((locB.getBlock().getType() == Material.AIR
				|| locB.getBlock().getType() == Material.RED_MUSHROOM
				|| locB.getBlock().getType() == Material.BROWN_MUSHROOM
				|| locB.getBlock().getType() == Material.YELLOW_FLOWER
				|| locB.getBlock().getType() == Material.DOUBLE_PLANT
				|| locB.getBlock().getType() == Material.GRASS)
				&& posY > 0) {
			posY--;
			updatePos();
		}

		if (locB.getBlock().getType() == Material.AIR 
				|| locB.getBlock().getType() == Material.RED_MUSHROOM
				|| locB.getBlock().getType() == Material.BROWN_MUSHROOM
				|| locB.getBlock().getType() == Material.YELLOW_FLOWER
				|| locB.getBlock().getType() == Material.DOUBLE_PLANT
				|| locB.getBlock().getType() == Material.GRASS) {
			player.sendMessage("false");
			return false;
		}
		player.sendMessage("true");
		return true;
	}

}
