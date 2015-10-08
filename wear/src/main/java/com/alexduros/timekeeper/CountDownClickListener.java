package com.alexduros.timekeeper;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

class CountDownClickListener implements View.OnClickListener {

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
    public void onClick(View v)
    {
        if (mCountDown != null) {
            mCountDown.cancel();
        }
        mCountDown = new CountDownTimer(POSSESION_INTERVALL, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextView.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTextView.setText("SCV");
                Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(SHOT_CLOCK_VIOLATION_DURATION);
            }
        };
        mCountDown.start();

    }

}
