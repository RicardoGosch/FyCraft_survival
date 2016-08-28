package net.generalcraft.plugins.terrenos.event;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.generalcraft.GeneralcraftAPI;
import net.generalcraft.plugins.terrenos.system.SysTerrenosComprar;
import net.generalcraft.plugins.terrenos.system.SysTerrenosVerBlocos;

public class EventTerrenosInventory implements Listener {

	private GeneralcraftAPI plugin;

	public EventTerrenosInventory(GeneralcraftAPI plugin) {
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
				new SysTerrenosVerBlocos(p, plugin, 10);

			if (e.getSlot() == 13)
				new SysTerrenosVerBlocos(p, plugin, 20);

			if (e.getSlot() == 15)
				new SysTerrenosVerBlocos(p, plugin, 30);

			p.playSound(p.getLocation(), Sound.CAT_HIT, 2, 2);

		} else if (e.isRightClick()) {
			if (e.getSlot() == 11)
				new SysTerrenosComprar(plugin, p, 10);

			if (e.getSlot() == 13)
				new SysTerrenosComprar(plugin, p, 20);

			if (e.getSlot() == 15)
				new SysTerrenosComprar(plugin, p, 30);
			p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 25, 25);
		}

		e.setCancelled(true);
		p.closeInventory();

	}
}
