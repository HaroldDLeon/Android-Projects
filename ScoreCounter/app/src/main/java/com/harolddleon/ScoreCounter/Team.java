package com.harolddleon.ScoreCounter;


import android.graphics.Color;

import java.util.Arrays;

class Team {
    static final String[] teams = {
            "Atlanta Hawks", "Boston Celtics", "Brooklyn Nets", "Charlotte Hornets", "Chicago Bulls",
            "Cleveland Cavaliers", "Dallas Mavericks", "Denver Nuggets", "Detroit Pistons", "Golden State Warriors",
            "Houston Rockets", "Indiana Pacers", "Los Angeles Clippers", "Los Angeles Lakers", "Memphis Grizzlies",
            "Miami Heat", "Milwaukee Bucks", "Minnesota Timberwolves", "New Orleans Pelicans", "New York Knicks",
            "Oklahoma City Thunder", "Orlando Magic", "Philadelphia 76ers", "Phoenix Suns", "Portland Trail Blazers",
            "Sacramento Kings", "San Antonio Spurs", "Toronto Raptors", "Utah Jazz", "Washington Wizards"};

    static final String[] colors = {
            "#E03A3E", "#007A33", "#C7C7C7", "#1D1160", "#CE1141",
            "#6F263D", "#00538C", "#0E2240", "#C8102E", "#006BB6",
            "#CE1141", "#FDBB30", "#C8102E", "#552583", "#5D76A9",
            "#38B5E6", "#00471B", "#00843D", "#85714D", "#006BB6",
            "#007AC1", "#0077C0", "#006BB6", "#E56020", "#E03A3E",
            "#542E91", "#C4CED4", "#CE1141", "#F9A01B", "#C4CED4"};

    protected static int getColor(String name) {

        try {
            int color_index = Arrays.asList(Team.teams).indexOf(name);
            String hex = Team.colors[color_index];
            return Color.parseColor(hex);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Color.BLUE;
        }
    }

}
