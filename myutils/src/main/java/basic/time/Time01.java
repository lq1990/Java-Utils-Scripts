package basic.time;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuqiang
 * @since 2020-09-26 13:38
 */
public class Time01 {
    public static void main(String[] args) {
//        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        final Timestamp endTime = new Timestamp(date.getTime());
        String format = df.format(endTime);

        System.out.println(format);
    }
}
