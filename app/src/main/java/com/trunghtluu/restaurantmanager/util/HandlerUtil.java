package com.trunghtluu.restaurantmanager.util;

import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class HandlerUtil {
    private Handler uiHandler;

    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static long KEEP_ALIVE_TIME =  10;

    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    private List<Runnable> taskList;

    private ThreadPoolExecutor threadPoolExecutor = null;

    public HandlerUtil(Handler uiHandler, ArrayList<Runnable> taskList) {
        this.uiHandler = uiHandler;
        this.taskList = taskList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void runTasks() {
        if (threadPoolExecutor == null)
            threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, taskQueue);

        taskList.forEach(new Consumer<Runnable>() {
            @Override
            public void accept(Runnable runnable) {
                threadPoolExecutor.execute(runnable);
            }
        });
    }
}
