package influx.foodbeverage.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import influx.foodbeverage.R;
import influx.foodbeverage.util.DBHandler;
import influx.foodbeverage.view.model.FoodBeverage;
import influx.foodbeverage.view.model.FoodBeverageSubItem;


/**
 * Created by saravana on 04-Dec-17.
 */

public class FoodBeverageAdapter extends RecyclerView.Adapter<FoodBeverageAdapter.FoodBeverageViewHolder> {

   private onClickCompleteListener listener;
   public interface onClickCompleteListener{
       public void onComplete();
   }
   public void setOnCompleteListener(onClickCompleteListener listener){
       this.listener = listener;
   }
   private Activity activity;
   private DBHandler mDBHandler;
   private int qtyFlag = 0;
   private ArrayList<FoodBeverage> mFoodBeverageArr;
    public FoodBeverageAdapter(Activity activity, ArrayList<FoodBeverage> mFoodBeverageArr) {
        this.activity = activity;
        this.mFoodBeverageArr = mFoodBeverageArr;
        this.mDBHandler = new DBHandler(activity);
    }
    @Override
    public FoodBeverageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_beverage,parent,false);
        return new FoodBeverageViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(FoodBeverageViewHolder holder, int position) {
     FoodBeverage foodBeverage = mFoodBeverageArr.get(position);
     String imageUrl = foodBeverage.getImageUrl();
     if(imageUrl!=null && !imageUrl.isEmpty()){
         Glide.with(activity).load(imageUrl)
                 .thumbnail(0.5f)
                 .crossFade()
                 .placeholder(R.mipmap.foodplaceholder)
                 .error(R.mipmap.foodplaceholder)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .into(holder.mFoodBeverageImage);
     }else{
         holder.mFoodBeverageImage.setImageResource(R.mipmap.foodplaceholder);
     }
        String imageVegClassUrl = foodBeverage.getVegClass();

        if(imageVegClassUrl!=null && !imageVegClassUrl.isEmpty()){
            holder.mFoodBeverageVegClass.setVisibility(View.VISIBLE);
            Glide.with(activity).load(imageVegClassUrl)
                    .into(holder.mFoodBeverageVegClass);
        }else{
            holder.mFoodBeverageVegClass.setVisibility(View.GONE);
        }

        holder.mFoodBeveragePlus.setId(position);
        holder.mFoodBeveragePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int position = view.getId();
                    FoodBeverage foodBeverage = mFoodBeverageArr.get(position);
                    qtyFlag = foodBeverage.getQty();
                    foodBeverage.setQty(++qtyFlag);
                    double qty = (double) foodBeverage.getQty();
                    double price = (double) foodBeverage.getItemPrice();
                    double total = price * qty;
                    foodBeverage.setTotal(total);
                    notifyDataSetChanged();
                    mDBHandler.storeFoodBeverage(foodBeverage);
                    if (listener != null) {
                        listener.onComplete();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        holder.mFoodBeverageMinus.setId(position);
        holder.mFoodBeverageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int position = view.getId();
                    FoodBeverage foodBeverage = mFoodBeverageArr.get(position);
                    qtyFlag = foodBeverage.getQty();
                    if (qtyFlag > 1) {
                        foodBeverage.setQty(--qtyFlag);
                    } else {
                        foodBeverage.setQty(0);
                    }
                    double qty = (double) foodBeverage.getQty();
                    double price = (double) foodBeverage.getItemPrice();
                    double total = price * qty;
                    foodBeverage.setTotal(total);
                    notifyDataSetChanged();
                    mDBHandler.storeFoodBeverage(foodBeverage);
                    if (listener != null) {
                        listener.onComplete();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
     holder.mFoodBeverageQty.setText(""+foodBeverage.getQty());
     holder.mFoodBeverageName.setText(foodBeverage.getName());

     ArrayList<FoodBeverageSubItem> mFoodBeverageSubItemList = mFoodBeverageArr.get(position).getFoodBeverageSubItemList();
     if(mFoodBeverageSubItemList.size()>0){
         holder.mSubItemView.setVisibility(View.VISIBLE);
         holder.mSubItemView.setAdapter(new FootBeverageSubItemAdapter(activity,mFoodBeverageSubItemList));
     }else{
         holder.mSubItemView.setVisibility(View.GONE);
     }
    }
    @Override
    public int getItemCount() {
        return mFoodBeverageArr.size();
    }

    public class FoodBeverageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mFoodBeverageImage, mFoodBeverageMinus, mFoodBeveragePlus,mFoodBeverageVegClass;
        private TextView mFoodBeverageName,mFoodBeverageQty;
        private GridView mSubItemView;
        public  FoodBeverageViewHolder(View itemView){
            super(itemView);
            mFoodBeverageImage = (ImageView) itemView.findViewById(R.id.imageView);
            mFoodBeverageName  = (TextView) itemView.findViewById(R.id.textView);
            mFoodBeverageQty  = (TextView) itemView.findViewById(R.id.qty);
            mFoodBeverageMinus = (ImageView) itemView.findViewById(R.id.minus);
            mFoodBeveragePlus = (ImageView) itemView.findViewById(R.id.plus);
            mSubItemView = (GridView) itemView.findViewById(R.id.subItemView);
            mFoodBeverageVegClass = (ImageView) itemView.findViewById(R.id.vegClass);

        }
    }

}
