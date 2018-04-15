package data;

import org.h2.engine.User;

public class Message {
    public String text;
    public enum  Sender {USER,BOT}
    public FeedResponse feedResponse;
    public Sender sender;

}
