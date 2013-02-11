package fr.ethilvan.bukkit.portals.exception;

public class NoSuchPortal extends TravelException {
    private static final long serialVersionUID = 5295228151441877863L;

    public NoSuchPortal(String name) {
        super("Aucun portail defini avec ce nom (" + name + ".");
    }
}
