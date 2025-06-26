package dev.linuq.linuqBoxPVP.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorFixer {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{6})");

    public static String addColors(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        input = translateHexColorCodes(input);
        input = ChatColor.translateAlternateColorCodes('&', input);
        return input;
    }

    public static List<String> addColors(List<String> input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        for (int i = 0; i < input.size(); i++) {
            input.set(i, addColors(input.get(i)));
        }
        return input;
    }

    private static String translateHexColorCodes(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String color = matcher.group(1);
            matcher.appendReplacement(buffer, "\u00a7x" +
                    "\u00a7" + color.charAt(0) +
                    "\u00a7" + color.charAt(1) +
                    "\u00a7" + color.charAt(2) +
                    "\u00a7" + color.charAt(3) +
                    "\u00a7" + color.charAt(4) +
                    "\u00a7" + color.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }
}