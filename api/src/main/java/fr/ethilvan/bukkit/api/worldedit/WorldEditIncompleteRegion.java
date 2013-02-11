package fr.ethilvan.bukkit.api.worldedit;

import fr.ethilvan.bukkit.api.EthilVanException;

public class WorldEditIncompleteRegion extends EthilVanException {

    private static final long serialVersionUID = -6538432959877065863L;

    public WorldEditIncompleteRegion() {
        super("Incomplete region");
    }
}