package net.fycraft.plugins.ground.system;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.fycraft.GeneralcraftAPI;

public class SysGroundVerify {
	private Player player;
	private Integer length;
	private Location locP;
	private Location posA;
	private Location posB;
	private World world;
	private WorldGuardPlugin worldguard;

	public SysGroundVerify(GeneralcraftAPI plugin, Player player, Integer length) {
		this.player = player;
		this.length = length;
		this.locP = player.getLocation();
		this.world = locP.getWorld();
		this.worldguard = (WorldGuardPlugin) player.getServer().getPluginManager().getPlugin("WorldGuard");
	}

	public boolean run() {
		// Servem para verificar se há terrenos próximos
		posA = new Location(world, locP.getX() + length + 5, locP.getY(), locP.getZ() + length + 5);
		posB = new Location(world, locP.getX() - length - 5, locP.getY(), locP.getZ() - length - 5);
		BlockVector vecC = new BlockVector(posA.getX(), 1, posA.getZ());
		BlockVector vecD = new BlockVector(posB.getX(), world.getMaxHeight(), posB.getZ());
		ProtectedRegion regionF = new ProtectedCuboidRegion(player.getName(), vecC, vecD);
		ApplicableRegionSet aplicableRegion = worldguard.getRegionManager(world).getApplicableRegions(regionF);
		if (aplicableRegion.getRegions().size() != 0) {
			return false;
		}

		return true;
	}

}
