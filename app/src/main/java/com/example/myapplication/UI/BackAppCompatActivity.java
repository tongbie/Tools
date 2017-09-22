package com.example.myapplication.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by aaa on 2017/9/20.
 */
//作为Activity的主类，实现修改界面
public class BackAppCompatActivity extends AppCompatActivity {

    public Button backButton;

    //修改通知栏颜色和标题栏颜色
    public void selfDefinedSetWindowColor(String windowColor) {
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor(windowColor));
        LinearLayout backLinearLayout = (LinearLayout) findViewById(R.id.backLinearLayout);
        backLinearLayout.setBackgroundColor(Color.parseColor(windowColor));
    }

    //设置Activity顶部TextView显示名称
    public void selfDefinedSetActivityName(String activityName) {
        TextView backTextView = (TextView) findViewById(R.id.backTextView);
        backTextView.setText(activityName);
    }
}
