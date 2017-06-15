package be.ipl.csacre15.lastfm.dal;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sacre on 15-06-17.
 */
public class LastDb {

    private MainDb mainDb;
    private SQLiteDatabase sqlDb;

    public LastDb(Context context) {
        this.mainDb = new MainDb(context);
    }

    public void open() throws SQLException {
        this.sqlDb = this.mainDb.getWritableDatabase();
    }

    public void close() {
        this.sqlDb.close();
    }

    public long addArtist(ContentValues newLine) {
        return this.sqlDb.insert(LastContract.ARTIST_TABLE, null, newLine);
    }

    public Cursor selectAll() {
        String order = LastContract.NB_LISTENERS + " ASC";
        return sqlDb.query(LastContract.ARTIST_TABLE, new String[] {
                LastContract.ARTIST_ID, LastContract.ARTIST_NAME, LastContract.NB_LISTENERS
        }, null, null, null, null, order, null);
    }

    private class MainDb extends SQLiteOpenHelper {

        public MainDb(Context context) {
            super(context, LastContract.DB_NAME, null, LastContract.DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(LastContract.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            throw new UnsupportedOperationException();
        }
    }
}
