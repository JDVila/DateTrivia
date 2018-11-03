package com.jdvila.datetrivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void chooseActivity(View v) {
        int id = v.getId();
        Class nextActivity = null;
        switch (id) {
            case R.id.cardView1:
                nextActivity = OnThisDayActivity.class;
                break;
            case R.id.cardView2:
                nextActivity = OnBirthdayActivity.class;
                break;
            case R.id.cardView3:
                nextActivity = SurpriseActivity.class;
                break;
        }
        startActivity(new Intent(MainActivity.this, nextActivity));
    }
}
