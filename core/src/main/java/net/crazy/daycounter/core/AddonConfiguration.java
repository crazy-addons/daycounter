package net.crazy.daycounter.core;

import net.crazy.daycounter.core.config.AnnounceChannel;
import net.crazy.daycounter.core.config.AnnounceTime;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class AddonConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SwitchSetting
  public final ConfigProperty<Boolean> startAtOne = new ConfigProperty<>(true);

  @DropdownSetting
  public final ConfigProperty<AnnounceTime> time = new ConfigProperty<>(AnnounceTime.MORNING);

  @DropdownSetting
  public final ConfigProperty<AnnounceChannel> channel = new ConfigProperty<>(AnnounceChannel.TYPED);

  @SwitchSetting
  public final ConfigProperty<Boolean> withSound = new ConfigProperty<>(true);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }
}
