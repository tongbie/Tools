package com.example.myapplication;

import android.app.Activity;
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

import com.example.myapplication.CourseTablePackage.CourseTable;
import com.example.myapplication.UI.Fruit;
import com.example.myapplication.UI.FruitAdapter;
import com.example.myapplication.UI.MyListView;
import com.example.myapplication.Weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long mExitTime;    //返回键计时
    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();    //隐藏标题栏
        actionBar.hide();
        initView();
        initFruits();//用以添加List子项
    }

    /* list点击事件 */
    private class ListClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Fruit fruit = fruitList.get(position);    //通过position选项获取用户点击的是哪一个子项
            Object activity = null;
            switch (fruit.getName().toString()) {
                case "计算器":
                    activity = CounterActivity.class;
                    break;
                case "通知":
                    activity = NotificationActivity.class;
                    break;
                case "二维码":
                    activity = QRCodeActivity.class;
                    break;
                case "天气":
                    activity = WeatherActivity.class;
                    break;
                case "地图":
                    activity = MapActivity.class;
                    break;
                default:
                    return;
            }
            startActivity(new Intent(MainActivity.this, (Class) activity));
        }
    }

    /* 添加View */
    private void initView() {
        ((Button) findViewById(R.id.notification_banner)).setOnClickListener(this);
        ((Button) findViewById(R.id.qrcode_banner)).setOnClickListener(this);
        ((Button) findViewById(R.id.weather_banner)).setOnClickListener(this);
        ((Button) findViewById(R.id.map_banner)).setOnClickListener(this);
        ((Button) findViewById(R.id.new_button)).setOnClickListener(this);
        {
            FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);//适配器
            MyListView listView = (MyListView) findViewById(R.id.listView);    //mainactivity.listView列表菜单
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new ListClick());
        }
    }

    @Override
    public void onClick(View view) {
        Object activity = null;
        switch (view.getId()) {
            case R.id.title_list:
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.notification_banner:
                activity = NotificationActivity.class;
                break;
            case R.id.qrcode_banner:
                activity = QRCodeActivity.class;
                break;
            case R.id.weather_banner:
                activity = WeatherActivity.class;
                break;
            case R.id.map_banner:
                activity = MapActivity.class;
                break;
            case R.id.new_button:
                activity = CourseTable.class;
                break;
            default:
                return;
        }
        startActivity(new Intent(MainActivity.this,(Class)activity));
    }

    //为listView添加子项
    private void initFruits() {
        fruitList.add(new Fruit("计算器", R.drawable.counter));
        fruitList.add(new Fruit("通知", R.drawable.notification));
        fruitList.add(new Fruit("二维码", R.drawable.qrcode));
        fruitList.add(new Fruit("天气", R.drawable.weather));
        fruitList.add(new Fruit("地图", R.drawable.map));

        fruitList.add(new Fruit("计算器", R.drawable.counter));
        fruitList.add(new Fruit("通知", R.drawable.notification));
        fruitList.add(new Fruit("二维码", R.drawable.qrcode));
        fruitList.add(new Fruit("天气", R.drawable.weather));
        fruitList.add(new Fruit("地图", R.drawable.map));
    }

    /* 双击返回 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
