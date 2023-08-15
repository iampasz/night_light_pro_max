package com.appsforkids.pasz.nightlightpromax.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Interfaces.ActionCalback;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;


import java.util.ArrayList;

import io.realm.Realm;

public class ListMusicAdapter extends RecyclerView.Adapter<ListMusicAdapter.ListMusicHolder> {

    ActionCalback actionCalback;
    ArrayList<AudioFile> audioFileAll;
    private String nameSong;

    public ListMusicAdapter(ActionCalback actionCalback, ArrayList<AudioFile> audioFileAll ){
        this.actionCalback = actionCalback;
        this.audioFileAll = audioFileAll;
    };

    @NonNull
    @Override
    public ListMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_music, parent, false);
        return new ListMusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMusicHolder holder, int position) {

        int AbsolutePosition = position;


        if(chekAudio(audioFileAll.get(AbsolutePosition).getInternetLink())){
            holder.download_bt.setImageResource(R.drawable.bt_close);
        }

        holder.music_name.setText(audioFileAll.get(AbsolutePosition).nameSong);
        holder.music_author.setText(audioFileAll.get(AbsolutePosition).authorSong);

        if(audioFileAll.get(AbsolutePosition).getStatus()){
            holder.download_bt.setImageResource(R.drawable.bt_close);
        }

        holder.download_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chekAudio(audioFileAll.get(AbsolutePosition).getInternetLink())){
                    Log.i("CHEK_DOWNLOAD_BUTTON", " true");
                }else{
                    actionCalback.download(AbsolutePosition);
                    Log.i("CHEK_DOWNLOAD_BUTTON", actionCalback+" false");
                }
            }
        });


        holder.music_constrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.play_item.setImageResource(R.drawable.pause_vector_gradient);

                if(audioFileAll.get(holder.getAbsoluteAdapterPosition()).getNameSong().equals(nameSong)){
                    holder.play_item.setImageResource(R.drawable.bt_play);
                    //.pressPosition(holder.getAbsoluteAdapterPosition(), false);
                    actionCalback.play(holder.getAbsoluteAdapterPosition());
                    nameSong="";
                }else{
                    actionCalback.play(holder.getAbsoluteAdapterPosition());
                    holder.play_item.setImageResource(R.drawable.bt_pause);
                    nameSong = audioFileAll.get(holder.getAbsoluteAdapterPosition()).getNameSong();
                }
                // notifyDataSetChanged();
            }
        });

        if(audioFileAll.get(holder.getAdapterPosition()).getNameSong().equals(nameSong)){
            holder.play_item.setImageResource(R.drawable.bt_pause);

        }else{
            holder.play_item.setImageResource(R.drawable.bt_play);
        }

        if(audioFileAll.get(holder.getAdapterPosition()).getNameSong().equals(nameSong) && nameSong!=""){
            holder.play_item.setImageResource(R.drawable.bt_pause);
        }
    }

    @Override
    public int getItemCount() {
        return audioFileAll.size();
    }

    public class ListMusicHolder extends RecyclerView.ViewHolder {
    private ImageView play_item;
    private TextView music_name;
    private TextView music_author;
    private ImageView download_bt;
    private ConstraintLayout music_constrain;
        public ListMusicHolder(@NonNull View itemView) {
            super(itemView);
            play_item = itemView.findViewById(R.id.play_item);
            music_name = itemView.findViewById(R.id.music_name);
            music_author = itemView.findViewById(R.id.music_author);
            download_bt = itemView.findViewById(R.id.download_bt);
            music_constrain = itemView.findViewById(R.id.music_constrain);
        }
    }

    public void setPressedPosition(){
        nameSong="";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    private Boolean chekAudio(String link){

        Realm realm = Realm.getDefaultInstance();

        Log.i("CHEKAUDIO",  link);

        AudioFile audioFile =  realm.where(AudioFile.class).isNotNull("lockalLink").equalTo("internetLink", link).findFirst();

       // RealmResults<AudioFile> array = realm.where(AudioFile.class).isNotNull("lockalLink").findAll();

        Boolean answer = false;

        if(audioFile!=null){
            answer = true;

            Log.i("CHEKAUDIO", audioFile.getLockalLink()+"  nn " + link);
        }
        return answer;
    }
}
