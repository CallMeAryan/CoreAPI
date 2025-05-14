package net.liven.coreapi.packets.listener;

import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.packets.PacketManager;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class BungeePacketListener implements PacketListener, Listener {
    private final PacketManager manager;


    public BungeePacketListener(PacketManager packetManager) {
        this.manager = packetManager;
    }


    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if (e.getTag().equals("coreapi:bukkit")) {
            e.setCancelled(true);
        }
    }


    @Override
    public void registerChannels() {
        ((Plugin) CoreAPIManager.getInstance().getPlugin()).getProxy().registerChannel("coreapi:bungee");
        ((Plugin) CoreAPIManager.getInstance().getPlugin()).getProxy().registerChannel("coreapi:bukkit");
        ((Plugin) CoreAPIManager.getInstance().getPlugin()).getProxy().getPluginManager().registerListener( ((Plugin) CoreAPIManager.getInstance().getPlugin()), this);
    }
}
