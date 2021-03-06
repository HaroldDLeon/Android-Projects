package com.harolddleon.goodolrecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    ImageView recipeImage;
    TextView recipeTitle;
    TextView recipeInfo;
    TextView recipeDescription;
    String recipeLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipeImage = (ImageView) findViewById(R.id.recipeImageDetail);
        recipeImage.setOnClickListener(this);
        recipeImage.setOnLongClickListener(this);

        recipeTitle = (TextView) findViewById(R.id.recipeTitleDetail);
        recipeInfo = (TextView) findViewById(R.id.recipeTagDetail);
        recipeLink = getIntent().getStringExtra("link");
        recipeDescription = (TextView) findViewById(R.id.recipeDescriptionDetail);

        Intent intent = getIntent();
        Glide.with(this).load(intent.getIntExtra("image", 0)).into(recipeImage);

        recipeTitle.setText(intent.getStringExtra("title"));
        recipeDescription.setText(intent.getStringExtra("description"));
        recipeInfo.setText(intent.getStringExtra("info"));


        toolbar.setTitle(recipeTitle.getText());
//        Only Enable if the images of the recipes were changed
//        toolbar.setBackground(null);
//        toolbar.setTitleTextColor(Color.GRAY);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences("preferences", 0);
        SharedPreferences.Editor editor = preferences.edit();

        boolean layout_initiated = preferences.getBoolean("layout_initiated", false);
        if (!layout_initiated) {
            Toast.makeText(this, "Tap the recipe's image to view on browser!", Toast.LENGTH_SHORT).show();
            editor.putBoolean("layout_initiated", true).apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            String message = String.format("Hey, look at this recipe for %s that I found. It's from %s ",
                    recipeTitle.getText().toString(), recipeLink);
            Intent share_recipe = new Intent(Intent.ACTION_SEND);
            share_recipe.putExtra(Intent.EXTRA_TEXT, message);
            share_recipe.setType("text/plain");
            startActivity(Intent.createChooser(share_recipe, "Share:"));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Uri recipeLink = Uri.parse(getIntent().getStringExtra("link"));
        Intent intent = new Intent(Intent.ACTION_VIEW, recipeLink);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
