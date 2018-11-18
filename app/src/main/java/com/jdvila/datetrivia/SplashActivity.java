package com.jdvila.datetrivia;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplashActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private long counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        counter = 4000;
    }

    private CountDownTimer returnCountDownTimer(final long count) {
        return new CountDownTimer(count, 500) {
            @Override
            public void onTick(long l) {
                counter -= 500;
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownTimer = returnCountDownTimer(counter);
        countDownTimer.start();
    }

    public void twitterClick(View view) {
        countDownTimer.cancel();
        Intent intent;
        try {
            getApplicationContext().getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=JDVilaCodes"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/JDVilaCodes"));
        }
        startActivity(intent);
    }
}
