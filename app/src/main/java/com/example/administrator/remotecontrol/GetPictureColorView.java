package com.example.administrator.remotecontrol;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class GetPictureColorView extends View {
    private static final String TAG = "GetPictureColorView";
    private Paint mPaint;
    private Bitmap bigIcon, smallIcon;
    private int radius;
    private int size;
    private int color = Color.WHITE;//颜色，默认为白色
    public OnUpdateColorListener onUpdateColorListener;
    private Bitmap mBackgroundBitmap;
    private Paint paint;
    private float nowX, nowY;
    private ColorBean bean;
    private int nowAction;
    public interface OnUpdateColorListener {
        void changeColor(int color);
    }
    public void setOnUpdateColorListener(OnUpdateColorListener mOnUpdateColorListener) {
        this.onUpdateColorListener = mOnUpdateColorListener;
    }
    public GetPictureColorView(Context context) {
        super(context, null);
        init();
        initView();
    }
    public GetPictureColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initView();
    }
    private void init() {
        paint = new Paint();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        paint.setAntiAlias(true);
    }
    private void initView() {
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.light_group_bg);
        size = Math.min(mBackgroundBitmap.getWidth(),
                mBackgroundBitmap.getHeight());
        radius = size / 2;
        bigIcon = BitmapFactory.decodeResource(getResources(), R.drawable.positioning_big_target);
        smallIcon = BitmapFactory.decodeResource(getResources(), R.drawable.positioning_target);
        setData();
    }
    private void setData() {
        bean = new ColorBean();
        Integer col = Color.WHITE;
        bean.x = radius;
        bean.y = radius;
        bean.color = col;
        bean.type = true;
        nowX = bean.x;
        nowY = bean.y;
        color = bean.color;
    }
    public GetPictureColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size, size);
    }
    @Override    protected void onDraw(Canvas canvas) {
//画背景色盘
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mPaint);
        if (bean.type && (Math.hypot(nowX - radius, nowY - radius) < (radius) * 8 / 10)) {
            bean.x = (int) nowX;
            bean.y = (int) nowY;
            canvas.drawBitmap(bigIcon, bean.x - bigIcon.getWidth() / 2, bean.y - bigIcon.getHeight() / 2, mPaint);
        } else {
            canvas.drawBitmap(smallIcon, bean.x - smallIcon.getWidth() / 2, bean.y - smallIcon.getHeight() / 2, mPaint);
        }
        if (nowAction == MotionEvent.ACTION_UP || nowAction == MotionEvent.ACTION_CANCEL) {
            if (onUpdateColorListener != null) {
                onUpdateColorListener.changeColor(color);
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        nowX = event.getX();
        nowY = event.getY();
        //手指按下
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (Math.hypot(nowX - radius, nowY - radius) < (radius) * 8 / 10) {
                bean.type = true;
                int pixel = mBackgroundBitmap.getPixel((int) event.getX(), (int) event.getY());
                color = pixel;
                bean.color = pixel;
            } else {
                return true;
            }
        }
        //手指滑动或者离开
        else if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP) {
            if ((Math.hypot(nowX - radius, nowY - radius) < (radius) * 7 / 10)) {
                int pixel = mBackgroundBitmap.getPixel((int) event.getX(), (int)
                        event.getY());
                color = pixel;
                bean.color = pixel;
            } else {
                return true;
            }
        } else {
            return true;
        }
        nowAction = event.getAction();
        nowX = event.getX();
        nowY = event.getY();
        invalidate();
        return true;
    }
    private class ColorBean {
        boolean type = false;//是否点击
        int color;
        int x, y;//位置
    }
}

