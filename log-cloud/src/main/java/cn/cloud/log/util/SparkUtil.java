package cn.cloud.log.util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;

public class SparkUtil {
   private static SparkConf conf;
   private static JavaSparkContext sc;
   static {
	   conf = new SparkConf().setMaster("local[2]").setAppName("mytest");
	   sc = new JavaSparkContext(conf);

   }
   
   public static boolean isinclude(String filepath,String searchdata) {
	   JavaPairRDD<String,Long> lines = sc.textFile(filepath).zipWithIndex();
	   long num=lines.filter(new Function<Tuple2<String, Long>, Boolean>(){

		@Override
		public Boolean call(Tuple2<String, Long> v1) throws Exception {
			// TODO Auto-generated method stub
			if(v1._1.contains(searchdata)) {
				return true;
			}
			return false;
		}
		   
	   }).count();
	   if(num!=0) {
		   return true;
	   }
	   return false;
	   
   }
	
}
