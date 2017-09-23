package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.UI.Fruit;
import com.example.myapplication.UI.FruitAdapter;
import com.example.myapplication.WeatherPackage.Weather;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long mExitTime;    //返回键计时
    private List<Fruit> fruitList = new ArrayList<>();
    Button titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        ActionBar actionBar = getSupportActionBar();    //隐藏标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        initFruits();    //MainActivity.initFruits()，用以添加子项
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);    //适配器?
        ListView listView = (ListView) findViewById(R.id.listView);    //mainactivity.listView列表菜单
        listView.setAdapter(adapter);    //通过适配器为listView添加选项
        titleList = (Button) findViewById(R.id.title_list);
        titleList.setOnClickListener(this);
        Button titleMenu = (Button) findViewById(R.id.title_menu);
        titleMenu.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);    //通过position选项获取用户点击的是哪一个子项
                switch (fruit.getName().toString()) {
                    case "计算器":
                        Intent counter = new Intent(MainActivity.this, Counter.class);
                        startActivity(counter);
                        break;
                    case "通知":
                        Intent notification = new Intent(MainActivity.this, Notification.class);
                        startActivity(notification);
                        break;
                    case "二维码":
                        Intent qrcode = new Intent(MainActivity.this, QRCode.class);
                        startActivity(qrcode);
                        break;
                    case "天气":
                        Intent weather = new Intent(MainActivity.this, Weather.class);
                        startActivity(weather);
                        break;
                    case "Null":
                        Intent Null = new Intent(MainActivity.this, CourseTable.class);
                        startActivity(Null);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_list:
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
//                    titleList.setBackground(getResources().getDrawable(R.drawable.list));
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
//                    titleList.setBackground(getResources().getDrawable(R.drawable.menu));
                }
                break;
        }
    }

    private void initFruits() {    //为listView添加子项
        Fruit counter = new Fruit("计算器", R.drawable.counter);
        fruitList.add(counter);
        Fruit notification = new Fruit("通知", R.drawable.notification);
        fruitList.add(notification);
        Fruit qrcode = new Fruit("二维码", R.drawable.qrcode);
        fruitList.add(qrcode);
        Fruit werther = new Fruit("天气", R.drawable.weather);
        fruitList.add(werther);
        Fruit Null = new Fruit("Null", R.drawable.add);
        fruitList.add(Null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {    //监听返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {    //返回键
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
