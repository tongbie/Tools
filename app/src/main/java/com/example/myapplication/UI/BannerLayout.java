package com.example.myapplication.UI;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

/**
 * Created by aaa on 2017/9/27.
 */

/* 首页滚动横幅效果 */
public class BannerLayout extends ViewGroup {
    private Scroller scroller;
    private float mLastMotionX;
    private int currentScreenIndex = 0;
    private boolean autoScroll = true;
    private int scrollTime = 3 * 1000;
    private int currentWhat = 0;

    public BannerLayout(Context context) {
        super(context);
        initView(context);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (autoScroll && currentWhat == msg.what) {
                currentScreenIndex = (currentScreenIndex + 1) % getChildCount();
                scrollToScreen(currentScreenIndex);
                handler.sendEmptyMessageDelayed(currentWhat, scrollTime);
            }
        }
    };

    private void initView(final Context context) {
        this.scroller = new Scroller(context, new DecelerateInterpolator(2));//OvershootInterpolator(1.1f)
        handler.sendEmptyMessageDelayed(currentWhat, scrollTime);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        int cLeft = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE)
                continue;
            final int childWidth = child.getMeasuredWidth();
            child.layout(cLeft, 0, cLeft + childWidth, child.getMeasuredHeight());
            cLeft += childWidth;
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getChildCount() == 0)
            return false;
        final int action = ev.getAction();
        final float x = ev.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                autoScroll = false;
                currentWhat++;
                mLastMotionX = x;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                final int deltaX = (int) (mLastMotionX - x);
                mLastMotionX = x;
                if ((0 == currentScreenIndex && deltaX < 0) || (getChildCount() - 1 == currentScreenIndex && deltaX > 0))
                    scrollBy(deltaX / 4, 0);
                else
                    scrollBy(deltaX, 0);
                final int screenWidth = getWidth();
                currentScreenIndex = (getScrollX() + (screenWidth / 2)) / screenWidth;
                return true;
            case MotionEvent.ACTION_UP:
                snapToDestination();
                if (!autoScroll) {
                    autoScroll = true;
                    handler.sendEmptyMessageDelayed(currentWhat, scrollTime);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                snapToDestination();
                if (!autoScroll) {
                    autoScroll = true;
                    handler.sendEmptyMessageDelayed(currentWhat, scrollTime);
                }
        }
        return false;
    }

    private void  scrollToScreen(int whichScreen) {
        int delta = 0;
        delta = whichScreen * getWidth() - getScrollX();
        scroller.startScroll(getScrollX(), 0, delta, 0, 1500);
        invalidate();
        currentScreenIndex = whichScreen;
    }

    private void snapToDestination() {
        final int x = getScrollX();
        final int screenWidth = getWidth();
        scrollToScreen((x + (screenWidth / 2)) / screenWidth);
    }
}