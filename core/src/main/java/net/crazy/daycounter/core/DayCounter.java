package net.crazy.daycounter.core;

import net.crazy.daycounter.core.listener.TickListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class DayCounter extends LabyAddon<AddonConfiguration> {

    public DayAnnouncer announcer;

    @Override
    protected void enable() {
        this.registerSettingCategory();

        this.announcer = new DayAnnouncer(this);
        this.registerListener(new TickListener(this));

        this.logger().info("Enabled Day Counter");
    }

    @Override
    protected Class<AddonConfiguration> configurationClass() {
        return AddonConfiguration.class;
    }
}
