package com.harolddleon.goodolrecipe;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<Recipe> recipes;
    private RecipeAdapter recipeAdapter;
    private Integer recipeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecipeBroadcastReceiver receiver = new RecipeBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.harolddleon.ACTION_RECIPE_SHOW");
        registerReceiver(receiver, intentFilter);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipes = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this, recipes);
        recyclerView.setAdapter(recipeAdapter);
        initialize();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN
                        | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                int from = viewHolder.getAdapterPosition();
                int to = viewHolder1.getAdapterPosition();
                Collections.swap(recipes, from, to);
                recipeAdapter.notifyItemMoved(from, to);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                recipes.remove(viewHolder.getAdapterPosition());
                recipeAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(recyclerView);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        String new_recipe_title;
        String new_recipe_description;
        String new_recipe_info;
        String new_recipe_link;
        try {
            new_recipe_title = data.getStringExtra("new_recipe_title");
            new_recipe_description = data.getStringExtra("new_recipe_description");
            new_recipe_info = data.getStringExtra("new_recipe_info");
            new_recipe_link = data.getStringExtra("new_recipe_link");
            addRecipe(new_recipe_title, new_recipe_info, new_recipe_description, new_recipe_link);
        } catch (Exception ignored) {

        }

    }

    private void initialize() {
        String[] recipeList = getResources().getStringArray(R.array.recipe_titles);
        String[] recipeInfo = getResources().getStringArray(R.array.recipe_info);
        String[] recipeDescription = getResources().getStringArray(R.array.recipe_descriptions);
        String[] recipeLinks = getResources().getStringArray(R.array.recipe_links);
        TypedArray recipeImages = getResources().obtainTypedArray(R.array.recipe_images);
        recipeAmount = recipeInfo.length;
        recipes.clear();

        for (int i = 0; i < recipeList.length; i++) {
            recipes.add(new Recipe(recipeList[i], recipeInfo[i], recipeDescription[i], recipeLinks[i], recipeImages.getResourceId(i, 0)));
        }
        recipeImages.recycle();
        recipeAdapter.notifyDataSetChanged();
    }

    private void addRecipe(String title, String info, String recipeDescription, String recipeLink) {
        TypedArray recipeImages = getResources().obtainTypedArray(R.array.recipe_images);
        recipes.add(new Recipe(title, info, recipeDescription, recipeLink, recipeImages.getResourceId(recipeAmount, recipeAmount)));
        recipeImages.recycle();
        recipeAdapter.notifyItemInserted(recipes.size());

    }

    @Override
    public void onClick(View v) {

    }
}
