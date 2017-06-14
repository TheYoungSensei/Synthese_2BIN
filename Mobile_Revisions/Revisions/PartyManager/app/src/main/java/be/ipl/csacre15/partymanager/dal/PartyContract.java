package be.ipl.csacre15.partymanager.dal;

/**
 * Created by sacre on 13-06-17.
 */
public class PartyContract {
    public static final int DB_VERSION =1;
    public static final String DB_NAME = "PartyDb";
    public static final String UTIL_TABLE = "utilisateurs";
    public static final String UTIL_ID = "_id";
    public static final String UTIL_NAME = "name";
    public static final String UTIL_TEL = "tel";
    public static final String UTIL_FIRSTNAME = "firstname";
    public static final String UTIL_DRINK = "drink";
    public static final String SQL_CREATE_ENTRIES = "create table " + UTIL_TABLE +
            " (" + UTIL_ID + " integer primary key autoincrement, "
            + UTIL_NAME + " text, "
            + UTIL_FIRSTNAME + " text, "
            + UTIL_TEL + " text, "
            + UTIL_DRINK + " text);";
}
