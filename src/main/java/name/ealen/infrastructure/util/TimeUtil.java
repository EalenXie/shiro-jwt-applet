package name.ealen.infrastructure.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by EalenXie on 2018/11/26 13:29.
 */
public enum TimeUtil {

    ;

    /**
     * 获取 yyyy-MM-dd HH:mm:ss 格式的当前时间
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        s.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return s.format(new Date());
    }
}
