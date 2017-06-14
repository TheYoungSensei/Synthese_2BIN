package csacre15.ipl.be.myapp.dao;

/**
 * Created by csacre15 on 29/03/2017.
 */
public class LaFeteContract {
    public static final int VERSION_DB = 1;
    public static final String NOM_DB = "MyAppDb";
    public static final String TABLE_UTIL = "utilisateurs";
    public static final String TABLE_UTIL_ID = "_id";
    public static final String TABLE_UTIL_NOM ="nom";
    public static final String TABLE_UTIL_TEL = "tel";
    public static final String TABLE_UTIL_PRENOM = "prenom";
    public static final String TABLE_UTIL_BOISSON = "boisson";
    public static final String SQL_CREATE_ENTRIES = "create table " + TABLE_UTIL +
            " (" + TABLE_UTIL_ID + " integer primary key autoincrement, "
            + TABLE_UTIL_NOM + " text, "
            + TABLE_UTIL_PRENOM + " text, "
            + TABLE_UTIL_TEL + " text, "
            + TABLE_UTIL_BOISSON + " text);";
    public static final String SQL_DELETE_ENTRIES = "";
}
