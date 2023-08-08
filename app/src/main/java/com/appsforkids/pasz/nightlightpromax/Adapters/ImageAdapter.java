package com.appsforkids.pasz.nightlightpromax.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.ImageFile;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.sync.SyncConfiguration;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    RealmResults<Light> items;
    int size;
    Realm realm = Realm.getDefaultInstance();


    public ImageAdapter(RealmResults<Light> items, int size) {
        this.items = items;
        this.size = size;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bg, parent, false);
        //renewPoint(true, view.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CardView.LayoutParams params = new CardView.LayoutParams(size, (int) (size * 1.5));
        holder.frame.setLayoutParams(params);



        if(items.get(holder.getAbsoluteAdapterPosition()).getMypic()==-1){
            Picasso.get().load(items.get(holder.getAbsoluteAdapterPosition()).getInternetLink()).into(holder.image);
            Log.i("PICASO", items.get(holder.getAbsoluteAdapterPosition()).getInternetLink()+"");
        }else{
            holder.image.setImageResource(items.get(holder.getAbsoluteAdapterPosition()).getMypic());
        }


        if(items.get(holder.getAbsoluteAdapterPosition()).getStatus()){
            holder.checkBox.setChecked(true);
        }


        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Light light = items.get(holder.getAbsoluteAdapterPosition());

                if(light!=null){
                    changeRealmImages(holder.checkBox.isChecked(), light.getMypic());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView image;
         FrameLayout frame;
         CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            frame = itemView.findViewById(R.id.frame);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    private void changeRealmImages(Boolean chekbox, int mypic) {

        realm.beginTransaction();
        Light currentImage =  realm.where(Light.class).equalTo("mypic", mypic).findFirst();
        currentImage.setStatus(chekbox);

        realm.commitTransaction();



    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
