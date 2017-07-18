package com.example.biodun.bakingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.biodun.bakingapp.data.Recipe;
import com.example.biodun.bakingapp.fragments.DetailFragment;


public class DetailActivity extends AppCompatActivity {
    private static  Recipe mRecipe;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState==null){
            setRecipe();
            DetailFragment detailFragment=DetailFragment.newInstance(mRecipe);
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.detail_fragment,detailFragment).commit();
        }
    }
    private void setRecipe(){
        Intent intent=getIntent();
        mRecipe=intent.getParcelableExtra(MainActivity.RECIPE_ID);
    }
    public static Recipe getRecipe(){
        return mRecipe;
    }

}

