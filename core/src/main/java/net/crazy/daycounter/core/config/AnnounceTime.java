package net.crazy.daycounter.core.config;


/**
 * @author cs-jako
 */
public enum AnnounceTime {
    MIDNIGHT(18000),
    MORNING(0);

    public final long ticks;
    AnnounceTime(long ticks) {
        this.ticks = ticks;
    }
}
