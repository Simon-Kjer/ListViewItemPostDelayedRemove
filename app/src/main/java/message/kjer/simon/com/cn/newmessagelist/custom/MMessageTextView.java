package message.kjer.simon.com.cn.newmessagelist.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import message.kjer.simon.com.cn.newmessagelist.bean.HoverMessage;

/**
 * @author simon.
 * Date: 2018/7/11.
 * Description:
 */
public class MMessageTextView extends android.support.v7.widget.AppCompatTextView {
    public MMessageTextView(Context context) {
        super(context);
    }

    public MMessageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MMessageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MMessageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    private HoverMessage message;

    public HoverMessage getMessage() {
        return message;
    }

    public void setMessage(HoverMessage message) {
        this.message = message;
    }
}
