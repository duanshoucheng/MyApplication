package com.example.administrator.imgcomposition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * 处理图片注意oom
 * Created by Administrator on 2015/8/24.
 */
public class ImgUtil {

    /**
     * 将Drawable转化为Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable){
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ?
                Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

//        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//        return bitmapDrawable.getBitmap();
    }

    /**
     * 将Bitmap转换为Drawable
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap){
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    /**
     * byte[] 转 bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap bytesToBimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * bitmap 转 byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 水印
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap compositionNewImgForWater(Bitmap src, Bitmap watermark){
        if (src == null)
            return null;
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src,0,0,null);  //在0,0坐标开始画入src
        cv.drawBitmap(watermark,w-ww+5,h-wh+5,null); //在src的右下角画入水印
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newb;
    }

    /**
     *  将彩色图转换为灰度图:方法一
     */
    public static Bitmap convertGreyImg01(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();

        int []pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0;i<height;i++){
            for (int j=0;j<width;j++){
                int grey = pixels[width * i +j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = ((grey) & 0x000000ff);

                grey = (int)((float)red * 0.3 + (float)green *0.59+(float)blue*0.11);
                grey = alpha | (grey << 16)|(grey << 8 )|grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return  result;
    }

    /**
     * 将彩色图转换为灰度图：方法二
     */
    public static Bitmap convertGreyImg02(Bitmap img){
        Bitmap mGrayBitmap = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mGrayBitmap);
        Paint mPaint = new Paint();

        //创建颜色变换矩阵
        ColorMatrix mColorMatrix = new ColorMatrix();
        //设置灰度影响
        mColorMatrix.setSaturation(0);
        //创建颜色过滤矩阵
        ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(mColorMatrix);
        //设置画笔的颜色过滤矩阵
        mPaint.setColorFilter(mColorFilter);
        //使用处理后的画笔绘制图像
        mCanvas.drawBitmap(img, 0, 0, mPaint);
        return  mGrayBitmap;
    }

    /**
     * 图片放大缩小
     * @param img
     * @return
     */
    public static Bitmap actionScaleImg(Bitmap img,int w,int h){
        int width = img.getWidth();
        int height = img.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(0.75f, 0.75f);
        Bitmap mScaleBitmap = Bitmap.createScaledBitmap(img, w, h, true);
//        Bitmap mScaleBitmap = Bitmap.createBitmap(img,0,0,width,height,matrix,true);

        return mScaleBitmap;
    }

    /**
     * 图片旋转
     * @param img
     * @param rotate 旋转的角度
     * @return
     */
    public static Bitmap actionRotatedImg(Bitmap img,int rotate){
        int width = img.getWidth();
        int height= img.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(rotate);
//        matrix.postRotate(rotate);
        Bitmap mRotateBitmap = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return mRotateBitmap;
    }

    /**
     * 图片倾斜
     * @param img
     * @param xSkew
     * @param ySkew
     * @return
     */
    public static Bitmap actionScrewImg(Bitmap img,float xSkew,float ySkew){
        int width = img.getWidth();
        int height = img.getHeight();

        Matrix matrix = new Matrix();
        matrix.preSkew(xSkew,ySkew);  //1.0f, 0.15f
        Bitmap mScrewBitmap = Bitmap.createBitmap(img,0,0,width,height,matrix,true);
        return mScrewBitmap;
    }

    /**
     * 图片倒影
     * @param img
     * @return
     */
    public static Bitmap actionReflectedImg(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1,-1);  //图片缩放，x轴变为原来的1倍，实现图片的反转

        //创建反转后的图片Bitmap对象，图片高是原图的一半。
        //Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, height/2, width, height/2, matrix, false);
        //创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。
        //注意两种createBitmap的不同
        //Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height*3/2, Config.ARGB_8888);

        Bitmap mInverseBitmap = Bitmap.createBitmap(img,0,0,width,height,matrix,false);
        Bitmap mReflectedBitmap = Bitmap.createBitmap(width,height*2, Bitmap.Config.ARGB_8888);

        //把新建的位图作为画板
        Canvas mCanvas = new Canvas(mReflectedBitmap);
        //绘制图片
        mCanvas.drawBitmap(img,0,0,null);
        mCanvas.drawBitmap(mInverseBitmap,0,height,null);

        //添加倒影的渐变效果
        Paint mPaint = new Paint();
        Shader mShader = new LinearGradient(0,height,0,mReflectedBitmap.getHeight(),0x70ffffff, 0x00ffffff, Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        //设置叠加模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //绘制遮罩效果
        mCanvas.drawRect(0,height,width,mReflectedBitmap.getHeight(),mPaint);

        return mReflectedBitmap;
    }

    /**
     * 图像剪切:未完成
     */
    public static Bitmap actionCutImg(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();

        int w = img.getWidth();
        int h = img.getHeight();
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawBitmap(img,0,0,mPaint);

        return null;
    }

    /**
     * 获得圆角图片的方法
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
