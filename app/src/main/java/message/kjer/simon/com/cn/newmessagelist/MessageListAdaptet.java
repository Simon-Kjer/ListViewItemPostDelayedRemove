package message.kjer.simon.com.cn.newmessagelist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author simon.
 * Date: 2018/7/10.
 * Description:
 */
public class MessageListAdaptet extends BaseAdapter {
    private final Context mContext;
    private LinkedList<TipsMessage> mDataList;

    private LayoutInflater mInflater;

    public MessageListAdaptet(Context context, LinkedList<TipsMessage> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_main_list_item, parent, false);

            holder = new ViewHolder();
            holder.contentTv = (TextView) convertView.findViewById(R.id.content_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mDataList != null && mDataList.size() > 0) {
            int type = mDataList.get(position).getType();
            if (type > 0) {
                holder.contentTv.setTextColor(Utils.getTypeColor(mContext, type));
            }
            String content = mDataList.get(position).getContent();
            if (!TextUtils.isEmpty(content)) {
                holder.contentTv.setText(content);
            }
            final ViewHolder finalHolder = holder;

            holder.contentTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder.contentTv.removeCallbacks(null);
                    TipsMessage t = mDataList.get(position);
                    Utils.updateMsgCount(t);
                    mDataList.remove(t);
                    MessageListAdaptet.this.notifyDataSetChanged();
                }
            });

            if (checkMap.get(holder.contentTv.hashCode()) == null) {

                Log.e("MainActivity", "holder.contentTv.hashCode()=" + holder.contentTv.hashCode());
                final ViewHolder finalHolder1 = holder;

                //type 1
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mDataList != null &&
                                mDataList.size() > position) {
                            TipsMessage t = mDataList.get(position);
                            if (t != null) {
                                mDataList.remove(t);
                                checkMap.remove(finalHolder1.contentTv.hashCode());
                                Utils.updateMsgCount(t);
//                                Log.d("MainActivity", "adapter   ...mDataList size=" + mDataList
//                                        .size());
                                MessageListAdaptet.this.notifyDataSetChanged();
                            }
                        }
                    }
                }, mDataList.get(position).getHintTime());

                //type 2
//                boolean ok = holder.contentTv.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mDataList != null &&
//                                mDataList.size() > position) {
//                            TipsMessage t = mDataList.get(position);
//                            if (t != null) {
//                                mDataList.remove(t);
//                                checkMap.remove(finalHolder1.contentTv.hashCode());
//                                Utils.updateMsgCount(t);
////                                Log.d("MainActivity", "adapter   ...mDataList size=" + mDataList
////                                        .size());
//                                MessageListAdaptet.this.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                }, mDataList.get(position).getHintTime());
//                holder.contentTv.setTag(holder.contentTv.hashCode(), true);

                //type3
//                updateItemViews(holder, position);


                checkMap.put(holder.contentTv.hashCode(), true);
            } else {
                Log.e("MainActivity", "else  hashCode()=" + holder.contentTv.hashCode());
            }
        }
        return convertView;
    }


    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Boolean> checkMap = new HashMap<>();

    private class ViewHolder {
        TextView contentTv;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MessageListAdaptet.this.notifyDataSetChanged();
        }
    };


    private void updateItemViews(final ViewHolder holder, final int position) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.e("MainActivity", "updateItemViews  ===>   hashCode()=" + holder.contentTv
                        .hashCode());
                if (mDataList != null &&
                        mDataList.size() > position) {
                    TipsMessage t = mDataList.get(position);
                    if (t != null) {
                        mDataList.remove(t);
                        checkMap.remove(holder.contentTv.hashCode());
                        Utils.updateMsgCount(t);
                        mHandler.sendEmptyMessage(0);
//                    MessageListAdaptet.this.notifyDataSetChanged();
                    }
                }
            }
        };
        timer.schedule(timerTask, mDataList.get(position).getHintTime());
    }
}
