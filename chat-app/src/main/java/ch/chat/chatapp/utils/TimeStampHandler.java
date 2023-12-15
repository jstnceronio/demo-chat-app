package ch.chat.chatapp.utils;

import java.text.SimpleDateFormat;

public class TimeStampHandler {
    public static String getCurrentTimestamp() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(new java.util.Date());
        timeStamp = timeStamp.replace(".", "T");
        return timeStamp;
    }

}
