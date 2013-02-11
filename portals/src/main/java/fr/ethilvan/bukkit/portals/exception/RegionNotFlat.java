package fr.ethilvan.bukkit.portals.exception;

public class RegionNotFlat extends TravelException {
    private static final long serialVersionUID = 5170379472839619821L;

    public RegionNotFlat() {
        super("La region n'est pas plane.");
    }

}
