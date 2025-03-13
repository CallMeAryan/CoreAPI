package net.liven.common.core.utils;

import net.liven.common.core.CoreAPIManager;
import net.liven.common.core.server.CommandSenderType;
import net.liven.common.core.server.wrappers.WrappedPlayer;

public class CoreUtils {




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
