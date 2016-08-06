package net.merrycraft.subplugins.terrenos.cmd;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.merrycraft.MerrycraftAPI;

public class CmdVer {

	private Player player;
	private Location locP;
	private Integer length;
	private World world;

	Integer posX;
	Integer posY;
	Integer posZ;

	Location locA;
	Location locB;
	Location locC;

	public CmdVer(Player player, MerrycraftAPI plugin, Integer length) {
		this.player = player;
		this.length = length;
		this.locP = player.getLocation();
		this.world = locP.getWorld();
		run();
	}

	@SuppressWarnings("deprecation")
	private void run() {
		if (length % 2 > 0)
			return;
		posX = locP.getBlockX() - (length / 2);
		posY = locP.getBlockY();
		posZ = locP.getBlockZ() - (length / 2);
		for (int a = 0; a < length; a++) {
			posX++;
			locA = new Location(world, posX, posY, posZ);
			locB = new Location(world, posX, posY - 1, posZ);

			// if (locA.getBlock().getType() != Material.AIR) {
			while ((locA.getBlock().getType() != Material.AIR) && posY < world.getMaxHeight()) {
				posY++;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
				locC = new Location(world, posX, posY + 10, posZ);
			}
			while ((locB.getBlock().getType() == Material.AIR || locB.getBlock().getType() == Material.LONG_GRASS)
					&& posY > 0) {
				posY--;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
				locC = new Location(world, posX, posY + 10, posZ);
			}
			player.playEffect(locA, Effect.EXPLOSION, 5);
			player.sendBlockChange(locA, Material.COBBLE_WALL, (byte) 1);

		}
		for (int a = 0; a < length; a++) {
			posZ++;
			locA = new Location(world, posX, posY, posZ);
			locB = new Location(world, posX, posY - 1, posZ);

			// if (locA.getBlock().getType() != Material.AIR) {
			while ((locA.getBlock().getType() != Material.AIR) && posY < world.getMaxHeight()) {
				posY++;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
				locC = new Location(world, posX, posY + 10, posZ);
			}
			while ((locB.getBlock().getType() == Material.AIR || locB.getBlock().getType() == Material.LONG_GRASS)
					&& posY > 0) {
				posY--;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
				locC = new Location(world, posX, posY + 10, posZ);
			}
			player.playEffect(locA, Effect.EXPLOSION, 5);
			player.sendBlockChange(locA, Material.COBBLE_WALL, (byte) 1);

		}
		for (int a = 0; a < length; a++) {
			posX--;
			locA = new Location(world, posX, posY, posZ);
			locB = new Location(world, posX, posY - 1, posZ);

			// if (locA.getBlock().getType() != Material.AIR) {
			while ((locA.getBlock().getType() != Material.AIR) && posY < world.getMaxHeight()) {
				posY++;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
				locC = new Location(world, posX, posY + 10, posZ);
			}
			while ((locB.getBlock().getType() == Material.AIR || locB.getBlock().getType() == Material.LONG_GRASS)
					&& posY > 0) {
				posY--;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
				locC = new Location(world, posX, posY + 10, posZ);
			}
			player.playEffect(locA, Effect.EXPLOSION, 5);
			player.sendBlockChange(locA, Material.COBBLE_WALL, (byte) 1);

		}
		for (int a = 0; a < length; a++) {
			posZ--;
			locA = new Location(world, posX, posY, posZ);
			locB = new Location(world, posX, posY - 1, posZ);

			// if (locA.getBlock().getType() != Material.AIR) {
			while ((locA.getBlock().getType() != Material.AIR) && posY < world.getMaxHeight()) {
				posY++;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
				locC = new Location(world, posX, posY + 10, posZ);
			}
			while ((locB.getBlock().getType() == Material.AIR || locB.getBlock().getType() == Material.LONG_GRASS)
					&& posY > 0) {
				posY--;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
				locC = new Location(world, posX, posY + 10, posZ);
			}
			player.playEffect(locA, Effect.EXPLOSION, 5);
			player.sendBlockChange(locA, Material.COBBLE_WALL, (byte) 1);

		}

		player.playEffect(locP, Effect.EXPLOSION, 5);

	}

}
