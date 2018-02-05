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
    private Handler uiHandler = new Handler();
    private boolean pointer = false;
    private EditText input;
    private String count = new String();
    private int buttons[] = new int[]{
            R.id.point, R.id.bc, R.id.bb, R.id.chu,
            R.id.b7, R.id.b8, R.id.b9, R.id.cheng,
            R.id.b4, R.id.b5, R.id.b6, R.id.jian,
            R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.jia,
            R.id.b0, R.id.deng
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_counter);

        for (int i = 0; i < buttons.length; i++) {
            ((Button) findViewById(buttons[i])).setOnClickListener(this);
        }
        input = (EditText) findViewById(R.id.input);
        new SlipBackClass(this).bind();//右滑退出
        Window window = getWindow();//修改通知栏
        window.setStatusBarColor(Color.parseColor("#e9191919"));//可以自定义状态栏颜色
    }

    @Override
    public void onClick(View view) {
        BackColor color = new BackColor();
        color.setView(view);
        switch (view.getId()) {
            case R.id.b0:
                if (count.length() == 1 && count.charAt(0) == '0')
                    break;
                if (count.length() > 2)
                    if (count.charAt(count.length() - 2) == '+' || count.charAt(count.length() - 2) == '-' || count.charAt(count.length() - 2) == '*' || count.charAt(count.length() - 2) == '/')
                        if (count.charAt(count.length() - 1) == '0')
                            break;
                count += "0";
                break;
            case R.id.b1:
                count += "1";
                break;
            case R.id.b2:
                count += "2";
                break;
            case R.id.b3:
                count += "3";
                break;
            case R.id.b4:
                count += "4";
                break;
            case R.id.b5:
                count += "5";
                break;
            case R.id.b6:
                count += "6";
                break;
            case R.id.b7:
                count += "7";
                break;
            case R.id.b8:
                count += "8";
                break;
            case R.id.b9:
                count += "9";
                break;
            case R.id.bb:
                if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '.')
                        pointer = false;
                    count = count.substring(0, count.length() - 1);
                }
                break;
            case R.id.bc:
                pointer = false;
                count = count.substring(0, 0);
                break;
            case R.id.point:
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
                pointer = false;
                if (count.length() == 0)
                    break;
                if (count.length() > 0)
                    if (count.charAt(count.length() - 1) == '+' || count.charAt(count.length() - 1) == '-' || count.charAt(count.length() - 1) == '*' || count.charAt(count.length() - 1) == '/' || count.charAt(count.length() - 1) == '.')
                        break;
                count += "+";
                break;
            case R.id.jian:
                pointer = false;
                if (count.length() > 0) {
                    if (count.charAt(count.length() - 1) == '+' || count.charAt(count.length() - 1) == '-' || count.charAt(count.length() - 1) == '*' || count.charAt(count.length() - 1) == '/' || count.charAt(count.length() - 1) == '.')
                        break;
                }
                count += "-";
                break;
            case R.id.cheng:
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
        color.start();
        view.setBackgroundColor(Color.parseColor("#2d313e"));
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

    public class BackColor extends Thread {
        private View view = null;

        public void setView(View view) {
            this.view = view;
        }

        public void run() {
            try {
                Thread.sleep(220);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        int id = view.getId();
                        if (id == R.id.chu || id == R.id.cheng || id == R.id.jian || id == R.id.jia) {
                            view.setBackgroundColor(Color.parseColor("#a63d46"));
                        } else {
                            view.setBackgroundColor(Color.parseColor("#373b4a"));
                        }
                    }
                };
                uiHandler.post(runnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}