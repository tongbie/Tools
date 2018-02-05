package com.example.myapplication.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

/**
 * Created by aaa on 2017/9/20.
 */
//作为Activity的主类，实现修改界面
public abstract class BackActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout title;

    public BackActivity(){
//        title=(LinearLayout)View.generateViewId(R.id.titleLayout);

        LinearLayout.LayoutParams layoutParams=
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onClick(View v){
        if (v.getId()==R.id.backButton)
            finish();
    }

    //修改通知栏颜色和标题栏颜色
    public void setWindowColor(String windowColor) {
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor(windowColor));
        LinearLayout backLinearLayout = (LinearLayout) findViewById(R.id.backLinearLayout);
        backLinearLayout.setBackgroundColor(Color.parseColor(windowColor));
    }

    public void setTitleBackground(int titleColor){
        LinearLayout backLinearLayout = (LinearLayout) findViewById(R.id.backLinearLayout);
        backLinearLayout.setBackground(this.getDrawable(titleColor));
    }

    //设置Activity顶部TextView显示名称
    public void setTitleName(String activityName) {
        TextView backTextView = (TextView) findViewById(R.id.backTextView);
        backTextView.setText(activityName);
    }
}
