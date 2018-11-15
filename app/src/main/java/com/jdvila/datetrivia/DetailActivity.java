package com.jdvila.datetrivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jdvila.datetrivia.helper.DateTimeSanitizer;
import com.jdvila.datetrivia.model.NumberDate;
import com.jdvila.datetrivia.model.NumberYear;

public class DetailActivity extends AppCompatActivity {
    private AdView adView;
    private TextView title;
    private TextView body;
    private Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_detail);
        title = (TextView) findViewById(R.id.year_detail_title);
        body = (TextView) findViewById(R.id.year_detail_body);
        goBack = (Button) findViewById(R.id.year_detail_return_button);
        Intent intent = getIntent();
        String sendingActivity = intent.getStringExtra("sendingActivity");
        switch (sendingActivity) {
            case OnThisYearActivity.TAG:
                NumberYear numberYear = (NumberYear) intent.getSerializableExtra("item");
                String year = intent.getStringExtra("year");
                if (numberYear.date != null) {
                    String date = String.valueOf(numberYear.date);
                    String day = DateTimeSanitizer.daySanitizer(date.substring(date.length()-2).trim());
                    int split = date.indexOf(" ");
                    String combined =  date.substring(0, split) +
                            " " + day + ", " + year;
                    title.setText(combined);
                } else {
                    title.setText("In the Year " + year);
                }
                body.setText(numberYear.text);
                break;
            case OnBirthdayActivity.TAG:
                NumberDate numberDate = (NumberDate) intent.getSerializableExtra("item");
                String month = intent.getStringExtra("month");
                String day = intent.getStringExtra("day");
                String combined = DateTimeSanitizer.monthSanitizer(month) + " " +
                        DateTimeSanitizer.daySanitizer(day) +
                        ", " + numberDate.year;
                title.setText(combined);
                body.setText(numberDate.text);
                break;
        }
        adView = findViewById(R.id.year_adView);
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
