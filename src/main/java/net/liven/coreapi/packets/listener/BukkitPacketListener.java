package net.liven.coreapi.packets.listener;

import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.packets.incomming.IncomingPacketManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BukkitPacketListener implements PacketListener, PluginMessageListener {
    private final IncomingPacketManager incomingPacketManager;

    public BukkitPacketListener(IncomingPacketManager incomingPacketManager) {
        this.incomingPacketManager = incomingPacketManager;
    }

    @Override
    public void registerChannels() {
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel((Plugin) CoreAPIManager.getInstance().getPlugin(), "coreapi:bungee", this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel((Plugin) CoreAPIManager.getInstance().getPlugin(), "coreapi:bukkit", this);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel((Plugin) CoreAPIManager.getInstance().getPlugin(), "coreapi:bukkit");
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel((Plugin) CoreAPIManager.getInstance().getPlugin(), "coreapi:bungee");
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        System.out.println(s);
        System.out.println(player);
        System.out.println("Received packet");
    }
}
