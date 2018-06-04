package com.udacity.garuolis.bakingapp;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.garuolis.bakingapp.provider.StepColumns;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepListAdapter extends RecyclerView.Adapter<RecipeStepListAdapter.ViewHolder> {
    private final Cursor mCursor;
    private final RecipeStepListFragment.OnRecipeStepInteractionListener mListener;

    public RecipeStepListAdapter(Cursor cursor, RecipeStepListFragment.OnRecipeStepInteractionListener listener) {
        mCursor = cursor;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        final int nr = mCursor.getInt(mCursor.getColumnIndex(StepColumns.NR));

        holder.mTitleView.setText(mCursor.getString(mCursor.getColumnIndex(StepColumns.SHORT_DESCRIPTION)));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onStepSelected(nr);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item) public View mView;
        @BindView(R.id.tv_title) public TextView mTitleView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
