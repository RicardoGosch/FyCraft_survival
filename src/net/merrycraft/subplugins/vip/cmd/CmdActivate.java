package net.merrycraft.subplugins.vip.cmd;

//net.merrycraft.subplugins.vip.api.pagseguro
import org.bukkit.plugin.Plugin;

import net.merrycraft.subplugins.vip.api.pagseguro.domain.AccountCredentials;
import net.merrycraft.subplugins.vip.api.pagseguro.domain.Credentials;
import net.merrycraft.subplugins.vip.api.pagseguro.domain.Transaction;
import net.merrycraft.subplugins.vip.api.pagseguro.exception.PagSeguroServiceException;
import net.merrycraft.subplugins.vip.api.pagseguro.service.TransactionSearchService;
import net.merrycraft.subplugins.vip.model.ModelVIP;

public class CmdActivate {
	private Transaction transation = null;
	private Credentials credentials;

	public boolean run(Plugin plugin, ModelVIP vip) throws PagSeguroServiceException {
		credentials = new AccountCredentials("ricardogosch@outlook.com", "6D0E785B172042739D2C541268F26B15");
		String key = vip.getKey();
		if (key.length() <= 10) { // Verificar se é uma key do pagseguro
			// Sistema plugin
		} else {
			// Sistema pagseguro
			plugin.getLogger().info("Aqui 01");
			try {
				transation = TransactionSearchService.searchByCode(credentials, key);
				plugin.getLogger().info("Aqui 02");
			} catch (PagSeguroServiceException e) {
				plugin.getLogger().info(e.getMessage());
				return false;
			}
			if (transation == null) {
				return false;
			}
			if (transation.getStatus().getValue() != 3 && transation.getStatus().getValue() != 4) {
				return false;
			}
			if (transation.getReference() == null) {
				return true;
			}
			switch (transation.getReference()) {
			case "prod_carl_30dias":

				break;
			case "prod_carl_eterno":

				break;
			default:
				break;
			}
			return true;
		}
		plugin.getLogger().info("Aqui 07");
		return false;
	}

}
