package com.example.gourav.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {
    private MediaPlayer mp;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AUDIOFOCUS_LOSS_TRANSIENT||i==AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mp.pause();
                mp.seekTo(0);
            }
            else if(i==AUDIOFOCUS_GAIN){
                mp.start();
            }
            else if(i==AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.word_list,container,false);
        mAudioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.default_family_father,R.string.miwok_family_father,R.drawable.family_father,R.raw.family_father));
        words.add(new Word(R.string.default_family_mother,R.string.miwok_family_mother,R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word(R.string.default_family_son,R.string.miwok_family_son,R.drawable.family_son,R.raw.family_son));
        words.add(new Word(R.string.default_family_daughter,R.string.miwok_family_daughter,R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word(R.string.default_family_older_brother,R.string.miwok_family_older_brother,R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word(R.string.default_family_younger_brother,R.string.miwok_family_younger_brother,R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word(R.string.default_family_older_sister,R.string.miwok_family_older_sister,R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word(R.string.default_family_younger_sister,R.string.miwok_family_younger_sister,R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word(R.string.default_family_grandmother,R.string.miwok_family_grandmother,R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word(R.string.default_family_grandfather,R.string.miwok_family_grandfather,R.drawable.family_grandfather,R.raw.family_grandfather));
        WordAdapter adapter = new WordAdapter(getActivity(), words,R.color.category_family);
        ListView listView = (ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Word word=words.get(i);
                int result=mAudioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getActivity(), word.getAudioResourceID());
                    mp.start();
                    mp.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }
    private void releaseMediaPlayer() {
        if (mp != null) {
            mp.release();
            mp = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
