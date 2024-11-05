package net.crazy.daycounter.core;


import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.Component;

/**
 * @author cs-jako
 */
public class DayAnnouncer {
    private final DayCounter addon;
    private final ChatExecutor chatExecutor;

    public DayAnnouncer(DayCounter addon) {
        this.addon = addon;
        this.chatExecutor = addon.labyAPI().minecraft().chatExecutor();
    }

    public void announce() {
        switch (addon.configuration().channel.getOrDefault()) {
            case ACTIONBAR_QUICK -> announceActionBarQuick();
            case NOTIFICATION -> announceNotification();
            case null, default -> announceActionBarTyped();
        }
    }

    private void announceActionBarQuick() {
        chatExecutor.displayActionBar(
            Component.text("-- ")
                .append(Component.translatable("daycounter.messages.day"))
                .append(Component.text(" " + getDay() + " --")));
    }

    private void announceActionBarTyped() {

    }

    private void announceNotification() {

    }

    private int getDay() {
        return (int) (addon.labyAPI().minecraft().clientWorld().getDayTime() / 24000);
    }
}
