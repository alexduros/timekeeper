package com.alexduros.timekeeper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

public class TimekeeperActivity extends Activity {

    TextView mTextView;
    View.OnClickListener mClickListener;
    Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_timekeeper);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) findViewById(R.id.textView);
                mTextView.setText("24");
                mClickListener = new CountDownClickListener(mTextView, instance);
                mTextView.setOnClickListener(mClickListener);
            }
        });
    }
}

;

