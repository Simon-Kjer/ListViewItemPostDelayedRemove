package message.kjer.simon.com.cn.newmessagelist;

/**
 * @author simon.
 * Date: 2018/7/10.
 * Description:
 */
public class TipsMessage {
    /**
     * 警告
     */
    public static final int WARNING = 1;

    /**
     * 提醒
     */
    public static final int PROMPT = 2;
    /**
     * 通知
     */
    public static final int NOTICE = 3;

    /**
     * 消息类型
     */
    private int type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 具体显示时间 1s.3s.5s
     */
    private int hintTime;

    public TipsMessage(int type, String content, int hintTime) {
        this.type = type;
        this.content = content;
        this.hintTime = hintTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHintTime() {
        return hintTime;
    }

    public void setHintTime(int hintTime) {
        this.hintTime = hintTime;
    }
}
