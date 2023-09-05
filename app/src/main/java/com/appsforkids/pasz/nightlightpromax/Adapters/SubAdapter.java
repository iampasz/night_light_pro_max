package com.appsforkids.pasz.nightlightpromax.Adapters;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.ProductDetails;
import com.appsforkids.pasz.nightlightpromax.Fragments.Subscription;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ChoseSub;
import com.appsforkids.pasz.nightlightpromax.R;

import java.util.ArrayList;
import java.util.List;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ListSubHolder> {

    List<ProductDetails.SubscriptionOfferDetails> list;
    int pressedPosition = 1;
    int currentMusicPosition = -1;
    ChoseSub choseSub;

    public SubAdapter(List<ProductDetails.SubscriptionOfferDetails> list, ChoseSub choseSub){
        this.list = list;
        this.choseSub = choseSub;
    };

    @NonNull
    @Override
    public ListSubHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_item, parent, false);
        return new ListSubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSubHolder holder, int position) {

        holder.crossed_text.setPaintFlags(holder.crossed_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.sub_month.setBackgroundResource(R.drawable.sub_button_large_pass);
        holder.s_m_text.setAlpha(0.5f);
        holder.sub_month.setAlpha(0.5f);

        Log.i("LEARNBILLING", list.get(0)+"");

        switch (list.get(position).getBasePlanId()){
            case "nl3month":
                holder.s_m_text.setText("3 месяца");
                break;

            case "main0sub":
                holder.s_m_text.setText("1 месяц");
                break;

            case "month12":
                holder.s_m_text.setText("12 месяцев");



                break;
        }

        int microPrice = (int) list.get(position).getPricingPhases().getPricingPhaseList().get(0).getPriceAmountMicros();
        microPrice=microPrice/1000000;
        int priceDiscount = ((microPrice*70)/30)+microPrice;
        String currenc = list.get(position).getPricingPhases().getPricingPhaseList().get(0).getPriceCurrencyCode();

        Log.i("HELOU", list.get(position).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice()+" ");


//                crossed_text1.setText();
//
//                price1.setText(microPrice+" "+currenc);

        holder.crossed_text.setText(priceDiscount+" "+currenc);
        holder.price.setText(microPrice+" "+currenc);

        holder.sub_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //currentMusicPosition=-1;

                if(pressedPosition==holder.getAdapterPosition()){
                    //holder.play_item.setImageResource(R.drawable.bt_play);
                    Log.i("Play", pressedPosition+ " тут должен плей поставить " +  holder.getAdapterPosition());

                    //pressedPosition=-1;

                }else{

                    //holder.play_item.setImageResource(R.drawable.bt_pause);
                    Log.i("Play", pressedPosition+ " тут должен стоп поставить " +  holder.getAdapterPosition());
                    pressedPosition =  holder.getAdapterPosition();
                }
                notifyDataSetChanged();
            }
        });

        if(holder.getAdapterPosition()==pressedPosition){
            holder.sub_month.setBackgroundResource(R.drawable.sub_button_large);

//            holder.crossed_text.setTextColor(holder.sub_month.getResources().getColor(R.color.sub_button_text));
            holder.s_m_text.setAlpha(1f);
//            holder.price.setTextColor(holder.sub_month.getResources().getColor(R.color.sub_button_text));
            holder.sub_month.setAlpha(1f);

            holder.s_m_text.setText(holder.s_m_text.getText()+" ✔ ");

            //Subscription.productDetails = ;
            choseSub.setToken(list.get(position).getOfferToken());
            //choseSub.setProduckt(list.get(position).);


        }else{



        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListSubHolder extends RecyclerView.ViewHolder {

        private TextView s_m_text;
        private TextView crossed_text;
        private TextView price;

        private LinearLayout sub_month;

        public ListSubHolder(@NonNull View itemView) {
            super(itemView);

            s_m_text = itemView.findViewById(R.id.s_m_text);
            crossed_text = itemView.findViewById(R.id.crossed_text);
            price = itemView.findViewById(R.id.price);
            sub_month = itemView.findViewById(R.id.sub_month);

        }
    }

}