package message.kjer.simon.com.cn.newmessagelist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import message.kjer.simon.com.cn.newmessagelist.R;
import message.kjer.simon.com.cn.newmessagelist.Utils;
import message.kjer.simon.com.cn.newmessagelist.bean.HoverMessage;
import message.kjer.simon.com.cn.newmessagelist.custom.MMessageTextView;

import static message.kjer.simon.com.cn.newmessagelist.adapter.MessageListAdapter.Tag;

/**
 * @author simon.
 * Date: 2018/7/16.
 * Description:
 */
public class MessageRecycleListAdatper extends RecyclerView.Adapter<MessageRecycleListAdatper
        .MyViewHolder> {
    private Context context;
    private ArrayList<HoverMessage> list;

    public MessageRecycleListAdatper(Context context, ArrayList<HoverMessage> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(
                R.layout.adapter_main_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //设置颜色
        int type = list.get(position).getType();
        if (type > 0) {
            holder.tv.setTextColor(Utils.getTypeColor(context, type));
        }
        String content = list.get(position).getContent();
        if (!TextUtils.isEmpty(content)) {
            holder.tv.setText(content);
        }
        holder.tv.setMessage(list.get(position));
        //类别添加 过滤以及 点击移除
        if (type == 1) {
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv.removeCallbacks(null);
                    MMessageTextView vv = (MMessageTextView) v;
                    HoverMessage msg = vv.getMessage();
                    int indexOf = list.indexOf(msg);
                    if (indexOf >= 0) {
                        Log.e(Tag,"click content ="+msg.getContent());
                        Utils.updateMsgCount(msg);
                        list.remove(msg);
                        notifyItemRemoved(indexOf);

                    }
                }
            });
            return;
        }
        //type =2、3 添加自动延时消失
        holder.tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.tv.removeCallbacks(null);
                if (list != null && list.size() > 0) {
                    HoverMessage message = holder.tv.getMessage();
                    if (message == null) {
                        return;
                    }
                    int indexOf = list.indexOf(message);
                    if (indexOf >= 0) {
                        Log.e(Tag, "position= " + position + "  list.size()= " + list.size() +
                                " holder.tv.getMessage().getType()= " + holder.tv.getMessage()
                                .getType() + " index =" + indexOf);
                        list.remove(message);
                        Utils.updateMsgCount(message);
                        notifyItemRemoved(indexOf);
                    }
                }
            }
        }, list.get(position).getHintTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addData(int position) {
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MMessageTextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (MMessageTextView) view.findViewById(R.id.content_tv);
        }
    }
}
