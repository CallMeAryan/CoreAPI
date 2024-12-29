package net.liven.coreAPI.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class Utils {
    public static String translateColors(String input) {

        final Pattern hexPattern = Pattern.compile("&#" + "([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(input);
        StringBuffer buffer = new StringBuffer(input.length() + 4 * 8);

        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        input = matcher.appendTail(buffer).toString();
        input = ChatColor.translateAlternateColorCodes('&', input);
        return input;
    }


    //TRANSLATES COLORS A WHOLE LIST OF STRING
    public static List<String> translateColors(List<String> ls) {
        List<String> finalLs = new ArrayList<>();
        for (String str : ls) {
            finalLs.add(translateColors(str));
        }
        return finalLs;
    }
}
