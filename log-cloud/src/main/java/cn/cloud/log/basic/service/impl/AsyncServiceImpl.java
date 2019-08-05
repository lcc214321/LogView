package cn.cloud.log.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.cloud.log.basic.po.MicroServicePo;
import cn.cloud.log.basic.service.AsyncService;
import cn.cloud.log.basic.service.LogService;
import cn.cloud.log.collect.thread.CollectThread;

@Service
public class AsyncServiceImpl implements AsyncService{
    @Autowired
    LogService logservice;
	
	@Override
	@Async("asyncServiceExecutor")
	public void executeAsync(MicroServicePo micropo,String collectdate) {
		// TODO Auto-generated method stub
		CollectThread thread=new CollectThread(logservice,micropo,collectdate);
		thread.run();
	}
  
}
