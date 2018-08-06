package com.innopolis.sergeypinkevich.bakingapp.feature.detail.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.AdapterClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder> {

    private AdapterClickListener listener;
    private List<String> recipes;

    public RecipeDetailAdapter(AdapterClickListener listener) {
        this.listener = listener;
    }

    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
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

        @BindView(R.id.recipe_step_text)
        TextView stepText;

        public ViewHolder(View itemView, AdapterClickListener clickListener) {
            super(itemView);
            listener = clickListener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String s) {
            stepText.setText(s);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
