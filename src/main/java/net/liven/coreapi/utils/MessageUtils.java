package net.liven.coreapi.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.List;

public class MessageUtils {
    private static final int MESSAGES_PER_PAGE = 10;

    public static int getMaxPage(List<String> messages) {
        return (int) Math.ceil((double) messages.size() / MESSAGES_PER_PAGE);
    }

    public static void sendPaginatedMessages(ProxiedPlayer player, List<String> messages, int page) {
        int totalPages = getMaxPage(messages);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        player.sendMessage("\n§6Messages (Page " + page + "/" + totalPages + ")\n");
        
        int start = (page - 1) * MESSAGES_PER_PAGE;
        int end = Math.min(start + MESSAGES_PER_PAGE, messages.size());
        
        for (int i = start; i < end; i++) {
            player.sendMessage("§7" + messages.get(i));
        }
        
        if (totalPages > 1) {
            sendPageButtons(player, page, totalPages);
        }
    }
    private static final double SIMILARITY_THRESHOLD = 0.85; // Adjust as needed

    public static boolean isSpam(String str1, String str2) {
        str1 = str1.toLowerCase().trim();
        str2 = str2.toLowerCase().trim();

        int maxLength = Math.max(str1.length(), str2.length());
        if (maxLength == 0) return true;

        int distance = new LevenshteinDistance().apply(str1, str2);
        double similarity = 1.0 - ((double) distance / maxLength);

        return similarity >= SIMILARITY_THRESHOLD;
    }

    private static void sendPageButtons(ProxiedPlayer player, int currentPage, int totalPages) {
        TextComponent message = new TextComponent("\n");

        if (currentPage > 1) {
            TextComponent prev = new TextComponent("[<< Prev] ");
            prev.setColor(ChatColor.YELLOW);
            prev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chatreport list " + (currentPage - 1)));
            prev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to page " + (currentPage - 1)).create()));
            message.addExtra(prev);
        }

        TextComponent pageInfo = new TextComponent("Page " + currentPage + "/" + totalPages);
        pageInfo.setColor(ChatColor.GOLD);
        message.addExtra(pageInfo);

        if (currentPage < totalPages) {
            TextComponent next = new TextComponent(" [Next >>]");
            next.setColor(ChatColor.YELLOW);
            next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chatreport list " + (currentPage + 1)));
            next.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to page " + (currentPage + 1)).create()));
            message.addExtra(next);
        }

        player.sendMessage(message);
    }
}
