package android.example.com.newdicodingmovie.appWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.example.com.newdicodingmovie.R;
import android.widget.Toast;


public class FavoriteMovieWIdget extends AppWidgetProvider {
    public static final String TOAST_ACTION = "android.example.com.newdicodingmovie.TOAST_ACTION";
    public static final String ITEM_EXTRA = "android.example.com.newdicodingmovie.ITEM_EXTRA";

    static void updateAppWidget(Context context, AppWidgetManager  appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views= new RemoteViews(context.getPackageName(),R.layout.favorite_movie_widget);
        views.setRemoteAdapter(R.id.stack_view,intent);
        views.setEmptyView(R.id.stack_view,R.id.no_dataview);

        Intent toastIntent = new Intent(context, FavoriteMovieWIdget.class);
        toastIntent.setAction(FavoriteMovieWIdget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,toastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view,pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId,views);

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(TOAST_ACTION)){
            int index = intent.getIntExtra(ITEM_EXTRA,0);

            Toast.makeText(context,"notification" + index, Toast.LENGTH_SHORT).show();
        }

        super.onReceive(context, intent);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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
}

