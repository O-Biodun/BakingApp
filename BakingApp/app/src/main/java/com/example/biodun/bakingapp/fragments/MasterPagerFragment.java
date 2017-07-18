package com.example.biodun.bakingapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.biodun.bakingapp.DetailActivity;
import com.example.biodun.bakingapp.MainActivity;
import com.example.biodun.bakingapp.R;
import com.example.biodun.bakingapp.adapters.MasterPageAdapter;
import com.example.biodun.bakingapp.data.Recipe;
import com.example.biodun.bakingapp.data.RecipeContract;
import com.example.biodun.bakingapp.data.RecipeDbHelper;
import com.example.biodun.bakingapp.utils.DatabaseUtil;
import com.example.biodun.bakingapp.utils.NetUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MasterPagerFragment extends Fragment implements MasterPageAdapter.MasterPageClickHandler {

    private final String ARRAY_RECIPE_ID="arrayRecipe";
    private final String RECIPE_ID = "recipe";
    private final String TAG="MasterFragment";
    private LinearLayoutManager mLinearLayoutManager;
    private MasterPageAdapter mAdapter;
    public static ArrayList<Recipe> mRecipe = new ArrayList<>();


    @BindView(R.id.masterPageRv)
    RecyclerView  mRecyclerView;
    @BindView(R.id.loadingBar)
    ProgressBar progressBar;

    Unbinder unbinder;

    public MasterPagerFragment(){

    }




    public static MasterPagerFragment newInstance(){
        MasterPagerFragment fragment=new MasterPagerFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master_pager, container, false);
       unbinder= ButterKnife.bind(this,view);
        mAdapter = new MasterPageAdapter(getActivity(), mRecipe,this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState==null && mRecipe.size()==0){
            if (NetUtil.isNetworkAvailable(getActivity())) {

                fetchRecipeData();
            } else {
                getDataFromDatabaseAsync.execute();
            }

        }




        return view;
    }

    @Override
    public void onClickRecipe(Recipe recipe) {

        //Put desired recipe into sharedPreference
        putSelectedIngreIntoSharedPref(recipe);


        if(MainActivity.getPaneMode()){
            DetailFragment fragment=DetailFragment.newInstance(recipe);
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.detailSide,fragment).commit();

        }else{
            Intent intent =new Intent(getActivity(),DetailActivity.class);
            intent.putExtra(RECIPE_ID,recipe);
            startActivity(intent);

        }




    }


    public void fetchRecipeData() {


        progressBar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        String url = NetUtil.getRecipeUrl();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "onFailure:Failed to retrieve network resources ",e);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Failed to retrieve network resource.",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.i(TAG,"Unexcepted response");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Bad response.Try again",Toast.LENGTH_SHORT).show();
                        }
                    });
                    throw new IOException("Unexpected code " + response);



                } else {
                    MainActivity.syncedState=true;
                    try {
                        String responseData = response.body().string();


                        final ArrayList<Recipe> recipes;
                        recipes = NetUtil.parseJsonResult(responseData);
                        mRecipe.clear();
                        mRecipe.addAll(recipes);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                loadIntoDatabaseInBackground.execute();
                                mAdapter.notifyDataSetChanged();

                            }
                        });


                    } catch (Exception e) {

                    }

                }
            }
        });


    }

    AsyncTask<Void, Void, ArrayList<Recipe>> getDataFromDatabaseAsync = new AsyncTask<Void, Void, ArrayList<Recipe>>() {
        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {

            RecipeDbHelper dbHelper = new RecipeDbHelper(getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String table = RecipeContract.RecipeEntry.TABLE_NAME;
            String[] projection = {RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME,
                    RecipeContract.RecipeEntry.COLUMN_SERVINGS,
                    RecipeContract.RecipeEntry.COLUMN_RECIPE_ID,
                    RecipeContract.RecipeEntry.COLUMN_IMAGE};
            String[] stepProjection = {RecipeContract.StepEntry.COLUMN_VIDEO_URL, RecipeContract.StepEntry.COLUMN_STEP_ID,
                    RecipeContract.StepEntry.COLUMN_DESCRIPTION, RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION, RecipeContract.StepEntry.COLUMN_IMAGE_ID};
            String[] ingredientProjection = {RecipeContract.IngredientEntry.COLUMN_INGREDIENT, RecipeContract.IngredientEntry.COLUMN_MEASURE,
                    RecipeContract.IngredientEntry.COLUMN_QUANTITY};
            String selection = RecipeContract.RecipeEntry.COLUMN_RECIPE_ID + "=?";
            String stepSelection = RecipeContract.StepEntry.COLUMN_RECIPE_ID + "=?";
            String ingredientSelection = RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + "=?";

            return DatabaseUtil.getRecipeFromDatabase(getActivity(), db, table, projection, selection, stepProjection, stepSelection, ingredientProjection, ingredientSelection);
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            super.onPostExecute(recipes);

            mRecipe.clear();
            mRecipe.addAll(recipes);


            mAdapter.notifyDataSetChanged();
        }
    };

    AsyncTask<Void,Void,Uri> loadIntoDatabaseInBackground=new AsyncTask<Void, Void, Uri>() {
        @Override
        protected Uri doInBackground(Void... voids) {
            Uri uri=DatabaseUtil.loadRecipeIntoDatabase(getActivity(),mRecipe);
            return uri;
        }

        @Override
        protected void onPostExecute(Uri uri) {
            super.onPostExecute(uri);


        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARRAY_RECIPE_ID,mRecipe);

    }
    public void putSelectedIngreIntoSharedPref(Recipe recipe){
        ArrayList<Recipe.Ingredient> ingredients=recipe.getIngredientArrayList();
        Set<String> ingredientNameList=new HashSet<>();
        for(int i=0;i<ingredients.size();i++){
            String ingredientName=ingredients.get(i).getIngredientName();
            ingredientNameList.add(ingredientName);
        }
        SharedPreferences sharedPref=getActivity().getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putStringSet(getString( R.string.pref_ingredient_list),ingredientNameList);
        editor.apply();
        updateWidget(ingredientNameList);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    public static ArrayList<Recipe> getRecipe(){
        return mRecipe;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void updateWidget(Set<String> ingredient){
        Intent intent=new Intent();
        intent.setAction("com.example.biodun.bakingapp.fragment.WIDGET_UPDATE_BROADCAST");
        ArrayList<String> ingredients=new ArrayList<>(ingredient);
        intent.putStringArrayListExtra("extra",ingredients);
        getActivity().sendBroadcast(intent);

    }
}