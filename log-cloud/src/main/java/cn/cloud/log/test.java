package cn.cloud.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class test {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("mytest");
		JavaSparkContext sc = new JavaSparkContext(conf);
		long starttime=System.currentTimeMillis();
		JavaRDD<String> lines = sc.textFile("D:/interface-2019.07.31.05-1.log").filter(s->s.contains("ERROR"));
		long endtime1=System.currentTimeMillis();
		System.out.println("所耗时间"+(endtime1-starttime));
		System.out.println(lines.count());
		long endtime=System.currentTimeMillis();
		System.out.println("所耗时间"+(endtime-starttime));
	}
}
