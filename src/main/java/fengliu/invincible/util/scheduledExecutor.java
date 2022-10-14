package fengliu.invincible.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class scheduledExecutor {
    public static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10000);
}
