package influx.foodbeverage.view.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import influx.foodbeverage.R;
import influx.foodbeverage.util.Constants;
import influx.foodbeverage.service.JsonObjReq;
import influx.foodbeverage.service.VolleyRequestQueue;
import influx.foodbeverage.util.DBHandler;
import influx.foodbeverage.view.adapter.FoodBeverageSummaryAdapter;
import influx.foodbeverage.view.adapter.TabPagerAdapter;
import influx.foodbeverage.view.model.FoodBeverage;
import influx.foodbeverage.view.model.FoodBeverageSubItem;

public class FoodBeverageActivity extends AppCompatActivity implements FoodBeverageFragment.OnFoodBeverageListener,ViewPager.OnPageChangeListener,Constants {

    private RecyclerView mBottomSheetRecyclerView;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ViewPager mViewPagerTab;
    private TabPagerAdapter mTabPagerAdapter;
    private ArrayList<String> mFoodBeverageTabNameArr;
    private LinearLayout mAEDLayout,mRecyclerViewLayout;
    private ImageView mArrowIndicator;
    private TextView mBottomSheetTitle,mTotalFoodBeverageQty;
    private HashMap<String,String> params;
    private RequestQueue mQueue;
    private ArrayList<FoodBeverage> mFoodBeverageArr;
    private DBHandler mDBHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_beverage);
        mFoodBeverageTabNameArr = new ArrayList<>();
        mFoodBeverageArr = new ArrayList<>();
        bottomSheet();
        getFnBItems();
    }
    private void getFnBItems(){
        try {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_CINEMAID, "0007");
        jsonObject.put(KEY_COUNTRYID, "1");
        mQueue  = VolleyRequestQueue.getInstance(FoodBeverageActivity.this).getRequestQueue();
        JsonObjReq jsonRequest = new JsonObjReq(new JSONObject(), Request.Method.POST, URL+GET_FNB_ITEMS,jsonObject, getResponseListener(), getErrorListener()) {};
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(jsonRequest);
}catch (JSONException e){
    e.printStackTrace();
}

    }


    private void bottomSheet(){
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mAEDLayout = (LinearLayout) bottomSheet.findViewById(R.id.aedLayout);
        mRecyclerViewLayout = (LinearLayout) bottomSheet.findViewById(R.id.recyclerViewLayout);

        mTotalFoodBeverageQty = (TextView) bottomSheet.findViewById(R.id.totalQty);
        mBottomSheetRecyclerView = (RecyclerView) bottomSheet.findViewById(R.id.recyclerView);
        mBottomSheetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBottomSheetTitle = (TextView) bottomSheet.findViewById(R.id.bottom_sheet_title);
        mArrowIndicator = (ImageView) bottomSheet.findViewById(R.id.arrow_indicator);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        if (mBottomSheetBehavior != null)
            mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    //showing the different states
                    switch (newState) {
                        case BottomSheetBehavior.STATE_HIDDEN:
                            break;
                        case BottomSheetBehavior.STATE_EXPANDED:
                            mArrowIndicator.setImageResource(R.mipmap.ic_action_arrow_bottom);
                            mBottomSheetTitle.setVisibility(View.VISIBLE);
                            mBottomSheetRecyclerView.setVisibility(View.VISIBLE);
                            mRecyclerViewLayout.setVisibility(View.VISIBLE);
                            break;
                        case BottomSheetBehavior.STATE_COLLAPSED:
                            mArrowIndicator.setImageResource(R.mipmap.ic_action_arrow_top);
                            mBottomSheetRecyclerView.setVisibility(View.GONE);
                            mBottomSheetTitle.setVisibility(View.GONE);
                            mRecyclerViewLayout.setVisibility(View.GONE);
                            break;
                        case BottomSheetBehavior.STATE_DRAGGING:
                            break;
                        case BottomSheetBehavior.STATE_SETTLING:
                            break;
                    }
                }
                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    // React to dragging events
                }
            });
        mAEDLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }
    private void initViewPagerAndTabs() {
        mViewPagerTab = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPagerTab);
        setViewPager();
    }
    public void setViewPager(){
        try {
            mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
            for (int i = 0; i < mFoodBeverageTabNameArr.size(); i++) {
                String mFoodBeverageName = mFoodBeverageTabNameArr.get(i);
                mTabPagerAdapter.addFragment(new FoodBeverageFragment(), mFoodBeverageName);
            }
            mViewPagerTab.setAdapter(mTabPagerAdapter);
            mViewPagerTab.setCurrentItem(0);
            mViewPagerTab.setOffscreenPageLimit(3);
            mViewPagerTab.addOnPageChangeListener(this);
            onPageSelected(0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onPageSelected(int position) {
        try {
            String foodBeverageTabName = mFoodBeverageTabNameArr.get(position);
            ArrayList<FoodBeverage> foodBeverageArr = getFoodBeverageArr(foodBeverageTabName);
            mViewPagerTab.setCurrentItem(position);
            FoodBeverageFragment fragment = (FoodBeverageFragment) mTabPagerAdapter.getFragment(position);
            fragment.onResume(FoodBeverageActivity.this, foodBeverageArr);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public ArrayList<FoodBeverage> getFoodBeverageArr(String foodBeverageTabName){
        ArrayList<FoodBeverage> mFoodBeverageAr = new ArrayList<>();
        for(FoodBeverage foodBeverage : mFoodBeverageArr){
            String fbTabName = foodBeverage.getTabname();
            if(fbTabName.equalsIgnoreCase(foodBeverageTabName)){
                mFoodBeverageAr.add(foodBeverage);
            }
        }
        return mFoodBeverageAr;
    }
    private Response.Listener<JSONObject> getResponseListener(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mFoodBeverageTabNameArr.clear();
                    mFoodBeverageArr.clear();
                  //  Log.d("RESPONSE RESULT ", "Response: " + response.toString());
                    String foodList = response.getString("FoodList");
                    JSONArray jsonArrFoodList = new JSONArray(foodList);
                    int lengthFoodList = jsonArrFoodList.length();
                    if (lengthFoodList > 0) {
                        for (int i = 0; i < lengthFoodList; i++) {
                            JSONObject jsonObjCharges = jsonArrFoodList.getJSONObject(i);
                            String tabName = jsonObjCharges.getString("TabName");
                            String fnblist = jsonObjCharges.getString("fnblist");
                            mFoodBeverageTabNameArr.add(tabName);
                            JSONArray jsonArrfnblist = new JSONArray(fnblist);
                            Log.d("RESPONSE RESULT ", "Response: " + tabName);
                            int lengthfnblist = jsonArrfnblist.length();
                            if (lengthfnblist > 0) {
                                for (int j = 0; j < lengthfnblist; j++) {
                                    ArrayList<FoodBeverageSubItem> mFoodBeverageSubItemArr = new ArrayList<>();
                                    JSONObject jsonObjfnblist = jsonArrfnblist.getJSONObject(j);
                                    String name = jsonObjfnblist.getString("Name");
                                    String fbtabName = jsonObjfnblist.getString("TabName");
                                    String imgUrl = jsonObjfnblist.getString("ImgUrl");
                                    String vegClass = jsonObjfnblist.getString("VegClass");
                                    double itemPrice = jsonObjfnblist.getDouble("ItemPrice");
                                    String subitems = jsonObjfnblist.getString("subitems");
                                    JSONArray jsonArrSubItems = new JSONArray(subitems);
                                    int lengthSubItems = jsonArrSubItems.length();
                                    if (lengthSubItems > 0) {
                                        for (int k = 0; k < lengthSubItems; k++) {
                                            FoodBeverageSubItem foodBeverageSubItem = new FoodBeverageSubItem();
                                            JSONObject jsonObjSubItems = jsonArrSubItems.getJSONObject(k);
                                            String subItemName = jsonObjSubItems.getString("Name");
                                            foodBeverageSubItem.setSubItemName(subItemName);
                                            mFoodBeverageSubItemArr.add(foodBeverageSubItem);
                                        }
                                    }
                                    FoodBeverage mFoodBeverage = new FoodBeverage(j+1,name,fbtabName,0,itemPrice,imgUrl,vegClass,mFoodBeverageSubItemArr);
                                    mFoodBeverageArr.add(mFoodBeverage);
                                }
                            }
                        }
                        initViewPagerAndTabs();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            };
        };
    }
    private Response.ErrorListener getErrorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR RESULT ", "error: " + error.toString());
            };
        };
    }

    @Override
    public void onOnFoodBeverageSummary(Activity activity) {
        mDBHandler = new DBHandler(activity);
        List<FoodBeverage> mFoodBeverageSummary = mDBHandler.getFoodBeverageSummary();
        Log.d("mFoodBeverageSummary","-->"+mFoodBeverageSummary.size());
        FoodBeverageSummaryAdapter foodBeverageSummaryAdapter = new FoodBeverageSummaryAdapter(activity,mFoodBeverageSummary);
        mBottomSheetRecyclerView.setAdapter(foodBeverageSummaryAdapter);
        double totalFoodBeverageQty = mDBHandler.getTotalFoodBeverage();
        mTotalFoodBeverageQty.setText(""+totalFoodBeverageQty);
    }
}
