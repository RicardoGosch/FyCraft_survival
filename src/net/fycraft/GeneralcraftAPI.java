package net.fycraft;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.fycraft.database.ConnectionDAO;
import net.fycraft.plugins.balance.cmd.CmdBalance;
import net.fycraft.plugins.balance.event.EventRegisterPlayer;
import net.fycraft.plugins.ground.cmd.CmdGround;
import net.fycraft.plugins.ground.event.EventGroundInventory;
import net.fycraft.plugins.ground.event.EventGroundMovePlayer;
import net.fycraft.plugins.ground.event.EventGroundQuitPlayer;
import net.fycraft.plugins.hometeleport.cmd.CmdDelHome;
import net.fycraft.plugins.hometeleport.cmd.CmdHomeTeleport;
import net.fycraft.plugins.hometeleport.cmd.CmdSetHome;
import net.fycraft.plugins.vip.cmd.CmdVip;
import net.fycraft.system.spawn.event.EventNPCClick;

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
		getCommand("home").setExecutor(new CmdHomeTeleport(this));
		getCommand("sethome").setExecutor(new CmdSetHome(this));
		getCommand("delhome").setExecutor(new CmdDelHome(this));

		// Sistema de VIP (vip)
		getCommand("vip").setExecutor(new CmdVip(this));

		// Sistema de terrenos
		getCommand("terreno").setExecutor(new CmdGround(this));
		getCommand("ground").setExecutor(new CmdGround(this));
		getCommand("land").setExecutor(new CmdGround(this));

		// Financeiro
		getCommand("money").setExecutor(new CmdBalance(this));
		getCommand("coins").setExecutor(new CmdBalance(this));
		getCommand("coin").setExecutor(new CmdBalance(this));
		getCommand("dinheiro").setExecutor(new CmdBalance(this));

	}

	private void pluginEvents() {
		// Sistema de terrenos
		Bukkit.getPluginManager().registerEvents(new EventGroundMovePlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new EventGroundQuitPlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new EventGroundInventory(this), this);

		// Spawn npc
		Bukkit.getPluginManager().registerEvents(new EventNPCClick(), this);

		// Cadastro do player : Balance

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
