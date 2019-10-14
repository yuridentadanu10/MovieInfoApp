package android.example.com.newdicodingmovie.appWidget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.example.com.newdicodingmovie.R;
import android.example.com.newdicodingmovie.parcelable.MovieParcelable;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import static android.example.com.newdicodingmovie.database.ContractDatabase.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor favoriteList;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {
        favoriteList = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favoriteList.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        MovieParcelable items = getItem(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.favorite_widget_item);

        Bitmap bitmap = null;

        try{

            bitmap = Glide.with(context).asBitmap().load( "https://image.tmdb.org/t/p/w500" + items.getPosterPath()).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.widget_image, bitmap);

        Bundle extra = new Bundle();
        extra.putInt(FavoriteMovieWIdget.ITEM_EXTRA,position);

        Intent fillIntent = new Intent();
        fillIntent.putExtras(extra);

        remoteViews.setOnClickFillInIntent(R.id.widget_image,fillIntent);
        return remoteViews;
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
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private MovieParcelable getItem(int position){
        if(!favoriteList.moveToPosition(position)){
            throw new IllegalStateException("Invalid Position!");
        }
        return new MovieParcelable(favoriteList);

    }
}
