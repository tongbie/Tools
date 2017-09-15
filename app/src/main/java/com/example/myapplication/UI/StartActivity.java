package com.example.myapplication.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.myapplication.MainActivity;

public class StartActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    //隐藏标题栏，需主类继承自Activity
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);    //隐藏通知栏
        //imageView=(ImageView)findViewById(R.id.welcome_img) ;
        //imageView.setImageResource(R.drawable.img_4);
        //new Thread(new Runnable() {
            //@Override
            //public void run() {
                //try {
                    //Thread.sleep(1000);
                    Intent startactivity=new Intent(StartActivity.this,MainActivity.class);
                    startActivity(startactivity);
                    finish();
                //}
                //catch (InterruptedException e) {
                    //e.printStackTrace();
                //}
            //}
        //}).start();
    }
}
