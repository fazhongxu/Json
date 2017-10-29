package xxl.com.json.bean;

/**
 * Created by xxl on 2017/10/26.
 */

public class ChatBean {

    private String chatContent;
    private int isMe;

    public ChatBean(String chatContent, int isMe) {
        this.chatContent = chatContent;
        this.isMe = isMe;
    }

    public String getChatContent() {
        return chatContent;
    }

    public int getIsMe() {
        return isMe;
    }
}
