package message.kjer.simon.com.cn.newmessagelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import message.kjer.simon.com.cn.newmessagelist.custom.MyMultipleFingerScrollLayout;

/**
 * @author simon.
 * Date: 2018/7/18.
 * Description:
 */
public class MyTouchActvity extends AppCompatActivity implements MyMultipleFingerScrollLayout
        .IScrollCallback {
    private final static String Tag = MyTouchActvity.class.getSimpleName();
    @BindView(R.id.touch_view)
    MyMultipleFingerScrollLayout touchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_layout);
        ButterKnife.bind(this);
        touchView.setCallback(this);
    }

    @Override
    public void onFingerStart() {
        Log.e(Tag, "onFingerStart");
    }

    @Override
    public void onFingerEnd() {
        Log.e(Tag, "onFingerEnd");
    }

    @Override
    public void onFingerScrolling(int percent) {
        Log.e(Tag, "onFingerScrolling");
    }

    @Override
    public void onMultipleFigerTouching() {
        Log.e(Tag, "onMultipleFigerTouching");
    }
}
