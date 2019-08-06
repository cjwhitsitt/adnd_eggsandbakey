package com.jaywhitsitt.eggsandbakey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaywhitsitt.eggsandbakey.data.Recipe;
import com.jaywhitsitt.eggsandbakey.data.Step;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    public interface ShowDetailListener {
        void showStep(Step step, View view);
        void showIngredients(View view);
    }

    private static final int POSITION_NOT_SELECTED = -1;

    private final Context mContext;
    private final ShowDetailListener mShowListener;
    private final boolean mTwoPane;
    private List<Step> mSteps;
    private int mSelectedPosition = POSITION_NOT_SELECTED;

    public StepAdapter(Context context, ShowDetailListener listener, boolean twoPane) {
        mContext = context;
        mShowListener = listener;
        mTwoPane = twoPane;
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int numberOfSteps = mSteps == null ? 0 : mSteps.size();
        return numberOfSteps + 1;
    }

    @NonNull
    @Override
    public StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_content, parent, false);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StepAdapterViewHolder holder, final int position) {
        holder.mIdView.setVisibility(position > 1 ? View.VISIBLE : View.GONE);

        String description;
        Serializable tag;
        if (position == 0) {
            description = mContext.getString(R.string.title_recipe_ingredients);
            tag = null;
        } else {
            int index = position - 1;
            description = mSteps.get(index).shortDescription;
            tag = mSteps.get(index);
            if (index > 0) {
                holder.mIdView.setText(String.valueOf(mSteps.get(index).stepId));
            }
        }
        holder.mContentView.setText(description);
        holder.itemView.setTag(tag);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPosition = position;
                if (position == 0) {
                    mShowListener.showIngredients(v);
                } else {
                    mShowListener.showStep((Step) v.getTag(), v);
                }
            }
        });

        if (mTwoPane && mSelectedPosition == POSITION_NOT_SELECTED && position == 0) {
            holder.itemView.performClick();
        }
    }

    class StepAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id_text)
        TextView mIdView;
        @BindView(R.id.content)
        TextView mContentView;

        public StepAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

    }

}
