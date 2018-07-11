package message.kjer.simon.com.cn.newmessagelist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String Tag = "MainActivity";

    @BindView(R.id.new_message_tv)
    TextView newMessageTv;
    @BindView(R.id.add_new_message_bt)
    Button addNewMessageBt;
    @BindView(R.id.notify_bt)
    Button notifyBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.new_message_tv, R.id.add_new_message_bt, R.id.notify_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_new_message_bt:
                showMsgAndUpdateList();
                break;
            case R.id.notify_bt:
                testViewPostUpdate();
                break;
        }
    }

    private void testViewPostUpdate() {
        notifyBt.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyBt.setVisibility(View.GONE);
            }
        },3*1000);
    }

    int clickCount;

    private void showMsgAndUpdateList() {
        mHandler.removeCallbacks(mMessgaeRunable);
        TipsMessage tipsMessage = Utils.perpareMessageData(clickCount);
        clickCount++;
        if (tipsMessage == null) {
            return;
        }
        String s = tipsMessage.getContent();
        if (TextUtils.isEmpty(s)) {
            Log.e(Tag, "message data error ...");
            return;
        }
        int typeColor = Utils.getTypeColor(this, tipsMessage.getType());
        if (typeColor == 0) {
            Log.e(Tag, "message typeColor error ...value=0");
            return;
        }
        newMessageTv.setTextColor(typeColor);
        newMessageTv.setText(s);
        mHandler.postDelayed(mMessgaeRunable, tipsMessage.getHintTime());

        // TODO: 2018/7/10  将封装好的 通知bean 添加到 list

        // TODO: 2018/7/10   添加排序   
        // TODO: 2018/7/10  移除时更新对应角标
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private Runnable mMessgaeRunable = new Runnable() {
        @Override
        public void run() {
            newMessageTv.setText("");
            Log.d(Tag, "clear msg content ...");
        }
    };

}
