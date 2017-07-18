package com.example.biodun.bakingapp.data;

import android.content.ContentProvider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Biodun on 6/29/2017.
 */

public class RecipeContentProvider extends ContentProvider {

    RecipeDbHelper mRecipeDbHelper;

    //UriMatcher constants for RECIPE
    public static final int RECIPE=100;
    public static final int RECIPE_WITH_ID=101;

    //UriMatcher constants for STEP
    public static final int STEP=200;
    public static final int STEP_WITH_ID=201;

    //UriMatcher constants for INGREDIENT
    public static final int INGREDIENT=300;
    public static final int INGREDIENT_WITH_ID=301;

    public static UriMatcher buildUriMatcher(){
     UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_RECIPE,RECIPE);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_RECIPE +"/#",RECIPE_WITH_ID);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_STEP,STEP);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_STEP+"/#",STEP_WITH_ID);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_INGREDIENT,INGREDIENT);
        uriMatcher.addURI(RecipeContract.AUTHORITY,RecipeContract.PATH_INGREDIENT+"/#",INGREDIENT_WITH_ID);
        return uriMatcher;

    }

    public static final UriMatcher sUriMatcher=buildUriMatcher();




    @Override
    public boolean onCreate() {
        Context context=getContext();
        mRecipeDbHelper=new RecipeDbHelper(context);
        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] columns, String selection, String[] selectionArgs,@Nullable String sortBy) {
        Cursor output;
        SQLiteDatabase db=mRecipeDbHelper.getReadableDatabase();
        int match=sUriMatcher.match(uri);
        switch(match){
            case(RECIPE):
                output=db.query(RecipeContract.RecipeEntry.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
                break;

            case(STEP):
                output=db.query(RecipeContract.StepEntry.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
                break;
            case(INGREDIENT):
                output=db.query(RecipeContract.IngredientEntry.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri"+uri);

        }
        output.setNotificationUri(getContext().getContentResolver(), uri);

        return output;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        int match =sUriMatcher.match(uri);
        SQLiteDatabase db=mRecipeDbHelper.getWritableDatabase();
        Uri returnUri;
        long id;

        switch(match){
            case(RECIPE):
                id=db.insert(RecipeContract.RecipeEntry.TABLE_NAME,null,values);
                if (id>0){
                    returnUri= ContentUris.withAppendedId(RecipeContract.RecipeEntry.RECIPE_CONTENT_URI,id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert into"+uri);
                }
                break;
            case(STEP):
                id=db.insert(RecipeContract.StepEntry.TABLE_NAME,null,values);
                if (id>0){
                    returnUri= ContentUris.withAppendedId(RecipeContract.StepEntry.STEP_CONTENT_URI,id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert into"+uri);
                }
                break;
            case(INGREDIENT):
                id=db.insert(RecipeContract.IngredientEntry.TABLE_NAME,null,values);
                if (id>0){
                    returnUri= ContentUris.withAppendedId(RecipeContract.IngredientEntry.INGREDIENT_CONTENT_URI,id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert into"+uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri"+uri);


        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
