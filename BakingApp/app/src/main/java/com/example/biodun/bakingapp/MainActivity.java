package com.example.biodun.bakingapp;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.biodun.bakingapp.fragments.MasterPagerFragment;




public class MainActivity extends AppCompatActivity  {
   public static boolean syncedState=false;
    private static boolean twoPane;
    public static final String RECIPE_ID="recipe";
    FragmentManager fragMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
              setPaneMode();

             fragMgr=getSupportFragmentManager();
            Fragment fragment=fragMgr.findFragmentById(R.id.container);
            if(fragment==null){

                fragment= MasterPagerFragment.newInstance();

                FragmentTransaction fragmentTransaction=fragMgr.beginTransaction();
                fragmentTransaction
                        .add(R.id.container,fragment)
                        .commit();




        }

    }

    public void setPaneMode(){
        if(findViewById(R.id.two_pane_mode)!=null){
            twoPane=true;
        }
        else{
            twoPane=false;
        }
    }



    public static  boolean getPaneMode(){
        return twoPane;
    }

    public Boolean IsSynced(){
        return syncedState;
    }
}
