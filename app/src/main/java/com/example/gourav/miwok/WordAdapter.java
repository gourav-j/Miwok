package com.example.gourav.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GOURAV on 28-11-2016.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;
    public WordAdapter(Context context, ArrayList<Word> words,int colorID) {
        super(context,0, words);
        mColorResourceId=colorID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null)
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        Word currentWord=getItem(position);
        TextView miwokTranslation=(TextView)listItemView.findViewById(R.id.miwok_text);
        miwokTranslation.setText(currentWord.getMiwok());
        TextView defaultTranslation=(TextView)listItemView.findViewById(R.id.default_text);
        defaultTranslation.setText(currentWord.getDefault());
        ImageView image=(ImageView)listItemView.findViewById(R.id.list_image);
        if(currentWord.hasImage())
        {
            image.setImageResource(currentWord.getImageResourceID());
            image.setVisibility(View.VISIBLE);
        }
        else
        {
            image.setVisibility(View.GONE);
        }

        View textContainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
