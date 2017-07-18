package com.example.biodun.bakingapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Biodun on 6/29/2017.
 */
//A class that hold the constants used by the database
public class RecipeContract {
    public static final String AUTHORITY = "com.example.biodun.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final String PATH_RECIPE = "recipe";
    public static final String PATH_STEP="step";
    public static final String PATH_INGREDIENT="ingredient";


    public static final class RecipeEntry implements BaseColumns{
        public static final Uri RECIPE_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_RECIPE_NAME = "recipe_name";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_SERVINGS = "serving";
        public static final String COLUMN_IMAGE = "image";
    }

    public static final class StepEntry implements BaseColumns{
        public static final Uri STEP_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEP).build();

        public static final String TABLE_NAME = "step";
        public static final String COLUMN_STEP_ID = "step_id";
        public static final String COLUMN_SHORT_DESCRIPTION= "short_description";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_VIDEO_URL = "video_url";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_IMAGE_ID = "image_url";

    }

    public static final class IngredientEntry implements BaseColumns{
        public static final Uri INGREDIENT_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();

        public static final String TABLE_NAME = "ingredient";
        public static final String COLUMN_INGREDIENT="ingredient_name";
        public static final String COLUMN_QUANTITY="quantity";
        public static final String COLUMN_MEASURE="measure";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
    }
}
