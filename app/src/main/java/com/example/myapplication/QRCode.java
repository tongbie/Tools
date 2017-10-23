package com.example.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.UI.BackAppCompatActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRCode extends BackAppCompatActivity implements View.OnClickListener {
    private boolean judgelogo = false;    //判断是否带logo
    EditText edit;
    ImageView image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        selfDefinedSetActivityName("二维码");
        selfDefinedSetWindowColor("#fafafa");
        selfDefinedSetTitleBackground(R.drawable.qrcode_bead);
        ZXingLibrary.initDisplayOpinion(this);    //zxing包初始操作
        edit = (EditText) findViewById(R.id.edit);
        image = (ImageView) findViewById(R.id.image);
        Button sweep = (Button) findViewById(R.id.sweep);    //扫描
        sweep.setOnClickListener(this);
        Button creat = (Button) findViewById(R.id.creat);    //生成
        creat.setOnClickListener(this);
        Button clean = (Button) findViewById(R.id.clean);    //清除
        clean.setOnClickListener(this);
        Button save = (Button) findViewById(R.id.save);    //保存
        save.setOnClickListener(this);
        Button creatwithlogo = (Button) findViewById(R.id.creatwithlogo);    //生成带logo的二维码
        creatwithlogo.setOnClickListener(this);
        Button backButton=(Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (ContextCompat.checkSelfPermission(QRCode.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QRCode.this, new String[]{android.Manifest.permission.CAMERA}, 1);
        } else if (ContextCompat.checkSelfPermission(QRCode.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QRCode.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.creatwithlogo:   //生成带logo的二维码
                judgelogo = true;
            case R.id.creat:    //生成
                image.getLayoutParams().height = image.getWidth();    //设置图片高度=宽度
                if (edit.getText().toString().length() == 0) {
                    Toast.makeText(QRCode.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (judgelogo == false) {
                        image.setImageBitmap(bg2WhiteBitmap(CodeUtils.createImage(edit.getText().toString(), 400, 400, null)));
                    } else if (judgelogo == true) {
                        Intent intent = new Intent("android.intent.action.GET_CONTENT");    //调用系统程序
                        intent.setType("image/*");
                        startActivityForResult(intent, 222);
                    }
                }
                break;
            case R.id.save:   //保存
                if (image == null) {
                    Toast.makeText(QRCode.this, "未生成二维码", Toast.LENGTH_SHORT).show();
                    return;
                }
                File fcreat = new File("/sdcard/Tools/");
                if (!fcreat.exists()) {
                    fcreat.mkdir();    //创建目录
                }
                File f = new File("/sdcard/Tools/", "(" + getFiles("/sdcard/Tools/") + ") " + edit.getText().toString().replace("/", "").replace(":", "").replace(".", "") + ".png");
                try {
                    FileOutputStream out = new FileOutputStream(f);    //创建向f中写入数据的文件输出流
                    image.setDrawingCacheEnabled(true);    //开启缓存，以类似截图方式?从ImageView对象获取图像
                    Bitmap.createBitmap(image.getDrawingCache()).compress(Bitmap.CompressFormat.PNG, 90, out);     //创建一个带有特定宽度、高度和颜色格式的位图
                    image.setDrawingCacheEnabled(false);    //清空缓冲区
                    Toast.makeText(QRCode.this, "保存成功", Toast.LENGTH_SHORT).show();
                    out.flush();    //强制刷新缓冲区
                    out.close();
                } catch (FileNotFoundException e) {
                    Toast.makeText(QRCode.this, "文件名不合法", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(QRCode.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                Intent saveIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);    //发送系统广播通知手机有图片更新
                Uri uri = Uri.fromFile(f);
                saveIntent.setData(uri);
                this.sendBroadcast(saveIntent);
                break;
            case R.id.sweep:    //扫描
                Intent intent = new Intent(QRCode.this, CaptureActivity.class);
                startActivityForResult(intent, 111);
                break;
            case R.id.clean:    //清除
                image.setImageDrawable(null);
                edit.setText(null);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (ContextCompat.checkSelfPermission(QRCode.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(QRCode.this, "未获得相机权限，无法使用此功能", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
            case 2:
                if (ContextCompat.checkSelfPermission(QRCode.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(QRCode.this, "未获得存储权限，无法使用此功能", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
        }
    }

    public static Bitmap bg2WhiteBitmap(Bitmap oldbitmap)    //给二维码图片添加白边
    {
        int size = oldbitmap.getWidth() < oldbitmap.getHeight() ? oldbitmap.getWidth() : oldbitmap.getHeight();
        int num = 100;
        int sizebig = size + num;
        // 背图
        Bitmap bitmap = Bitmap.createBitmap(sizebig, sizebig, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // 生成白色的
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(oldbitmap, num / 2, num / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        // 画正方形的
        canvas.drawRect(0, 0, sizebig, sizebig, paint);
        return bitmap;
    }

    private int getFiles(String string) {    //获取图片数量
        int i = 0;
        File file = new File(string);
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            String name = files[j].getName();
            if (files[j].isDirectory()) {
                String dirPath = files[j].toString().toLowerCase();
                //System.out.println(dirPath);
                getFiles(dirPath + "/");
            } else if (files[j].isFile() & name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".bmp") || name.endsWith(".gif") || name.endsWith(".jpeg")) {
                //System.out.println("FileName===" + files[j].getName());
                i++;
            }
        }
        return i + 1;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    bundle.getString(CodeUtils.RESULT_STRING);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(result.toString());
                    intent.setData(content_url);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(QRCode.this, "解析二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == 222) {
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
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, opt);    //路径转Bitmap
        judgelogo = false;
        image.setImageBitmap(bg2WhiteBitmap(CodeUtils.createImage(edit.getText().toString(), 400, 400, bmp)));
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

