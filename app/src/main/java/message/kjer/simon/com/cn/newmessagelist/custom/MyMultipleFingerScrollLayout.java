package message.kjer.simon.com.cn.newmessagelist.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import java.util.ArrayList;

import message.kjer.simon.com.cn.newmessagelist.R;

/**
 * @author simon.
 * Date: 2018/7/18.
 * Description:
 * 原作者 地址 https://github.com/devin1014/AndroidDemo
 */
public class MyMultipleFingerScrollLayout extends FrameLayout {
    private String TAG = getClass().getSimpleName();

    private static final int FINGER_TOUCH_DURATION = 500;

    private float mScale = 0.8f;

    private Paint mBlackPaint = new Paint();

    private Paint mRedPaint = new Paint();

//    private Paint mTextPaint = new Paint();

    private Finger mFirstFinger;

    private Finger mSecondFinger;

    private boolean mFingerTouchMode = false;

    private int mPercent;

    //scroll
    private int mTouchSlop;

    private IScrollCallback mCallback;

    public interface IScrollCallback {
        void onFingerStart();

        void onFingerEnd();

        void onFingerScrolling(int percent);

        void onMultipleFigerTouching();
    }

    public MyMultipleFingerScrollLayout(Context context) {
        super(context);
        initialize();
    }

    public MyMultipleFingerScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MyMultipleFingerScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize();
    }

    @SuppressWarnings("deprecation")
    private void initialize() {
        setWillNotDraw(false);
        setScale(mScale);
        mBlackPaint.setColor(getResources().getColor(R.color.black));
        mBlackPaint.setStrokeWidth(10f);
        mRedPaint.setColor(getResources().getColor(R.color.colorAccent));
        mRedPaint.setStrokeWidth(10f);
//        mTextPaint.setColor(getResources().getColor(R.color.black));
//        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.pointer_text_size));
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setCallback(IScrollCallback callback) {
        mCallback = callback;
    }

    public void setScale(float scale) {
        if (scale > 0f && scale < 1f) {
            mScale = scale;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, parseAction(ev.getActionMasked()) + " , index:" + ev.getActionIndex());

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                resetFinger();
                final int pointerId = ev.getPointerId(ev.getActionIndex());
                final int index = ev.findPointerIndex(pointerId);
                Log.d(TAG, "pointerId:" + pointerId + " ,index:" + index);
                mFirstFinger = new Finger(pointerId);
                TouchPoint touchPoint = new TouchPoint(ev.getX(index), ev.getY(index));
                mFirstFinger.add(touchPoint);
                mFirstFinger.setTargetPoint(touchPoint);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE: {
                mFingerTouchMode = false;
                resetFinger();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mFirstFinger != null) {
                    final int index = ev.findPointerIndex(mFirstFinger.id);
                    if (index != -1) {
                        TouchPoint touchPoint = new TouchPoint(ev.getX(index), ev.getY(index));
                        mFirstFinger.add(touchPoint);
                        if (mSecondFinger == null) {
                            mFirstFinger.setTargetPoint(touchPoint);
                        }
                    }
                }

                if (mSecondFinger != null) {
                    final int index = ev.findPointerIndex(mSecondFinger.id);
                    if (index != -1) {
                        TouchPoint touchPoint = new TouchPoint(ev.getX(index), ev.getY(index));
                        mSecondFinger.add(touchPoint);
                    }
                }

                if (mFirstFinger != null && mSecondFinger != null) {
                    if (Math.abs(mFirstFinger.touchTime - mSecondFinger.touchTime) <=
                            FINGER_TOUCH_DURATION) {
                        int fd = mFirstFinger.getDistanceFromTargetPoint();
                        int sd = mSecondFinger.getDistanceFromTargetPoint();
                        Log.d(TAG, "finger dis:" + fd + " , " + sd);
                        if (fd * sd > 0) {
                            mFingerTouchMode = true;
                            int dDelta = (fd + sd) / 2;
                            if (dDelta > 100) {


//                            mPercent = Math.min((int) (dDelta * 100f * mScale / getHeight()),
// 100);
                                if (mCallback != null) {
//                                mCallback.onFingerScrolling(mPercent);
                                    mCallback.onMultipleFigerTouching();
                                }
                            }
                        }
                    }
                }

                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                if (mSecondFinger == null) {
                    final int pointerId = ev.getPointerId(ev.getActionIndex());
                    final int index = ev.findPointerIndex(pointerId);
                    Log.d(TAG, "pointerId:" + pointerId + " ,index:" + index);
                    mSecondFinger = new Finger(pointerId);
                    TouchPoint touchPoint = new TouchPoint(ev.getX(index), ev.getY(index));
                    mSecondFinger.add(touchPoint);
                    mSecondFinger.setTargetPoint(touchPoint);
                    if (mCallback != null) {
                        mCallback.onFingerStart();
                    }
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                mFingerTouchMode = ev.getPointerCount() > 2;

                final int pointerUpId = ev.getPointerId(ev.getActionIndex());
                Log.d(TAG, "pointerUpId:" + pointerUpId);
                if (mFirstFinger != null && pointerUpId == mFirstFinger.id) {
                    if (mSecondFinger != null) {
                        mFirstFinger = mSecondFinger;
                        mFirstFinger.resetTargetPoint();
                        mSecondFinger = null;
                    }
                } else if (mSecondFinger != null && pointerUpId == mSecondFinger.id) {
                    if (mFirstFinger != null) mFirstFinger.resetTargetPoint();
                    mSecondFinger = null;
                }
                if (mCallback != null) {
                    mCallback.onFingerEnd();
                }
                mPercent = 0;
                Log.d(TAG, "firstPointerId:" + (mFirstFinger != null ? mFirstFinger.id : "-1"));
                Log.d(TAG, "secondPointerId:" + (mSecondFinger != null ? mSecondFinger.id : "-1"));
                break;
            }
        }

        if (!mFingerTouchMode) {
            super.dispatchTouchEvent(ev);
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mFirstFinger != null) {
            drawLine(canvas, mFirstFinger.getTouchList(), mBlackPaint);
        }
        if (mSecondFinger != null) {
            drawLine(canvas, mSecondFinger.getTouchList(), mRedPaint);
        }

//        canvas.drawText(String.valueOf(mPercent) + "%", getWidth() / 2, getHeight() / 2,
//                mTextPaint);
    }

    // 把手指的移动轨迹画出来
    private void drawLine(Canvas canvas, ArrayList<TouchPoint> touchPoints, Paint paint) {
        int size = touchPoints.size();
        if (size > 1) {
            TouchPoint lastPoint = touchPoints.get(0);
            for (int i = 1; i < size; i++) {
                TouchPoint newPoint = touchPoints.get(i);
                canvas.drawLine(lastPoint.x, lastPoint.y, newPoint.x, newPoint.y, paint);
                lastPoint = newPoint;
            }
        }
    }

    private void resetFinger() {
        mFirstFinger = null;
        mSecondFinger = null;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------
    // Helper class
    // -------------------------------------------------------------------------------------------------------------------------------------
    static class Finger {
        final int id;
        final long touchTime;
        // 用ArrayList保存MotionEvent，每次add之后list里最后一项把前面的都覆盖了！
        // 这里定义了TouchPoint类来存坐标x和y
        private ArrayList<TouchPoint> touchList;
        private TouchPoint targetPoint;

        Finger(int id) {
            this.id = id;
            touchList = new ArrayList<>(100);
            touchTime = SystemClock.uptimeMillis();
        }

        void add(TouchPoint touchPoint) {
            touchList.add(touchPoint);
        }

        void resetTargetPoint() {
            if (touchList.size() == 0) {
                targetPoint = null;
            } else {
                targetPoint = touchList.get(touchList.size() - 1);
            }
        }

        void setTargetPoint(TouchPoint touchPoint) {
            targetPoint = touchPoint;
        }

        int getId() {
            return id;
        }

        int getDistanceFromTargetPoint() {
            if (touchList.size() == 0 || targetPoint == null) return 0;
            return (int) (touchList.get(touchList.size() - 1).y - targetPoint.y);
        }

        ArrayList<TouchPoint> getTouchList() {
            return touchList;
        }

    }

    static class TouchPoint {
        float x;
        float y;

        TouchPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static String parseAction(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "DOWN";
            case MotionEvent.ACTION_UP:
                return "UP";
            case MotionEvent.ACTION_MOVE:
                return "MOVE";
            case MotionEvent.ACTION_CANCEL:
                return "CANCEL";
            case MotionEvent.ACTION_OUTSIDE:
                return "OUTSIDE";
            case MotionEvent.ACTION_POINTER_DOWN:
                return "POINTER_DOWN";
            case MotionEvent.ACTION_POINTER_UP:
                return "POINTER_UP";
        }
        return String.valueOf(action);
    }
}
