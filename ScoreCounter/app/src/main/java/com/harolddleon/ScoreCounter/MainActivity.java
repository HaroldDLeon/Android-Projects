package com.harolddleon.ScoreCounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener, AlertDialog.OnClickListener, OnLongClickListener {

    private TextView textViewHomeScore;
    private TextView textViewAwayScore;
    private Button buttonViewHome;
    private Button buttonViewAway;
    private EditText input;
    private AlertDialog.Builder builder;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private int homeScore = 0;
    private int awayScore = 0;
    private int color;
    private String text;
    private boolean layout_initiated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("preferences", 0);
        editor = preferences.edit();

        textViewHomeScore = findViewById(R.id.textView_HomeTeamScore);
        textViewAwayScore = findViewById(R.id.textView_AwayTeamScore);

        buttonViewHome = findViewById(R.id.buttonView_HomeTeam);
        buttonViewAway = findViewById(R.id.buttonView_AwayTeam);

        buttonViewAway.setOnClickListener(this);
        buttonViewHome.setOnClickListener(this);
        buttonViewAway.setOnLongClickListener(this);

        text = preferences.getString("team", "Golden State Warriors");
        color = Color.parseColor(preferences.getString("color", "#006BB6"));

        buttonViewAway.setText(text);
        buttonViewAway.setBackgroundColor(color);

        layout_initiated = preferences.getBoolean("layout_initiated", false);
        if (!layout_initiated) {
            Toast.makeText(this, "Tip: Long press Away Team to choose a different one.", Toast.LENGTH_SHORT).show();
            editor.putBoolean("layout_initiated", true).apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                try {
                    text = input.getText().toString();
                    int color_index = Arrays.asList(Team.teams).indexOf(text);
                    String hex = Team.colors[color_index];
                    color = Color.parseColor(hex);

                    buttonViewAway.setBackgroundColor(color);
                    editor.putString("team", text).apply();
                    editor.putString("color", hex).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                buttonViewAway.setText(text);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonViewAway) {
            awayScore += 1;
            updateScores();
            if (awayScore >= 5) {
                awayWon();
            }
        } else {
            homeScore += 1;
            updateScores();
            if (homeScore >= 5) {
                homeWon();
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v == buttonViewAway) {
            builder = new AlertDialog.Builder(this);
            input = new EditText(this);
            builder.setTitle("Who is Miami playing against?");

            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", this);
            builder.setNegativeButton("Cancel", this);
            builder.show();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings activity called", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateScores() {
        textViewAwayScore.setText(String.format(Locale.US, "%d", awayScore));
        textViewHomeScore.setText(String.format(Locale.US, "%d", homeScore));
    }

    private void resetScores() {
        awayScore = 0;
        homeScore = 0;
        updateScores();
    }

    private void awayWon() {
        String awayTeam = buttonViewAway.getText().toString();
        int score = awayScore - homeScore;
        createIntent(awayTeam, score);
    }

    private void homeWon() {
        String homeTeam = buttonViewHome.getText().toString();
        int score = homeScore - awayScore;
        createIntent(homeTeam, score);

    }

    private void createIntent(String winner, int score) {
        Intent intent = new Intent(this, WinnerActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_WINNER", winner);
        extras.putInt("EXTRA_SCORE", score);
        intent.putExtras(extras);
        resetScores();
        startActivity(intent);
    }
}
