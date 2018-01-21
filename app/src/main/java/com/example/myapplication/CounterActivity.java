package com.example.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.UI.SlipBackClass;

public class CounterActivity extends Activity implements View.OnClickListener {
    Handler uiHandler = new Handler();
    Button b0;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button bb;
    Button bc;
    Button jia;
    Button jian;
    Button cheng;
    Button chu;
    Button deng;
    Button point;
    Button change;
    boolean pointer = false;
    EditText input;
    String count = new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_counter);
        b0 = (Button) findViewById(R.id.b0);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        bb = (Button) findViewById(R.id.bb);
        bc = (Button) findViewById(R.id.bc);
        jia = (Button) findViewById(R.id.jia);
        jian = (Button) findViewById(R.id.jian);
        cheng = (Button) findViewById(R.id.cheng);
        chu = (Button) findViewById(R.id.chu);
        deng = (Button) findViewById(R.id.deng);
        point = (Button) findViewById(R.id.point);
        change = (Button) findViewById(R.id.change);
        input = (EditText) findViewById(R.id.input);

        /* 设置右滑退出 */
        SlipBackClass slideBackLayout;
        slideBackLayout = new SlipBackClass(this);
        slideBackLayout.bind();
        /* 设置右滑退出 */

        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        bb.setOnClickListener(this);
        bc.setOnClickListener(this);
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
        cheng.setOnClickListener(this);
        chu.setOnClickListener(this);
        deng.setOnClickListener(this);
        point.setOnClickListener(this);
        change.setOnClickListener(this);
        //修改通知栏
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#e9191919"));//可以自定义状态栏颜色
    }

    @Override
    public void onClick(View v) {
        ButtonBackground color = new ButtonBackground();
        switch (v.getId()) {
            case R.id.b0:
                color.start();
                b0.setBackgroundColor(Color.parseColor("#2d313e"));
                /*if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '0')
                        if (count.charAt(0) == '0' && pointer == false)
                            break;
                }*/
                if (count.length() == 1 && count.charAt(0) == '0')
                    break;
                if (count.length() > 2)
                    if (count.charAt(count.length() - 2) == '+' || count.charAt(count.length() - 2) == '-' || count.charAt(count.length() - 2) == '*' || count.charAt(count.length() - 2) == '/')
                        if (count.charAt(count.length() - 1) == '0')
                            break;
                count += "0";
                break;
            case R.id.b1:
                color.start();
                b1.setBackgroundColor(Color.parseColor("#2d313e"));
                /*if (count.length() > 0) {
                    if (count.charAt(0) == '0' && pointer == false)
                        break;
                }*/
                count += "1";
                break;
            case R.id.b2:
                color.start();
                b2.setBackgroundColor(Color.parseColor("#2d313e"));
                count += "2";
                break;
            case R.id.b3:
                color.start();
                b3.setBackgroundColor(Color.parseColor("#2d313e"));
                count += "3";
                break;
            case R.id.b4:
                color.start();
                b4.setBackgroundColor(Color.parseColor("#2d313e"));
                count += "4";
                break;
            case R.id.b5:
                color.start();
                b5.setBackgroundColor(Color.parseColor("#2d313e"));
                count += "5";
                break;
            case R.id.b6:
                color.start();
                b6.setBackgroundColor(Color.parseColor("#2d313e"));
                count += "6";
                break;
            case R.id.b7:
                color.start();
                b7.setBackgroundColor(Color.parseColor("#2d313e"));
                count += "7";
                break;
            case R.id.b8:
                color.start();
                b8.setBackgroundColor(Color.parseColor("#2d313e"));
                count += "8";
                break;
            case R.id.b9:
                color.start();
                b9.setBackgroundColor(Color.parseColor("#2d313e"));
                count += "9";
                break;
            case R.id.bb:
                color.start();
                bb.setBackgroundColor(Color.parseColor("#2d313e"));
                if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '.')
                        pointer = false;
                    count = count.substring(0, count.length() - 1);
                }
                break;
            case R.id.bc:
                color.start();
                bc.setBackgroundColor(Color.parseColor("#2d313e"));
                pointer = false;
                count = count.substring(0, 0);
                break;
            case R.id.point:
                color.start();
                point.setBackgroundColor(Color.parseColor("#2d313e"));
                if (count.length() == 0)
                    break;
                if (pointer == true)
                    break;
                if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '+' || count.charAt(count.length() - 1) == '-' || count.charAt(count.length() - 1) == '*' || count.charAt(count.length() - 1) == '/' || count.charAt(count.length() - 1) == '.')
                        break;
                }
                pointer = true;
                count += ".";
                break;
            case R.id.jia:
                color.start();
                jia.setBackgroundColor(Color.parseColor("#94343c"));
                pointer = false;
                if (count.length() == 0)
                    break;
                if (count.length() > 0)
                    if (count.charAt(count.length() - 1) == '+' || count.charAt(count.length() - 1) == '-' || count.charAt(count.length() - 1) == '*' || count.charAt(count.length() - 1) == '/' || count.charAt(count.length() - 1) == '.')
                        break;
                count += "+";
                break;
            case R.id.jian:
                color.start();
                jian.setBackgroundColor(Color.parseColor("#94343c"));
                pointer = false;
                if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '+' || count.charAt(count.length() - 1) == '-' || count.charAt(count.length() - 1) == '*' || count.charAt(count.length() - 1) == '/' || count.charAt(count.length() - 1) == '.')
                        break;
                }
                count += "-";
                break;
            case R.id.cheng:
                color.start();
                cheng.setBackgroundColor(Color.parseColor("#94343c"));
                pointer = false;
                if (count.length() == 0)
                    break;
                if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '+' || count.charAt(count.length() - 1) == '-' || count.charAt(count.length() - 1) == '*' || count.charAt(count.length() - 1) == '/' || count.charAt(count.length() - 1) == '.')
                        break;
                }
                count += "*";
                break;
            case R.id.chu:
                color.start();
                chu.setBackgroundColor(Color.parseColor("#94343c"));
                pointer = false;
                if (count.length() == 0)
                    break;
                if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '+' || count.charAt(count.length() - 1) == '-' || count.charAt(count.length() - 1) == '*' || count.charAt(count.length() - 1) == '/' || count.charAt(count.length() - 1) == '.')
                        break;
                }
                count += "/";
                break;
            case R.id.deng:
                color.start();
                deng.setBackgroundColor(Color.parseColor("#2d313e"));
                pointer = false;
                if (count.length() == 0)
                    break;
                if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '+' || count.charAt(count.length() - 1) == '-' || count.charAt(count.length() - 1) == '*' || count.charAt(count.length() - 1) == '/' || count.charAt(count.length() - 1) == '.')
                        break;
                    double result = Counter(count);
                    if (result - (int) result == 0) {
                        count = count.substring(0, 0);
                        count += (int) result;
                        input.setText(count);
                    } else {
                        count = count.substring(0, 0);
                        pointer = true;
                        if (result >= 1) {
                            java.text.DecimalFormat df = new java.text.DecimalFormat("#.000");
                            result = Double.valueOf(df.format(result)).doubleValue();
                            count += result;
                        } else if (result < 1 && result >= 0.1) {
                            java.text.DecimalFormat df = new java.text.DecimalFormat("#.0000");
                            result = Double.valueOf(df.format(result)).doubleValue();
                            count += result;
                        } else if (result < 0.1 && result >= 0.00001) {
                            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00000");
                            result = Double.valueOf(df.format(result)).doubleValue();
                            count += result;
                        } else if (result < 0.00001 && result >= 0) {
                            Toast.makeText(CounterActivity.this, "数值过小", Toast.LENGTH_LONG).show();
                        } else if (result > -0.00001 && result < 0) {
                            Toast.makeText(CounterActivity.this, "数值过小", Toast.LENGTH_LONG).show();
                        } else {
                            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00000");
                            result = Double.valueOf(df.format(result)).doubleValue();
                            count += result;
                        }
                    }
                }
        }
        input.setText(count);
    }

    private double Counter(String reckon) {
        double intenger[] = new double[42];
        char character[] = new char[42];
        int intenger1 = 0, character1 = 0, position = 0, i, j;
        boolean judge = false;
        char characterKind;
        if (reckon.charAt(0) == '-') {
            reckon = reckon.substring(1);
            judge = true;
        }
        reckon += '+';
        try {
            while (reckon.length() > 1) {
                characterKind = reckon.charAt(0);
                if (characterKind == '+' || characterKind == '-' || characterKind == '*' || characterKind == '/') {
                    character[character1] = characterKind;
                    character1++;
                    reckon = reckon.substring(1);
                } else {
                    while (reckon.charAt(position) != '+' && reckon.charAt(position) != '-' && reckon.charAt(position) != '*' && reckon.charAt(position) != '/') {
                        position++;
                    }
                    String str = reckon.substring(0, position);
                    reckon = reckon.substring(position);
                    intenger[intenger1] = Double.valueOf(str).doubleValue();
                    intenger1++;
                    position = 0;
                }
            }
            if (judge == true)
                intenger[0] = -intenger[0];
            while (intenger1 > 1) {
                begin:
                for (j = 0; j < character1; j++) {
                    if (character[j] == '*') {
                        intenger[j + 1] = intenger[j] * intenger[j + 1];
                        for (i = j; i < intenger1; i++) {
                            intenger[i] = intenger[i + 1];
                        }
                        intenger1--;
                        for (i = j; i < character1; i++) {
                            character[i] = character[i + 1];
                        }
                        character1--;
                        break;
                    }
                    if (character[j] == '/') {
                        if (intenger[j + 1] == 0) {
                            Toast.makeText(CounterActivity.this, "不合法操作", Toast.LENGTH_LONG).show();
                            return intenger[j];
                        }
                        intenger[j + 1] = intenger[j] / intenger[j + 1];
                        for (i = j; i < intenger1; i++) {
                            intenger[i] = intenger[i + 1];
                        }
                        intenger1--;
                        for (i = j; i < character1; i++) {
                            character[i] = character[i + 1];
                        }
                        character1--;
                        break;
                    }
                    for (int c = 0; c < character1; c++) {
                        if (character[c] == '*' || character[c] == '/')
                            continue begin;
                    }
                    if (character[j] == '+') {
                        intenger[j + 1] = intenger[j] + intenger[j + 1];
                        for (i = j; i < intenger1; i++) {
                            intenger[i] = intenger[i + 1];
                        }
                        intenger1--;
                        for (i = j; i < character1; i++) {
                            character[i] = character[i + 1];
                        }
                        character1--;
                        break;
                    }
                    if (character[j] == '-') {
                        intenger[j + 1] = intenger[j] - intenger[j + 1];
                        for (i = j; i < intenger1; i++) {
                            intenger[i] = intenger[i + 1];
                        }
                        intenger1--;
                        for (i = j; i < character1; i++) {
                            character[i] = character[i + 1];
                        }
                        character1--;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            intenger[0] = 0;
        }
        return intenger[0];
    }

    public class ButtonBackground extends Thread {
        public void run() {
            try {
                Thread.sleep(220);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        b0.setBackgroundColor(Color.parseColor("#373b4a"));
                        b1.setBackgroundColor(Color.parseColor("#373b4a"));
                        b2.setBackgroundColor(Color.parseColor("#373b4a"));
                        b3.setBackgroundColor(Color.parseColor("#373b4a"));
                        b4.setBackgroundColor(Color.parseColor("#373b4a"));
                        b5.setBackgroundColor(Color.parseColor("#373b4a"));
                        b6.setBackgroundColor(Color.parseColor("#373b4a"));
                        b7.setBackgroundColor(Color.parseColor("#373b4a"));
                        b8.setBackgroundColor(Color.parseColor("#373b4a"));
                        b9.setBackgroundColor(Color.parseColor("#373b4a"));
                        bb.setBackgroundColor(Color.parseColor("#373b4a"));
                        bc.setBackgroundColor(Color.parseColor("#373b4a"));
                        jia.setBackgroundColor(Color.parseColor("#a63d46"));
                        jian.setBackgroundColor(Color.parseColor("#a63d46"));
                        cheng.setBackgroundColor(Color.parseColor("#a63d46"));
                        chu.setBackgroundColor(Color.parseColor("#a63d46"));
                        deng.setBackgroundColor(Color.parseColor("#373b4a"));
                        point.setBackgroundColor(Color.parseColor("#373b4a"));
                    }
                };
                uiHandler.post(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}