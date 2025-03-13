package net.liven.coreapi.utils;

import net.liven.coreapi.CoreAPIManager;
import net.liven.coreapi.server.CommandSenderType;
import net.liven.coreapi.server.wrappers.WrappedPlayer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CoreUtils {



    public static void sendClickableMessage(ProxiedPlayer player, String message, String command, String hoverText) {
        TextComponent component = new TextComponent(message);
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(hoverText)}));
        player.sendMessage(component);
    }

    public static List<String> getPaginationMessage(List<String> messages, String heading, int page) {
        List<String> message = new ArrayList<>();

        HashMap<Integer, List<String>> pages = StringUtils.divideStrings(messages, 10);
        if (!pages.containsKey(page)) {
            return null;
        }

        String topMessage = heading + " &f| &aPage &7" + page + "&f/&7" + pages.keySet().size();
        message.add(ChatUtils.translateColors(topMessage));
        List<String> items = pages.get(page);
        message.addAll(items);
        return message;
    }


    public static String getOnlineUUID(String playerName) {
        String uuid = null;
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            Scanner scanner = new Scanner(url.openStream());
            String response = scanner.useDelimiter("\\Z").next();
            scanner.close();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response);
            uuid = (String) json.get("id");
        } catch (FileNotFoundException e) {
            System.out.println("No Profile was found for player");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    public static void  performAction(WrappedPlayer player, String actionData) {
        String[] args = actionData.split(" ");

        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        if(args[0].equalsIgnoreCase("[message]")) {
            player.sendMessage(ChatUtils.translateColors(message.toString()));
        } else if(args[0].equalsIgnoreCase("[console]")) {
            CoreAPIManager.getInstance().getWrappedServer().dispatchCommand(CommandSenderType.CONSOLE, player, message.toString().replace("<player>", player.getName()));
        } else if (args[0].equalsIgnoreCase("[player]")) {
            player.performCommand(ChatUtils.translateColors(message.toString()));
        } else if (args[0].equalsIgnoreCase("[close]")) {
            player.closeInventory();
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
