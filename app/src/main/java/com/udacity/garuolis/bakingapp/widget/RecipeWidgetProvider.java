package com.udacity.garuolis.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.udacity.garuolis.bakingapp.MainActivity;
import com.udacity.garuolis.bakingapp.R;
import com.udacity.garuolis.bakingapp.RecipeActivity;

public class RecipeWidgetProvider extends AppWidgetProvider {
    public final static String KEY_RECIPE_ID    = "recipeId";
    public final static String KEY_RECIPE_TITLE = "recipeTitle";
    public final static String KEY_INGREDIENTS  = "ingredients";

    static void updateAppWidget(Context context, AppWidgetManager manager, int widgetId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int recipeId = prefs.getInt(KEY_RECIPE_ID + widgetId, 0);
        String recipeTitle = prefs.getString(KEY_RECIPE_TITLE + widgetId, "");
        String recipeIngredients = prefs.getString(KEY_INGREDIENTS + widgetId, "");


        Intent intent = new Intent(context, RecipeActivity.class);
        intent.putExtra(RecipeActivity.EXTRA_ID, recipeId);

        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addParentStack(MainActivity.class)
                .addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
        views.setOnClickPendingIntent(R.id.tv_ingredients, pendingIntent);
        views.setTextViewText(R.id.tv_title, recipeTitle);
        views.setTextViewText(R.id.tv_ingredients, recipeIngredients);

        manager.updateAppWidget(widgetId, views);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateRecipeWidgets(context, appWidgetManager, appWidgetIds);
    }

    public void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
