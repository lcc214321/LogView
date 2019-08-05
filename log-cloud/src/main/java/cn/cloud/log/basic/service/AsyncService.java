package cn.cloud.log.basic.service;

import cn.cloud.log.basic.po.MicroServicePo;

public interface AsyncService {
	/**
     * 执行异步任务
     * 可以根据需求，自己加参数拟定，我这里就做个测试演示
     */
    public void executeAsync(MicroServicePo micropo,String collectdate);
}
