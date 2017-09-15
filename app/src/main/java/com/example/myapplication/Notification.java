package com.example.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Notification extends AppCompatActivity implements View.OnClickListener {
    ImageButton sign1;
    ImageButton sign2;
    ImageButton sign3;
    ImageButton sign4;
    Button send;    //以选中图标发送
    Button chooseFromAlbum;
    Button circular;    //绘制为圆形
    Boolean circular_judge = false;    //判断是否裁剪为圆形
    EditText title;
    Bitmap bmp = null;
    EditText text;
    private ImageView picture;
    public static final int CHOOSE_PHOTO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        picture = (ImageView) findViewById(R.id.picture);
        Toast.makeText(Notification.this, "使用此功能，需开启通知权限", Toast.LENGTH_SHORT).show();
        LinearLayout NotificationLinearLayout = (LinearLayout) findViewById(R.id.NotificationLinearLayout);
        ViewGroup.LayoutParams layoutParams = NotificationLinearLayout.getLayoutParams();
        layoutParams.height = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() / 4;    //设置四个图标高度
        NotificationLinearLayout.setLayoutParams(layoutParams);
        sign1 = (ImageButton) findViewById(R.id.sign1);
        sign2 = (ImageButton) findViewById(R.id.sign2);
        sign3 = (ImageButton) findViewById(R.id.sign3);
        sign4 = (ImageButton) findViewById(R.id.sign4);
        send = (Button) findViewById(R.id.send);
        circular = (Button) findViewById(R.id.circular);    //绘制为圆形
        circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circular_judge = true;
                if (ContextCompat.checkSelfPermission(Notification.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Notification.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
        title = (EditText) findViewById(R.id.title);
        text = (EditText) findViewById(R.id.text);
        sign1.setOnClickListener(this);
        sign2.setOnClickListener(this);
        sign3.setOnClickListener(this);
        sign4.setOnClickListener(this);
        send.setOnClickListener(this);
        chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Notification.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Notification.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        String gettitle = title.getText().toString();
        String gettext = text.getText().toString();
        switch (view.getId()) {
            case R.id.sign1:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sign1);
                break;
            case R.id.sign2:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sign2);
                break;
            case R.id.sign3:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sign3);
                break;
            case R.id.sign4:
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sign4);
                break;
            case R.id.send:

        }
        Intent home=new Intent(Intent.ACTION_MAIN);    //返回桌面
        home.addCategory(Intent.CATEGORY_HOME);
        PendingIntent pi=PendingIntent.getActivities(this,0, new Intent[]{home},0);    //延迟执行的intent
        NotificationManager manager4 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        android.app.Notification notification4 = new NotificationCompat.Builder(this)
                .setContentTitle(gettitle)
                .setContentText(gettext)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bmp)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pi)    //通知点击事件
                .setAutoCancel(true)    //点击消失
                .build();
        manager4.notify(1, notification4);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "没有获得存储权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openAlbum() {    //打开相册选择照片
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    //回调函数
        switch (requestCode) {
            case CHOOSE_PHOTO:
                handleImageOnKitKat(data);
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {    //获取从相册选择图片路径
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        if (circular_judge == true) {    //判断是否以圆形发送
            bmp = createCircleImage(getSDCardImg(imagePath), getImageWidthHeight(imagePath));
        } else if (circular_judge == false) {
            bmp = getSDCardImg(imagePath);
        }
        picture.setImageBitmap(bmp);    //显示图标预览图
        circular_judge = false;
    }

    public static Bitmap getSDCardImg(String imagePath) {    //将相片路径转换为Bitmap
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        return BitmapFactory.decodeFile(imagePath, opt);
    }

    /*private void displayImage(String imagePath) {    //显示相片,传入路径转换为Bitmap后显示
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(Notification.this, "失败了", Toast.LENGTH_SHORT).show();
        }
    }*/

    private Bitmap createCircleImage(Bitmap source, int min)    //将图片绘制为圆形
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);    //产生一个同样大小的画布
        Canvas canvas = new Canvas(target);    //首先绘制圆形
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);    //使用SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));    //绘制图片
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    public static int getImageWidthHeight(String path) {    //获取相片宽度
        //public static int[] getImageWidthHeight(String path){    //原方法定义
        BitmapFactory.Options options = new BitmapFactory.Options();
        //最关键在此，把options.inJustDecodeBounds = true;
        //这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        //ptions.outHeight为原始图片的高
        //return new int[]{options.outWidth,options.outHeight};    //原方法返回值
        return options.outWidth;
    }


    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
