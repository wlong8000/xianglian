package com.xianglian.love.utils;

/**
 * Created by Administrator on 2016/11/30.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.xianglian.love.R;


/**
 * 自定义圆形图片控件
 * Created by ice on 2016/5/5.
 */
public class RoundImage extends ImageView {
    /**
     * 半径
     */
    private int radius;
    /**
     * 模式，手动设置半径大小还是按图片大小裁剪
     */
    private int mode;
    private static final int AUTO = 0;
    private static final int MANUAL = 1;

    private Paint paint;
    /**
     * 画布大小，即RoundImage大小
     */
    private int mWidth;
    private int mHeight;

    public RoundImage(Context context) {
        this(context, null);
    }

    public RoundImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundImage, defStyleAttr, 0);

        mode = a.getInt(R.styleable.RoundImage_round_mode, AUTO);//默认AUTO
        radius = (int) a.getDimension(R.styleable.RoundImage_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        Log.e("log", "mode   " + mode + "  radius  " + radius);

        paint = new Paint();

        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);//不继承ImageView中的onDraw方法，否则会显示未处理的图做北京

        Drawable drawable = getDrawable();//获取图片
        if (drawable == null) {
            return;
        }
        /**
         * 根据图片宽高创建一样大小的空白Bitmap
         */
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        /**
         * 将空白Bitmap作为画布，将图片画上，实现drawable转Bitmap
         */
        Canvas c = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(c);

        Bitmap newBmp;

        float scaleWidth = ((float) mWidth / w);   // 计算缩放比例
        float scaleHeight = ((float) mHeight / h);

        if(scaleWidth!=1||scaleHeight!=1)//如果图像大小和画布大小不同，则缩放
        {
            Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
            matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
            newBmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图

        }
        else{
            newBmp=bitmap;
        }


        if (mode == AUTO) {//如果自动模式，以图像大小一半作为半径
            radius = newBmp.getHeight() / 2;
        }

        /**
         * 以图片作为填充器，画圆时，以图片填充圆环内部，实现圆形图片效果
         */
        BitmapShader bitmapShader = new BitmapShader(newBmp, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        canvas.drawCircle(newBmp.getWidth() / 2, newBmp.getHeight() / 2, radius, paint);


    }


}
