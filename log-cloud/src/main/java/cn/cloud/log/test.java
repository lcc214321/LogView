package cn.cloud.log;

import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.Map;

public class test {
	private static List<String> errinfo=new ArrayList<String>();
	private static String regex="(?<time>\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}\\.\\d{3})\\|\\s+(?<level>[A-Z]+)\\s+\\|\\s+[0-9a-z]+\\s+-\\s+(\\S+)\\s+\\|\\s+\\[HTTP-([A-Z]+)\\]\\.\\s+(?<content>.*)";
	private static Pattern pattern=Pattern.compile(regex);
	public static void main(String[] args) {
//		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("mytest");
//		JavaSparkContext sc = new JavaSparkContext(conf);
//		long starttime=System.currentTimeMillis();
//		JavaPairRDD<String,Long> lines = sc.textFile("D:/interface-2019.07.31.05-1.log").zipWithIndex();
		
		
	}
	
	
	
	
}
