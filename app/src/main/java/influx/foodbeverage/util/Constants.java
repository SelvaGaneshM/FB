package influx.foodbeverage.util;

/**
 * Created by saravana on 04-Dec-17.
 */

public interface Constants {
    public static String URL = "http://apps.novocinemas.com/api/service/";
    public static String GET_FNB_ITEMS = "GetFnBItems";

    public static String KEY_CINEMAID = "cinemaid";
    public static String KEY_COUNTRYID = "countryid";



    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FoodBeverage.db";
    public static final String TABLE_NAME = "foodbeverage";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FOOD_BEVERAGE_ID = "food_beverage_id";
    public static final String COLUMN_FOOD_BEVERAGE_NAME = "food_beverage_name";
    public static final String COLUMN_FOOD_BEVERAGE_QTY = "food_beverage_qty";
    public static final String COLUMN_FOOD_BEVERAGE_PRICE = "food_beverage_price";
    public static final String COLUMN_FOOD_BEVERAGE_TOTAL = "food_beverage_total";

}
