package com.example.biodun.bakingapp.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.biodun.bakingapp.MainActivity;
import com.example.biodun.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {
    private static final String INTENT_ID="com.example.biodun.bakingapp.fragment.WIDGET_UPDATE_BROADCAST";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {





        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        Intent intent=new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widgetIngredientName,pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setAdapter(context, views);
        } else {
            setAdapterV11(context, views);
        }
        setAdapter(context,views);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
        if(INTENT_ID.equals(intent.getAction())){
            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget=new ComponentName(context.getPackageName(),RecipeWidgetProvider.class.getName());
            int[] appwidgetIds=appWidgetManager.getAppWidgetIds(thisAppWidget);

            appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetIds,R.id.recipeListView);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @SuppressWarnings("deprecation")
    private static void setAdapterV11(Context context , @NonNull final RemoteViews views){
        views.setRemoteAdapter(0,R.id.recipeListView,new Intent(context,RecipeWidgetService.class));


    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.recipeListView,
                new Intent(context,RecipeWidgetService.class));
    }
}

