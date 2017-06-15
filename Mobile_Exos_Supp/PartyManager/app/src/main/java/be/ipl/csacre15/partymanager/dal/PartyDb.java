package be.ipl.csacre15.partymanager.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sacre on 13-06-17.
 */
public class PartyDb {

    private MainDb mainDb;
    private SQLiteDatabase sqlDb;

    public PartyDb(Context context) {
        this.mainDb = new MainDb(context);
    }

    public void open() throws SQLException {
        this.sqlDb = this.mainDb.getWritableDatabase();
    }

    public void close() {
        this.sqlDb.close();
    }

    public long addUtil(ContentValues newLine) {
        return this.sqlDb.insert(PartyContract.UTIL_TABLE, null, newLine);
    }

    public Cursor selectAll() {
        String order = PartyContract.UTIL_ID + " DESC";
        return sqlDb.query(PartyContract.UTIL_TABLE, new String[] {
                PartyContract.UTIL_ID, PartyContract.UTIL_NAME,
                PartyContract.UTIL_FIRSTNAME, PartyContract.UTIL_DRINK,
                PartyContract.UTIL_TEL
        }, null, null, null, null, order, "10");
    }


    private class MainDb  extends SQLiteOpenHelper {
        public MainDb(Context context) {
            super(context, PartyContract.DB_NAME, null, PartyContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(PartyContract.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            throw new UnsupportedOperationException();
        }
    }
}
