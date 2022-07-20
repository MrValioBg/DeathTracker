package me.mrvaliobg.mc.playerstatistics.utils;

import java.util.Timer;
import java.util.TimerTask;

public final class ScheduleUtils {

    private ScheduleUtils() {

    }

    public static void createScheduledTask(final TimerTask task,final int intervalInSeconds) {
        Timer timer = new Timer();
        final long period = intervalInSeconds * 1000L;
        timer.scheduleAtFixedRate(task, period, period);
    }
}
