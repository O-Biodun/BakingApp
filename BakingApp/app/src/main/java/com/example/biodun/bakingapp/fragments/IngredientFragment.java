package com.example.biodun.bakingapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biodun.bakingapp.DetailActivity;
import com.example.biodun.bakingapp.R;
import com.example.biodun.bakingapp.adapters.IngredientFragmentAdapter;
import com.example.biodun.bakingapp.data.Recipe;

import java.util.ArrayList;

/**
 * Created by Biodun on 6/26/2017.
 */

public class IngredientFragment  extends Fragment{
    private static final String RECIPE_KEY="recipe";
    private IngredientFragmentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Recipe.Ingredient> mIngredient=new ArrayList<>();

public IngredientFragment(){

}


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ingredient_fragment_layout,container,false);
        getIngredientArrayList();
        mRecyclerView=(RecyclerView) view.findViewById(R.id.ingredientRv);
        linearLayoutManager =new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new IngredientFragmentAdapter(getActivity(),mIngredient);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void  getIngredientArrayList(){

        Recipe recipe=getArguments().getParcelable(RECIPE_KEY);
        mIngredient=recipe.getIngredientArrayList();

    }

    static IngredientFragment newInstance(Recipe recipe){
        Bundle args=new Bundle();
        args.putParcelable(RECIPE_KEY,recipe);


        IngredientFragment frag=new IngredientFragment();
        frag.setArguments(args);
        return frag;
    }




}
