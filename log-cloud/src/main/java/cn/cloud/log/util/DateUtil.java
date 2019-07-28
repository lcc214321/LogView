package cn.cloud.log.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
   public static String getNowDate() {
	   Date date = new Date();
	   SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
	   return dateFormat.format(date);

   }
}
