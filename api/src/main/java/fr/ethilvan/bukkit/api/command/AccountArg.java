package fr.ethilvan.bukkit.api.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.arg.impl.AbstractSenderMatchingArg;
import fr.aumgn.bukkitutils.command.exception.CommandUsageError;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.EthilVanException;
import fr.ethilvan.bukkit.api.accounts.Account;

public class AccountArg extends AbstractSenderMatchingArg<Account> {

    public static class AccountNotFound extends EthilVanException {

        private static final long serialVersionUID = 336378996448784961L;

        public AccountNotFound(String token) {
            super("Joueur non trouvé pour " + token + ".");
        }
    }

    public static class MoreThanOneAccountFound extends EthilVanException {

        private static final long serialVersionUID = 4378394249212483132L;

        public MoreThanOneAccountFound(String token) {
            super("Plus d'un joueur trouvé pour " + token + ".");
        }
    }

    public AccountArg(String string) {
        super(string);
    }

    @Override
    public Account value() {
        List<Account> accounts = match();
        if (accounts.size() > 1) {
            throw new MoreThanOneAccountFound(string);
        } else {
            
        }

        return accounts.get(0);
    }

    @Override
    public List<Account> match() {
        List<Account> accounts =
                EthilVan.getAccounts().getByPartialName(string);
        if (accounts.size() == 0) {
            throw new AccountNotFound(string);
        }

        return accounts;
    }

    @Override
    protected Account defaultFor(CommandSender sender) {
        if (!(sender instanceof Player)) {
            throw new CommandUsageError("Précisez un joueur.");
        }

        return EthilVan.getAccounts().get((Player) sender);
    }

    @Override
    protected String missingPermOtherMessage(String perm) {
        return "Permission " + perm + " manquante.";
    }
}
