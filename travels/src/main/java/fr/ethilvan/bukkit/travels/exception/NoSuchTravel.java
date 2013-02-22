package fr.ethilvan.bukkit.travels.exception;

public class NoSuchTravel extends TravelsException {

    private static final long serialVersionUID = -5545473846458496262L;

    public NoSuchTravel(String name) {
        super(name);
    }
}