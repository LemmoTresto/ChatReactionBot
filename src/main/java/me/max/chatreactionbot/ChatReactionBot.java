package me.max.chatreactionbot;

import me.max.chatreactionbot.listeners.MessageListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class ChatReactionBot {

    public static void main(String[] args) {
        try {
            new JDABuilder(AccountType.CLIENT)
                    .setToken(args[0])
                    .addEventListener(new MessageListener())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
