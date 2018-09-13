package com.harolddleon.ScoreCounter;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;

public class WinnerActivity extends AppCompatActivity {

    private TextView textViewWinnerTeam;
    private String alert;
    private String winner;
    private int advantage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        winner = extras.getString("EXTRA_WINNER");
        advantage = extras.getInt("EXTRA_SCORE");
        alert = String.format("Congratulations, %s won by %d points!", winner, advantage);

        textViewWinnerTeam = findViewById(R.id.textView_WinnerTeam);
        textViewWinnerTeam.setText(alert);
    }


}
