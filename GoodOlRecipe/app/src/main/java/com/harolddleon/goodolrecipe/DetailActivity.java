package com.harolddleon.goodolrecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity  implements View.OnClickListener, View.OnLongClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView recipeImage = (ImageView) findViewById(R.id.recipeImageDetail);
        recipeImage.setOnClickListener(this);
        recipeImage.setOnLongClickListener(this);
        TextView recipeTitle = (TextView) findViewById(R.id.recipeTitleDetail);
        TextView recipeInfo = (TextView) findViewById(R.id.recipeTagDetail);
        TextView recipeDescription = (TextView) findViewById(R.id.recipeDescriptionDetail);

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
        if(!layout_initiated){
            Toast.makeText(this, "Tap the recipe's image to view on browser!", Toast.LENGTH_SHORT).show();
            editor.putBoolean("layout_initiated", true).apply();
        }



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
    public boolean onLongClick(View v) {
        return false;
    }
}
