package android.example.com.newdicodingmovie;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.example.com.newdicodingmovie.database.FavoriteHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import static android.example.com.newdicodingmovie.database.ContractDatabase.AUTHORITY;
import static android.example.com.newdicodingmovie.database.ContractDatabase.CONTENT_URI;
import static android.example.com.newdicodingmovie.database.ContractDatabase.TABLE_MOVIE;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE=100;
    private static final int FAVORITE_ID=101;
    private FavoriteHelper helperFavorite;

    @Override
    public boolean onCreate() {
        helperFavorite =new FavoriteHelper(getContext());
        helperFavorite.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case FAVORITE:
                cursor= helperFavorite.queryProvider();
                break;
            case FAVORITE_ID:
                cursor = helperFavorite.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor= null;
                break;
        }
        if(cursor!=null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;
        if (sUriMatcher.match(uri) == FAVORITE) {
            added = helperFavorite.insertProvider(contentValues);
        } else {
            added = 0;
        }
        if (added>0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        if (sUriMatcher.match(uri) == FAVORITE_ID) {
            deleted = helperFavorite.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }
        if (deleted>0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;
        if (sUriMatcher.match(uri) == FAVORITE_ID) {
            updated = helperFavorite.updateProvider(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }
        if (updated>0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        }
        return updated;
    }

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE,FAVORITE);
        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE + "/#",FAVORITE_ID);
    }
}
