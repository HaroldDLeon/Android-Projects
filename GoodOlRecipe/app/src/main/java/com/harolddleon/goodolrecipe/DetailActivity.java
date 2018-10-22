package com.harolddleon.goodolrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView recipeImage = (ImageView) findViewById(R.id.recipeImageDetail);
        TextView recipeTitle = (TextView) findViewById(R.id.recipeTitleDetail);
        TextView recipeDescription = (TextView) findViewById(R.id.recipeDescriptionDetail);

        Intent intent = getIntent();
        Glide.with(this).load(intent.getIntExtra("image", 0)).into(recipeImage);
        recipeTitle.setText(intent.getStringExtra("title"));
        recipeDescription.setText(intent.getStringExtra("description"));



    }

}