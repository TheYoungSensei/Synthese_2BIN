package be.ipl.csacre15.lastfm.dal;

/**
 * Created by sacre on 15-06-17.
 */
public class LastContract {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "LastDb";
    public static final String ARTIST_TABLE = "artists";
    public static final String ARTIST_ID = "_id";
    public static final String ARTIST_NAME = "name";
    public static final String NB_LISTENERS = "listeners";
    public static final String SQL_CREATE_ENTRIES = "create table " + ARTIST_TABLE +
            " (" + ARTIST_ID + " integer primary key autoincrement, "
            + ARTIST_NAME + " text, "
            + NB_LISTENERS + " integer);";
}
