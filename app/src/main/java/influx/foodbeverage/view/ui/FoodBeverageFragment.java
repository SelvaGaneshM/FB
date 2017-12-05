package influx.foodbeverage.view.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import influx.foodbeverage.R;
import influx.foodbeverage.view.adapter.FoodBeverageAdapter;
import influx.foodbeverage.view.model.FoodBeverage;

/**
 * Created by user on 04-Dec-17.
 */

public class FoodBeverageFragment extends Fragment {

    private OnFoodBeverageListener mListener;
    public interface OnFoodBeverageListener{
        public void onOnFoodBeverageSummary(Activity activity);
    }
    private View itemView;
    private RecyclerView mRecyclerView;
    private FoodBeverageAdapter mFoodBeverageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_food_beverage,container,false);
        return itemView;
    }
    public void onResume(final Activity activity,  ArrayList<FoodBeverage> foodBeverageArr ) {
        try {
            Log.d("foodBeverageArr","-->"+foodBeverageArr.size());
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            mFoodBeverageAdapter = new FoodBeverageAdapter(activity,foodBeverageArr);
            mRecyclerView.setAdapter(mFoodBeverageAdapter);
            mFoodBeverageAdapter.setOnCompleteListener(new FoodBeverageAdapter.onClickCompleteListener() {
                @Override
                public void onComplete() {
                    mListener.onOnFoodBeverageSummary(activity);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onAttach(Context context) {
       super.onAttach(context);
        try{
            mListener = (OnFoodBeverageListener) getActivity();
        }catch (Exception e){
            e.printStackTrace();
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}
