package com.jaywhitsitt.eggsandbakey;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaywhitsitt.eggsandbakey.data.Recipe;

import java.util.List;

public class RecipesAdapter extends BaseAdapter {

    private static final String TAG = RecipesAdapter.class.getSimpleName();

    private Context mContext;
    private List<Recipe> mRecipes;

    public RecipesAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    @Override
    public Recipe getItem(int position) {
        return mRecipes == null ? null : mRecipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = getItem(position);
        if (recipe == null) {
            Log.e(TAG, "No recipe for given position");
            return null;
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.tile_recipe, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.tv_recipe_name);
        nameTextView.setText(recipe.getName());

        ImageView imageView = convertView.findViewById(R.id.iv_recipe_card);
        try {
            imageView.setImageURI(Uri.parse(recipe.getImageUrlString()));
            imageView.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            imageView.setVisibility(View.GONE);
        }

        return convertView;
    }

}
