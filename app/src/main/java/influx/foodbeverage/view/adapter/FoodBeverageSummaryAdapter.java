package influx.foodbeverage.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import influx.foodbeverage.R;
import influx.foodbeverage.view.model.FoodBeverage;

/**
 * Created by saravana on 05-Dec-17.
 */

public class FoodBeverageSummaryAdapter extends RecyclerView.Adapter<FoodBeverageSummaryAdapter.FoodBeverageSummaryViewHolder> {

    private Activity activity;
    private List<FoodBeverage> mFoodBeverageSummaryArr;
    public FoodBeverageSummaryAdapter(Activity activity, List<FoodBeverage> mFoodBeverageSummaryArr) {
        this.activity = activity;
        this.mFoodBeverageSummaryArr = mFoodBeverageSummaryArr;
    }
    @Override
    public FoodBeverageSummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_beverage_summary,parent,false);
        return new FoodBeverageSummaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodBeverageSummaryViewHolder holder, int position) {
      FoodBeverage foodBeverage = mFoodBeverageSummaryArr.get(position);
      holder.mName.setText(foodBeverage.getName());
      holder.mQty.setText("("+foodBeverage.getQty()+")");
      holder.mTotal.setText(""+foodBeverage.getTotal());

    }

    @Override
    public int getItemCount() {
        return mFoodBeverageSummaryArr.size();
    }

    public class FoodBeverageSummaryViewHolder extends RecyclerView.ViewHolder{
        private TextView mName,mQty,mTotal;

        public  FoodBeverageSummaryViewHolder(View itemView){
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mQty = (TextView) itemView.findViewById(R.id.qty);
            mTotal = (TextView) itemView.findViewById(R.id.total);

        }

    }
}
