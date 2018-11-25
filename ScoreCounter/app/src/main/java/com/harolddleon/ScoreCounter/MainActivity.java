package com.harolddleon.ScoreCounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
		setContentView(R.layout.activity_main);

		textViewHomeScore = findViewById(R.id.textView_HomeTeamScore);
		textViewAwayScore = findViewById(R.id.textView_AwayTeamScore);

		buttonViewHome = findViewById(R.id.buttonView_HomeTeam);
		buttonViewAway = findViewById(R.id.buttonView_AwayTeam);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.registerOnSharedPreferenceChangeListener(this);

		buttonViewAway.setOnClickListener(this);
		buttonViewHome.setOnClickListener(this);

		updateTitles();
		updateDrawables();
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
		switch (key) {
			case "home_text":
				updateTitles();
				break;
			case "away_text":
				updateTitles();
				break;
		}
		recreate();
	}

	private void updateDrawables() {
		ViewGroup main_layout = findViewById(R.id.main_layout);
		String sport = preferences.getString("sports_list", "Basketball");
		int nightModeFlags =
				getResources().getConfiguration().uiMode &
						Configuration.UI_MODE_NIGHT_MASK;
		switch (sport) {
			case "Basketball":
				switch (nightModeFlags) {
					case Configuration.UI_MODE_NIGHT_YES:
						main_layout.setBackground(getResources().getDrawable(R.drawable.ic_background_basketball_dark));
						break;
					case Configuration.UI_MODE_NIGHT_NO:
						main_layout.setBackground(getResources().getDrawable(R.drawable.ic_background_basketball));
						break;
				}
				break;
			case "Hockey":
				switch (nightModeFlags) {
					case Configuration.UI_MODE_NIGHT_YES:
						main_layout.setBackground(getResources().getDrawable(R.drawable.ic_background_hockey_dark));
						break;
					case Configuration.UI_MODE_NIGHT_NO:
						main_layout.setBackground(getResources().getDrawable(R.drawable.ic_background_hockey));
						break;
				}
				break;
			case "Baseball":
				switch (nightModeFlags) {
					case Configuration.UI_MODE_NIGHT_YES:
						main_layout.setBackground(getResources().getDrawable(R.drawable.ic_background_baseball_dark));
						break;
					case Configuration.UI_MODE_NIGHT_NO:
						main_layout.setBackground(getResources().getDrawable(R.drawable.ic_background_baseball));
						break;
				}
				break;
		}
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
