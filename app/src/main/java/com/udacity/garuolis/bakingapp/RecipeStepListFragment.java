package com.udacity.garuolis.bakingapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.garuolis.bakingapp.provider.RecipeProvider;
import com.udacity.garuolis.bakingapp.provider.StepColumns;

public class RecipeStepListFragment extends Fragment {
    public static final String ARG_RECIPE_ID = "recipe-id";
    private int mRecipeId = 0;

    private OnRecipeStepInteractionListener mListener;

    public RecipeStepListFragment() {
    }

    public static RecipeStepListFragment newInstance(int recipeId) {
        RecipeStepListFragment fragment = new RecipeStepListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mRecipeId = getArguments().getInt(ARG_RECIPE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipestep_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if (mRecipeId != 0) {
                Cursor stepCursor = getActivity().getContentResolver().query(RecipeProvider.Steps.STEPS, null, StepColumns.RECIPE_ID + " = " + mRecipeId, null, StepColumns.NR + " ASC");
                recyclerView.setAdapter(new RecipeStepListAdapter(stepCursor, mListener));
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeStepInteractionListener) {
            mListener = (OnRecipeStepInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeStepInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRecipeStepInteractionListener {
        void onStepSelected(int stepNr);
    }
}
