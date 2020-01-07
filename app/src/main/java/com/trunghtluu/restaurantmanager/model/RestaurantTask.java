package com.trunghtluu.restaurantmanager.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestaurantTask implements Runnable {

    private String name;
    private Context applicationContext;

    public RestaurantTask(String name, Context context) {
        this.name = name;
        this.applicationContext = context;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent progressIntent = new Intent("progress.intent");
            progressIntent.putExtra("name", name);
            progressIntent.putExtra("progress", i);
            applicationContext.sendBroadcast(progressIntent);
        }
        Intent progressIntent = new Intent("finish.intent");
        applicationContext.sendBroadcast(progressIntent);
    }
}
