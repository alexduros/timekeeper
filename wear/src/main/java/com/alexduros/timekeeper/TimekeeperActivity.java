package com.alexduros.timekeeper;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import java.util.Arrays;

public class TimekeeperActivity extends Activity  {

    public final static String TAG = "TimekeeperActivity";

    TextView mTextView;
    CountDownClickListener mClickListener;

    Activity instance;

    private SensorManager mSensorManager;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event){
            Log.v(TAG, "receive onSensorChanged");

            final float alpha = (float) 0.8;
            float[] gravity = new float[3];
            float[] linear_acceleration = new float[3];

            // In this example, alpha is calculated as t / (t + dT),
            // where t is the low-pass filter's time-constant and
            // dT is the event delivery rate.

            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];

            Log.v(TAG, String.format("gravity: %s", Arrays.toString(gravity)));
            Log.v(TAG, String.format("linear_acceleration: %s", Arrays.toString(linear_acceleration)));

            if (gravity[0] < 0 && gravity[1] < 0 && gravity[2] < 0) {
                // This is a numb way to test
                // a violation or foul gesture
                mClickListener.pauseCountDown();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(TAG, "receive onAccuracyChanged");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "creating activity");
        super.onCreate(savedInstanceState);
        instance = this;

        setContentView(R.layout.activity_timekeeper);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

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

