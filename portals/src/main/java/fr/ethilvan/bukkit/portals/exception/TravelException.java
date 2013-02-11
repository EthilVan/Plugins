package fr.ethilvan.bukkit.portals.exception;

import fr.aumgn.bukkitutils.command.exception.CommandException;

public class TravelException extends RuntimeException implements CommandException {
    private static final long serialVersionUID = -6716029981691090073L;

    public TravelException(String msg) {
        super(msg);
    }

    public TravelException(Throwable thr) {
        super(thr);
    }
}
