package com.example.biodun.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.biodun.bakingapp.R;
import com.example.biodun.bakingapp.utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Biodun on 7/1/2017.
 */

public class WidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<String> ingredientList=new ArrayList<>();

    public WidgetRemoteViewFactory(Context context){
        mContext=context;

    }

    @Override
    public void onCreate() {
    loadIngredientList();
    }

    @Override
    public void onDataSetChanged() {
    loadIngredientList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views=new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list);
        views.setTextViewText(R.id.ingredient_list_tv,ingredientList.get(position));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    private void loadIngredientList(){
        ingredientList.clear();
        Context context=mContext.getApplicationContext();
        SharedPreferences sharedPref=mContext.getSharedPreferences(context.getString(R.string.pref_file_key),0);
        Set<String> defaultSet=new HashSet<>();
        defaultSet.add("LOng time no see");
        defaultSet.add("Here");
        Set<String> ingredientNames=sharedPref.getStringSet(context.getString(R.string.pref_ingredient_list),defaultSet);
        ingredientList.addAll(ingredientNames);
    }
}
