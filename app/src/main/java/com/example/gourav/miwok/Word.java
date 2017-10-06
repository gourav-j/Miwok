package com.example.gourav.miwok;

/**
 * Created by GOURAV on 28-11-2016.
 */

public class Word {
    private int mDefaultTranslation,mMiwokTranslation; private int mAudioResourceID;
    private int mImageResourceID=NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED=-1;
    public Word(int DefaultTranslation,int MiwokTranslation,int AudioResourceID){
        mDefaultTranslation=DefaultTranslation;
        mMiwokTranslation=MiwokTranslation;
        mAudioResourceID=AudioResourceID;
    }
    public Word(int DefaultTranslation,int MiwokTranslation,int ImageResourceID,int AudioResourceID){
        mDefaultTranslation=DefaultTranslation;
        mMiwokTranslation=MiwokTranslation;
        mImageResourceID=ImageResourceID;
        mAudioResourceID=AudioResourceID;
    }

    public int getDefault(){
        return mDefaultTranslation;
    }
    public int getMiwok(){
        return mMiwokTranslation;
    }
    public int getImageResourceID(){return mImageResourceID;}
    public boolean hasImage(){
        return mImageResourceID!=NO_IMAGE_PROVIDED;
    }
    public int getAudioResourceID(){
        return mAudioResourceID;
    }
}
