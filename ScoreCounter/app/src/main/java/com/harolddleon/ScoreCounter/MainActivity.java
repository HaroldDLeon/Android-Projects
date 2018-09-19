package com.harolddleon.ScoreCounter;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.DialogInterface;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener, AlertDialog.OnClickListener {

    private TextView textViewHomeScore;
    private TextView textViewAwayScore;
    private Button buttonViewHome;
    private Button buttonViewAway;
    private EditText input;

    private int homeScore = 0;
    private int awayScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHomeScore = findViewById(R.id.textView_HomeTeamScore);
        textViewAwayScore = findViewById(R.id.textView_AwayTeamScore);

        buttonViewHome = findViewById(R.id.buttonView_HomeTeam);
        buttonViewAway = findViewById(R.id.buttonView_AwayTeam);

        buttonViewAway.setOnClickListener(this);
        buttonViewHome.setOnClickListener(this);

        // Create alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input = new EditText(this);
        builder.setTitle("Who is Miami playing against?");

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);
        builder.show();

    }
    @Override
    protected void onPause(){
        super.onPause();
        homeScore = 0;
        awayScore = 0;

        updateScores();
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                String text = input.getText().toString();
                try {
                    int color_index = Arrays.asList(Team.teams).indexOf(text);
                    String hex = Team.colors[color_index];
                    int color = Color.parseColor(hex);
                    buttonViewAway.setBackgroundColor(color);
                } catch (Exception e) {
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
    private void updateScores(){
        textViewAwayScore.setText(String.format(Locale.US, "%d", awayScore));
        textViewHomeScore.setText(String.format( Locale.US, "%d", homeScore));
    }

    private void awayWon() {

        Intent intent = new Intent(this, WinnerActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_WINNER", (String) buttonViewAway.getText());
        extras.putInt("EXTRA_SCORE", awayScore - homeScore);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void homeWon() {

        Intent intent = new Intent(this, WinnerActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_WINNER", (String) buttonViewHome.getText());
        extras.putInt("EXTRA_SCORE", homeScore - awayScore);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
