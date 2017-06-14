package csacre15.ipl.be.myapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by csacre15 on 23/03/2017.
 */
class LaFeteDb {

    private MainDb mDbHelper;
    private SQLiteDatabase db;

    public LaFeteDb(Context context) {
        this.mDbHelper = new MainDb(context);
    }

    public void open() throws SQLException {
        this.db = this.mDbHelper.getWritableDatabase();
    }

    public void close() {
        this.db.close();
    }

    public long addUtil(ContentValues nouvelleLigne) {
        return this.db.insert(LaFeteContract.TABLE_UTIL, null, nouvelleLigne);

    }

    public Cursor selectAll() {
        String order = LaFeteContract.TABLE_UTIL_ID + " DESC";
        return db.query(LaFeteContract.TABLE_UTIL, new String[] {LaFeteContract.TABLE_UTIL_ID,
                        LaFeteContract.TABLE_UTIL_NOM, LaFeteContract.TABLE_UTIL_PRENOM,
                        LaFeteContract.TABLE_UTIL_BOISSON, LaFeteContract.TABLE_UTIL_TEL}
                , null, null, null, null, order, "10");
    }

    private static class MainDb extends SQLiteOpenHelper {

        public MainDb(Context context) {
            super(context, LaFeteContract.NOM_DB, null, LaFeteContract.VERSION_DB);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(LaFeteContract.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(LaFeteContract.SQL_DELETE_ENTRIES);
        }


    }
}
