package net.generalcraft;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.generalcraft.database.ConnectionDAO;
import net.generalcraft.plugins.economy.cmd.CmdEconomy;
import net.generalcraft.plugins.economy.event.EventRegisterPlayer;
import net.generalcraft.plugins.taxiteleport.cmd.CmdDelTeleport;
import net.generalcraft.plugins.taxiteleport.cmd.CmdSetTeleport;
import net.generalcraft.plugins.taxiteleport.cmd.CmdTeleport;
import net.generalcraft.plugins.terrenos.cmd.CmdTerrenos;
import net.generalcraft.plugins.terrenos.event.EventTerrenosInventory;
import net.generalcraft.plugins.terrenos.event.EventTerrenosMovePlayer;
import net.generalcraft.plugins.terrenos.event.EventTerrenosQuitPlayer;
import net.generalcraft.plugins.vip.cmd.CmdVip;

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
		getCommand("terreno").setExecutor(new CmdTerrenos(this));
		getCommand("ground").setExecutor(new CmdTerrenos(this));
		getCommand("land").setExecutor(new CmdTerrenos(this));

		// Financeiro
		getCommand("money").setExecutor(new CmdEconomy(this));
		getCommand("coins").setExecutor(new CmdEconomy(this));
		getCommand("coin").setExecutor(new CmdEconomy(this));
		getCommand("dinheiro").setExecutor(new CmdEconomy(this));

	}

	private void pluginEvents() {
		// Sistema de terrenos
		Bukkit.getPluginManager().registerEvents(new EventTerrenosMovePlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new EventTerrenosQuitPlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new EventTerrenosInventory(this), this);

		// Spawn npc

		
		// Cadastro do player : Economy

		Bukkit.getPluginManager().registerEvents(new EventRegisterPlayer(), this);
	}

	private void hasConnection() {
		if (ConnectionDAO.getConnection() == null) {
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
