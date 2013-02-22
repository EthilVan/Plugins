package fr.ethilvan.bukkit.travels.exception;

import fr.aumgn.bukkitutils.command.exception.CommandException;

public class TravelsException extends RuntimeException implements CommandException {

    private static final long serialVersionUID = 997691204261242923L;

    public TravelsException(String msg) {
        super(msg);
    }

    public TravelsException(Throwable thr) {
        super(thr);
    }
}