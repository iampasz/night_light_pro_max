package com.appsforkids.pasz.nightlightpromax.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ImageOnlineAdapter extends RecyclerView.Adapter<ImageOnlineAdapter.ViewHolder> {

    ArrayList<Light> arrayList;
    int size;
    Realm realm = new InstanceRealmConfigurationUseCase().connect();

    public ImageOnlineAdapter(ArrayList<Light> arrayList, int size) {
        this.arrayList = arrayList;
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

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, (int) (size * 1.5));
        holder.frame.setLayoutParams(params);

        if (chekNLFromRealm(arrayList.get(position).getInternetLink())) {
            holder.checkBox.setChecked(true);
        }

        if (arrayList.get(holder.getAbsoluteAdapterPosition()).getMypic() == -1) {
            Picasso.get().load(arrayList.get(holder.getAbsoluteAdapterPosition()).getInternetLink()).into(holder.image);
            Log.i("PICASO", arrayList.get(holder.getAbsoluteAdapterPosition()).getInternetLink() + "");
        } else {
            holder.image.setImageResource(arrayList.get(holder.getAbsoluteAdapterPosition()).getMypic());
        }

        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    addToRealm(arrayList.get(position).getInternetLink());
                } else {
                    removeFromRealm(arrayList.get(position).getInternetLink());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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

    private boolean chekNLFromRealm(String link) {

        Light light = realm.where(Light.class).equalTo("internetLink", link).findFirst();
        Boolean answer = false;

        if (light != null) {
            answer = true;
        }

        return answer;
    }

    private void addToRealm(String link) {

        realm.beginTransaction();

        Light light = new Light();
        light.setInternetLink(link);
        light.setOnline(true);
        light.setStatus(true);
        light.setMypic(-1);

        realm.copyToRealm(light);

        realm.commitTransaction();
    }

    private void removeFromRealm(String link) {
        realm.beginTransaction();
        realm.where(Light.class).equalTo("internetLink", link).isNotEmpty("internetLink").findFirst().deleteFromRealm();
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
