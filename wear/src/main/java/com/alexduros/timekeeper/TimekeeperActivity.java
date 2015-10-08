package com.alexduros.timekeeper;

import android.app.Activity;
import android.util.Log;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

public class TimekeeperActivity extends Activity {

    public final static String TAG = "TimekeeperActivity";

    TextView mTextView;
    CountDownClickListener mClickListener;
    Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "creating activity");
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_timekeeper);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) findViewById(R.id.textView);

                Log.d(TAG, "initialize text to 24 seconds");
                mTextView.setText("24");

                Log.d(TAG, "initialize clock listener");
                mClickListener = new CountDownClickListener(mTextView, instance);
                mTextView.setOnClickListener(mClickListener);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "pausing activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mClickListener.clearCountDown();
        Log.d(TAG, "stopping activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "destroying activity");
    }
};

