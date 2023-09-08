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
import com.appsforkids.pasz.nightlightpromax.MainActivity;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class JsonMusicAdapter extends RecyclerView.Adapter<JsonMusicAdapter.ListMusicHolder> {

    ActionCalback actionCalback;
    ArrayList<AudioFile> arrayList;
    private String nameSong;

    int pressedPosition = -1;
    int currentMusicPosition = -1;

    public JsonMusicAdapter(ActionCalback actionCalback, ArrayList<AudioFile> arrayList) {
        this.actionCalback = actionCalback;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ListMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music, parent, false);
        return new ListMusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMusicHolder holder, int position) {

        int absolutePosition = position;

        if (chekAudio(arrayList.get(absolutePosition).getInternetLink())) {
            holder.download_bt.setVisibility(View.INVISIBLE);
        }

        holder.music_name.setText(arrayList.get(absolutePosition).getNameSong());
        holder.music_author.setText(arrayList.get(absolutePosition).getAuthorSong());

        if (arrayList.get(absolutePosition).getStatus()) {
            holder.download_bt.setVisibility(View.INVISIBLE);
        }

        holder.download_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chekAudio(arrayList.get(absolutePosition).getInternetLink())) {
                    Log.i("CHEK_DOWNLOAD_BUTTON", " true2");
                    try {
                        actionCalback.delete(absolutePosition);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    actionCalback.download(absolutePosition);
                    Log.i("CHEK_DOWNLOAD_BUTTON", actionCalback + " false");
                }

                //notifyDataSetChanged();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentMusicPosition = -1;

                if (pressedPosition == absolutePosition) {
                    holder.play_item.setImageResource(R.drawable.bt_pause);
                    Log.i("MYPLAYER", pressedPosition + " тут должен плей поставить " + holder.getAdapterPosition());
                    actionCalback.play(-1);
                    pressedPosition = -1;
                } else {

                    actionCalback.play(absolutePosition);
                    holder.play_item.setImageResource(R.drawable.bt_play);
                    Log.i("MYPLAYER", pressedPosition + " тут должен стоп поставить " + holder.getAdapterPosition());
                    pressedPosition = absolutePosition;
                }
                notifyDataSetChanged();
            }
        });

        if (absolutePosition == pressedPosition) {
            holder.play_item.setImageResource(R.drawable.bt_pause);

        } else {
            holder.play_item.setImageResource(R.drawable.bt_play);
        }

        if (absolutePosition == currentMusicPosition && currentMusicPosition != -1) {
            holder.play_item.setImageResource(R.drawable.bt_pause);
        }
    }

    @Override
    public int getItemCount() {

        if(MainActivity.subscribleStatus){
            return arrayList.size();
        }else{
            return 3;
        }

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

    public void setPressedPosition() {
        nameSong = "";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    private Boolean chekAudio(String link) {

        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .name("MyRealm")
                .allowWritesOnUiThread(true)
                .build();

        Realm realm = Realm.getInstance(configuration);

        Log.i("CHEKAUDIO", link);

        AudioFile audioFile = realm.where(AudioFile.class).isNotNull("lockalLink").equalTo("internetLink", link).findFirst();

        // RealmResults<AudioFile> array = realm.where(AudioFile.class).isNotNull("lockalLink").findAll();

        Boolean answer = false;

        if (audioFile != null) {
            answer = true;

            Log.i("CHEKAUDIO", audioFile.getLockalLink() + "  nn " + link);
        }
        return answer;
    }
}
