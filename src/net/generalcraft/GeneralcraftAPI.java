package net.generalcraft;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.generalcraft.database.ConnectionDAO;
import net.generalcraft.subplugins.taxiteleport.cmd.CmdDelTeleport;
import net.generalcraft.subplugins.taxiteleport.cmd.CmdSetTeleport;
import net.generalcraft.subplugins.taxiteleport.cmd.CmdTeleport;
import net.generalcraft.subplugins.terrenos.cmd.CmdMain;
import net.generalcraft.subplugins.terrenos.event.EventInventory;
import net.generalcraft.subplugins.terrenos.event.EventMovePlayer;
import net.generalcraft.subplugins.terrenos.event.EventQuitPlayer;
import net.generalcraft.subplugins.vip.cmd.CmdVip;

public class GeneralcraftAPI extends JavaPlugin {
	FileConfiguration messages;
	FileConfiguration safechunk;
	File fileMessages, fileSafeChunk;

	@Override
	public void onEnable() {
		hasConnection();
		loadFiles();
		pluginEvents();
		pluginCommands();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	private void pluginCommands() {
		// Sistema de Homes (taxiteleport)
		getCommand("home").setExecutor(new CmdTeleport(this));
		getCommand("sethome").setExecutor(new CmdSetTeleport(this));
		getCommand("delhome").setExecutor(new CmdDelTeleport(this));
		
		// Sistema de VIP (vip)
		getCommand("vip").setExecutor(new CmdVip(this));

		// Sistema de terrenos
		getCommand("terreno").setExecutor(new CmdMain(this));
		getCommand("ground").setExecutor(new CmdMain(this));
		getCommand("land").setExecutor(new CmdMain(this));

	}

	private void pluginEvents() {
		// Sistema de terrenos
		Bukkit.getPluginManager().registerEvents(new EventMovePlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new EventQuitPlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new EventInventory(this), this);
	}

	private void hasConnection() {
		if (new ConnectionDAO().getConnection() == null) {
			ConsoleCommandSender console = Bukkit.getConsoleSender();
			console.sendMessage("");
			console.sendMessage("");
			console.sendMessage("");
			console.sendMessage("");
			console.sendMessage("");
			console.sendMessage("§4*-----------------------------*");
			console.sendMessage("§4|                             |");
			console.sendMessage("§4|        SEM CONEXAO AO       |");
			console.sendMessage("§4|        §cBANCO DE DADOS§4       |");
			console.sendMessage("§4|                             |");
			console.sendMessage("§4*-----------------------------*");
			console.sendMessage("§4|                             |");
			console.sendMessage("§4|    §e§n DESLIGANDO SERVIDOR §4    |");
			console.sendMessage("§4|                             |");
			console.sendMessage("§4*-----------------------------*");
			console.sendMessage("");
			console.sendMessage("");
			console.sendMessage("");
			console.sendMessage("");
			console.sendMessage("");
			getServer().shutdown();
		}

	}

	private void loadFiles() {
		// TODO: Carrega todos os arquivos de configurações e mensagens
		// e deixa disponível nas variáveis.
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}

		fileMessages = new File(getDataFolder(), "messages.yml");

		if (!fileMessages.exists())
			saveResource("messages.yml", false);
		messages = YamlConfiguration.loadConfiguration(fileMessages);

		fileSafeChunk = new File(getDataFolder(), "safechunk.yml");

		if (!fileSafeChunk.exists())
			saveResource("safechunk.yml", false);
		safechunk = YamlConfiguration.loadConfiguration(fileSafeChunk);
	}

	public FileConfiguration getSafeChunkConfig() {
		return safechunk;
	}

	public FileConfiguration getMessages() {
		return messages;
	}

	public void saveSafeChunkConfig() {
		try {
			safechunk.save(fileSafeChunk);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
