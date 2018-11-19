package com.harolddleon.ScoreCounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView textViewHomeScore;
    private TextView textViewAwayScore;

    private Button buttonViewHome;
    private Button buttonViewAway;

    private SharedPreferences preferences;

    private int awayScore = 0;
    private int homeScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        textViewHomeScore = findViewById(R.id.textView_HomeTeamScore);
        textViewAwayScore = findViewById(R.id.textView_AwayTeamScore);

        buttonViewHome = findViewById(R.id.buttonView_HomeTeam);
        buttonViewAway = findViewById(R.id.buttonView_AwayTeam);

        preferences.registerOnSharedPreferenceChangeListener(this);

        buttonViewAway.setOnClickListener(this);
        buttonViewHome.setOnClickListener(this);

        updateTitles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateTitles();
    }

    private void updateTitles() {
        String away_text = preferences.getString("away_text", "Golden State Warriors");
        int away_color = Team.getColor(away_text);

        buttonViewAway.setText(away_text);
        buttonViewAway.setBackgroundColor(away_color);

        String home_text = preferences.getString("home_text", "Miami Heat");
        int home_color = Team.getColor(home_text);

        buttonViewHome.setText(home_text);
        buttonViewHome.setBackgroundColor(home_color);
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
