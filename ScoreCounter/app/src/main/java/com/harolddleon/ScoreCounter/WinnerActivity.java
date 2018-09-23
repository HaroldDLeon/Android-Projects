package com.harolddleon.ScoreCounter;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class WinnerActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog.Builder builder;
    private TextView textViewWinnerTeam;
    private LinearLayout share_layout;
    private ImageButton phone_button;
    private ImageButton sms_button;
    private ImageButton map_button;

    private String alert;
    private String winner;
    private Intent intent;
    private int advantage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        Toolbar winner_toolbar = (Toolbar) findViewById(R.id.winner_toolbar);
        setSupportActionBar(winner_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        share_layout = (LinearLayout) findViewById(R.id.share_layout);
        share_layout.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();

        winner = extras.getString("EXTRA_WINNER");
        advantage = extras.getInt("EXTRA_SCORE");
        alert = String.format("Congratulations, %s won by %d points!", winner, advantage);

        textViewWinnerTeam = findViewById(R.id.textView_WinnerTeam);
        textViewWinnerTeam.setText(alert);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Choose a share medium:", Toast.LENGTH_SHORT).show();
                createLayout();
                return true;
            case R.id.sms_menu:
                smsIntent();
                return true;
            case R.id.phone_menu:
                phoneIntent();
                return true;
            case R.id.map_menu:
                mapIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createLayout() {
        share_layout.setVisibility(View.VISIBLE);
        setListeners();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(share_layout, "TranslationY", -100);
        objectAnimator.start();
    }

    private void setListeners() {
        phone_button = (ImageButton) findViewById(R.id.phone_share);
        phone_button.setOnClickListener(this);

        sms_button = (ImageButton) findViewById(R.id.sms_share);
        sms_button.setOnClickListener(this);

        map_button = (ImageButton) findViewById(R.id.map_share);
        map_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == map_button) {
            mapIntent();
        } else if (v == phone_button) {
            phoneIntent();
        } else if (v == sms_button) {
            smsIntent();
        }
    }

    private void mapIntent() {
        Uri geolocation = Uri.parse("geo:0,0?q=basketball%20arenas");
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geolocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void phoneIntent() {
        intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "877-CASH-NOW"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void smsIntent() {
        String message = String.format("Hey! %s just won by %d point! What a game that was!", winner, advantage);
        intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
