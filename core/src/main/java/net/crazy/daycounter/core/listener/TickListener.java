package net.crazy.daycounter.core.listener;


import net.crazy.daycounter.core.config.AnnounceTime;
import net.crazy.daycounter.core.DayCounter;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

/**
 * @author cs-jako
 */
public class TickListener {

    private final DayCounter addon;

    private boolean hasAnnouncedToday = false;

    public TickListener(DayCounter addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onTick(GameTickEvent event) {
        if (!event.phase().equals(Phase.POST)) {
            return;
        }

        ClientWorld world = addon.labyAPI().minecraft().clientWorld();
        if (world == null || !addon.labyAPI().minecraft().isIngame()) {
            return;
        }

        // Limit to one day cycle
        long dayTime = world.getDayTime() % 24000;

        // Prevent false dayTime when world is loaded
        if (dayTime == 0) {
            return;
        }

        AnnounceTime announceTime = addon.configuration().time.getOrDefault();
        long targetTime = announceTime.ticks;
        int buffer = 100;

        boolean inRange = Math.abs(dayTime - targetTime) <= buffer;

        if (!inRange) {
            hasAnnouncedToday = false;
        }

        if (hasAnnouncedToday || !inRange) {
            return;
        }

        addon.announcer.announce();
        hasAnnouncedToday = true;
    }
}
