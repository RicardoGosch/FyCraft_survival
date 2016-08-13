package net.generalcraft.subplugins.economy.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.generalcraft.GeneralcraftAPI;
import net.generalcraft.subplugins.economy.system.SysEconomy;

public class CmdMoney implements CommandExecutor {
	private FileConfiguration messages;
	private String prefix;

	public CmdMoney(GeneralcraftAPI plugin) {
		this.messages = plugin.getMessages();
		this.prefix = messages.getString("PrefixEconomy") + " ";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (args.length) {
		case 0:
			// Vê o próprio money
			// /money

			if (!(sender instanceof Player)) {
				sender.sendMessage(messages.getString("ComandoSomentePlayer"));
				return true;
			}
			Player player = (Player) sender;
			if (SysEconomy.exists(player)) {
				sender.sendMessage(prefix
						+ messages.getString("EconomyVerMoney").replace("%valor%", "" + SysEconomy.getMoney(player)));
			}
			break;

		case 1:
			// Vê o money de outro player
			// /money player
			String playerS = args[0];
			if (SysEconomy.exists(args[0])) {
				sender.sendMessage(prefix
						+ messages.getString("EconomyVerMoney").replace("%valor%", "" + SysEconomy.getMoney(playerS)));
			} else {
				sender.sendMessage("");
				sender.sendMessage(prefix + messages.getString("PlayerNaoEncontrado"));
				sender.sendMessage("");
			}

			break;
		case 3:
			// Modifica o money de outro player
			// /money set/add/remove

			break;

		default:
			// Mensagem de padrões

			break;
		}

		return true;
	}

}
