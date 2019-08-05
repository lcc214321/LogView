package cn.cloud.log.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
   public static String getNowDate() {
	   Date date = new Date();
	   SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
	   return dateFormat.format(date);

   }
   
   public static String formateDate(Long value,String formatstr) throws ParseException {
	   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatstr);
       return simpleDateFormat.format(new Date(value*1000));
   }
}
