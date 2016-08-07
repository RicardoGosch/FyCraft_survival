package net.generalcraft.subplugins.terrenos.system;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.generalcraft.GeneralcraftAPI;

public class SysComprar {
	private GeneralcraftAPI plugin;
	private Player player;
	private Integer length;
	private Location locP;
	private Location posA;
	private Location posB;
	private Location posC;
	private Location posD;
	private World world;
	private WorldGuardPlugin worldguard;
	FileConfiguration messages;

	public SysComprar(GeneralcraftAPI plugin, Player player, Integer length) {
		this.plugin = plugin;
		this.player = player;
		this.length = length;
		this.locP = player.getLocation();
		this.world = locP.getWorld();
		this.worldguard = (WorldGuardPlugin) player.getServer().getPluginManager().getPlugin("WorldGuard");
		this.messages = plugin.getMessages();
		run();
	}

	private void run() {
		// Ajeitando as posições
		posA = new Location(world, locP.getX() + (length / 2), locP.getY(), locP.getZ() + (length / 2));
		posB = new Location(world, locP.getX() - (length / 2), locP.getY(), locP.getZ() - (length / 2));
		posC = new Location(world, locP.getX() + length + 5, locP.getY(), locP.getZ() + length + 5);
		posD = new Location(world, locP.getX() - length - 5, locP.getY(), locP.getZ() - length - 5);

		// WorldGuard
		// worldguard.getRegionManager(world).
		BlockVector vecA = new BlockVector(posA.getX(), 1, posA.getZ());
		BlockVector vecB = new BlockVector(posB.getX(), world.getMaxHeight(), posB.getZ());
		ProtectedRegion regionV = new ProtectedCuboidRegion(player.getName(), vecA, vecB);

		// Servem para verificar se há terrenos próximos
		BlockVector vecC = new BlockVector(posC.getX(), 1, posC.getZ());
		BlockVector vecD = new BlockVector(posD.getX(), world.getMaxHeight(), posD.getZ());
		ProtectedRegion regionF = new ProtectedCuboidRegion(player.getName(), vecC, vecD);

		DefaultDomain owners = new DefaultDomain();
		owners.addPlayer(player.getName());
		regionV.setOwners(owners);

		ApplicableRegionSet aplicableRegion = worldguard.getRegionManager(world).getApplicableRegions(regionF);
		if (aplicableRegion.getRegions().size() != 0) {
			player.sendMessage("");
			player.sendMessage(messages.getString("TerrenoProximo"));
			return;
		}
		worldguard.getRegionManager(world).addRegion(regionV);
		player.sendMessage("");
		player.sendMessage(messages.getString("TerrenoComprado"));
		new SysVer(player, plugin, length);
	}

}
