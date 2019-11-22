package com.loslink.opencv;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("image_process");
        System.loadLibrary("opencv_java");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv_test=findViewById(R.id.iv_test);
        iv_test.setImageBitmap(blur(BitmapFactory.decodeResource(getResources(),R.drawable.test)));
    }

    /**
     * 毛玻璃一张图片
     * @param srcBitmap    原始图片
     * @return  毛玻璃后的图片
     */
    public static Bitmap blur(Bitmap srcBitmap){
        // 获取原始图片的宽高
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        // 初始化一个用来存储图片所有像素的int数组
        int[] pixels = new int[width * height];
        // 把原始图片的所有原始存入数组中
        srcBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        // 通过jni本地方法毛玻璃化图片
        blurImage(pixels, width, height);
        // 创建一个新的图片
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // 把处理后的图片像素设置给新图片
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    // 毛玻璃图片
    public static native void blurImage(int[] pixels, int w, int h);
}
