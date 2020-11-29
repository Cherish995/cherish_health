import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/28
 */
public class MyTest {

    @Test
    public void test(/*Date orderDate*/) {

        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        if ((nowHour >= 13 && nowHour <= 24) || (nowHour >= 0 && nowHour <= 8)) {
            calendar.set(Calendar.HOUR_OF_DAY, 9);
        } else if (nowHour > 8 && nowHour < 13) {
            calendar.set(Calendar.HOUR_OF_DAY, 14);
        }
        calendar.set(Calendar.YEAR,2019);
        now = calendar.getTime();
        System.out.println(now);
    }
}
