package com.example.myapplication.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

/**
 * Created by aaa on 2017/9/1.
 */

/* 适配器 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resourceId;
    private List<Fruit> fruitList;
    private Context context;

    /* 构造方法传参 */
    public FruitAdapter(Context context, int resourseId, List<Fruit> fruitList) {
        super(context, resourseId, fruitList);
        this.context=context;
        this.resourceId = resourseId;
        this.fruitList =fruitList;
    }

    /* 用以向List提供一个视图
    * convertView用以存放缓存列表项
    * * 在某项变为不可见时，将其存储在convertView中
    * * 重新赋值即可使用 */
    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(getContext()).inflate(resourceId, viewGroup, false);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.fruit_image);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.fruit_name);
            convertView.setTag(viewHolder);//setTag()用以向View追加额外数据
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Fruit fruit = getItem(position);
        viewHolder.imageView.setImageResource(fruit.getImageId());//在setTag()后赋值仍然生效？
        viewHolder.textView.setText(fruit.getName());
        return convertView;
    }


    public /*final*/ class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
