package com.udacity.garuolis.bakingapp;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.garuolis.bakingapp.provider.IngredientColumns;
import com.udacity.garuolis.bakingapp.provider.RecipeProvider;
import com.udacity.garuolis.bakingapp.provider.StepColumns;
import com.udacity.garuolis.bakingapp.utils.ApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
public class StepDetailsFragment extends Fragment implements Player.EventListener {
    public static final String ARG_RECIPE_ID        = "recipe-id";
    public static final String ARG_STEP_ID          = "step-id";

    public static final String KEY_PLAYER_POSITION  = "player-position";

    OnStepNavigationListener mListener;

    @BindView(R.id.exo_player) PlayerView mPlayerView;
    @BindView(R.id.tv_short_description) TextView mShortDescriptionView;
    @BindView(R.id.tv_description) TextView mDescriptionView;
    @BindView(R.id.pb_loading_video) ProgressBar mLoading;
    @BindView(R.id.iv_image) ImageView mImage;
    @BindView(R.id.ll_wrap) LinearLayout mItemWrap;

    @BindView(R.id.fab_next) FloatingActionButton mBtnNext;
    @BindView(R.id.fab_prev) FloatingActionButton mBtnPrev;

    private ExoPlayer mPlayer;
    private long mPlayerPosition;

    private int mRecipeId;
    private int mStepId;
    private boolean hastNextStep = false;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    public int getCurrentStep() {
        return mStepId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeId = getArguments().getInt(ARG_RECIPE_ID);
            mStepId = getArguments().getInt(ARG_STEP_ID);
        }
    }

    String mVideoUrl = "";

    private void updateActionButtons() {
        mBtnNext.setVisibility(hastNextStep ? View.VISIBLE : View.GONE);
        mBtnPrev.setVisibility((mStepId > 0) ? View.VISIBLE : View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, v);

        if (mRecipeId != 0) {
            Cursor stepCursor = getActivity().getContentResolver().query(RecipeProvider.Steps.STEPS, null,
                    StepColumns.RECIPE_ID + " = " + mRecipeId + " AND " + StepColumns.NR + " >= " + mStepId, null, StepColumns.NR + " ASC");
            if (stepCursor.getCount() > 0) {
                stepCursor.moveToFirst();
                hastNextStep = (stepCursor.getCount() > 1);

                mVideoUrl = stepCursor.getString(stepCursor.getColumnIndex(StepColumns.VIDEO_URL));

                String description = stepCursor.getString(stepCursor.getColumnIndex(StepColumns.DESCRIPTION));
                String shortDescription = stepCursor.getString(stepCursor.getColumnIndex(StepColumns.SHORT_DESCRIPTION));

                mShortDescriptionView.setText(shortDescription);
                mDescriptionView.setText(description);

                // Recipe introduction, show ingredients
                if (mStepId == 0) {
                    Cursor inCursor = getActivity().getContentResolver().query(RecipeProvider.Ingredients.INGREDIENTS, null,IngredientColumns.RECIPE_ID + " = " + mRecipeId, null, null);
                    while (inCursor.moveToNext()) {
                        String ingredient   = inCursor.getString(inCursor.getColumnIndex(IngredientColumns.NAME));
                        String measure      = inCursor.getString(inCursor.getColumnIndex(IngredientColumns.MEASURE));
                        float quantity      = inCursor.getFloat(inCursor.getColumnIndex(IngredientColumns.QUANTITY));

                        View iv = inflater.inflate(R.layout.item_ingredient_list, null, false);
                        ((TextView) iv.findViewById(R.id.tv_ingredient)).setText(ingredient);
                        ((TextView) iv.findViewById(R.id.tv_quantity)).setText(quantity + " " + measure);
                        mItemWrap.addView(iv);
                    }
                }

                mBtnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onStepChange(mStepId + 1);
                        }
                    }
                });

                mBtnPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onStepChange(mStepId - 1);
                        }
                    }
                });

                if (savedInstanceState != null) {
                    mPlayerPosition = savedInstanceState.getLong(KEY_PLAYER_POSITION);
                }
                updateActionButtons();
            }
        }


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepNavigationListener) {
            mListener = (OnStepNavigationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepNavigationListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ApiUtils.ValidVideoUrl(mVideoUrl)) {
            initPlayer(Uri.parse(mVideoUrl));
        }
    }

    @Override
    public void onPause() {
        releasePlayer();
        super.onPause();
    }

    public interface OnStepNavigationListener {
        void onStepChange(int newStep);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPlayer != null) {
            outState.putLong(KEY_PLAYER_POSITION, mPlayer.getCurrentPosition());
        }
    }

    private void initPlayer(Uri mediaUri) {
        mLoading.setVisibility(View.VISIBLE);
        if (mPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            mPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);

            mPlayerView.setControllerAutoShow(false);
            mPlayerView.setControllerShowTimeoutMs(3000);

            //mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingApp"));
            ExtractorMediaSource.Factory mediaSourceFactory = new ExtractorMediaSource.Factory(dataSourceFactory);
            MediaSource mediaSource = mediaSourceFactory.createMediaSource(mediaUri);
            mPlayer.prepare(mediaSource);

            mPlayer.seekTo(mPlayerPosition);
            mPlayer.setPlayWhenReady(true);

            mPlayer.addListener(this);
        }

        mPlayerView.setPlayer(mPlayer);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayerPosition = mPlayer.getCurrentPosition();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_READY:
                // Forcing ratio via layout params to avoid ExoPlayerView glitching on start
                int width = getView().getWidth();
                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(width, width * 9 / 16);
                mPlayerView.setLayoutParams(lp);


                mLoading.setVisibility(View.GONE);
                mShortDescriptionView.setVisibility(View.GONE);
                mPlayerView.setVisibility(View.VISIBLE);
                break;

            case Player.STATE_ENDED:
                mPlayerPosition = 0;
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
