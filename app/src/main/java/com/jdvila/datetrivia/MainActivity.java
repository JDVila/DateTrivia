package com.jdvila.datetrivia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_more_vert_white_48dp));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about_menu_item:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.follow_me_menu_item:
                Intent twitterIntent;
                try {
                    getApplicationContext().getPackageManager().getPackageInfo("com.twitter.android", 0);
                    twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=JDVilaCodes"));
                    twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/JDVilaCodes"));
                }
                startActivity(twitterIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void chooseActivity(View v) {
        int id = v.getId();
        Class nextActivity = null;
        switch (id) {
            case R.id.cardView1:
                nextActivity = OnThisYearActivity.class;
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
