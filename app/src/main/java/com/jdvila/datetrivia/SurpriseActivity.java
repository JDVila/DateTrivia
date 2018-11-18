package com.jdvila.datetrivia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jdvila.datetrivia.helper.DateTimeSanitizer;
import com.jdvila.datetrivia.model.NumberDate;
import com.jdvila.datetrivia.networking.NumberRetrofit;
import com.jdvila.datetrivia.networking.NumberService;

import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SurpriseActivity extends AppCompatActivity {
    public static final String TAG = "SurpriseActivity";
    private Toolbar toolbar;
    private AdView adView;
    private TextView randomDateTextView;
    private CountDownTimer countDownTimer;
    private Random random;
    private ProgressDialog pd;
    private boolean onStopTriggered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise);
        adView = findViewById(R.id.surprise_adView);
        randomDateTextView = (TextView) findViewById(R.id.random_date_textview);
        toolbar = (Toolbar) findViewById(R.id.surprise_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_white_72dp));
        toolbar.setNavigationContentDescription("Go Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        countDownTimer = new CountDownTimer(3000, 100) {
            @Override
            public void onTick(long l) {
                randomDateTextView.setText(generateRandomDateString());
            }

            @Override
            public void onFinish() {
                randomDateTextView.setVisibility(View.INVISIBLE);
                int monthInt = random.nextInt(12) + 1;
                int dayInt = random.nextInt(31) + 1;
                boolean validDate = DateTimeSanitizer.monthDayValidator(monthInt, dayInt);
                while (!validDate) {
                    monthInt = random.nextInt(12) + 1;
                    dayInt = random.nextInt(31) + 1;
                    validDate = DateTimeSanitizer.monthDayValidator(monthInt, dayInt);
                }
                makeRandomDateRequest(String.valueOf(monthInt), String.valueOf(dayInt));
            }
        };
        random = new Random();

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public String generateRandomDateString() {
        int randomMonth = random.nextInt(12) + 1;
        int randomDay = random.nextInt(31) + 1;
        int randomYear = random.nextInt(Calendar.getInstance().get(Calendar.YEAR)) + 1;
        String randomMonthString = randomMonth < 10 ? "0" + randomMonth : "" + randomMonth;
        String randomDayString = randomDay < 10 ? "0" + randomDay : "" + randomDay;
        String randomYearString = randomYear < 10 ? "000" + randomYear :
                randomYear < 100 ? "00" + randomYear :
                        randomYear < 1000 ? "0" + randomYear :
                                "" + randomYear;
        return randomMonthString + "/" + randomDayString + "/" + randomYearString;
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownTimer.start();
    }

    private void makeRandomDateRequest(final String month, final String day) {
        try {
            pd = new ProgressDialog(SurpriseActivity.this);
            pd.setMessage("loading");
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Retrofit retrofit = NumberRetrofit.getRetrofitInstance();
        NumberService service = retrofit.create(NumberService.class);
        Call<NumberDate> call = service.getBirthday(month, day);
        call.enqueue(new Callback<NumberDate>() {

            @Override
            public void onResponse(Call<NumberDate> call, Response<NumberDate> response) {
                if (response.isSuccessful()) {
                    try {
                        pd.dismiss();
                        nextActivity(response.body(), month, day);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        pd.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Server Unavailable", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NumberDate> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void nextActivity(NumberDate nd, String month, String day) {
        if (!onStopTriggered) {
            Intent intent = new Intent(SurpriseActivity.this, DetailActivity.class);
            intent.putExtra("item", nd);
            intent.putExtra("sendingActivity", TAG);
            intent.putExtra("month", month);
            intent.putExtra("day", day);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        onStopTriggered = true;
    }
}
