package com.randomdot.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.sax.EndElementListener;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.randomdot.R;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

/**
 * date:2019/7/23
 * name:windy
 * function:
 */
public class RandomDot extends View {

    private Paint mGreenCirclePaint; //绿色的圆点
    private Paint mRedCirclePaint;   //红色的圆点
    private Paint mRectPaint;   //矩形

    private List<Point> list = new ArrayList<>();
    private List<Integer> delList = new ArrayList<>();
    private boolean flag;
    private int cx; //定义圆心横坐标
    private int cy; //定义圆心纵坐标
    private int mWidth;
    private int mHeight;
    private int rectStartX;
    private int rectStartY;
    private int rectEndX;
    private int rectEndY;

    public RandomDot(Context context) {
        this(context, null);
    }

    public RandomDot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RandomDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {

        /**
         * 初始化画笔
         */
        mGreenCirclePaint = new Paint();
        mRedCirclePaint = new Paint();
        mRectPaint = new Paint();

        mGreenCirclePaint.setColor(ContextCompat.getColor(context, R.color.colorCircle));

        mRedCirclePaint.setColor(ContextCompat.getColor(context, R.color.colorRed));

        mRectPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取自定义view的宽高值
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //如果矩形从左上往右下画： 则初始下标小于结束的下标
        if (rectStartX < rectEndX && rectStartY < rectEndY) {
            canvas.drawRect(rectStartX, rectStartY, rectEndX, rectEndY, mRectPaint);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).x >= rectStartX &&
                        list.get(i).x <= rectEndX &&
                        list.get(i).y >= rectStartY &&
                        list.get(i).y <= rectEndY
                        ) {
                    canvas.drawCircle(list.get(i).x, list.get(i).y, 10, mRedCirclePaint);
                } else {
                    canvas.drawCircle(list.get(i).x, list.get(i).y, 10, mGreenCirclePaint);
                }
            }
        } else if (rectStartX > rectEndX && rectStartY > rectEndY) {//从右下往左上画
            canvas.drawRect(rectEndX, rectEndY, rectStartX, rectStartY, mRectPaint);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).x >= rectEndX &&
                        list.get(i).x <= rectStartX &&
                        list.get(i).y >= rectEndY &&
                        list.get(i).y <= rectStartY
                        ) {
                    canvas.drawCircle(list.get(i).x, list.get(i).y, 10, mRedCirclePaint);
                } else {
                    canvas.drawCircle(list.get(i).x, list.get(i).y, 10, mGreenCirclePaint);
                }
            }
        } else if (rectStartX < rectEndX && rectEndY < rectStartY) {//从左下往右上画
            canvas.drawRect(rectStartX, rectEndY, rectEndX, rectStartY, mRectPaint);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).x >= rectStartX &&
                        list.get(i).x <= rectEndX &&
                        list.get(i).y >= rectEndY &&
                        list.get(i).y <= rectStartY
                        ) {
                    canvas.drawCircle(list.get(i).x, list.get(i).y, 10, mRedCirclePaint);
                } else {
                    canvas.drawCircle(list.get(i).x, list.get(i).y, 10, mGreenCirclePaint);
                }
            }
        } else {//从右上往左下画
            canvas.drawRect(rectEndX, rectStartY, rectStartX, rectEndY, mRectPaint);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).x >= rectEndX &&
                        list.get(i).x <= rectStartX &&
                        list.get(i).y >= rectStartY &&
                        list.get(i).y <= rectEndY
                        ) {
                    canvas.drawCircle(list.get(i).x, list.get(i).y, 10, mRedCirclePaint);
                } else {
                    canvas.drawCircle(list.get(i).x, list.get(i).y, 10, mGreenCirclePaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: //抬起事件
                break;
            case MotionEvent.ACTION_MOVE: //移动事件
                //获取在滑动中的矩形坐标 (右下角or左上角)
                rectEndX = (int) event.getX();
                rectEndY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN: //按下事件
                //获取初始的矩形坐标 (左上角or右下角)
                rectStartX = (int) event.getX();
                rectStartY = (int) event.getY();
                break;
        }
        return true;
    }

    /**
     * 添加随机圆点
     */
    public void addRandDot() {

        cx = new Random().nextInt(mWidth);
        cy = new Random().nextInt(mHeight);

        Point point = new Point();

        point.x = cx;
        point.y = cy;

        list.add(point);

        invalidate();//刷新
    }

    /**
     * 删除矩形框内的圆点
     */
    public void clearCircle() {
//        for (int k = 0; k < delList.size(); k++) {
//            list.remove(delList.get(k));
//        }
//                        list.removeAll(delList);
//        mRedCirclePaint.
//        delList.clear();
        flag = true;
        invalidate();
    }
}
