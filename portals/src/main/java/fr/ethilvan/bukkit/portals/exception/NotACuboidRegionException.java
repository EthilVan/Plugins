package fr.ethilvan.bukkit.portals.exception;

public class NotACuboidRegionException extends TravelException {
    private static final long serialVersionUID = -7001588715307350471L;

    public NotACuboidRegionException() {
        super("La selection n'est pas une selection cuboidale.");
    }
}
