package com.innopolis.sergeypinkevich.bakingapp.feature.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.di.scope.ApplicationContext;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Sergey Pinkevich
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> recipes;

    public MainAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        recipes = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_image)
        ImageView recipeImage;
        @BindView(R.id.recipe_name)
        TextView recipeName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Recipe recipe) {
            int imageId = context.getResources().getIdentifier(recipe.getName().toLowerCase().split("\\s+")[0], "drawable", context.getPackageName());
            recipeImage.setImageResource(imageId);
            recipeName.setText(recipe.getName());
        }
    }
}
