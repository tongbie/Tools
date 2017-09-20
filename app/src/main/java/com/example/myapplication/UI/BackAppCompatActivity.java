package com.example.myapplication.UI;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

/**
 * Created by aaa on 2017/9/20.
 */
//作为Activity的主类，实现修改界面
public class BackAppCompatActivity extends AppCompatActivity {
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

    //声明外部类 ：通过重写构造函数，传入相应的activity，和相应的控件，不过很少这样做的，内部类可以提高程序的内聚性，
    public class SendSmsListener implements View.OnLongClickListener {
        private Activity act;
        private EditText address;
        private EditText content;

        public SendSmsListener(Activity act, EditText address, EditText content) {
            this.act = act;
            this.address = address;
            this.content = content;
        }

        @Override
        public boolean onLongClick(View source) {
            String addressStr = address.getText().toString();
            String contentStr = content.getText().toString();
            SmsManager smsManager = SmsManager.getDefault();
            PendingIntent sentIntent = PendingIntent.getBroadcast(act, 0,
                    new Intent(), 0);
            smsManager.sendTextMessage(addressStr, null, contentStr
                    , sentIntent, null);
            Toast.makeText(act, "短信发送完成", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
