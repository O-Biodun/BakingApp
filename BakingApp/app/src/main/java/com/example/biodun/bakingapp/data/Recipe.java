package com.example.biodun.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Biodun on 6/24/2017.
 */

public class Recipe implements Parcelable {

    int id;
    String recipeName;
    int servings;
    String image;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Steps> stepsArrayList;

    public Recipe(int id, String recipeName, int servings, ArrayList<Ingredient> ingredientArrayList, ArrayList<Steps> stepsArrayList,String image) {
        this.id = id;
        this.recipeName = recipeName;
        this.servings = servings;
        this.ingredientArrayList = ingredientArrayList;
        this.stepsArrayList = stepsArrayList;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ArrayList<Ingredient> getIngredientArrayList() {
        return ingredientArrayList;
    }

    public void setIngredientArrayList(ArrayList<Ingredient> ingredientArrayList) {
        this.ingredientArrayList = ingredientArrayList;
    }

    public ArrayList<Steps> getStepsArrayList() {
        return stepsArrayList;
    }

    public void setStepsArrayList(ArrayList<Steps> stepsArrayList) {
        this.stepsArrayList = stepsArrayList;
    }

    public static class Ingredient implements Parcelable {
        double quantity;
        String measure;
        String ingredientName;

        public Ingredient(double quantity, String measure, String ingredientName) {
            this.quantity = quantity;
            this.measure = measure;
            this.ingredientName = ingredientName;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public String getIngredientName() {
            return ingredientName;
        }

        public void setIngredientName(String ingredientName) {
            this.ingredientName = ingredientName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.quantity);
            dest.writeString(this.measure);
            dest.writeString(this.ingredientName);
        }

        protected Ingredient(Parcel in) {
            this.quantity = in.readDouble();
            this.measure = in.readString();
            this.ingredientName = in.readString();
        }

        public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
            @Override
            public Ingredient createFromParcel(Parcel source) {
                return new Ingredient(source);
            }

            @Override
            public Ingredient[] newArray(int size) {
                return new Ingredient[size];
            }
        };
    }
    public static class Steps implements Parcelable {
        int stepId;
        String shortDescription;
        String description;
        String videoUrl;
        String thumbnailUrl;

        public Steps(int stepId, String shortDescription, String description, String videoUrl,String thumbnailUrl) {
            this.stepId = stepId;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoUrl = videoUrl;
            this.thumbnailUrl=thumbnailUrl;
        }

        public int getStepId() {
            return stepId;
        }

        public void setStepId(int stepId) {
            this.stepId = stepId;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.stepId);
            dest.writeString(this.shortDescription);
            dest.writeString(this.description);
            dest.writeString(this.videoUrl);
            dest.writeString(this.thumbnailUrl);
        }

        protected Steps(Parcel in) {
            this.stepId = in.readInt();
            this.shortDescription = in.readString();
            this.description = in.readString();
            this.videoUrl = in.readString();
            this.thumbnailUrl = in.readString();
        }

        public static final Creator<Steps> CREATOR = new Creator<Steps>() {
            @Override
            public Steps createFromParcel(Parcel source) {
                return new Steps(source);
            }

            @Override
            public Steps[] newArray(int size) {
                return new Steps[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.recipeName);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
        dest.writeList(this.ingredientArrayList);
        dest.writeList(this.stepsArrayList);
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.recipeName = in.readString();
        this.servings = in.readInt();
        this.image = in.readString();
        this.ingredientArrayList = new ArrayList<Ingredient>();
        in.readList(this.ingredientArrayList, Ingredient.class.getClassLoader());
        this.stepsArrayList = new ArrayList<Steps>();
        in.readList(this.stepsArrayList, Steps.class.getClassLoader());
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
