package com.example.myapplication.CourseTablePackage;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.UI.BackAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CourseTable extends BackAppCompatActivity {
    LinearLayout weekPanels[] = new LinearLayout[7];
    List courseData[] = new ArrayList[7];
    int itemHeight;
    int marTop, marLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursetable);
        selfDefinedSetWindowColor("#303f9f");
        selfDefinedSetActivityName("课程表");
        itemHeight = getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
        marTop = getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
        marLeft = getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);
        //数据
        getData();
        for (int i = 0; i < weekPanels.length; i++) {
            weekPanels[i] = (LinearLayout) findViewById(R.id.weekPanel_1 + i);
            //initWeekPanel(weekPanels[i], courseData[i]);
        }

    }

    public void getData() {
        List<CourseGson> list1 = new ArrayList<CourseGson>();
        CourseGson c1 = new CourseGson("软件工程", "A402", 1, 4, "典韦", "1002");
        list1.add(c1);
        list1.add(new CourseGson("C语言", "A101", 5, 3, "甘宁", "1001"));
        courseData[0] = list1;

        List<CourseGson> list2 = new ArrayList<CourseGson>();
        list2.add(new CourseGson("计算机组成原理", "A106", 5, 3, "马超", "1001"));
        courseData[1] = list2;

        List<CourseGson> list3 = new ArrayList<CourseGson>();
        list3.add(new CourseGson("数据库原理", "A105", 2, 3, "孙权", "1008"));
        list3.add(new CourseGson("计算机网络", "A405", 5, 2, "司马懿", "1009"));
        list3.add(new CourseGson("电影赏析", "A112", 9, 2, "诸葛亮", "1039"));
        courseData[2] = list3;

        List<CourseGson> list4 = new ArrayList<CourseGson>();
        list4.add(new CourseGson("数据结构", "A223", 1, 3, "刘备", "1012"));
        list4.add(new CourseGson("操作系统", "A405", 5, 3, "曹操", "1014"));
        courseData[3] = list4;

        List<Course> list5 = new ArrayList<Course>();
        list5.add(new Course("面向对象程序设计", "1-18", "信工学院综合楼109", "崔嘉"));

        /*List<CourseGson>list5=new ArrayList<CourseGson>();
        list5.add(new CourseGson("Android开发","C120",1,4,"黄盖","1250"));
        list5.add(new CourseGson("游戏设计原理","C120",8,4,"陆逊","1251"));
        courseData[4]=list5;*/
    }

    //这是对的
    /*public void initWeekPanel(LinearLayout ll, List<CourseGson> data) {
        if (ll == null || data == null || data.size() < 1) {
            return;
        }
        CourseGson pre = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            CourseGson c = data.get(i);
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, itemHeight * c.getStep() + marTop * (c.getStep() - 1));
            //void setMargins(int left, int top, int right, int bottom)
            if (i > 0) {
                lp.setMargins(marLeft, (c.getStart() - (pre.getStart() + pre.getStep())) * (itemHeight + marTop) + marTop, 0, 0);
            } else {
                lp.setMargins(marLeft, (c.getStart() - 1) * (itemHeight + marTop) + marTop, 0, 0);
            }
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.TOP);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(12);
            tv.setTextColor(getResources().getColor(R.color.courseTextColor));
            tv.setText(c.getName() + "\n" + c.getRoom() + "\n" + c.getTeach());
            tv.setBackground(getResources().getDrawable(R.drawable.qrcode_bead));    //这里设置课程背景图
            ll.addView(tv);
            pre = c;
        }
    }*/

    /*public void initWeekPanel(LinearLayout ll, List<CourseGson> data) {
        if (ll == null || data == null || data.size() < 1) {
            return;
        }
        CourseGson pre = data.get(0);    //pre = 第0列数据
        for (int i = 0; i < data.size(); i++) {
            CourseGson c = data.get(i);    // c = 每列数据
            TextView tv = new TextView(this);
            //itemHeiget =40dp, marTop = 2dp, marLeft = 2dp
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, itemHeight * c.getStep() + marTop * (c.getStep() - 1));
            //void setMargins(左,上,右,下) ,单位dp
            if (i > 0) {
                lp.setMargins(marLeft, (c.getStart()  - (pre.getStart() + pre.getStep())) * (itemHeight + marTop) + marTop, 0, 0);
            } else {
                lp.setMargins(marLeft, (c.getStart() - 1) * (itemHeight + marTop) + marTop, 0, 0);
            }
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.TOP);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(12);
            tv.setTextColor(getResources().getColor(R.color.courseTextColor));
            tv.setText(c.getName() + "\n" + c.getRoom() + "\n" + c.getTeach());
            tv.setBackground(getResources().getDrawable(R.drawable.qrcode_bead));    //这里设置课程背景图
            ll.addView(tv);
            pre = c;
        }
    }*/

    public void initWeekPanel(LinearLayout ll, List<CourseGson> data,int start,int step) {
        if (ll == null || data == null || data.size() < 1) {
            return;
        }
        CourseGson pre = data.get(0);    //pre = 第0列数据
        for (int i = 0; i < data.size(); i++) {
            CourseGson c = data.get(i);    // c = 每列数据
            TextView tv = new TextView(this);
            //itemHeiget =40dp, marTop = 2dp, marLeft = 2dp
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, itemHeight * c.getStep() + marTop * (c.getStep() - 1));
            //void setMargins(左,上,右,下) ,单位dp
            if (i > 0) {
                lp.setMargins(marLeft, (c.getStart()  - (pre.getStart() + pre.getStep())) * (itemHeight + marTop) + marTop, 0, 0);
            } else {
                lp.setMargins(marLeft, (start - 1) * (itemHeight + marTop) + marTop, 0, 0);
            }
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.TOP);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(12);
            tv.setTextColor(getResources().getColor(R.color.courseTextColor));
            tv.setText(c.getName() + "\n" + c.getRoom() + "\n" + c.getTeach());
            tv.setBackground(getResources().getDrawable(R.drawable.qrcode_bead));    //这里设置课程背景图
            ll.addView(tv);
            pre = c;
        }
    }
}

