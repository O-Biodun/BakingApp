package com.example.biodun.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biodun.bakingapp.R;
import com.example.biodun.bakingapp.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by Biodun on 6/26/2017.
 */

public class StepFragmentAdapter extends RecyclerView.Adapter<StepFragmentAdapter.StepFragmentViewHolder>{
    private Boolean isCardViewExpanded =false;
    private Context mContext;
    private ArrayList<Recipe.Steps> mSteps =new ArrayList<>();

    VideoButtonClickHandler mClickHandler;

    public StepFragmentAdapter(Context context, ArrayList<Recipe.Steps> steps, VideoButtonClickHandler handler){
        mContext=context;
        mSteps=steps;
        mClickHandler=handler;
    }

    public interface VideoButtonClickHandler{
        void onClickVideo(String videoUrl,String step);
    }

    @Override
    public StepFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.steps_fragment_content;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new StepFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepFragmentViewHolder holder, int position) {
        Recipe.Steps steps=mSteps.get(position);
        holder.stepValue.setText(String.valueOf(steps.getStepId()));
        holder.shortDescriptionTv.setText(steps.getShortDescription());
        holder.fullDescriptionTv.setText(steps.getDescription());
        String imageUrl=steps.getThumbnailUrl();
        if(!TextUtils.isEmpty(imageUrl)){
            Picasso.with(holder.imageButton.getContext()).load(imageUrl).error(R.drawable.error_pics).into(holder.imageButton);
        }


    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public  class StepFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       @BindView(R.id.videoButton)
       ImageButton videoButton;
       @BindView(R.id.toogleButton)
       ImageButton toogleButton;
       @BindView(R.id.stepsTV)
       TextView steps;
       @BindView(R.id.stepValueTv)
       TextView stepValue;
       @BindView(R.id.shortDescriptionTv)
       TextView shortDescriptionTv;
       @BindView(R.id.fullDescription)
       TextView fullDescriptionTv;
        @BindView(R.id.imageButton)
        ImageView imageButton;

       public StepFragmentViewHolder(View itemView){
           super(itemView);
           ButterKnife.bind(this,itemView);
           videoButton.setOnClickListener(this);
           toogleButton.setOnClickListener(this);
           imageButton.setOnClickListener(this);
       }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            Recipe.Steps eachStep=mSteps.get(position);


            if(view.getId()==R.id.toogleButton){
                if(!isCardViewExpanded){
                    fullDescriptionTv.setVisibility(View.VISIBLE);
                    if(eachStep.getVideoUrl().length()>0){
                        videoButton.setVisibility(View.VISIBLE);
                    }
                    if(!TextUtils.isEmpty(eachStep.getThumbnailUrl())){
                        imageButton.setVisibility(View.VISIBLE);
                    }

                    isCardViewExpanded=true;
                    toogleButton.setImageResource(R.drawable.arrow_up);
                }
                else if(isCardViewExpanded){
                    fullDescriptionTv.setVisibility(GONE);
                    imageButton.setVisibility(View.GONE);
                    videoButton.setVisibility(GONE);
                    isCardViewExpanded=false;
                    toogleButton.setImageResource(R.drawable.arrow_down);
                }


            }
            else if(view.getId()==R.id.videoButton){
                String video=eachStep.getVideoUrl();
                String step=eachStep.getDescription();
                mClickHandler.onClickVideo(video,step);


            }

        }
    }
}
