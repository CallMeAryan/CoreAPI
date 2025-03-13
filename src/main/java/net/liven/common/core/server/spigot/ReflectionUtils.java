package net.liven.common.core.server.spigot;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void addCommand(Plugin plugin, BukkitCommand command){
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(plugin.getName(), command);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
