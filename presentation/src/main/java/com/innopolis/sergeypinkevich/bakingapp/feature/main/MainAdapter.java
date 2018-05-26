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

    private AdapterClickListener listener;
    private Context context;
    private List<Recipe> recipes;

    public MainAdapter(AdapterClickListener listener) {
        this.listener = listener;
    }

    public void setData(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_image)
        ImageView recipeImage;
        @BindView(R.id.recipe_name)
        TextView recipeName;

        public ViewHolder(View itemView, AdapterClickListener clickListener) {
            super(itemView);
            listener = clickListener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Recipe recipe) {
            int imageId = context.getResources().getIdentifier(recipe.getName().toLowerCase().split("\\s+")[0], "drawable", context.getPackageName());
            recipeImage.setImageResource(imageId);
            recipeName.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
