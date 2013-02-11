package fr.ethilvan.bukkit.portals.exception;

public class RefPointNotInside extends TravelException {
    private static final long serialVersionUID = -5234620021649281862L;

    public RefPointNotInside() {
        super("Reference point is not inside the area");
    }

}
