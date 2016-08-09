package net.generalcraft.subplugins.terrenos.system;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.generalcraft.GeneralcraftAPI;

public class SysSet {
	private Player player;
	private Location locP;
	private Integer length;
	private World world;

	private Integer posX;
	private Integer posY;
	private Integer posZ;

	private Location locA;
	private Location locB;
	private Location locC;

	public SysSet(Player player, GeneralcraftAPI plugin, Integer length) {
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
		locC = new Location(world, locP.getX(), locP.getY() - 1, locP.getZ());
		locC.getBlock().setType(Material.REDSTONE_LAMP_ON);
		for (int a = 0; a < length; a++) {
			posX++;
			locA = new Location(world, posX, posY, posZ);
			locB = new Location(world, posX, posY - 1, posZ);

			while ((locA.getBlock().getType() != Material.AIR) && posY < world.getMaxHeight()) {
				if (locB.getBlock().getType() == Material.AIR) {
					player.sendBlockChange(locA, Material.COBBLE_WALL, (byte) 1);
				}
				posY++;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
			}
			while ((locB.getBlock().getType() == Material.AIR || locB.getBlock().getType() == Material.LONG_GRASS
					|| locB.getBlock().getType() == Material.YELLOW_FLOWER
					|| locB.getBlock().getType() == Material.DOUBLE_PLANT) && posY > 0) {
				posY--;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);

			}
			player.playEffect(locA, Effect.EXPLOSION, 5);
			locA.getBlock().setType(Material.COBBLE_WALL);

		}
		for (int a = 0; a < length; a++) {
			posZ++;
			locA = new Location(world, posX, posY, posZ);
			locB = new Location(world, posX, posY - 1, posZ);

			while ((locA.getBlock().getType() != Material.AIR) && posY < world.getMaxHeight()) {
				if (locB.getBlock().getType() == Material.AIR) {
					player.sendBlockChange(locA, Material.COBBLE_WALL, (byte) 1);
				}
				posY++;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
			}
			while ((locB.getBlock().getType() == Material.AIR || locB.getBlock().getType() == Material.LONG_GRASS
					|| locB.getBlock().getType() == Material.YELLOW_FLOWER
					|| locB.getBlock().getType() == Material.DOUBLE_PLANT) && posY > 0) {
				posY--;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);

			}
			player.playEffect(locA, Effect.EXPLOSION, 5);
			locA.getBlock().setType(Material.COBBLE_WALL);

		}
		for (int a = 0; a < length; a++) {
			posX--;
			locA = new Location(world, posX, posY, posZ);
			locB = new Location(world, posX, posY - 1, posZ);

			while ((locA.getBlock().getType() != Material.AIR) && posY < world.getMaxHeight()) {
				if (locB.getBlock().getType() == Material.AIR) {
					player.sendBlockChange(locA, Material.COBBLE_WALL, (byte) 1);
				}
				posY++;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
			}
			while ((locB.getBlock().getType() == Material.AIR || locB.getBlock().getType() == Material.LONG_GRASS
					|| locB.getBlock().getType() == Material.YELLOW_FLOWER
					|| locB.getBlock().getType() == Material.DOUBLE_PLANT) && posY > 0) {
				posY--;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);

			}
			player.playEffect(locA, Effect.EXPLOSION, 5);
			locA.getBlock().setType(Material.COBBLE_WALL);

		}
		for (int a = 0; a < length; a++) {
			posZ--;
			locA = new Location(world, posX, posY, posZ);
			locB = new Location(world, posX, posY - 1, posZ);

			while ((locA.getBlock().getType() != Material.AIR) && posY < world.getMaxHeight()) {
				if (locB.getBlock().getType() == Material.AIR) {
					player.sendBlockChange(locA, Material.COBBLE_WALL, (byte) 1);
				}
				posY++;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);
			}
			while ((locB.getBlock().getType() == Material.AIR || locB.getBlock().getType() == Material.LONG_GRASS
					|| locB.getBlock().getType() == Material.YELLOW_FLOWER
					|| locB.getBlock().getType() == Material.DOUBLE_PLANT) && posY > 0) {
				posY--;
				locA = new Location(world, posX, posY, posZ);
				locB = new Location(world, posX, posY - 1, posZ);

			}
			player.playEffect(locA, Effect.EXPLOSION, 5);
			locA.getBlock().setType(Material.COBBLE_WALL);

		}

		player.playEffect(locP, Effect.EXPLOSION, 5);

	}
}
