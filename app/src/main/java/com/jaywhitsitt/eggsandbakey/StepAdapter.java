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

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private final StepListActivity mParentActivity;
    private List<Step> mSteps;
    private final boolean mTwoPane;

    public StepAdapter(StepListActivity parent,
                       boolean twoPane) {
        mParentActivity = parent;
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
            description = "Recipe Ingredients";
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
                if (position == 0) {
                    // TODO: open ingredients
                } else {
                    showStep((Step) v.getTag(), v);
                }
            }
        });
    }

    class StepAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView mIdView;
        final TextView mContentView;

        public StepAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            mIdView = (TextView) itemView.findViewById(R.id.id_text);
            mContentView = (TextView) itemView.findViewById(R.id.content);
        }

    }

    private void showStep(Step step, View view) {
        Bundle extras = new Bundle();
        extras.putSerializable(StepDetailFragment.ARG_STEP, step);
        extras.putBoolean(StepDetailFragment.ARG_IS_LAST, step.stepId >= mSteps.size() - 1);

        if (mTwoPane) {
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(extras);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();

            View playFab = mParentActivity.findViewById(R.id.fab_play);
            if (playFab != null) {
                boolean hasVideo = step.videoUrl != null && step.videoUrl.length() > 0;
                playFab.setVisibility(hasVideo ? View.VISIBLE : View.GONE);
            }

        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, StepDetailActivity.class);
            intent.putExtras(extras);
            context.startActivity(intent);
        }
    }

}
