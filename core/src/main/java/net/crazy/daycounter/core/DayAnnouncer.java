package net.crazy.daycounter.core;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.I18n;
import net.labymod.api.util.concurrent.task.Task;

/**
 * @author cs-jako
 */
public class DayAnnouncer {

    private final DayCounter addon;
    private final ChatExecutor chatExecutor;

    private final Component prefix = Component.text("-- "),
        suffix = Component.text(" --");

    public DayAnnouncer(DayCounter addon) {
        this.addon = addon;
        this.chatExecutor = addon.labyAPI().minecraft().chatExecutor();
    }

    public void announce() {
        switch (addon.configuration().channel.getOrDefault()) {
            case ACTIONBAR -> announceActionBarQuick();
            case NOTIFICATION -> announceNotification();
            case null, default -> announceActionBarTyped();
        }
    }

    private void announceActionBarQuick() {
        chatExecutor.displayActionBar(
            Component.empty()
                .append(prefix)
                .append(Component.translatable("daycounter.messages.day"))
                .append(Component.text(" " + getDay()))
                .append(suffix));
    }


    private Task task;

    private void announceActionBarTyped() {
        AtomicInteger counter = new AtomicInteger(0);
        int day = getDay();
        String message = I18n.translate("daycounter.messages.day");
        String announcement = message + " " + day;

        task = Task.builder(() -> {
            if (counter.get() >= announcement.length()) {
                task.cancel();
                return;
            }

            chatExecutor.displayActionBar(
                Component.empty()
                    .append(prefix)
                    .append(Component.text(
                        counter.get() < announcement.length() ?
                            announcement.substring(0, counter.get() + 1) : announcement
                    ))
                    .append(suffix)
            );
            counter.incrementAndGet();
        }).repeat(500, TimeUnit.MILLISECONDS).build();
        task.executeOnRenderThread();
    }

    private void announceNotification() {
        Notification.builder()
            .title(Component.translatable("daycounter.messages.day")
                .append(Component.text(" " + getDay())));
    }

    private int getDay() {
        return (int) (addon.labyAPI().minecraft().clientWorld().getDayTime() / 24000) +
            (addon.configuration().startAtOne.getOrDefault() ? 1 : 0);
    }
}
