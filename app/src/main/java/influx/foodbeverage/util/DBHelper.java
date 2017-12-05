package influx.foodbeverage.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by saravana on 05-Dec-17.
 */

public class DBHelper extends SQLiteOpenHelper implements Constants {

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteTables(db);
        createTables(db);
    }
    private void createTables(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_FOOD_BEVERAGE_ID + " TEXT," +
                        COLUMN_FOOD_BEVERAGE_NAME + " TEXT," +
                        COLUMN_FOOD_BEVERAGE_QTY + " TEXT," +
                        COLUMN_FOOD_BEVERAGE_PRICE + " TEXT," +
                        COLUMN_FOOD_BEVERAGE_TOTAL + " TEXT)";

        try {
            db.execSQL(SQL_CREATE_ENTRIES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteTables(SQLiteDatabase db) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        try {
            db.execSQL(SQL_DELETE_ENTRIES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}