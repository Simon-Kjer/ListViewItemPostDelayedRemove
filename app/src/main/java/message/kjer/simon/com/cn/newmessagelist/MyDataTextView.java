package message.kjer.simon.com.cn.newmessagelist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author simon.
 * Date: 2018/7/11.
 * Description:
 */
public class MyDataTextView extends android.support.v7.widget.AppCompatTextView {
    public MyDataTextView(Context context) {
        super(context);
    }

    public MyDataTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDataTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//
//    public MyDataTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
//            defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    private TipsMessage message;

    public TipsMessage getMessage() {
        return message;
    }

    public void setMessage(TipsMessage message) {
        this.message = message;
    }
}
