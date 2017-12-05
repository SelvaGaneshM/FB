package influx.foodbeverage.util;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import influx.foodbeverage.view.model.FoodBeverage;

/**
 * Created by saravana on 05-Dec-17.
 */

public class DBHandler  implements Constants {
    private SQLiteDatabase db;
    public DBHelper dbHelper;

    public DBHandler(Activity context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int storeFoodBeverage(FoodBeverage foodbeverage) {
        long newRowId = 0;
        ContentValues values = new ContentValues();

        values.put(COLUMN_FOOD_BEVERAGE_NAME, foodbeverage.getName());
        values.put(COLUMN_FOOD_BEVERAGE_QTY, foodbeverage.getQty());
        values.put(COLUMN_FOOD_BEVERAGE_PRICE, foodbeverage.getItemPrice());
        values.put(COLUMN_FOOD_BEVERAGE_TOTAL, foodbeverage.getTotal());
        try {
            String id = getFoodBeverageId(foodbeverage.getId());
            if(id!=null && !id.isEmpty()){
                String selection = COLUMN_FOOD_BEVERAGE_ID + " = ?";
                String[] selectionArgs = {id};
                if(foodbeverage.getQty()==0){
                    db.delete(TABLE_NAME, selection, selectionArgs);
                }else{
                    db.update(TABLE_NAME,values,selection,selectionArgs);
                }
            }else{
                values.put(COLUMN_FOOD_BEVERAGE_ID, foodbeverage.getId());
                newRowId = db.insert(TABLE_NAME, null, values);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (int) newRowId;
    }

    public List<FoodBeverage> getFoodBeverageSummary() {
        List<FoodBeverage> foodbeverageList = new ArrayList<FoodBeverage>();
        String[] projection = {
                COLUMN_FOOD_BEVERAGE_NAME,
                COLUMN_FOOD_BEVERAGE_QTY,
                COLUMN_FOOD_BEVERAGE_PRICE,
                COLUMN_FOOD_BEVERAGE_TOTAL
        };
        String sortOrder = COLUMN_ID + " DESC";
        Cursor cursor = db.query(TABLE_NAME,projection,null,null,null,null,sortOrder);
        if (cursor.moveToFirst()) {
            do {
                FoodBeverage foodbeverage = new FoodBeverage();
                foodbeverage.setName(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_BEVERAGE_NAME)));
                foodbeverage.setQty(cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_BEVERAGE_QTY)));
                foodbeverage.setItemPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_FOOD_BEVERAGE_PRICE)));
                foodbeverage.setTotal(cursor.getDouble(cursor.getColumnIndex(COLUMN_FOOD_BEVERAGE_TOTAL)));
                foodbeverageList.add(foodbeverage);
            } while (cursor.moveToNext());
        }
        return foodbeverageList;
    }
    public String getFoodBeverageId(int id) {
        String foodBeverageId = "";
        try {
            Cursor c = db.query(TABLE_NAME,
                    new String[]{COLUMN_FOOD_BEVERAGE_ID}, COLUMN_FOOD_BEVERAGE_ID
                            + " = ? ", new String[]{String.valueOf(id)}, null,
                    null, null);
            c.moveToFirst();
            if (c.getCount() > 0) {
                foodBeverageId = c.getString(c.getColumnIndex(COLUMN_FOOD_BEVERAGE_ID));
            }
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodBeverageId;
    }
    public double getTotalFoodBeverage(){
        double totalFoodBeverage = 0;
        String query = "SELECT sum(food_beverage_total) FROM " + TABLE_NAME ;
        Cursor c  = db.rawQuery(query, null);
        if ( c != null &&  c.getCount() > 0) {
            c.moveToFirst();
            do {
                totalFoodBeverage = c.getDouble(0);
            } while ( c.moveToNext());
        }
        c.close();
        return totalFoodBeverage;
    }
}
