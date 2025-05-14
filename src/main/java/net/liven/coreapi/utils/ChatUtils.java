package net.liven.coreapi.utils;


import net.md_5.bungee.api.ChatColor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;


public class ChatUtils {
    private static final String COLORS = "0123456789abcdef";
    private static final String FORMATTING = "klmnor";
    private static final Random RANDOM = new Random();


    public static String translateColors(String input, boolean hex) {
       if(hex) {
           final Pattern hexPattern = Pattern.compile("&#" + "([A-Fa-f0-9]{6})");

           Matcher matcher = hexPattern.matcher(input);
           StringBuilder buffer = new StringBuilder(input.length() + 4 * 8);

           while (matcher.find()) {
               String group = matcher.group(1);
               matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                       + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                       + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                       + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
               );
           }
           input = matcher.appendTail(buffer).toString();

       }
        input = ChatColor.translateAlternateColorCodes('&', input);
        return input;
    }

    public static String translateColors(String input) {
        return translateColors(input, false);
    }


    public static boolean checkRegex(String target, String regex) {
        // Remove spaces and make it case-insensitive
        String cleanedTarget = target.replaceAll("\\s+", "").toLowerCase();
        String cleanedRegex = regex.replaceAll("\\s+", "").toLowerCase();

        // Compile the pattern
        Pattern pattern = Pattern.compile(cleanedRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cleanedTarget);

        return matcher.find();
    }


    public static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        return String.format("%02d:%02d", minutes, remainingSeconds);
    }



        public static String generateColorID(int length) {
            StringBuilder id = new StringBuilder();
            for (int i = 0; i < length; i++) {
                id.append("§").append(RANDOM.nextBoolean() ? getRandomChar(COLORS) : getRandomChar(FORMATTING));
            }
            return id.toString() + "§r";
        }

        private static char getRandomChar(String source) {
            return source.charAt(RANDOM.nextInt(source.length()));
        }

    //TRANSLATES COLORS A WHOLE LIST OF STRING
    public static List<String> translateColors(List<String> ls) {
        List<String> finalLs = new ArrayList<>();
        for (String str : ls) {
            finalLs.add(translateColors(str));
        }
        return finalLs;
    }

    public static LinkedList<String> translateColors(LinkedList<String> ls) {
        LinkedList<String> finalLs = new LinkedList<>();
        for (String str : ls) {
            finalLs.add(translateColors(str));
        }
        return finalLs;
    }



    public static Set<String> translateColors(Set<String> ls) {
        Set<String> finalLs = new HashSet<>();
        for (String str : ls) {
            finalLs.add(translateColors(str));
        }
        return finalLs;
    }
}
