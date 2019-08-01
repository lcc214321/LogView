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
		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("mytest");
		JavaSparkContext sc = new JavaSparkContext(conf);
		long starttime=System.currentTimeMillis();
		JavaPairRDD<String,Long> lines = sc.textFile("D:/interface-2019.07.31.05-1.log").zipWithIndex();
		HashMap<String,HashMap<Long,String>> result=lines.aggregate(new HashMap<String,HashMap<Long,String>>(), new Function2<HashMap<String,HashMap<Long,String>>,Tuple2<String,Long>,HashMap<String,HashMap<Long,String>>>(){

			@Override
			public HashMap<String, HashMap<Long, String>> call(HashMap<String, HashMap<Long, String>> arg0,
					Tuple2<String, Long> arg1) throws Exception {
				// TODO Auto-generated method stub
				//System.out.println(arg1._2);
				Matcher matcher=pattern.matcher(arg1._1);
				if(matcher.find()){
					String level=matcher.group("level");
					if(!arg0.contains(level)){
						HashMap<Long, String> map=new HashMap<Long, String>();
						map.put(arg1._2, arg1._1);
						arg0.put(level,map);
					}else{
						HashMap<Long, String> map=arg0.get(level).get();
						map.put(arg1._2, arg1._1);
					}
				}else{
					if(arg0.contains("extra")){
						HashMap<Long, String> map=arg0.get("extra").get();
						map.put(arg1._2, arg1._1);
					}else{
						HashMap<Long, String> map=new HashMap<Long, String>();
						map.put(arg1._2, arg1._1);
						arg0.put("extra",map);
					}
				}
				return arg0;
			}

		
			
			
		}, new Function2<HashMap<String,HashMap<Long,String>>,HashMap<String,HashMap<Long,String>>,HashMap<String,HashMap<Long,String>>>(){

			@Override
			public HashMap<String, HashMap<Long, String>> call(HashMap<String, HashMap<Long, String>> arg0,
					HashMap<String, HashMap<Long, String>> arg1) throws Exception {
				// TODO Auto-generated method stub
				HashMap<String, HashMap<Long, String>> aggmap=new HashMap<String, HashMap<Long, String>>();
				HashMap<Long,String> infomap1=null;
				HashMap<Long,String> infomap2=null;
				HashMap<Long,String> debugmap1=null;
				HashMap<Long,String> debugmap2=null;
				HashMap<Long,String> warnmap1=null;
				HashMap<Long,String> warnmap2=null;
				HashMap<Long,String> errmap1=null;
				HashMap<Long,String> errmap2=null;
				if(arg0.contains("INFO")){
					infomap1=arg0.get("INFO").get();
				}
				if(arg1.contains("INFO")){
					infomap2=arg1.get("INFO").get();
				}
				
				if(arg0.contains("DEBUG")){
					debugmap1=arg0.get("DEBUG").get();
				}
				if(arg1.contains("DEBUG")){
					debugmap2=arg1.get("DEBUG").get();
				}
				
				if(arg0.contains("WARN")){
					warnmap1=arg0.get("WARN").get();
				}
				if(arg1.contains("WARN")){
					warnmap2=arg1.get("WARN").get();
				}
				
				if(arg0.contains("ERROR")){
					errmap1=arg0.get("ERROR").get();
				}
				if(arg1.contains("ERROR")){
					errmap2=arg1.get("ERROR").get();
				}
			   
				

//			    
			    
				return null;
			}

			
			
		});
//		List<Tuple2<String, Long>>list=lines.take(100);
//		for(Tuple2<String, Long> tuple:list){
//			System.out.println(tuple._1);
//		}
		long endtime1=System.currentTimeMillis();
		System.out.println("所耗时间"+(endtime1-starttime));
		System.out.println(lines.count());
		long endtime=System.currentTimeMillis();
		System.out.println("所耗时间"+(endtime-starttime));
//        String str="2019-07-31 05:00:00.074| ERROR | 15c8e6e32e8f4c218d47f63693ca2ce1 - APP:EX | [HTTP-RESP]. response={\"code\":\"B-500\",\"desc\":\"数据完整性错误\"}";
//        
//        Pattern pattern=Pattern.compile(regex);
//        Matcher matcher=pattern.matcher(str);
//        if(matcher.find()){
//        	System.out.println(matcher.group("time"));
//        	System.out.println(matcher.group("level"));
//        	System.out.println(matcher.group("content"));
//        }else{
//        	System.out.println("未找到");
//        }
	}
	
	
	
	
}
