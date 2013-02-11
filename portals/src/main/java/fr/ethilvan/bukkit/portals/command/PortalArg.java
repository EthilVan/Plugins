package fr.ethilvan.bukkit.portals.command;

import fr.aumgn.bukkitutils.command.arg.impl.AbstractCommandArg;
import fr.ethilvan.bukkit.portals.exception.NoSuchPortal;
import fr.ethilvan.bukkit.portals.portal.Portal;
import fr.ethilvan.bukkit.portals.portal.Portals;

public class PortalArg extends AbstractCommandArg<Portal> {

    private final Portals portals;

    public PortalArg(Portals portals, String string) {
        super(string);
        this.portals = portals;
    }

    @Override
    public Portal value() {
        Portal portal = portals.get(string);
        if (portal == null) {
            throw new NoSuchPortal(string);
        }

        return portal;
    }
}
