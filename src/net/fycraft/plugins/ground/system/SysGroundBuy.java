package net.fycraft.plugins.ground.system;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.fycraft.FyCraft;
import net.fycraft.plugins.balance.system.Balance;

public class SysGroundBuy {
	private FyCraft plugin;
	private Player player;
	private Integer length;
	private Location locP;
	private Location posA;
	private Location posB;
	private World world;
	private WorldGuardPlugin worldguard;
	private FileConfiguration messages;
	private String prefix;

	public SysGroundBuy(FyCraft plugin, Player player, Integer length) {
		this.plugin = plugin;
		this.player = player;
		this.length = length;
		this.locP = player.getLocation();
		this.world = locP.getWorld();
		this.worldguard = (WorldGuardPlugin) player.getServer().getPluginManager().getPlugin("WorldGuard");
		this.messages = plugin.getMessages();
		this.prefix = messages.getString("PrefixTerreno") + " ";
		run();
	}

	private void run() {
		if (length <= 10) {
			if (!Balance.has(100.0, player)) {
				player.sendMessage(prefix + messages.getString("CoinInsuficiente"));
				return;
			} else {
				
			}
			
		} else if (length <= 20) {
			if (!Balance.has(500.0, player)) {
				player.sendMessage(prefix + messages.getString("CoinInsuficiente"));
				return;
			}
		} else {
			if (!Balance.has(1000.0, player)) {
				player.sendMessage(prefix + messages.getString("CoinInsuficiente"));
				return;
			}
		}

		// Ajeitando as posições
		posA = new Location(world, locP.getX() + (length / 2), locP.getY(), locP.getZ() + (length / 2));
		posB = new Location(world, locP.getX() - (length / 2), locP.getY(), locP.getZ() - (length / 2));

		// WorldGuard
		// worldguard.getRegionManager(world).
		BlockVector vecA = new BlockVector(posA.getX(), 1, posA.getZ());
		BlockVector vecB = new BlockVector(posB.getX(), world.getMaxHeight(), posB.getZ());
		ProtectedRegion regionV = new ProtectedCuboidRegion(player.getName(), vecA, vecB);
		if (!new SysGroundVerify(plugin, player, length).run()) {
			player.sendMessage(prefix + messages.getString("TerrenoProximo"));
			return;
		}
		DefaultDomain owners = new DefaultDomain();
		owners.addPlayer(player.getName());
		regionV.setOwners(owners);

		new SysGroundSetBlock(player, plugin, length);

		worldguard.getRegionManager(world).addRegion(regionV);
		player.sendMessage("");
		player.sendMessage(prefix + messages.getString("TerrenoComprado"));
		player.sendMessage("");
	}

}
