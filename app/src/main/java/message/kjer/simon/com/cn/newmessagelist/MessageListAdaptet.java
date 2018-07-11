package message.kjer.simon.com.cn.newmessagelist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

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
            if (checkMap.get(holder.contentTv.hashCode())==null) {
                Log.e("MainActivity","holder.contentTv.hashCode()="+holder.contentTv.hashCode());
                final ViewHolder finalHolder1 = holder;
                holder.contentTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mDataList != null  &&
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
//                holder.contentTv.setTag(holder.contentTv.hashCode(), true);
                checkMap.put(holder.contentTv.hashCode(), true);
            }else{
                Log.e("MainActivity","else  hashCode()="+holder.contentTv.hashCode());
            }
        }
        return convertView;
    }

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Boolean> checkMap = new HashMap<>();

    private class ViewHolder {
        TextView contentTv;
    }
}