package message.kjer.simon.com.cn.newmessagelist;

import android.content.Context;

import java.util.Random;

import message.kjer.simon.com.cn.newmessagelist.bean.HoverMessage;

/**
 * @author simon.
 * Date: 2018/7/10.
 * Description:
 */
public class Utils {

    /**
     * 警告总数
     */
    public static int warningCount;
    /**
     * 提醒总数
     */
    public static int promptCount;

    public static HoverMessage perpareMessageData(int clickCount) {
        Random rand = new Random();
        int result = rand.nextInt(3);
        HoverMessage tipsMessage;
        if (result == 0) {
            tipsMessage = new HoverMessage(1, "警告：:::" + String.valueOf(clickCount), 4000);
        } else if (result == 1) {
            tipsMessage = new HoverMessage(2, "提醒：:::" + String.valueOf(clickCount), 2500);
        } else {
            tipsMessage = new HoverMessage(3, "通知：:::" + String.valueOf(clickCount), 1500);
        }
        return tipsMessage;
    }

    public static int getTypeColor(Context context, int type) {
        int typeColor = 0;
        switch (type) {
            case HoverMessage.WARNING:
                typeColor = context.getResources().getColor(R.color.warning);
                break;
            case HoverMessage.PROMPT:
                typeColor = context.getResources().getColor(R.color.prompt);
                break;
            case HoverMessage.NOTICE:
                typeColor = context.getResources().getColor(R.color.notice);
                break;
            default:
                break;
        }
        return typeColor;
    }

    public static void updateMsgCount(HoverMessage t) throws IllegalArgumentException {
        if (t == null) {
            throw new IllegalArgumentException("message data is null ...");
        }
        int msgType = t.getType();
        if (msgType == 1) {
            --warningCount;
        } else if (msgType == 2) {
            --promptCount;
        }
    }
}
