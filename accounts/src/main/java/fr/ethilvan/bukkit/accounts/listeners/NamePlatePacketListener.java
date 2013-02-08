package fr.ethilvan.bukkit.accounts.listeners;

import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Account;

public class NamePlatePacketListener extends PacketAdapter {

    public NamePlatePacketListener(Plugin plugin) {
        super(plugin, ConnectionSide.SERVER_SIDE, 0x14);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        StructureModifier<String> strings = event.getPacket().getStrings();

        String name = strings.read(0);
        Account account = EthilVan.getAccounts().getByMinecraftName(name);

        String coloredName = account.getColoredNamePlate();
        if (coloredName.length() > 16) {
            coloredName = coloredName.substring(0, 16);
        }

        strings.write(0, coloredName);
    }
}
