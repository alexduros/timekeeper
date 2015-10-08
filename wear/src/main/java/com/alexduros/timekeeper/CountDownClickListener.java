package com.alexduros.timekeeper;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

class CountDownClickListener implements View.OnClickListener {

    private static final String TAG = "CountDownClickListener";
    public static final int POSSESION_INTERVALL = 24000;

    public static final int SHOT_CLOCK_VIOLATION_DURATION = 1000;

    TextView mTextView;
    CountDownTimer mCountDown;
    Context mContext;

    public CountDownClickListener (TextView textView, Context ctx) {
        mTextView = textView;
        mContext = ctx;
    }

    @Override
    public void onClick(View v) {
        this.clearCountDown();
        mCountDown = new CountDownTimer(POSSESION_INTERVALL, 1000) {

            public void onTick(long millisUntilFinished) {
                String remainingTime = String.format("%d", millisUntilFinished / 1000);
                Log.d(TAG, "set current remaing time " + remainingTime);
                mTextView.setText(remainingTime);
            }

            public void onFinish() {
                mTextView.setText("SCV");
                Log.d(TAG, "shot clock violoation");
                Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(SHOT_CLOCK_VIOLATION_DURATION);
            }
        };
        Log.d(TAG, "starting new countdown");
        mCountDown.start();

    }

    public void clearCountDown() {
        if (mCountDown != null) {
            Log.d(TAG, "canceling current countdown");
            mCountDown.cancel();
        }
    }

}
