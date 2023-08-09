package com.appsforkids.pasz.nightlightpromax.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Interfaces.DoThis;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.Light;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyCategoryImageAdapter extends RecyclerView.Adapter<MyCategoryImageAdapter.ViewHolder> {

    JSONArray jsonArray;
    DoThis doThis;


    public MyCategoryImageAdapter(JSONArray jsonArray, DoThis doThis) {
        this.jsonArray = jsonArray;
        this.doThis = doThis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            String name = new JSONObject(jsonArray.get(position).toString()).getString("name");
            holder.text.setText(name);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        holder.constrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doThis.doThis(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView image;
         TextView text;
         ConstraintLayout constrain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            constrain = itemView.findViewById(R.id.constrain);
        }
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
