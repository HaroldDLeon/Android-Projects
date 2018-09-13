package com.harolddleon.ScoreCounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    public static final String EXTRA_MESSAGE = "com.harolddleon.ScoreCounter.MESSAGE";

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

    }
    @Override
    protected void onPause(){
        super.onPause();
        homeScore = 0;
        awayScore = 0;

        textViewHomeScore.setText(Integer.toString(homeScore));
        textViewAwayScore.setText(Integer.toString(awayScore));
    }

    @Override
    public void onClick(View v) {
        if (v == buttonViewAway) {
            awayScore += 1;
            textViewAwayScore.setText(Integer.toString(awayScore));
            if (awayScore >= 5) {
                awayWon();
            }
        } else {
            homeScore += 1;
            textViewHomeScore.setText(Integer.toString(homeScore));
            if (homeScore >= 5) {
                homeWon();
            }
        }
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
