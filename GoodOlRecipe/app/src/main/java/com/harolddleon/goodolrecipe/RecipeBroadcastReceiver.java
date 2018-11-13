package com.harolddleon.goodolrecipe;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeBroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        MainActivity instance = new MainActivity();

        int random_recipe = (int) (Math.random() * instance.getRecipeAmount());

        ArrayList<Recipe> recipes = MainActivity.recipes;
        Recipe recipe = recipes.get(random_recipe);

        Intent detail_intent = new Intent(context, DetailActivity.class);

        detail_intent.putExtra("title", recipe.getTitle());
        detail_intent.putExtra("info", recipe.getInfo());
        detail_intent.putExtra("description", recipe.getDescription());
        detail_intent.putExtra("link", recipe.getLink());
        detail_intent.putExtra("image", recipe.getImageResource());

        detail_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Toast.makeText(context, "Happy Cooking " + recipe.getTitle(), Toast.LENGTH_SHORT).show();
        context.startActivity(detail_intent);
    }
}
