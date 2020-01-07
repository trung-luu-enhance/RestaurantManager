package com.trunghtluu.restaurantmanager.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trunghtluu.restaurantmanager.R;
import com.trunghtluu.restaurantmanager.model.RestaurantTask;
import com.trunghtluu.restaurantmanager.util.HandlerUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_tv)
    TextView mainTextView;

    @BindView(R.id.add_bt)
    Button addButtton;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int x = intent.getIntExtra("progress", 1);
            String name = intent.getStringExtra("name");
            mainTextView.setText(name);
            Log.d("TAG", "" + x);
            progressBar.setProgress(x*10);
        }
    };

    BroadcastReceiver finishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mainTextView.setText("Waiting...");
            progressBar.setProgress(0);
        }
    };

    private ArrayList<Runnable> myList = new ArrayList<>();

    Handler handler = new Handler();

    private int numOfClicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        registerReceiver(progressReceiver, new IntentFilter("progress.intent"));
        registerReceiver(finishReceiver, new IntentFilter("finish.intent"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.add_bt)
    public void addTask() {
        numOfClicks++;
        myList.add(new RestaurantTask("Person " + numOfClicks, getApplicationContext()));

        HandlerUtil handlerUtil = new HandlerUtil(handler, myList);
        handlerUtil.runTasks();
    }
}
