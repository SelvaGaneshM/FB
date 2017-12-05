package influx.foodbeverage.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import influx.foodbeverage.R;
import influx.foodbeverage.view.model.FoodBeverageSubItem;

/**
 * Created by saravana on 05-Dec-17.
 */

public class FootBeverageSubItemAdapter extends BaseAdapter {
    private Activity activity;  ViewHolder holder = null;
    ArrayList<FoodBeverageSubItem> mFoodBeverageSubItemList;
    public FootBeverageSubItemAdapter(Activity activity,ArrayList<FoodBeverageSubItem> mFoodBeverageSubItemList){
        this.activity = activity;
        this.mFoodBeverageSubItemList = mFoodBeverageSubItemList;
    }

    @Override
    public int getCount() {
        return mFoodBeverageSubItemList.size();
    }

    @Override
    public FoodBeverageSubItem getItem(int position) {
        return mFoodBeverageSubItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mFoodBeverageSubItemList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_food_beverage_sub_item, null);
            holder = new ViewHolder();
            holder.txtSubItem = (TextView) convertView.findViewById(R.id.subItem);
            holder.itemLayout = (LinearLayout) convertView.findViewById(R.id.itemLayout);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        FoodBeverageSubItem mFoodBeverageSubItem =  getItem(position);
        holder.txtSubItem.setText(mFoodBeverageSubItem.getSubItemName() );
        holder.itemLayout.setId(position);
        if(mFoodBeverageSubItem.isSelection()){
            holder.itemLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
            holder.txtSubItem.setTextColor(Color.parseColor("#000000"));
        }else{
            holder.itemLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            holder.txtSubItem.setTextColor(Color.parseColor("#737373"));
        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = view.getId();
                FoodBeverageSubItem mFoodBeverageSubItem =  getItem(position);
                unSelectionAll();
                mFoodBeverageSubItem.setSelection(true);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    private class ViewHolder {
        TextView txtSubItem;
        LinearLayout itemLayout;
    }
    private void unSelectionAll(){
        for(FoodBeverageSubItem foodBeverageSubItem : mFoodBeverageSubItemList){
            foodBeverageSubItem.setSelection(false);
        }
    }
}
