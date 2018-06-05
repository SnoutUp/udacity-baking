package com.udacity.garuolis.bakingapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.garuolis.bakingapp.provider.RecipeColumns;
import com.udacity.garuolis.bakingapp.utils.ApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private final OnInteractionListener mListener;
    private Cursor mCursor;
    private Context mContext;



    public RecipeListAdapter(Context context, Cursor cursor, OnInteractionListener listener) {
        mContext    = context;
        mCursor     = cursor;
        mListener   = listener;
    }

    public void updateCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        mCursor.moveToPosition(position);
        String title = mCursor.getString(mCursor.getColumnIndex(RecipeColumns.NAME));
        String imageUrl = mCursor.getString(mCursor.getColumnIndex(RecipeColumns.IMAGE));
        int servings = mCursor.getInt(mCursor.getColumnIndex(RecipeColumns.SERVINGS));
        final int id = mCursor.getInt(mCursor.getColumnIndex(RecipeColumns._ID));

        holder.mTitle.setText(title);
        holder.mServings.setText(String.format(mContext.getResources().getString(R.string.string_servings), servings));
        if (ApiUtils.ValidImageUrl(imageUrl)) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.cakes).into(holder.mImage);
        } else {
            holder.mImage.setImageResource(R.drawable.cakes);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onItemSelected(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title) public TextView mTitle;
        @BindView(R.id.tv_servings) public TextView mServings;
        @BindView(R.id.iv_image) public ImageView mImage;
        @BindView(R.id.item) public View mView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnInteractionListener {
        void onItemSelected(int recipeId);
    }
}
