package com.example.biodun.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Biodun on 6/29/2017.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="recipeDb.db";
    private static final int DATABASE_VERSION=1;
    public RecipeDbHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //SQL Statement to create recipe table
        final String CREATE_RECIPE_TABLE = "CREATE TABLE "  + RecipeContract.RecipeEntry.TABLE_NAME + " (" +
                RecipeContract.RecipeEntry._ID                + " INTEGER PRIMARY KEY, " +
                RecipeContract.RecipeEntry.COLUMN_RECIPE_ID+ " INTEGER NOT NULL, " +
                RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME + " STRING NOT NULL, " +
                RecipeContract.RecipeEntry.COLUMN_IMAGE + " STRING , " +
                RecipeContract.RecipeEntry.COLUMN_SERVINGS + " INTEGER NOT NULL);";

        //SQL Statement to create step table
        final String CREATE_STEP_TABLE = "CREATE TABLE "  + RecipeContract.StepEntry.TABLE_NAME + " (" +
                RecipeContract.StepEntry._ID                + " INTEGER PRIMARY KEY, " +
                RecipeContract.StepEntry.COLUMN_STEP_ID+ " INTEGER NOT NULL, " +
                RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION+ " STRING NOT NULL, " +
                RecipeContract.StepEntry.COLUMN_RECIPE_ID+ " INTEGER NOT NULL, " +
                RecipeContract.StepEntry.COLUMN_VIDEO_URL+ " STRING , " +
                RecipeContract.StepEntry.COLUMN_IMAGE_ID+ " STRING , " +
                RecipeContract.StepEntry.COLUMN_DESCRIPTION + " TEXT );";

        //SQL Statement to create ingredient  table
        final String CREATE_INGREDIENT_TABLE = "CREATE TABLE "  + RecipeContract.IngredientEntry.TABLE_NAME + " (" +
                RecipeContract.IngredientEntry._ID                + " INTEGER PRIMARY KEY, " +
                RecipeContract.IngredientEntry.COLUMN_RECIPE_ID+ " INTEGER NOT NULL, " +
                RecipeContract.IngredientEntry.COLUMN_QUANTITY+ " REAL NOT NULL, " +
                RecipeContract.IngredientEntry.COLUMN_MEASURE+ " STRING NOT NULL, " +
                RecipeContract.IngredientEntry.COLUMN_INGREDIENT + " STRING NOT NULL);";


        String[] ArrayOfCommands={
                CREATE_INGREDIENT_TABLE,CREATE_RECIPE_TABLE,CREATE_STEP_TABLE
        };
        for(int i=0;i<ArrayOfCommands.length;i++){
            sqLiteDatabase.execSQL(ArrayOfCommands[i]);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.StepEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.IngredientEntry.TABLE_NAME);
        onCreate(db);

    }

}
