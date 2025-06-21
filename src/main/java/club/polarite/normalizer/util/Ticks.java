package club.polarite.normalizer.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Simple tick utility
 */
public class Ticks {
    private static final Queue<DelayedTask> tasks = new LinkedList<>();
    private static boolean initialized = false;

    private static void init() {
        if (initialized) return;
        initialized = true;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Iterator<DelayedTask> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                DelayedTask task = iterator.next();
                task.ticksRemaining--;
                if (task.ticksRemaining <= 0) {
                    task.runnable.run();
                    iterator.remove();
                }
            }
        });
    }

    public static void runAfter(int ticks, Runnable runnable) {
        if (ticks <= 0) {
            runnable.run();
            return;
        }
        init();
        tasks.add(new DelayedTask(ticks, runnable));
    }

    private static class DelayedTask {
        int ticksRemaining;
        Runnable runnable;

        DelayedTask(int ticks, Runnable runnable) {
            this.ticksRemaining = ticks;
            this.runnable = runnable;
        }
    }
}