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

public class AddItemActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {
    private EditText recipeTitle;
    private EditText recipeDescription;
    private EditText recipeInfo;
    private EditText recipeLink;
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
        recipeInfo = (EditText) findViewById(R.id.editText_AddItemInfo);
        recipeLink = (EditText) findViewById(R.id.editText_AddItemLink);
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
        Intent intent = new Intent();
        intent.putExtra("new_recipe_title", recipeTitle.getText().toString());
        intent.putExtra("new_recipe_description", recipeDescription.getText().toString());
        intent.putExtra("new_recipe_info", recipeInfo.getText().toString());
        intent.putExtra("new_recipe_link", recipeLink.getText().toString());
        setResult(1, intent);
        finish();
    }
}
