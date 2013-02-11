package fr.ethilvan.bukkit.portals.exception;

public class AreaNotInitialized extends TravelException {
    private static final long serialVersionUID = -7251022770349976008L;

    public AreaNotInitialized() {
        super("Area has not been initialized");
    }

}
