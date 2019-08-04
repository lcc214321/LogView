package cn.cloud.log.collect;

import cn.cloud.log.basic.po.MicroServicePo;

public interface CollectService {
      public void walker(String filepath);
      public void collect();
      public boolean filenamefilter(String filename,String[] filenamepatterns); 
      public boolean filepostfixfilter(String filename,String[] filepostfix); 
}
