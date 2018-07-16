package message.kjer.simon.com.cn.newmessagelist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import message.kjer.simon.com.cn.newmessagelist.adapter.MessageRecycleListAdatper;
import message.kjer.simon.com.cn.newmessagelist.bean.HoverMessage;

/**
 * @author simon.
 * Date: 2018/7/16.
 * Description:
 */
public class RecyclerviewActivity extends AppCompatActivity {

    public static final String Tag = "RecyclerviewActivity";
    @BindView(R.id.add_item_bt)
    Button addItemBt;
    @BindView(R.id.delete_item_bt)
    Button deleteItemBt;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    MessageRecycleListAdatper messageRecycleListAdatper;
    ArrayList<HoverMessage> datas;
    @BindView(R.id.new_message_tv)
    TextView newMessageTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_recycle_view_layout);
        ButterKnife.bind(this);
//        listView纵向滑动样子,纵向滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
//      获取数据，向适配器传数据，绑定适配器
//        datas = initData();
        datas = new ArrayList<HoverMessage>();
        messageRecycleListAdatper = new MessageRecycleListAdatper(RecyclerviewActivity.this, datas);
        recyclerview.setAdapter(messageRecycleListAdatper);
//      添加动画
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        recyclerview.setItemAnimator(defaultItemAnimator);

//        recyclerview.setItemAnimator(new DefaultItemAnimator());
        myOnClick();
    }

    private void myOnClick() {
        addItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              添加自带默认动画
                addMessageAndUpdate();
            }
        });
        deleteItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               删除自带默认动画
                messageRecycleListAdatper.removeData(0);

            }
        });
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
    private int clickCount;

    public void addMessageAndUpdate() {
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
        messageRecycleListAdatper.addData(i);
//        messageListView.smoothScrollToPosition(i);
//        messageListAdapter.notifyDataSetChanged();
    }

    private int insertDataToList(HoverMessage msg) {
        int index = 0;
        switch (msg.getType()) {
            case HoverMessage.WARNING:
                datas.add(index, msg);
                ++Utils.warningCount;
                break;
            case HoverMessage.PROMPT:
                index = Utils.warningCount;
                datas.add(index, msg);
                ++Utils.promptCount;
                break;
            case HoverMessage.NOTICE:
                index = Utils.promptCount + Utils.warningCount;
                datas.add(index, msg);
                break;

        }
//        Log.e("Main", "Utils.warningCount=" + Utils.warningCount +
//                " Utils.promptCount=" + Utils.promptCount + " mDataList.size=" + datas.size()
//                +" index="+index);
        return index;
    }


    /**
     * 编写一套假数据
     */
//    protected ArrayList<HoverMessage> initData() {
//        ArrayList<HoverMessage> mDatas = new ArrayList<HoverMessage>();
//        for (int i = 0; i < 100; i++) {
//            mDatas.add("条目 :: " + i);
//        }
//        return mDatas;
//    }
    @OnClick({R.id.add_item_bt, R.id.delete_item_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_item_bt:
                break;
            case R.id.delete_item_bt:
                break;
        }
    }
}

