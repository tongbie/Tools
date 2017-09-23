package com.example.myapplication.UI;

/**
 * Created by aaa on 2017/9/23.
 */

public class GoogleCard {

    private String mDescription;
    private int mDrawable;

    public GoogleCard(String mDescription, int mDrawable) {
        this.mDescription = mDescription;
        this.mDrawable = mDrawable;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getDrawable() {
        return mDrawable;
    }

    public void setDrawable(int mDrawable) {
        this.mDrawable = mDrawable;
    }

}

