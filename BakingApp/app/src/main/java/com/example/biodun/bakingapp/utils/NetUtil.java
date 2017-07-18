package com.example.biodun.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.biodun.bakingapp.data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Biodun on 6/25/2017.
 */

public class NetUtil {

    private static ArrayList<Recipe> recipe=new ArrayList<>();
    private static int recipeId;
    private static String recipeName;
    private static int servings;
    private static String image;

    private static  String reipeUrl="http://go.udacity.com/android-baking-app-json";

    public static String getRecipeUrl(){

        return reipeUrl;
    }
    public static ArrayList<Recipe> parseJsonResult(String jsonResult){
        try {




            JSONArray rootArray=new JSONArray(jsonResult) ;
            for (int i=0;i<rootArray.length();i++){
                ArrayList<Recipe.Ingredient> ingredients=new ArrayList<>();
                ArrayList<Recipe.Steps> steps=new ArrayList<>();
                JSONObject eachRecipeObject=rootArray.getJSONObject(i);
                recipeId=eachRecipeObject.getInt("id");
                recipeName=eachRecipeObject.getString("name");
                servings=eachRecipeObject.getInt("servings");
                image=eachRecipeObject.getString("image");
                JSONArray ingredientArray=eachRecipeObject.getJSONArray("ingredients");
                for (int j=0; j<ingredientArray.length();j++){
                    JSONObject eachIngredientObject=ingredientArray.getJSONObject(j);
                    double quantity=eachIngredientObject.getDouble("quantity");
                    String measure=eachIngredientObject.getString("measure");
                    String ingredient=eachIngredientObject.getString("ingredient");
                    Recipe.Ingredient eachIngredient=new Recipe.Ingredient(quantity,measure,ingredient);
                    ingredients.add(eachIngredient);
                }
                JSONArray stepsArray=eachRecipeObject.getJSONArray("steps");
                for(int k=0;k<stepsArray.length();k++){
                    JSONObject eachStepObject=stepsArray.getJSONObject(k);
                    int id=eachStepObject.getInt("id");
                    String shortDescription=eachStepObject.getString("shortDescription");
                    String description=eachStepObject.getString("description");
                    String videoUrl=eachStepObject.getString("videoURL");
                    String thumbnailUrl=eachStepObject.getString("thumbnailURL");
                    Recipe.Steps eachStep=new Recipe.Steps(id,shortDescription,description,videoUrl,thumbnailUrl);
                    steps.add(eachStep);
                }

                Recipe eachRecipe=new Recipe(recipeId,recipeName,servings,ingredients,steps,image);
                recipe.add(eachRecipe);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

     return recipe;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
