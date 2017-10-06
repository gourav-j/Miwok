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
public class NumbersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        mAudioManager=(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.default_numbers_one,R.string.miwok_numbers_one,R.drawable.number_one,R.raw.number_one));
        words.add(new Word(R.string.default_numbers_two,R.string.miwok_numbers_two,R.drawable.number_two,R.raw.number_two));
        words.add(new Word(R.string.default_numbers_three,R.string.miwok_numbers_three,R.drawable.number_three,R.raw.number_three));
        words.add(new Word(R.string.default_numbers_four,R.string.miwok_numbers_four,R.drawable.number_four,R.raw.number_four));
        words.add(new Word(R.string.default_numbers_five,R.string.miwok_numbers_five,R.drawable.number_five,R.raw.number_five));
        words.add(new Word(R.string.default_numbers_six,R.string.miwok_numbers_six,R.drawable.number_six,R.raw.number_six));
        words.add(new Word(R.string.default_numbers_seven,R.string.miwok_numbers_seven,R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word(R.string.default_numbers_eight,R.string.miwok_numbers_eight,R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word(R.string.default_numbers_nine,R.string.miwok_numbers_nine,R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word(R.string.default_numbers_ten,R.string.miwok_numbers_ten,R.drawable.number_ten,R.raw.number_ten));
        WordAdapter adapter = new WordAdapter(getActivity(), words,R.color.category_numbers);
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
