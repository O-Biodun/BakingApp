package com.example.biodun.bakingapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biodun.bakingapp.DetailActivity;
import com.example.biodun.bakingapp.ExoplayerActivity;
import com.example.biodun.bakingapp.R;
import com.example.biodun.bakingapp.adapters.StepFragmentAdapter;
import com.example.biodun.bakingapp.data.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment implements StepFragmentAdapter.VideoButtonClickHandler {
    private static final String RECIPE_KEY="recipe";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StepFragmentAdapter mAdapter;
    private ArrayList<Recipe.Steps>  mSteps=new ArrayList<>();
    private  final String VIDEO_URL="video_url";
    private  final String STEP="step";

    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_steps, container, false);
        getStepsArrayList();
        mRecyclerView=(RecyclerView) view.findViewById(R.id.stepsRv);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        mAdapter=new StepFragmentAdapter(getActivity(),mSteps,this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void  getStepsArrayList(){

        Recipe recipe=getArguments().getParcelable(RECIPE_KEY);
        mSteps=recipe.getStepsArrayList();

    }

    @Override
    public void onClickVideo(String videoUrl, String step) {

        Intent intent =new Intent(getActivity(), ExoplayerActivity.class);
        intent.putExtra(VIDEO_URL,videoUrl);
        intent.putExtra(STEP,step);
        startActivity(intent);


    }

    static StepsFragment newInstance(Recipe recipe){
        Bundle args=new Bundle();
        args.putParcelable(RECIPE_KEY,recipe);
        StepsFragment frag=new StepsFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
}
