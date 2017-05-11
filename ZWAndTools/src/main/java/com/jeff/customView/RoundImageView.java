package com.jeff.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形图片和圆角矩形图片，默认是圆形，使用圆角矩形只需调用接口设置roundcorner
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    private int roundCorner = 0;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRoundCorner(int roundcorner) {
        this.roundCorner = roundcorner;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        BitmapDrawable bd = (BitmapDrawable) getDrawable();
        if (bd != null) {
            Bitmap bitmap = bd.getBitmap();
            bitmap = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), false);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Bitmap des = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
            Paint p = new Paint();
            p.setAntiAlias(true);
            Canvas ca = new Canvas(des);
            if (roundCorner == 0) {
                int radius = Math.min(width, height);
                ca.drawCircle(radius / 2.0f, radius / 2.0f, radius / 2.0f, p);
            } else {
                RectF rect = new RectF(0, 0, width, height);
                ca.drawRoundRect(rect, roundCorner, roundCorner, p);
            }
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            ca.drawBitmap(bitmap, 0, 0, p);
            canvas.drawBitmap(des, 0, 0, null);
        }else{
            super.onDraw(canvas);
        }
    }
}
