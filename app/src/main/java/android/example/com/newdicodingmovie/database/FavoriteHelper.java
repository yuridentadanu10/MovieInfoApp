package android.example.com.newdicodingmovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.example.com.newdicodingmovie.database.ContractDatabase.TABLE_MOVIE;
import static android.provider.BaseColumns._ID;

public class FavoriteHelper {
    private static String DATABASE_TABLE=TABLE_MOVIE;


    DatabaseHelper helper;
    SQLiteDatabase database;
    private Context context;
    public FavoriteHelper(Context context){
        this.context=context;
    }

    public FavoriteHelper open() throws SQLException {
        helper =new DatabaseHelper(context);
        database= helper.getWritableDatabase();
        return this;
    }

    public void close(){
        helper.close();}

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC ");
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,
                null,
                _ID +" = ? ",
                new String[]{id},
                null,
                null,
                null);
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values) ;
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID + " = ? ",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }



}
