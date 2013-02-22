package fr.ethilvan.bukkit.travels.exception;

public class NoSuchPort extends TravelsException {

    private static final long serialVersionUID = 947068945844578142L;

    public NoSuchPort(String name) {
        super(name);
    }
}