package me.mrvaliobg.mc.playerstatistics.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class ScheduleUtils {

    private ScheduleUtils() {

    }

    public static void createScheduledTask(final Runnable runnable, final int initialDelayInSeconds, final int intervalInSeconds) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> result = executor.scheduleAtFixedRate(runnable, initialDelayInSeconds, intervalInSeconds, TimeUnit.SECONDS);
        try {
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
