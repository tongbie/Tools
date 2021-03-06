package com.example.myapplication.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by mjc on 2016/2/26.
 * 功能：当activity布局中嵌入当前布局，该activity可以从边缘滑动关闭
 * 实现原理：
 * 1.获取DecorView的RootView,删除RootView，把RootView添加到当前View
 * 再把当前View添加到DecorView
 */
public class SlipBackClass extends LinearLayout {
    private ViewGroup viewGroup;//当前Activity的DecorView
    private View view;//DecorView下的LinearLayout
    private Activity activity;//需要边缘滑动删除的Activity
    private ViewDragHelper viewDragHelper;//Drag助手类
    private float mSlideWidth;//触发退出当前Activity的宽度
    private int mScreenWidth;//屏幕宽度
    private int mScreenHeight;//屏幕高度
    private Paint paint;//画笔，用来绘制阴影效果
    private int curSlideX;//用于记录当前滑动距离
    public SlipBackClass slideBackLayout;


    public SlipBackClass(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        /*必须是传入Activity*/
        activity = (Activity) context;//构造ViewDragHelper
        viewDragHelper = ViewDragHelper.create(this, new DragCallback());
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);//设置从左边缘捕捉View
        paint = new Paint();//初始化画笔
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
    }

    /*绑定方法，在Activity的DecorView下插入当前ViewGroup,原来的RootView放于当前ViewGroup下*/
    public void bind() {
        viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        view = viewGroup.getChildAt(0);
        viewGroup.removeView(view);
        this.addView(view);
        viewGroup.addView(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();//计算屏幕宽度
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        mSlideWidth = displayMetrics.widthPixels *0.28f;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        /*松手回调*/
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int left = releasedChild.getLeft();
            if (left <= mSlideWidth) {//当前回调，松开手时触发，比较触发条件和当前的滑动距离
                viewDragHelper.settleCapturedViewAt(0, 0);//缓慢滑动的方法,小于触发条件，滚回去
            } else {//大于触发条件，滚出去...
                viewDragHelper.settleCapturedViewAt(mScreenWidth, 0);
            }
            //需要手动调用更新界面的方法
            invalidate();
        }

        /*位置改变回调*/
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            curSlideX = left;
            invalidate();//当滑动位置改变时，刷新View,绘制新的阴影位置
            if (changedView == view && left >= mScreenWidth) {//当滚动位置到达屏幕最右边，则关掉Activity
                activity.finish();
            }
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            left = left >= 0 ? left : 0;//限制左右拖拽的位移
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;//上下不能移动，返回0
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            //触发边缘时，主动捕捉mRootView
            viewDragHelper.captureChildView(view, pointerId);
        }
    }


    @Override
    public void computeScroll() {
        //使用settleCapturedViewAt方法是，必须重写computeScroll方法，传入true
        //持续滚动期间，不断刷新ViewGroup
        if (viewDragHelper.continueSettling(true))
            invalidate();

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //进行阴影绘制,onDraw（）方法在ViewGroup中不一定会执行
        drawShadow(canvas);
        super.dispatchDraw(canvas);

    }

    private void drawShadow(Canvas canvas) {
        canvas.save();
        //构造一个渐变
        Shader mShader = new LinearGradient(curSlideX - 40, 0, curSlideX, 0, new int[]{Color.parseColor("#1edddddd"), Color.parseColor("#6e666666"), Color.parseColor("#9e666666")}, null, Shader.TileMode.REPEAT);
        //设置着色器
        paint.setShader(mShader);
        //绘制时，注意向左边偏移
        RectF rectF = new RectF(curSlideX - 40, 0, curSlideX, mScreenHeight);
        canvas.drawRect(rectF, paint);
        canvas.restore();
    }
}
