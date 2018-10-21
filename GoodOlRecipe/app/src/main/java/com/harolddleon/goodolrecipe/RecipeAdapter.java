package com.harolddleon.goodolrecipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import com.bumptech.glide.Glide;
import org.w3c.dom.Text;

import java.util.ArrayList;

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<Recipe> recipes;
    private Context context;

    RecipeAdapter(Context context, ArrayList<Recipe> recipes){
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
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        private TextView infoText;
        private ImageView recipeImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.title);
            infoText = (TextView)itemView.findViewById(R.id.subTitle);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipeImage);

        }
        void bindTo(Recipe currentRecipe){
            titleText.setText(currentRecipe.getTitle());
            infoText.setText(currentRecipe.getInfo());

        }
    }
}
