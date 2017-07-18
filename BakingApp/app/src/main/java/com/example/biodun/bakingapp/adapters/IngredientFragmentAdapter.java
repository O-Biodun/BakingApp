package com.example.biodun.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biodun.bakingapp.R;
import com.example.biodun.bakingapp.data.Recipe;
import com.example.biodun.bakingapp.utils.ImageUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Biodun on 6/26/2017.
 */

public class IngredientFragmentAdapter extends RecyclerView.Adapter<IngredientFragmentAdapter.IngredientFragmentViewHolder> {
    private Context mContext;
    private ArrayList<Recipe.Ingredient> mIngredient=new ArrayList<>();

    public IngredientFragmentAdapter(Context context, ArrayList<Recipe.Ingredient> ingredients){
        mContext=context;
        mIngredient=ingredients;


    }


    @Override
    public IngredientFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingredient_fragment_content;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new IngredientFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientFragmentViewHolder holder, int position) {
        Recipe.Ingredient eachIngredient=mIngredient.get(position);

        holder.ingredientName.setText(eachIngredient.getIngredientName());
        String measure=eachIngredient.getMeasure()+": ";
        holder.measureTextView.setText(measure);
 holder.quantityTextView.setText(Double.toString(eachIngredient.getQuantity()));
     //   holder.measureImage.setImageResource(ImageUtil.getIngredientMeasureImage(eachIngredient.getMeasure()));


    }

    @Override
    public int getItemCount() {
        return mIngredient.size();
    }

    public class  IngredientFragmentViewHolder extends RecyclerView.ViewHolder {
       /* @BindView(R.id.ingredientMeasureImage)
        ImageView measureImage;*/
        @BindView(R.id.ingredientName)
        TextView ingredientName;
        @BindView(R.id.measureTextView)
        TextView measureTextView;
        @BindView(R.id.quantityTextView)
        TextView quantityTextView;

        public IngredientFragmentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    }




