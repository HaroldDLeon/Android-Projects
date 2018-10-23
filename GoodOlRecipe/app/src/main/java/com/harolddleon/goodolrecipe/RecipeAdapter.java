package com.harolddleon.goodolrecipe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<Recipe> recipes;
    private Context context;

    RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder, int i) {
        Recipe currentRecipe = recipes.get(i);
        viewHolder.bindTo(currentRecipe);
        Glide.with(context).load(currentRecipe.getImageResource()).into(viewHolder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleText;
        private TextView infoText;
        private TextView descriptionText;
        private ImageView recipeImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.title);
            infoText = (TextView) itemView.findViewById(R.id.subTitle);
            descriptionText = (TextView) itemView.findViewById(R.id.recipeDescriptionDetail);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipeImage);
            itemView.setOnClickListener(this);

        }

        void bindTo(Recipe currentRecipe) {
            titleText.setText(currentRecipe.getTitle());
            infoText.setText(currentRecipe.getInfo());

        }

        @Override
        public void onClick(View v) {
            Recipe currentRecipe = recipes.get(getAdapterPosition());
            Intent detail_intent = new Intent(context, DetailActivity.class);
            detail_intent.putExtra("title", currentRecipe.getTitle());
            detail_intent.putExtra("image", currentRecipe.getImageResource());
            detail_intent.putExtra("description", currentRecipe.getDescription());
            context.startActivity(detail_intent);

        }
    }
}
