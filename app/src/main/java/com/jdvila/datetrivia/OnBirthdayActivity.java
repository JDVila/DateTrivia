package com.jdvila.datetrivia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jdvila.datetrivia.helper.DateTimeSanitizer;
import com.jdvila.datetrivia.model.NumberDate;
import com.jdvila.datetrivia.networking.NumberRetrofit;
import com.jdvila.datetrivia.networking.NumberService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnBirthdayActivity extends AppCompatActivity {
    public static final String TAG = "OnBirthdayActivity";
    private EditText monthEditText;
    private EditText dayEditText;
    private Button submit;
    private Toolbar actionBar;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_birthday);
        monthEditText = (EditText) findViewById(R.id.month_edit_text);
        dayEditText = (EditText) findViewById(R.id.day_edit_text);
        submit = (Button) findViewById(R.id.submit_button);
        adView = findViewById(R.id.birthday_adView);
        actionBar = (Toolbar) findViewById(R.id.birthday_toolbar);
        actionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_white_72dp));
        actionBar.setNavigationContentDescription("Go Back");
        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String monthContents = monthEditText.getText().toString();
                String dayContents = dayEditText.getText().toString();
                int monthInt = (monthContents.length() > 0) ? Integer.parseInt(monthContents) : 0;
                int dayInt = (dayContents.length() > 0) ? Integer.parseInt(dayContents) : 0;
                if((monthContents.length() != 0) && (dayContents.length() != 0) && DateTimeSanitizer.monthDayValidator(monthInt, dayInt)) {
                    makeBirthdayRequest(monthContents, dayContents);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter valid Month and Day Value", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void makeBirthdayRequest(final String month, final String day) {
        final ProgressDialog pd = new ProgressDialog(OnBirthdayActivity.this);
        pd.setMessage("loading");
        pd.show();
        Retrofit retrofit = NumberRetrofit.getRetrofitInstance();
        NumberService service = retrofit.create(NumberService.class);
        Call<NumberDate> call = service.getBirthday(month, day);
        call.enqueue(new Callback<NumberDate>() {

            @Override
            public void onResponse(Call<NumberDate> call, Response<NumberDate> response) {
                if (response.isSuccessful()) {
                    NumberDate nd = response.body();
                    Intent intent = new Intent(OnBirthdayActivity.this, DetailActivity.class);
                    intent.putExtra("item", nd);
                    intent.putExtra("sendingActivity", TAG);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    startActivity(intent);
                    pd.dismiss();
                } else {
                    pd.dismiss();
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
}
