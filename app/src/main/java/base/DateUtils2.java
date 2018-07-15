package base;

import com.xianglian.love.utils.Trace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wanglong on 2018/7/14.
 * 日期工具类
 */

public class DateUtils2 {
    private static final String TAG = "DateUtils2";

    private static final int invalidAge = -1;//非法的年龄，用于处理异常。

    public static String getBirthday(String age) {
        Calendar myCalendar = Calendar.getInstance();//获取现在时间
        String year = String.valueOf(myCalendar.get(Calendar.YEAR));//获取年份
        int birth = Integer.parseInt(year) - Integer.parseInt(age);
        String birthStr = birth + "-01-01";
        Trace.d(TAG, "age " + age + "birthday " + birthStr);
        return birthStr;
    }

    /**
     * 根据生日计算年龄
     *
     * @param dateStr 这样格式的生日 1990-01-01
     */

    public static int getAgeByDateStr(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date birthday = simpleDateFormat.parse(dateStr);
            return getAgeByDate(birthday);
        } catch (ParseException e) {
            return -1;
        }
    }

    public static int getAgeByDate(Date birthday) {
        Calendar calendar = Calendar.getInstance();

        //calendar.before()有的点bug
        if (calendar.getTimeInMillis() - birthday.getTime() < 0L) {
            return invalidAge;
        }
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(birthday);


        int yearBirthday = calendar.get(Calendar.YEAR);
        int monthBirthday = calendar.get(Calendar.MONTH);
        int dayOfMonthBirthday = calendar.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirthday;

        if (monthNow <= monthBirthday && monthNow == monthBirthday && dayOfMonthNow < dayOfMonthBirthday || monthNow < monthBirthday) {
            age--;
        }

        return age;
    }
}
