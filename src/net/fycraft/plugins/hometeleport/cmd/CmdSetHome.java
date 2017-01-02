package net.fycraft.plugins.hometeleport.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.fycraft.FyCraft;
import net.fycraft.plugins.hometeleport.controller.SysAssistant;
import net.fycraft.plugins.hometeleport.controller.ControllerHome;
import net.fycraft.plugins.hometeleport.model.ModelHome;

public class CmdSetHome implements CommandExecutor {
	// ****************
	// * Atributes
	// ****************
	FyCraft plugin;
	FileConfiguration messages;
	String prefix;

	public CmdSetHome(FyCraft plugin) {
		this.plugin = plugin;
		this.messages = plugin.getMessages();
		this.prefix = messages.getString("PrefixHome") + " ";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*
		 * 
		 * TODO Comando respons�vel pela inclus�o de uma nova HOME
		 * 
		 * USO: /<command> [name]
		 * 
		 * <command> = [sethome]
		 * 
		 */

		// Verifica se sender � PLAYER
		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + messages.getString("ComandoSomentePlayer"));
			return true;
		}

		// Verifica se o usu�rio usou algum argumento e informa como deve usar
		if (args.length != 1) {
			sender.sendMessage(prefix + messages.getString("UsoSetHome"));
			return true;
		}
		Player player = (Player) sender;

		String name = args[0];

		// Verifica se os caracteres do argumento s�o v�lidos
		if (!SysAssistant.verifyCaracteres(name)) {
			sender.sendMessage(ChatColor.GOLD + "[TAXI] " + ChatColor.RESET + "Caracteres inv�lidos!");
			return false;

		}

		// Verifica se o limite de teleport do usu�rio excedeu
		if (!SysAssistant.hasPermission(player, ControllerHome.selectAll(player.getName()).size())) {
			sender.sendMessage(prefix + messages.getString("LimiteHome"));
			return true;
		}

		// Caso o player tiver uma home com este nome, ser� deletada
		ControllerHome.remove(player.getName(), name);

		// Criamos o objeto
		ModelHome home = new ModelHome(player, name);

		// Setamos a home e verificamos se tudo deu certo
		if (ControllerHome.insert(home)) {
			// Deu tudo certo
			player.sendMessage(prefix + messages.getString("HomeCriada").replace("%home%", name));

		} else {
			// Deu algo errado
			player.sendMessage(prefix + messages.getString("HomeNaoCriada").replace("%home%", name));

		}
		return true;
	}
}
