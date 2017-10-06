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
public class PhrasesFragment extends Fragment {
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
        words.add(new Word(R.string.default_phrases_where_are_you_going,R.string.miwok_where_are_you_going,R.raw.phrase_where_are_you_going));
        words.add(new Word(R.string.default_phrases_what_is_your_name,R.string.miwok_phrases_what_is_your_name,R.raw.phrase_what_is_your_name));
        words.add(new Word(R.string.default_phrases_my_name_is,R.string.miwok_phrases_my_name_is,R.raw.phrase_my_name_is));
        words.add(new Word(R.string.default_phrases_how_are_you_feeling,R.string.miwok_phrases_how_are_you_feeling,R.raw.phrase_how_are_you_feeling));
        words.add(new Word(R.string.default_phrases_im_feeling_good,R.string.miwok_phrases_im_feeling_good,R.raw.phrase_im_feeling_good));
        words.add(new Word(R.string.default_phrases_are_you_coming,R.string.miwok_phrases_are_you_coming,R.raw.phrase_are_you_coming));
        words.add(new Word(R.string.default_phrases_yes_im_coming,R.string.miwok_phrases_yes_im_coming,R.raw.phrase_yes_im_coming));
        words.add(new Word(R.string.default_phrases_im_coming,R.string.miwok_phrases_im_coming,R.raw.phrase_im_coming));
        words.add(new Word(R.string.default_phrases_lets_go,R.string.miwok_phrases_lets_go,R.raw.phrase_lets_go));
        words.add(new Word(R.string.default_phrases_come_here,R.string.miwok_phrases_come_here,R.raw.phrase_come_here));
        WordAdapter adapter = new WordAdapter(getActivity(), words,R.color.category_phrases);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
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
