package net.fycraft.plugins.ground.event;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.fycraft.FyCraft;
import net.fycraft.plugins.ground.system.SysGroundBuy;
import net.fycraft.plugins.ground.system.SysGroundSeeBlock;

public class EventGroundInventory implements Listener {

	private FyCraft plugin;

	public EventGroundInventory(FyCraft plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		if (!inv.getTitle().equalsIgnoreCase("Terreno")) {
			return;
		}
		if (inv != e.getClickedInventory()) {
			e.setCancelled(true);
			return;
		}
		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem().getType() == Material.AIR) {
			return;
		}
		if (e.isShiftClick()) {
			e.setCancelled(true);
			return;
		}
		if (e.isLeftClick()) {
			if (e.getSlot() == 11)
				new SysGroundSeeBlock(p, plugin, 10);

			if (e.getSlot() == 13)
				new SysGroundSeeBlock(p, plugin, 20);

			if (e.getSlot() == 15)
				new SysGroundSeeBlock(p, plugin, 30);

			p.playSound(p.getLocation(), Sound.CAT_HIT, 2, 2);

		} else if (e.isRightClick()) {
			if (e.getSlot() == 11)
				new SysGroundBuy(plugin, p, 10);

			if (e.getSlot() == 13)
				new SysGroundBuy(plugin, p, 20);

			if (e.getSlot() == 15)
				new SysGroundBuy(plugin, p, 30);
			p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 25, 25);
		}

		e.setCancelled(true);
		p.closeInventory();

	}
}
