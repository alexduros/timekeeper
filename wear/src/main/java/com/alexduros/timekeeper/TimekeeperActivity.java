package com.alexduros.timekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Chronometer;

public class TimekeeperActivity extends Activity {

    Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timekeeper);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mChronometer = (Chronometer) findViewById(R.id.chronometer);
                mChronometer.setText("24");
            }
        });
        stub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mChronometer = (Chronometer) findViewById(R.id.chronometer);
                    mChronometer.start();
            }
        });
    }
}
