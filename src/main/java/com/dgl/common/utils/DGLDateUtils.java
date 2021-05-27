package com.dgl.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @description: 日期工具类
 * @author: dgl
 * @time: 2020/6/24 17:22
 */
public class DGLDateUtils {

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    private static final Object object = new Object();

    /**
     * 英文简写（默认）如：23:15:06
     */
    public static final String FORMAT_TIME = "HH:mm:ss";
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static final String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static final String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static final String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 纯数字时间  如:20191008103656
     */
    public static final String FORMAT_TIME_TO_NUMBER = "yyyyMMddHHmmss";

    /**
     * 纯数字日期  如:20191008
     */
    public static final String FORMAT_DATE_TO_NUMBER = "yyyyMMdd";

    /**
     * Excel表格默认日期  如:2020/11/30
     */
    public static final String FORMAT_DATE_TO_EXCEL = "yyyy/MM/dd";

    /**
     * 纯数字时间  如:202012240001
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * Date to LocalDateTime 时间互转
     */
    public static LocalDateTime dateToLdt(Date date) {
        LocalDateTime ldt = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return ldt;
    }

    /**
     * String to LocalDateTime 时间互转
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(FORMAT_LONG);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, df);
        return localDateTime;
    }


    /**
     * string转为LocalDate
     */
    public static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(FORMAT_SHORT);
        LocalDate localDate = LocalDate.parse(date,formatters);
        return localDate;
    }

    /**
     * LocalDateTime to String 类型的yyyyMMdd 时间互转
     */
    public static String ldToyyyyMMdd(LocalDate localDate,String reg) {
        if (localDate==null){
            return "";
        }
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(reg);
        String date = localDate.format(formatters);
        return date;
    }

    /**
     * 获取本月第一天
     */
    public static LocalDate getFirstDayOfMonth() {
        LocalDate localDate = LocalDate.now();
        //本月第一天
        LocalDate firstDay = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 1);
        return firstDay;
    }

    /**
     * 获取本月最后一天
     */
    public static LocalDate getLastDayOfMonth() {
        LocalDate localDate = LocalDate.now();
        //本月的最后一天
        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return lastDay;
    }

     /**
     * 获取上个月第一天
     */
    public static LocalDate getFirstDayOfLastMonth() {
        LocalDateTime localDateTime = LocalDateTime.now();
        //上个月第一天
        LocalDateTime months = localDateTime.minusMonths(1);
        LocalDate localDate = LocalDate.of(months.getYear(), months.getMonth(), 1);
//        LocalDate lastMonth = LocalDate.of(localDate.getYear(), localDate.getMonthValue() - 1, 1);
        System.out.println("上个月第一天是: "+localDate);
        return months.toLocalDate();
    }

    /**
     * 获取上个月最后一天
     */
    public static LocalDate getLastDayOfLastMonth() {
        LocalDate date = getFirstDayOfLastMonth();
        //上个月的最后一天
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("上个月最后一天是: "+lastDay);
        return lastDay;
    }


    /**
     * Date to LocalDate 时间互转
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date==null){
            return null;
        }
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate;
    }




    /**
     * Date to LocalTime 时间互转
     */
    public static LocalTime dateToLt(Date date) {
        LocalTime localTime = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
        return localTime;
    }

    /**
     * LocalDateTime to Date
     */
    public static Date ldtToDate(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    /**
     * LocalDate to Date
     */
    public static Date ldtToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }


    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param time 日期字符串
     * @return 日期
     */
    public static Date parseStringToDate(String time){
        Date date;
        try {
            // 设置日期格式
            SimpleDateFormat df = new SimpleDateFormat(FORMAT_SHORT);
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static Date parseStringToDate(String time,String reg){
        Date date;
        try {
            // 设置日期格式
            SimpleDateFormat df = new SimpleDateFormat(reg);
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 格式化日期
     * @param dateTime
     * @param reg
     * @return
     */
    public static Date parseDate(Date dateTime, String reg) {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(reg);
        String time = df.format(dateTime);
        Date date = parseStringToDate(time, reg);
        return date;
    }

    /**
     * 计算两个时间之间的差值
     * @param start
     * @param end
     * @return
     */
    public static long getMinutes (LocalTime start, LocalTime end) {
        long minutes = Duration.between(start, end).toMinutes();
        if (minutes<0){
            return 0;
        }
        return minutes;
    }

    /**
     * 计算两个时间之间的差值
     * @param start 开始时间(未来的时间)
     * @param end 结束时间(过去的时间)
     * @param unit 时间单位
     * @return
     */
    public static long getDuration (LocalDateTime start, LocalDateTime end, ChronoUnit unit) {
        long duration = start.until(end, unit);
        return duration;
    }

    /**
     * 计算两个时间之间的差值
     * @param start  开始时间(过去的时间)
     * @param end 结束时间(未来的时间)
     * @param unit 时间单位
     * @return
     */
    public static long getDuration (LocalDate start, LocalDate end, ChronoUnit unit) {
        if (start==null||end==null){
            return 0;
        }
        long duration = start.until(end, unit);
        return duration;
    }


    public static Date timeStampToDate(String seconds, String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return null;
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String format1 = sdf.format(new Date(Long.valueOf(seconds + "000")));
        Date date = parseStringToDate(format1);
        return date;
    }
    /**
     * 将 string的"yyyy-MM-dd HH:mm:ss" 时间格式转为 String的"yyyy-MM-dd"的时间格式
     * @param date       旧日期字符串
     * @return 新日期字符串
     */
    public static String strToyyyyMMdd(String date) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(FORMAT_LONG);
        LocalDateTime localDateTime = LocalDateTime.parse(date,formatters);
        String string = localDateTime.toLocalDate().toString();
        return string;
    }

    /**
     * String to LocalDate 时间互转
     */
    public static LocalDate strToLocalDate(String date,String reg) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(reg);
        LocalDate localDate = LocalDate.parse(date,formatters);
        return localDate;
    }

    /**
     * LocalDate to String 时间互转
     */
    public static String ldToString(LocalDate localDate,String reg) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(reg);
        String stringDate = localDate.format(dateTimeFormatter);
        return stringDate;
    }

    /**
     * LocalDateTime to String 时间互转
     */
    public static String ldtToString(LocalDateTime localDateTime,String reg) {
        if (localDateTime==null){
            return "";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(reg);
        String stringDate = localDateTime.format(dateTimeFormatter);
        return stringDate;
    }

    /**
     * String to LocalTime 时间互转
     */
    public static LocalTime strToLocalTime(String date) {
        if (StringUtils.isBlank(date)){
            return null;
        }
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(FORMAT_TIME);
        LocalTime parse = LocalTime.parse(date, formatters);
        System.out.println(parse);
        return parse;
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date        日期
     * @param monthAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int monthAmount) {
        return addDate(date, Calendar.MONTH, monthAmount);
    }



    /**
     * 计算两个时间之间相差的分钟数/小时数/天数
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff/ nh;
        // 计算差多少分钟
        long min = diff/ nm;
        return min;
    }



    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String pattern) {
        String dateString = "";
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期
     * @param dateType 类型(日/周/月/年)
     * @param amount   数值
     * @return 计算后日期
     */
    public static Date addDate(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }



    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            if (date.contains("T")){
                date=date.replace("T"," ");
            }
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 获取两个日期之间的所有日期
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @return
     */
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return days;
    }

    /**
     * 计算当天两个时间差多少分钟
     * @param startTime
     * @param endTime
     * @return
     */
    public  static  Integer getMinute(Date startTime,Date endTime){
        long min=0;
        long diff =   endTime.getTime()-startTime.getTime();//这样得到的差值是微秒级别
        long day = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - day * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        min=(day *24 *60 )+ (hours * 60) + minutes;
        if (min<0){
            min=0;
        }
        return   (int)min;
    }



    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date        旧日期字符串
     * @param olddPattern 旧日期格式
     * @param newPattern  新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, String olddPattern, String newPattern) {
        return DateToString(StringToDate(date, olddPattern), newPattern);
    }


    /**
     * 获取精确的日期
     *
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = {timestamps.get(i), timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
                // 因此不能将minAbsoluteValue取默认值0
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > absoluteValues.get(i)) {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 根据时间和年级号获取入学年份,以8月1日为基准,如果在这之前创建就是当前时间-1年.如果在这之后创建,就是当前时间
     * @param createTime 时间
     * @param gradeNo 年级号
     * @return
     */
    public static int getInSchoolYear(LocalDateTime createTime,int gradeNo) {
        if (createTime!=null&&gradeNo>0){
            //当前年份
            int nowYear = createTime.getYear();
            //标准参照日期是8月1日
            LocalDateTime standardDate = LocalDateTime.of(nowYear, 8, 1,0,0,0);
            if (createTime.isBefore(standardDate)){
                return createTime.minusYears(gradeNo).getYear();
            }else{
                return nowYear-gradeNo+1;
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        int inSchoolYear = getInSchoolYear(LocalDateTime.now(), 5);
        System.err.println(inSchoolYear);
    }

}
