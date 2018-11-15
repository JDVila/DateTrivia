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
import com.jdvila.datetrivia.model.NumberYear;
import com.jdvila.datetrivia.networking.NumberRetrofit;
import com.jdvila.datetrivia.networking.NumberService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnThisYearActivity extends AppCompatActivity {
    public static final String TAG = "OnThisYearActivity";
    private EditText yearEditText;
    private Button submit;
    private AdView adView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_this_year);
        yearEditText = (EditText) findViewById(R.id.year_edit_text);
        submit = (Button) findViewById(R.id.submit_button);
        adView = findViewById(R.id.year_adView);
        toolbar = (Toolbar) findViewById(R.id.year_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_chevron_left_white_72dp));
        toolbar.setNavigationContentDescription("Go Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
                String editContents = yearEditText.getText().toString();
                if (!editContents.isEmpty() && DateTimeSanitizer.yearValidator(Integer.parseInt(editContents))) {
                    makeYearRequest(editContents);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Year Value", Toast.LENGTH_SHORT).show();
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

    private void makeYearRequest(final String year) {
        final ProgressDialog pd = new ProgressDialog(OnThisYearActivity.this);
        pd.setMessage("loading");
        pd.show();
        Retrofit retrofit = NumberRetrofit.getRetrofitInstance();
        NumberService service = retrofit.create(NumberService.class);
        Call<NumberYear> call = service.getYear(year);
        call.enqueue(new Callback<NumberYear>() {

            @Override
            public void onResponse(Call<NumberYear> call, Response<NumberYear> response) {
                if (response.isSuccessful()) {
                    NumberYear numberYear = response.body();
                    Intent intent = new Intent(OnThisYearActivity.this, DetailActivity.class);
                    intent.putExtra("sendingActivity", TAG);
                    intent.putExtra("item", numberYear);
                    intent.putExtra("year", year);
                    startActivity(intent);
                    pd.dismiss();
                } else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Server Unavailable", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NumberYear> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
