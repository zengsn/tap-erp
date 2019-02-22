package com.erp.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static ExecutorService executor;
    private ThreadUtil(){}

    static {
        if (executor != null){
            executor.shutdownNow();
        }
        executor = Executors.newCachedThreadPool();
    }

    public static void execute(Runnable runnable){
        executor.execute(runnable);
    }
}
