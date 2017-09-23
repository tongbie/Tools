package com.example.myapplication.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

/**
 * Created by aaa on 2017/9/23.
 */

public class GoogleCardAdapter extends BaseAdapter {
    private List<GoogleCard> mCards;
    private Context mContext;

    public GoogleCardAdapter(Context mContext, List<GoogleCard> mCards) {
        this.mContext = mContext;
        this.mCards = mCards;
    }

    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Object getItem(int Index) {
        return mCards.get(Index);
    }

    @Override
    public long getItemId(int Index) {
        return Index;
    }

    @Override
    public View getView(int Index, View mView, ViewGroup mParent) {
        ViewHolder mHolder = new ViewHolder();
        mView = LayoutInflater.from(mContext).inflate(R.layout.google_card_adapter, null);
        mHolder.Card_Title = (TextView) mView.findViewById(R.id.Card_Title);
        mHolder.Card_Title.setText(mCards.get(Index).getDescription());
        mHolder.Card_Pic = (ImageView) mView.findViewById(R.id.Card_Pic);
        //记住啊，这里是setImageResource()方法，不是setBackgroundResource(),否则图像会变形啊
        mHolder.Card_Pic.setImageResource(mCards.get(Index).getDrawable());
        return mView;
    }

    private static class ViewHolder {
        TextView Card_Title;
        ImageView Card_Pic;
    }
}

