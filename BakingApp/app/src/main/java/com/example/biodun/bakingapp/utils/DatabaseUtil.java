package com.example.biodun.bakingapp.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.biodun.bakingapp.data.Recipe;
import com.example.biodun.bakingapp.data.RecipeContract;
import com.example.biodun.bakingapp.data.RecipeDbHelper;

import java.util.ArrayList;

/**
 * Created by Biodun on 6/29/2017.
 */

public class DatabaseUtil {
    static ArrayList<Recipe> mRecipe=new ArrayList<>();
    static String recipeName;
     static int recipeId;
    static int servings;
    static String image;
   static  Recipe recipe;
   static ArrayList<Recipe.Steps> step=new ArrayList<>();
   static ArrayList<Recipe.Ingredient> ingredient=new ArrayList<>();

    public static ArrayList<Recipe> getRecipeFromDatabase(Context context, SQLiteDatabase db,
                                                          String table,String[] projection,
                                                          String selection,
                                                          String[] stepProjection,String stepSelection,
                                                          String[] ingredientProjection,String ingredientSelection
                                                          ){

        if(mRecipe.size()>0){
            mRecipe.clear();
        }
        long tableSize=getRecipeSize(db,table);
        for(long i=1;i<=tableSize;i++){

             String[] selectionArg={String.valueOf(i)};

            Cursor output=queryRecipeDhhelper(RecipeContract.RecipeEntry.RECIPE_CONTENT_URI,
                    context,projection,selection,selectionArg);


            setRecipeProperties(output);
            Cursor stepOutput=queryRecipeDhhelper(RecipeContract.StepEntry.STEP_CONTENT_URI,context,stepProjection,
                    stepSelection,selectionArg);

            step=  getStepProperties(stepOutput);
            Cursor ingredientOutput=queryRecipeDhhelper(RecipeContract.IngredientEntry.INGREDIENT_CONTENT_URI,
                                                        context,ingredientProjection,ingredientSelection,selectionArg);

            ingredient=getIngredientProperties(ingredientOutput);
            recipe=new Recipe(recipeId,recipeName,servings,ingredient,step,image);



            mRecipe.add(recipe);

        }


        return mRecipe;
    }
    private static long getRecipeSize(SQLiteDatabase db,String table){
        return DatabaseUtils.queryNumEntries(db,table);
    }

    private static void setRecipeProperties(Cursor cursor ){
        while(cursor.moveToNext()){
            recipeId=cursor.getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID));
            recipeName=cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME));
            servings=cursor.getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_SERVINGS));
            image=cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_IMAGE));

        }

    }
    private static Cursor queryRecipeDhhelper(Uri uri,Context context,String[] projection,
                                             String selection,String[] selectionArgs){
        ContentResolver resolver=context.getContentResolver();
        Cursor output=resolver.query(uri,
                projection,selection,selectionArgs,null);
        return output;
    }

    private static ArrayList<Recipe.Steps> getStepProperties(Cursor cursor){
        ArrayList<Recipe.Steps> stepsArrayList=new ArrayList<>();
        while(cursor.moveToNext()){
            Recipe.Steps step;
            int step_id=cursor.getInt(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_STEP_ID));
            String shortDesc=cursor.getString(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION));
            String videoUrl=cursor.getString(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_VIDEO_URL));
            String fullDesc=cursor.getString(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_DESCRIPTION));
            String imageUrl=cursor.getString(cursor.getColumnIndex(RecipeContract.StepEntry.COLUMN_IMAGE_ID));
            step=new Recipe.Steps(step_id,shortDesc,fullDesc,videoUrl,imageUrl);
            stepsArrayList.add(step);
        }
    return stepsArrayList;
    }
    private static ArrayList<Recipe.Ingredient> getIngredientProperties(Cursor cursor){
        ArrayList<Recipe.Ingredient> ingredientArrayList=new ArrayList<>();
        while (cursor.moveToNext()){
            Recipe.Ingredient ingredients;
            double quantity =cursor.getDouble(cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_QUANTITY));
            String measure=cursor.getString(cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_MEASURE));
            String ingredientName=cursor.getString(cursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT));
            ingredients=new Recipe.Ingredient(quantity,measure,ingredientName);
            ingredientArrayList.add(ingredients);
        }
        return ingredientArrayList;
    }

   public static Uri loadRecipeIntoDatabase( Context context,ArrayList<Recipe> recipe){
       RecipeDbHelper dbHelper=new RecipeDbHelper(context);
       SQLiteDatabase db=dbHelper.getWritableDatabase();
       dbHelper.onUpgrade(db,0,0);
       Uri uri =null;
       for(int i=0;i<recipe.size();i++){
           Recipe eachRecipe=recipe.get(i);
           uri=loadIntoRecipeTable(context,eachRecipe);
           ArrayList<Recipe.Steps> eachRecipeStep=eachRecipe.getStepsArrayList();
           loadIntoStepTable(context,eachRecipeStep,i+1);
           ArrayList<Recipe.Ingredient> eachRecipeIngredient=eachRecipe.getIngredientArrayList();
           loadIntoIngredientTable(context,eachRecipeIngredient,i+1);



       }
return uri;
   }
    private static Uri loadIntoRecipeTable(Context context,Recipe recipe){
        ContentValues contentValues=new ContentValues();
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID,recipe.getId());
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME,recipe.getRecipeName());
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_SERVINGS,recipe.getServings());
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_IMAGE,recipe.getImage());
        Uri uri=context.getContentResolver().insert(RecipeContract.RecipeEntry.RECIPE_CONTENT_URI,contentValues);
     return uri;
    }

    private static void loadIntoStepTable(Context context, ArrayList<Recipe.Steps> stepsArrayList,int recipeIndex){
        for(int i=0;i<stepsArrayList.size();i++){
            Recipe.Steps eachStepEntry=stepsArrayList.get(i);
            ContentValues contentValues=new ContentValues();
            contentValues.put(RecipeContract.StepEntry.COLUMN_RECIPE_ID,recipeIndex);
            contentValues.put(RecipeContract.StepEntry.COLUMN_STEP_ID,i);
            contentValues.put(RecipeContract.StepEntry.COLUMN_VIDEO_URL,eachStepEntry.getVideoUrl());
            contentValues.put(RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION,eachStepEntry.getShortDescription());
            contentValues.put(RecipeContract.StepEntry.COLUMN_IMAGE_ID,eachStepEntry.getThumbnailUrl());
            contentValues.put(RecipeContract.StepEntry.COLUMN_DESCRIPTION,eachStepEntry.getDescription());

            Uri uri=context.getContentResolver().insert(RecipeContract.StepEntry.STEP_CONTENT_URI,contentValues);


        }


    }

    private static  void loadIntoIngredientTable(Context context,
                                                 ArrayList<Recipe.Ingredient> ingredientArrayList,
                                                 int recipeIndex){

        for (int i=0;i<ingredientArrayList.size();i++){
            Recipe.Ingredient eachIngredientEntry=ingredientArrayList.get(i);
            ContentValues contentValues=new ContentValues();
            contentValues.put(RecipeContract.IngredientEntry.COLUMN_RECIPE_ID,recipeIndex);
            contentValues.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT,eachIngredientEntry.getIngredientName());
            contentValues.put(RecipeContract.IngredientEntry.COLUMN_MEASURE,eachIngredientEntry.getMeasure());
            contentValues.put(RecipeContract.IngredientEntry.COLUMN_QUANTITY,eachIngredientEntry.getQuantity());
            Uri uri=context.getContentResolver().insert(RecipeContract.IngredientEntry.INGREDIENT_CONTENT_URI,contentValues);

        }

    }



}
