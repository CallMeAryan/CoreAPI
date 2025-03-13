package net.liven.coreapi.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConvertUtil {

    public static List<String> uuidsToStrings(List<UUID> uuids) {
        List<String> strings = new ArrayList<>();
        for (UUID uuid : uuids) {
            strings.add(uuid.toString());
        }
        return strings;
    }

    public static List<String> playersToNames(List<ProxiedPlayer> members) {
        List<String> names = new ArrayList<>();
        for(ProxiedPlayer player : members){
            names.add(player.getName());
        }
        return names;
    }

    public static String formatOnlineList(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(ChatColor.GREEN + string + " &a&l●").append("&7 ");
        }
        return ChatUtils.translateColors(stringBuilder.toString());
    }

    public static String formatGuildListMessage(List<String> names, List<ProxiedPlayer> onlineMembers) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> onlineMemberNames = playersToNames(onlineMembers);

        for (String name : names) {
            if(onlineMemberNames.contains(name)) {
                stringBuilder.append(ChatColor.GREEN + name + " &a&l●").append("&7 ");
            } else {
                stringBuilder.append(ChatColor.GRAY + name + " &7&l●").append("&7 ");
            }
        }

        return ChatUtils.translateColors(stringBuilder.toString());

    }

    public static int convertToSeconds(String input) {
        int totalSeconds = 0;
        String[] parts = input.split(",\\s*");

        for (String part : parts) {
            char unit = part.charAt(part.length() - 1);
            int value = Integer.parseInt(part.substring(0, part.length() - 1));

            switch (unit) {
                case 's': // seconds
                    totalSeconds += value;
                    break;
                case 'm': // minutes
                    totalSeconds += value * 60;
                    break;
                case 'h': // hours
                    totalSeconds += value * 3600;
                    break;
                case 'd': // days
                    totalSeconds += value * 86400;
                    break;
                case 'w': // weeks
                    totalSeconds += value * 604800;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid time unit: " + unit);
            }
        }

        return totalSeconds;
    }

    public static double secondsToYears(long seconds) {
        return seconds / (60.0 * 60 * 24 * 365);
    }

}
