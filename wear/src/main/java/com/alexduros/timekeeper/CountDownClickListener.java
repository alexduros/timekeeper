package com.alexduros.timekeeper;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

class CountDownClickListener implements View.OnClickListener {

    private static final String TAG = "CountDownClickListener";

    public static final int POSSESION_INTERVALL = 24000;
    public static final boolean RUN_COUNTDOWN_AT_START = true;

    public static final int SHOT_CLOCK_VIOLATION_DURATION = 1000;

    TextView mTextView;
    CountDownTimerWithPause mCountDown;
    Context mContext;

    public CountDownClickListener (TextView textView, Context ctx) {
        mTextView = textView;
        mContext = ctx;
    }

    @Override
    public void onClick(View v) {
        this.clearCountDown();
        if (mCountDown != null && mCountDown.isPaused()) {
            this.resumeCountDown();
        } else {
            mCountDown = new CountDownTimerWithPause(POSSESION_INTERVALL, 1000, RUN_COUNTDOWN_AT_START) {

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
            mCountDown.create();
        }
    }

    public void pauseCountDown() {
        if (mCountDown != null) {
            Log.d(TAG, "pausing current countdown");
            mCountDown.pause();
        }
    }

    public void resumeCountDown() {
        if (mCountDown != null) {
            Log.d(TAG, "resume current countdown");
            mCountDown.resume();
        }
    }


    public void clearCountDown() {
        if (mCountDown != null) {
            Log.d(TAG, "canceling current countdown");
            mCountDown.cancel();
        }
    }

}

