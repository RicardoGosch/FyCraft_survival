package net.fycraft.plugins.balance.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.fycraft.FyCraft;
import net.fycraft.plugins.balance.system.Balance;

public class CmdBalance implements CommandExecutor {
	private FileConfiguration messages;
	private String prefix;

	public CmdBalance(FyCraft plugin) {
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
			if (Balance.exists(player)) {
				sender.sendMessage(
						prefix + messages.getString("EconomyVerMoney").replace("%valor%", "" + Balance.get(player)));
			}
			break;

		case 1:
			// Vê o money de outro player
			// /money player
			String playerS = args[0];
			if (Balance.exists(args[0])) {
				sender.sendMessage(
						prefix + messages.getString("EconomyVerMoney").replace("%valor%", "" + Balance.get(playerS)));
			} else {
				sender.sendMessage("");
				sender.sendMessage(prefix + messages.getString("PlayerNaoEncontrado"));
				sender.sendMessage("");
			}

			break;
		case 3:
			if (!sender.isOp()) {
				sender.sendMessage(messages.getString("SemPermissaoComando"));
				return true;
			}
			// Modifica o money de outro player
			// /money set/add/remover <player> [quantia]
			if (args[0].equalsIgnoreCase("set")) {
				if (!Balance.exists(args[1])) {
					// Mensagem: Player não existe
					sender.sendMessage("");
					sender.sendMessage(prefix + messages.getString("PlayerNaoEncontrado"));
					sender.sendMessage("");
					return true;
				}
				Double amount;
				try {
					amount = Double.parseDouble(args[2]);
				} catch (NumberFormatException e) {
					// Mensagem de formato de numero errado
					return true;
				}

				Balance.set(amount, args[1]);

				String msg = messages.getString("ValorModificado");
				msg = msg.replace("%valor%", Balance.get(args[1]) + "");
				msg = msg.replace("%player%", args[1]);
				sender.sendMessage(prefix + msg);

			} else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("adicionar")) {
				if (!Balance.exists(args[1])) {
					// Mensagem: Player não existe
					sender.sendMessage("");
					sender.sendMessage(prefix + messages.getString("PlayerNaoEncontrado"));
					sender.sendMessage("");
					return true;
				}
				Double amount;
				try {
					amount = Double.parseDouble(args[2]);
				} catch (NumberFormatException e) {
					// Mensagem de formato de numero errado
					return true;
				}
				Balance.add(amount, args[1]);
				String msg = messages.getString("ValorModificado");
				msg = msg.replace("%valor%", Balance.get(args[1]) + "");
				msg = msg.replace("%player%", args[1]);
				sender.sendMessage(prefix + msg);
				// Mensagem de coin setado com sucesso!
				// Mensagem de coin recebido!

			} else if (args[0].equalsIgnoreCase("remover")) {
				if (!Balance.exists(args[1])) {
					// Mensagem: Player não existe
					sender.sendMessage("");
					sender.sendMessage(prefix + messages.getString("PlayerNaoEncontrado"));
					sender.sendMessage("");
					return true;
				}
				Double amount;
				try {
					amount = Double.parseDouble(args[2]);
				} catch (NumberFormatException e) {
					// Mensagem de formato de numero errado
					return true;
				}
				Balance.remove(amount, args[1]);

				String msg = messages.getString("ValorModificado");
				msg = msg.replace("%valor%", Balance.get(args[1]) + "");
				msg = msg.replace("%player%", args[1]);
				sender.sendMessage(prefix + msg);

			} else {

			}

			break;

		default:
			// Mensagem de padrões

			break;
		}

		return true;
	}

}
