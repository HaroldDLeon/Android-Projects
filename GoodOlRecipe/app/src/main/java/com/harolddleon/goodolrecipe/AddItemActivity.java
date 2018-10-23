package com.harolddleon.goodolrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {
    private EditText recipeTitle;
    private EditText recipeDescription;
    private Button recipeAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(R.string.activity_add_item);
        setSupportActionBar(toolbar);

        recipeTitle = (EditText) findViewById(R.id.editText_AddItemTitle);
        recipeDescription = (EditText) findViewById(R.id.editText_AddItemDescription);
        recipeAddButton = (Button) findViewById(R.id.button_recipeAdd);
        recipeAddButton.setOnClickListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
        intent.putExtra("new_recipe_title", recipeTitle.getText().toString());
        intent.putExtra("new_recipe_description", recipeDescription.getText().toString());
        startActivity(intent);

    }
}
