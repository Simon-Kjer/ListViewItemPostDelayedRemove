package message.kjer.simon.com.cn.newmessagelist;

import android.content.Context;

import java.util.Random;

/**
 * @author simon.
 * Date: 2018/7/10.
 * Description:
 */
public class Utils {


    public static TipsMessage perpareMessageData(int clickCount) {
        Random rand = new Random();
        int result = rand.nextInt(10);
        TipsMessage tipsMessage;
        if (result <= 3) {
            tipsMessage = new TipsMessage(1, "警告：:::" + String.valueOf(clickCount), 5000);
        } else if (result >= 7) {
            tipsMessage = new TipsMessage(2, "提醒：:::" + String.valueOf(clickCount), 3000);
        } else {
            tipsMessage = new TipsMessage(3, "通知：:::" + String.valueOf(clickCount), 1000);
        }
        return tipsMessage;
    }

    public static int getTypeColor(Context context,int type){
        int typeColor=0;
        switch (type) {
            case TipsMessage.WARNING:
                typeColor = context.getResources().getColor(R.color.warning);
                break;
            case TipsMessage.PROMPT:
                typeColor = context.getResources().getColor(R.color.prompt);
                break;
            case TipsMessage.NOTICE:
                typeColor = context.getResources().getColor(R.color.notice);
                break;
            default:
                break;
        }
        return typeColor;
    }
}
