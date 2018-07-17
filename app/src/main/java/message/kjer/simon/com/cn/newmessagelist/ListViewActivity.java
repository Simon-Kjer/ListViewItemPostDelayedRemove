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
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import message.kjer.simon.com.cn.newmessagelist.adapter.MessageListAdapter;
import message.kjer.simon.com.cn.newmessagelist.bean.HoverMessage;

/**
 * @author simon.
 * Date: 2018/7/16.
 * Description:
 */
public class ListViewActivity extends AppCompatActivity {
    public static final String Tag = "ListViewActivity";

    @BindView(R.id.new_message_tv)
    TextView newMessageTv;
    @BindView(R.id.add_new_message_bt)
    Button addNewMessageBt;
    @BindView(R.id.message_list_view)
    ListView messageListView;

    MessageListAdapter messageListAdapter;
    LinkedList<HoverMessage> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mDataList = new LinkedList<HoverMessage>();
        messageListAdapter = new MessageListAdapter(this, mDataList);
        messageListView.setAdapter(messageListAdapter);

        //整体动画
//        Animation animation= AnimationUtils.loadAnimation(this,R.anim.item_animation);
//        LayoutAnimationController controller=new LayoutAnimationController(animation);
//        controller.setDelay(0.5f);
//        messageListView.setLayoutAnimation(controller);
    }

    @OnClick({R.id.new_message_tv, R.id.add_new_message_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_new_message_bt:
                addMessageAndUpdate();
                break;
        }
    }

    private int clickCount;

    private void addMessageAndUpdate() {
        mHandler.removeCallbacks(mMessgaeRunable);
        HoverMessage tipsMessage = Utils.perpareMessageData(clickCount);
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
        int i = insertDataToList(tipsMessage);
        messageListView.smoothScrollToPosition(i);
        messageListAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁 ，清除参数
        Utils.clearMsgCount();
        if (mDataList != null) {
            mDataList.clear();
        }
    }

    private int insertDataToList(HoverMessage msg) {
        int index = 0;
        switch (msg.getType()) {
            case HoverMessage.WARNING:
                mDataList.add(index, msg);
                ++Utils.warningCount;
                break;
            case HoverMessage.PROMPT:
                index = Utils.warningCount;
                mDataList.add(index, msg);
                ++Utils.promptCount;
                break;
            case HoverMessage.NOTICE:
                index = Utils.promptCount + Utils.warningCount;
                mDataList.add(index, msg);
                break;

        }
        Log.e("Main", "Utils.warningCount=" + Utils.warningCount +
                " Utils.promptCount=" + Utils.promptCount + " mDataList.size=" + mDataList.size()
                + " index=" + index);
        return index;
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