package com.appsforkids.pasz.nightlightpromax.Adapters;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by pasz on 30.10.2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {

    public static ArrayList<Light> mylights;

    public MyAdapter(ArrayList<Light> lights) {
        mylights = lights;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        if(mylights.get(holder.getAbsoluteAdapterPosition()).getMypic()==-1){
            Picasso.get().load(mylights.get(holder.getAbsoluteAdapterPosition()).getInternetLink()).into(holder.imageView);
            Log.i("PICASO", mylights.get(holder.getAbsoluteAdapterPosition()).getInternetLink()+"");
        }else{
            holder.imageView.setImageResource(mylights.get(holder.getAbsoluteAdapterPosition()).getMypic());
        }

        int value = getBrightsPreference(holder.imageView.getContext());
        holder.imageView.setColorFilter(brightIt(value));


    }

    @Override
    public int getItemCount() {
        return mylights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public static ColorMatrixColorFilter brightIt(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[] {
                1, 0, 0, 0, fb,
                0, 1, 0, 0, fb,
                0, 0, 1, 0, fb,
                0, 0, 0, 1, 0   });

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);

        return f;
    }


    private int getBrightsPreference(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences("MYPREFS", 0);
        int brights = sharedPref.getInt("BRIGHT", 0);

        Log.i("BRIGHTS", brights+" ADAPTER");

        return brights;

    }

    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }

}



