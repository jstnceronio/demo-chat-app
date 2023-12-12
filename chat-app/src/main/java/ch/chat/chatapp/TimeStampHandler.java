package ch.chat.chatapp;

import java.text.SimpleDateFormat;

public class TimeStampHandler {
    static String getCurrentTimestamp() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(new java.util.Date());
        timeStamp = timeStamp.toString().replace(".", "T");
        return timeStamp;
    }

}
