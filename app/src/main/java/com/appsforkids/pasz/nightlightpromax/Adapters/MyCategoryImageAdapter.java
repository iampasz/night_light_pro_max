package com.appsforkids.pasz.nightlightpromax.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Interfaces.DoThis;
import com.appsforkids.pasz.nightlightpromax.MainActivity;
import com.appsforkids.pasz.nightlightpromax.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            String name = new JSONObject(jsonArray.get(position).toString()).getString("name");
            holder.text.setText(name);
            String link_image = new JSONObject(jsonArray.get(position).toString()).getString("image");
            Picasso.get().load(link_image).into(holder.image);

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

        if(MainActivity.subscribleStatus){
            return jsonArray.length();
        }else{
            return 2;
        }

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
