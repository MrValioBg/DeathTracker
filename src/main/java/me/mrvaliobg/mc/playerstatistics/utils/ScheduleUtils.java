package me.mrvaliobg.mc.playerstatistics.utils;

import java.util.Timer;
import java.util.TimerTask;

public final class ScheduleUtils {

    private ScheduleUtils() {

    }

    public static void createScheduledTask(final TimerTask runnable,final int intervalInSeconds) {
        Timer timer = new Timer();

        // Schedule to run after every 3 second(3000 millisecond)
        timer.schedule(runnable, intervalInSeconds* 1000L);
    }
}
