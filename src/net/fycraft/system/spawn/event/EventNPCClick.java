package net.fycraft.system.spawn.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class EventNPCClick implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(NPCRightClickEvent e) {
		run(e);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(NPCLeftClickEvent e) {
		run(e);
	}

	private void run(NPCClickEvent e) {
		Player p = (Player) e.getClicker();
		NPC npc = e.getNPC();
		if (p.getWorld() != Bukkit.getWorld("world_spawn")) {
			return;
		}
		switch (npc.getFullName().toLowerCase()) {
		case "§cterrenos":
			p.teleport(new Location(Bukkit.getWorld("world_build"), 328, 97, 338));
			e.setCancelled(true);
			break;

		default:
			return;
		}

	}

}
