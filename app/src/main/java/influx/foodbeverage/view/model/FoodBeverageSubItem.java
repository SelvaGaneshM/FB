package influx.foodbeverage.view.model;

/**
 * Created by user on 05-Dec-17.
 */

public class FoodBeverageSubItem {

    private String subItemName;
    private boolean isSelection;

    public String getSubItemName() {
        return subItemName;
    }

    public void setSubItemName(String subItemName) {
        this.subItemName = subItemName;
    }


    public boolean isSelection() {
        return isSelection;
    }

    public void setSelection(boolean selection) {
        isSelection = selection;
    }
}
