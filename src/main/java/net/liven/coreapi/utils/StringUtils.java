package net.liven.coreapi.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static HashMap<Integer, List<String>> divideStrings(List<String> input, int size) {
        HashMap<Integer, List<String>> resultMap = new HashMap<>();

        int totalGroups = (int) Math.ceil((double) input.size() / size);

        for (int i = 0; i < totalGroups; i++) {
            int startIndex = i * size;
            int endIndex = Math.min(startIndex + size, input.size());

            List<String> sublist = input.subList(startIndex, endIndex);
            resultMap.put(i + 1, new ArrayList<>(sublist));
        }

        return resultMap;
    }

    public static void sendCopyableMessage(ProxiedPlayer player, String message, int guildID) {
        TextComponent component = new TextComponent(message);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://clirzer.com/" + guildID));
        player.sendMessage(component);
    }

    public static List<Integer> divideUUIDs(List<Integer> restoreGuilds, int page) {
        List<Integer> result = new ArrayList<>();
        int groupSize = 10;
        int totalGroups = (int) Math.ceil((double) restoreGuilds.size() / groupSize);

        if (page > totalGroups) {
            return null;
        }

        int startIndex = (page - 1) * groupSize;
        int endIndex = Math.min(startIndex + groupSize, restoreGuilds.size());

        for (int i = startIndex; i < endIndex; i++) {
            result.add(restoreGuilds.get(i));
        }

        return result;
    }

    public static int getPages(List<Integer> restoreGuilds) {
        int groupSize = 10;
        return (int) Math.ceil((double) restoreGuilds.size() / groupSize);
    }

    public static String buildString(String[] strings, int startIndex) {
        if (startIndex < 0 || startIndex >= strings.length) {
            throw new IllegalArgumentException("Invalid start index.");
        }

        StringBuilder result = new StringBuilder();
        for (int i = startIndex; i < strings.length; i++) {
            result.append(strings[i]);
            if (i < strings.length - 1) {
                result.append(" ");
            }
        }
        return result.toString().trim();
    }


    public static String generateBlockedPattern(List<String> blockedWords) {
        StringBuilder regexBuilder = new StringBuilder();

        for (String word : blockedWords) {
            String pattern = word.replaceAll(".", "$0+[^a-zA-Z]*"); // Allows letter stretching & special chars
            if (regexBuilder.length() > 0) regexBuilder.append("|");
            regexBuilder.append("(").append(pattern).append(")");
        }

        return regexBuilder.toString();
    }

    public static boolean containsBlockedWord(String blockedPattern, String input) {
        Pattern pattern = Pattern.compile(blockedPattern, Pattern.CASE_INSENSITIVE);

        String normalized = input.toLowerCase();
        return pattern.matcher(normalized).find();
    }

    public static String censorPattern(String blockedPattern, String message) {
        Pattern pattern = Pattern.compile(blockedPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);

        StringBuilder censored = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            censored.append(message, lastEnd, matcher.start());
            censored.append("*".repeat(matcher.group().length()));
            lastEnd = matcher.end();
        }

        censored.append(message.substring(lastEnd));
        return censored.toString();
    }

}
