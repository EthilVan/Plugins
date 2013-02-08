package fr.ethilvan.bukkit.api.worldedit;

import fr.ethilvan.bukkit.api.EthilVanException;

public class WorldEditMissing extends EthilVanException {

    private static final long serialVersionUID = 9098437835095642160L;

    public WorldEditMissing() {
        super("WorldEdit is missing");
    }
}