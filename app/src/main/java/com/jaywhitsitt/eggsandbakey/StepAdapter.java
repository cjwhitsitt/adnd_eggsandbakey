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

import com.jaywhitsitt.eggsandbakey.data.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private final StepListActivity mParentActivity;
    private final List<Step> mValues;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Step step = (Step) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putSerializable(StepDetailFragment.ARG_STEP, step);
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putExtra(StepDetailFragment.ARG_STEP, step);
                context.startActivity(intent);
            }
        }
    };

    public StepAdapter(StepListActivity parent,
                       List<Step> items,
                       boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @NonNull
    @Override
    public StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_content, parent, false);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StepAdapterViewHolder holder, int position) {
        holder.mIdView.setText(String.valueOf(mValues.get(position).stepId + 1));
        holder.mContentView.setText(mValues.get(position).shortDescription);

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
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

}
