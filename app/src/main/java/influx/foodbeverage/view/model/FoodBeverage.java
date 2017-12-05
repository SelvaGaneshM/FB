package influx.foodbeverage.view.model;

import java.util.ArrayList;

/**
 * Created by user on 04-Dec-17.
 */

public class FoodBeverage {
    private int id;
    private String name;
    private String tabname;
    private int qty;
    private String imageUrl;
    private String vegClass;
    private double itemPrice;
    private double total;
    private ArrayList<FoodBeverageSubItem> foodBeverageSubItemList = new ArrayList<FoodBeverageSubItem>();
    public FoodBeverage() {
    }
    public FoodBeverage(int id,String  name,String tabname,int qty,double itemPrice,String imageUrl,String vegClass,ArrayList<FoodBeverageSubItem> foodBeverageSubItemList) {
        super();
        this.id = id;
        this.name = name;
        this.tabname = tabname;
        this.qty = qty;
        this.itemPrice = itemPrice;
        this.imageUrl = imageUrl;
        this.vegClass = vegClass;
        this.foodBeverageSubItemList = foodBeverageSubItemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTabname() {
        return tabname;
    }

    public void setTabname(String tabname) {
        this.tabname = tabname;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getVegClass() {
        return vegClass;
    }

    public void setVegClass(String vegClass) {
        this.vegClass = vegClass;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<FoodBeverageSubItem> getFoodBeverageSubItemList() {
        return foodBeverageSubItemList;
    }

    public void setFoodBeverageSubItemList(ArrayList<FoodBeverageSubItem> foodBeverageSubItemList) {
        this.foodBeverageSubItemList = foodBeverageSubItemList;
    }
}
