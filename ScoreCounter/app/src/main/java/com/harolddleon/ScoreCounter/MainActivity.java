package com.harolddleon.ScoreCounter;

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

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private TextView textViewHomeScore;
    private TextView textViewAwayScore;
    private Button buttonViewHome;
    private Button buttonViewAway;

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
        final EditText input = new EditText(this);
        builder.setTitle("Who is Miami playing against?");

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buttonViewAway.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
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
