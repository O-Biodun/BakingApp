package com.example.biodun.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biodun.bakingapp.R;
import com.example.biodun.bakingapp.data.Recipe;
import com.example.biodun.bakingapp.utils.ImageUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Biodun on 6/24/2017.
 */

public class MasterPageAdapter extends RecyclerView.Adapter<MasterPageAdapter.MasterFragmentViewHolder> {

    private ArrayList<Recipe> mRecipe=new ArrayList<>();
    private Context mContext;
   MasterPageClickHandler masterPageClickHandler;

    public MasterPageAdapter(Context context,ArrayList<Recipe> recipe,MasterPageClickHandler clickHandler){
        mRecipe=recipe;
        mContext=context;
       masterPageClickHandler=clickHandler;
            }

    public interface MasterPageClickHandler{
        void onClickRecipe(Recipe recipe);
    }

    @Override
    public MasterFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.master_page_content;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MasterFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasterFragmentViewHolder holder, int position) {
        Recipe recipe=mRecipe.get(position);
        holder.recipeName.setText(recipe.getRecipeName());
        holder.servingValueText.setText(Integer.toString(recipe.getServings()));
        holder.servingsText.setText(mContext.getResources().getString(R.string.serving));
        if(TextUtils.isEmpty(recipe.getImage())){
            holder.recipeImage.setImageResource(ImageUtil.getRecipeImage(recipe.getId()));
        }
        else{
            Picasso.with(holder.recipeImage.getContext()).load(recipe.getImage())
                    .placeholder(ImageUtil.getRecipeImage(recipe.getId()))
                    .into(holder.recipeImage);
        }

        //holder.cardView.setCardElevation(10);
    }

    @Override
    public int getItemCount() {
        return mRecipe.size();
    }

    public class  MasterFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      @BindView(R.id.recipeImage) ImageView recipeImage;
      @BindView(R.id.recipeName) TextView recipeName;
      @BindView(R.id.servingsText) TextView servingsText;
      @BindView(R.id.servingValueText) TextView servingValueText;
        //@BindView(R.id.cardView)
       // CardView cardView;
      public MasterFragmentViewHolder(View itemView) {
          super(itemView);
          ButterKnife.bind(this,itemView);
          itemView.setOnClickListener(this);
      }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            Recipe recipe=mRecipe.get(position);
            masterPageClickHandler.onClickRecipe(recipe);
        }
    }
}
