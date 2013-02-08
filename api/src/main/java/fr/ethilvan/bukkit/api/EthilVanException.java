package fr.ethilvan.bukkit.api;

import fr.aumgn.bukkitutils.command.exception.CommandException;

public class EthilVanException extends RuntimeException
        implements CommandException {

    private static final long serialVersionUID = 7384622239682439697L;

    public EthilVanException(String message) {
        super(message);
    }

    public EthilVanException(Throwable throwable) {
        super(throwable);
    }

    public EthilVanException(String message, Throwable throwable) {
        super(message, throwable);
    }
}