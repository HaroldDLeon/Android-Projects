package com.harolddleon.ScoreCounter;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class WinnerActivity extends AppCompatActivity implements View.OnClickListener, AlertDialog.OnClickListener {

    private AlertDialog.Builder builder;
    private TextView textViewWinnerTeam;
    private LinearLayout share_layout;
    private ImageButton phone_button;
    private ImageButton sms_button;
    private ImageButton map_button;
    private ImageButton more_share;

    private String alert;
    private EditText input;
    private String winner;
    private Intent intent;
    private int advantage;
    private boolean layout_visible = false;
    private boolean layout_initiated = false;

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
        alert = String.format("Congratulations, %s won by %d point(s)!", winner, advantage);

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
                createLayout();
                return true;
            case R.id.sms_menu:
                smsIntent();
                return true;
            case R.id.phone_menu:
                showPhoneInput();
                return true;
            case R.id.map_menu:
                mapIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createLayout() {
        if(!layout_initiated){
            Toast.makeText(this, "Share the good news!", Toast.LENGTH_SHORT).show();
            layout_initiated = !layout_initiated;
        }

        if(!layout_visible) {
            setListeners();
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(share_layout, View.ALPHA, 0, 1).setDuration(1000);
            share_layout.setVisibility(View.VISIBLE);
            objectAnimator.start();
        }
        else{
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(share_layout, View.ALPHA, 1, 0).setDuration(1000);
            objectAnimator.start();
            share_layout.setVisibility(View.GONE);
        }
        layout_visible = !layout_visible;

    }

    private void setListeners() {
        phone_button = (ImageButton) findViewById(R.id.phone_share);
        phone_button.setOnClickListener(this);

        sms_button = (ImageButton) findViewById(R.id.sms_share);
        sms_button.setOnClickListener(this);

        map_button = (ImageButton) findViewById(R.id.map_share);
        map_button.setOnClickListener(this);

        more_share = (ImageButton) findViewById(R.id.more_share);
        more_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == map_button) {
            mapIntent();
        } else if (v == phone_button) {
            showPhoneInput();
        } else if (v == sms_button) {
            smsIntent();
        } else if (v == more_share) {
            shareIntent();
        }
    }
    private void showPhoneInput(){
        builder = new AlertDialog.Builder(this);
        input = new EditText(this);
        builder.setTitle("What's the phone number to dial?");
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);
        builder.setPositiveButton("Call", this);
        builder.setNegativeButton("Cancel", this);
        builder.show();
    }
    private void mapIntent() {
        Uri geolocation = Uri.parse("geo:0,0?q=basketball%20near%20me");
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geolocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void phoneIntent(String phone) {
        intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
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

    private void shareIntent() {
        String message = String.format("Hey! %s just won by %d point! What a game that was!", winner, advantage);
        intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Use an app"));
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                phoneIntent(input.getText().toString());
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }
}
