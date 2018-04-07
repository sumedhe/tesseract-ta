package _;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Formatter {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public static String formatLogTimestamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return simpleDateFormat.format(timestamp);
    }
}
