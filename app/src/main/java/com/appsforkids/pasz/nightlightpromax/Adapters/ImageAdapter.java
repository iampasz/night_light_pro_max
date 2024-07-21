package com.appsforkids.pasz.nightlightpromax.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmConfigManager;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    RealmResults<Light> items;
    int size;
    //Realm realm = new InstanceRealmConfigurationUseCase().connect();

    Realm realm;

    int width;
    int height;


    public ImageAdapter(RealmResults<Light> items, int size) {
        this.items = items;
        this.size = size;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bg, parent, false);
        int width = parent.getWidth()/3;
        view.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width*1.5)));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, (int) (size * 1.5));
       // holder.frame.setLayoutParams(params);


        if (items.get(holder.getAbsoluteAdapterPosition()).getStatus()) {
            holder.checkBox.isChecked();
            holder.image.setAlpha(1f);
        }

        if (items.get(holder.getAbsoluteAdapterPosition()).getMypic() == -1) {
            Picasso.get().load(items.get(holder.getAbsoluteAdapterPosition()).getInternetLink()).into(holder.image);
            Log.i("PICASO", items.get(holder.getAbsoluteAdapterPosition()).getInternetLink() + "");
        } else {
            holder.image.setImageResource(items.get(holder.getAbsoluteAdapterPosition()).getMypic());
        }

        if (items.get(holder.getAbsoluteAdapterPosition()).getStatus()) {
            holder.checkBox.setChecked(true);
        }

        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                    holder.image.setAlpha(0.5f);
                }else{
                    holder.checkBox.setChecked(true);
                    holder.image.setAlpha(1f);
                }

                changeRealmImages(items.get(holder.getAbsoluteAdapterPosition()).getId(), holder.checkBox.isChecked());
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

    private void changeRealmImages(String id, boolean status) {
        realm.beginTransaction();
        Light light = realm.where(Light.class).equalTo("id", id).findFirst();

        Log.i("CHANGESTATUS", id + " id");

        light.setStatus(status);

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


    private void initRealm(){
       // RealmConfiguration configuration = RealmConfigManager.getRealmConfiguration(getActivity());
       // realm = Realm.getInstance(configuration);
    }



}
