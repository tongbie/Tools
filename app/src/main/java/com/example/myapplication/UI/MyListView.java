package com.example.myapplication.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by BieTong on 2018/1/30.
 */

public class MyListView extends ListView {
    private Context context;

    public MyListView(Context context) {
        super(context);
        this.context = context;
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /* 实现ListView弹性效果 */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY,
                scrollRangeX, scrollRangeY,
                maxOverScrollX, (int) dp(60),
                isTouchEvent);

    }

    private float dp(float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return px * scale + 0.5f;
    }
}
