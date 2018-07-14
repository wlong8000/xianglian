package base;

import com.xianglian.love.utils.Trace;

import java.util.Calendar;

/**
 * Created by wanglong on 2018/7/14.
 * 日期工具类
 */

public class DateUtils2 {
    private static final String TAG = "DateUtils2";

    public static String getBirthday(String age) {
        Calendar myCalendar = Calendar.getInstance();//获取现在时间
        String year = String.valueOf(myCalendar.get(Calendar.YEAR));//获取年份
        int birth = Integer.parseInt(year) - Integer.parseInt(age);
        String birthStr = birth + "-01-01";
        Trace.d(TAG, "age " + age + "birthday " + birthStr);
        return birthStr;
    }
}
