package com.appsforkids.pasz.nightlightpromax.Fragments.Melodies;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsforkids.pasz.nightlightpromax.Adapters.ListMusicAdapter;
import com.appsforkids.pasz.nightlightpromax.Interfaces.ActionCalback;
import com.appsforkids.pasz.nightlightpromax.MyMediaPlayer;
import com.appsforkids.pasz.nightlightpromax.R;
import com.appsforkids.pasz.nightlightpromax.RealmObjects.AudioFile;
import com.appsforkids.pasz.nightlightpromax.WrapContentLinearLayoutManager;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.CreateMyMediaPlayerUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.DeleteFileUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.GetAudioFilesFromRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.GetMediaPlayerUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.InstanceRealmConfigurationUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.PlayMelodyUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.RemoveAudioFromRealmUseCase;
import com.appsforkids.pasz.nightlightpromax.domain.usecase.StopPlayingUseCase;

import io.realm.Realm;
import io.realm.RealmResults;

public class MelodyListFragment extends Fragment {

    Realm realm = new InstanceRealmConfigurationUseCase().connect();
    RemoveAudioFromRealmUseCase removeAudioUseCase = new RemoveAudioFromRealmUseCase();
    StopPlayingUseCase stopPlaying = new StopPlayingUseCase();
    PlayMelodyUseCase playMelodyUseCase = new PlayMelodyUseCase();
    CreateMyMediaPlayerUseCase createMyMediaPlayerUseCase = new CreateMyMediaPlayerUseCase();
    GetAudioFilesFromRealmUseCase getAudioFilesFromRealm = new GetAudioFilesFromRealmUseCase();
    DeleteFileUseCase deleteFileUseCase = new DeleteFileUseCase();
    ListMusicAdapter listMusicAdapter;
    MyMediaPlayer myMediaPlayer;
    GetMediaPlayerUseCase getMediaPlayerUseCase = new GetMediaPlayerUseCase();

    public MelodyListFragment() {
        super(R.layout.list_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView close_button = (ImageView) view.findViewById(R.id.close_button);
        myMediaPlayer = getMediaPlayerUseCase.getPlayer(getActivity());
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv_cards);
        LinearLayoutManager llm = new WrapContentLinearLayoutManager(getContext());
        RealmResults<AudioFile> arrayList = getAudioFilesFromRealm.getArray(realm);
        ActionCalback actionCalback = new ActionCalback() {
            @Override
            public void play(int position) {
                if (position == -1) {
                    stopPlaying.stop(myMediaPlayer);
                } else {
                    stopPlaying.stop(myMediaPlayer);

                    if (arrayList.get(position).getLockalLink() != null) {
                        myMediaPlayer.playAudio(arrayList.get(position).getLockalLink());
                    } else {
                        if (arrayList.get(position).getAuthorSong() != null) {
                            myMediaPlayer.playAudio(arrayList.get(position).getResourceLink());
                        }
                    }
                }
            }

            @Override
            public void download(int position) {

            }

            @Override
            public void delete(int position) {

                AudioFile audioFile = arrayList.get(position);
                assert audioFile != null;

                String file_name = audioFile.getFileName();
                String internet_link = audioFile.getInternetLink();

                if (deleteFileUseCase.delete(getActivity(), file_name)) {
                    removeAudioUseCase.remove(realm, internet_link);
                    listMusicAdapter.notifyItemChanged(position);
                }
            }
        };

        Boolean ans = stopPlaying.stop(myMediaPlayer);
        Log.i("ANS", ans + "");

        listMusicAdapter = new ListMusicAdapter(actionCalback, arrayList);
        rv.setLayoutManager(llm);
        rv.setAdapter(listMusicAdapter);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabAudiotFragment tabAudiotFragment = (TabAudiotFragment) getParentFragmentManager()
                        .findFragmentByTag("TAB_AUDIO_FRAGMENT");

                assert tabAudiotFragment != null;
                getParentFragmentManager()
                        .beginTransaction()
                        .remove(tabAudiotFragment)
                        .commit();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        listMusicAdapter.notifyDataSetChanged();
    }
}