package com.udacity.garuolis.bakingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.udacity.garuolis.bakingapp.provider.RecipeColumns;
import com.udacity.garuolis.bakingapp.provider.RecipeProvider;

public class RecipeActivity extends AppCompatActivity implements RecipeStepListFragment.OnRecipeStepInteractionListener, StepDetailsFragment.OnStepNavigationListener {
    private final static String TAG             = RecipeActivity.class.getName();

    public final static String EXTRA_ID         = "recipeId";
    public final static String EXTRA_STEP_NR    = "stepNr";

    public final static String TAG_STEP_FRAGMENT= "detailsFragment";

    public final static String EXTRA_TITLE  = "recipeTitle";

    public int mRecipeId    = 0;
    public int mMaxSteps    = 0;

    public int mContainer   = R.id.container_main;
    public int mSidebar     = R.id.container_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean isTablet = getResources().getBoolean(R.bool.tablet);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mRecipeId = bundle.getInt(RecipeActivity.EXTRA_ID);

            Cursor c = getContentResolver().query(RecipeProvider.Recipes.RECIPES, null, RecipeColumns._ID + " = " + mRecipeId, null, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                String title = c.getString(c.getColumnIndex(RecipeColumns.NAME));
                getSupportActionBar().setTitle(title);
            }

            if (isTablet) {
                mSidebar = R.id.container_sidebar;
                if (savedInstanceState == null) {
                    showStepDetails(0, false);
                }
            }
            showStepListFragment();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
        int lastStep = savedInstanceState.getInt(EXTRA_STEP_NR);
        if (lastStep != -1) {
            showStepDetails(lastStep, true);
        }
        */

        if (savedInstanceState != null) {
            try {
                StepDetailsFragment savedStepFragment = (StepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, TAG_STEP_FRAGMENT);
                if (savedStepFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(mContainer, savedStepFragment, TAG_STEP_FRAGMENT);
                    transaction.commit();
                }
            } catch (IllegalStateException e) {
                Log.e(TAG, "Failed to restore fragment", e);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int step = -1;
        StepDetailsFragment myFragment = (StepDetailsFragment)getSupportFragmentManager().findFragmentByTag(TAG_STEP_FRAGMENT);
        if (myFragment != null && myFragment.isVisible()) {
            step = myFragment.getCurrentStep();
            getSupportFragmentManager().putFragment(outState, TAG_STEP_FRAGMENT, myFragment);
        }
        outState.putInt(EXTRA_STEP_NR, step);

/*
        if (getSupportFragmentManager().findFragmentByTag(TAG_STEP_FRAGMENT) != null && mStepFragment != null) {
            getSupportFragmentManager().putFragment(outState, TAG_STEP_FRAGMENT, mStepFragment);
        }
        */

    }

    public void showStepListFragment() {
        RecipeStepListFragment frag = new RecipeStepListFragment();
        Bundle args = new Bundle();
        args.putInt(RecipeStepListFragment.ARG_RECIPE_ID, mRecipeId);
        frag.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mSidebar, frag);
        transaction.commit();
    }

    public void showStepDetails(int stepNr, boolean addToBackStack) {
        StepDetailsFragment frag = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(StepDetailsFragment.ARG_RECIPE_ID, mRecipeId);
        args.putInt(StepDetailsFragment.ARG_STEP_ID, stepNr);
        frag.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mContainer, frag, TAG_STEP_FRAGMENT);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onStepSelected(int stepNr) {
        showStepDetails(stepNr, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // go back to the step list if ActionBar home button pressed
            // instead of moving all the way through the Fragment backstack

            FragmentManager fm = getSupportFragmentManager();
            for(int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
                fm.popBackStack();
            }
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepChange(int newStep) {
        showStepDetails(newStep, true);
    }
}
