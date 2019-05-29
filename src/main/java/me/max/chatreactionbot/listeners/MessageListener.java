package me.max.chatreactionbot.listeners;

import net.dv8tion.jda.core.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageListener extends ListenerAdapter {

    private static final Pattern regionalIndicator = Pattern.compile(":regional_indicator_{1}([a-z]){1}:");

    private static Set<String> permute(String chars) {
        // Use sets to eliminate semantic duplicates (aab is still aab even if you switch the two 'a's)
        // Switch to HashSet for better performance
        Set<String> set = new TreeSet<>();

        // Termination condition: only 1 permutation for a string of length 1
        if (chars.length() == 1) {
            set.add(chars);
        } else {
            // Give each character a chance to be the first in the permuted string
            for (int i = 0; i < chars.length(); i++) {
                // Remove the character at index i from the string
                String pre = chars.substring(0, i);
                String post = chars.substring(i + 1);
                String remaining = pre + post;

                // Recurse to find all the permutations of the remaining chars
                for (String permutation : permute(remaining)) {
                    // Concatenate the first character with the permutations of the remaining chars
                    set.add(chars.charAt(i) + permutation);
                }
            }
        }
        return set;
    }

    @Override
    public void onTextChannelUpdateTopic(TextChannelUpdateTopicEvent event) {
        if (!event.getChannel().getId().equalsIgnoreCase("536030320894672897")) return; //needs to be our channel

        StringBuilder builder = new StringBuilder();


        Matcher matcher = regionalIndicator.matcher(event.getChannel().getTopic());
        while (matcher.find()) {
            builder.append(matcher.group(1));
        }

        String random = builder.toString();

        System.out.println("Found: " + random);

        permute(random).forEach(s -> event.getChannel().sendMessage(s).queue());

        System.out.println("Finished");

    }
}
