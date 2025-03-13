package net.liven.coreapi.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class SpigotUtils {


    public static ItemStack getHead(String texture) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        if (texture.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        try {
            Class<?> gameProfileClass = Class.forName("com.mojang.authlib.GameProfile");
            Object gameProfile = gameProfileClass.getConstructor(UUID.class, String.class)
                    .newInstance(UUID.randomUUID(), null);

            Class<?> propertyClass = Class.forName("com.mojang.authlib.properties.Property");
            Object property = propertyClass.getConstructor(String.class, String.class)
                    .newInstance("textures", texture);

            Class<?> propertyMapClass = Class.forName("com.mojang.authlib.properties.PropertyMap");
            Method putMethod = propertyMapClass.getMethod("put", Object.class, Object.class);
            Field propertiesField = gameProfileClass.getDeclaredField("properties");
            propertiesField.setAccessible(true);
            Object propertyMap = propertiesField.get(gameProfile);
            putMethod.invoke(propertyMap, "textures", property);

            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, gameProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        head.setItemMeta(headMeta);
        return head;
    }

    public static void clearOldPermissions(Player player, Plugin plugin) {
        Set<PermissionAttachmentInfo> attachments = player.getEffectivePermissions();
        for (PermissionAttachmentInfo permInfo : attachments) {
            if (permInfo.getAttachment() != null && permInfo.getAttachment().getPlugin().equals(plugin)) {
                PermissionAttachment attachment = permInfo.getAttachment();
                if (player.getEffectivePermissions().contains(permInfo)) {
                    player.removeAttachment(attachment);
                }
            }
        }
    }

    public static ItemStack getItem(Material material, String name, short data) {
        ItemStack itemStack = new ItemStack(material, 1, data);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static ItemStack getItem(int material, String name, short data) {
        ItemStack itemStack = new ItemStack(material, 1, data);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
