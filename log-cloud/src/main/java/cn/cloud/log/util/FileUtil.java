package cn.cloud.log.util;

import java.io.File;

public class FileUtil {
    public static void mkdir(String dirpath) {
    	File dir=new File(dirpath);
    	if(!dir.exists()) {
    		dir.mkdirs();
    	}
    }
}
