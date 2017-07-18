package com.example.biodun.bakingapp.utils;

import com.example.biodun.bakingapp.R;

/**
 * Created by Biodun on 6/24/2017.
 */

public class ImageUtil {


    public static int getRecipeImage(int recipeId) {
        int imageResources;
        switch (recipeId) {
            case (1):
                imageResources = R.drawable.nutella;
                return imageResources;
            case (2):
                imageResources = R.drawable.browncake;
                return imageResources;
            case (3):
                imageResources = R.drawable.yellowcake;
                return imageResources;
            case (4):
                imageResources = R.drawable.cheesecake;
                return imageResources;
            default:
                return 0;

        }
    }





    }

