package com.jdvila.datetrivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = (Toolbar) findViewById(R.id.main_toolbar);
        actionBar.setTitle("");
        setSupportActionBar(actionBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about_menu_item:
                Toast.makeText(MainActivity.this, "Action clicked: About", Toast.LENGTH_LONG).show();
                return true;
            case R.id.contact_menu_item:
                Toast.makeText(MainActivity.this, "Action clicked: Contact", Toast.LENGTH_LONG).show();
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
