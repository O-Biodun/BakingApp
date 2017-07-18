package com.example.biodun.bakingapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biodun.bakingapp.DetailActivity;
import com.example.biodun.bakingapp.MainActivity;
import com.example.biodun.bakingapp.R;
import com.example.biodun.bakingapp.adapters.ViewPagerAdapter;
import com.example.biodun.bakingapp.data.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private DetailActivity activity;
    private MainActivity mainActivity;
    private boolean mTwoPane;
    private static final String RECIPE="recipe";
    public Recipe mRecipe;
    @BindView(R.id.detailPageToolbar)
    Toolbar toolbar;
    @BindView(R.id.detailPageViewPager)
    ViewPager mViewPager;
    @BindView(R.id.detailPageTabs)
    TabLayout tabs;
    Unbinder unbinder;



    public DetailFragment() {
        // Required empty public constructor
    }
    public static DetailFragment newInstance(Recipe recipe){
        Bundle argument=new Bundle();
        argument.putParcelable(RECIPE,recipe);

        DetailFragment fragment=new DetailFragment();
        fragment.setArguments(argument);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder= ButterKnife.bind(this,view);



        if(getActivity().findViewById(R.id.two_pane_mode)==null){
            activity=(DetailActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            boolean hasActionBar =(activity.getSupportActionBar()!=null);
            mTwoPane=false;
            if(hasActionBar){
                activity.getSupportActionBar().setTitle(mRecipe.getRecipeName());

            }



        }
        else{
            mainActivity=(MainActivity) getActivity();

            toolbar.setVisibility(View.GONE);
            boolean hasActionBar =(mainActivity.getSupportActionBar()!=null);
            mTwoPane=true;
            if(hasActionBar){
                mainActivity.getSupportActionBar().setTitle(mRecipe.getRecipeName());

            }

        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch(position){
                    case(0):
                        IngredientFragment ingredientFragment=IngredientFragment.newInstance(mRecipe);
                        return ingredientFragment;
                    case(1):
                        StepsFragment stepsFragment=StepsFragment.newInstance(mRecipe);
                        return stepsFragment;
                    default:
                        return null;

                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {

                switch(position){
                    case(0):
                        return getString(R.string.ingredient);
                    case(1):
                        return getString(R.string.steps);
                    default:
                        return null;
                }

            }
        });
        tabs.setupWithViewPager(mViewPager);




        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRecipe=getArguments().getParcelable(RECIPE);
    }






    }

