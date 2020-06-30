package com.wujie.app.business.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * id 生成规则
 * 项目一般使用UUID作为项目组件。但是该id是无序的。
 * 特别当业务量大时，需要进行水平分库的时候，就无法有效的进行拆分
 * 如果使用自增长作为id，则能有有效的避免上面的问题
 * 但是如果使用orm框架或者数据库的自增长，则会在进行分布式部署的时候，导致id重复
 * 
 * 结构
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * @author Administrator
 *
 */
public class IdRuleDemo {

	static Logger logger = LoggerFactory.getLogger(IdRuleDemo.class);
	
	/** 开始时间截 (2017-12-15) */
    private final long twepoch = 1513267200000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;
    
    private volatile static IdRuleDemo idRuleDemo;
    
    /**
     * 使用单例模式保证多线程下调用不会导致id重复
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     * @return
     */
    public static IdRuleDemo getInstance(long workerId, long datacenterId){
    	if (idRuleDemo==null){
    		synchronized (IdRuleDemo.class) {
				if (idRuleDemo==null){
					idRuleDemo = new IdRuleDemo(workerId,datacenterId);
				}
			}
    	}
    	return idRuleDemo;
    }
    
    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    private IdRuleDemo(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }
    
    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        int km=0;
        while (km++<100000&&timestamp <= lastTimestamp) {
        	System.out.println("tilNextMillis km test:"+km);
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
    
    /**
     * 
     * 获取完成ID（永不重复）
     * @author 曾鑫
     * @return
     */
    public static Long getIdComplete(){
    	Long res = null;
		IdRuleDemo idWorker = IdRuleDemo.getInstance(0, 0);
		res = idWorker.nextId();
    	return  res;
    }
    
    /**
     * 
     * 获取ID（永不重复）
     * @author 曾鑫
     * @return
     */
    public static Long getId(){
    	Long res = null;
		res = getId(5, 17);
    	return  res;
    }
    
    
    /**
     * 
     * 获取ID区间段（0-17）（永不重复）
     * @author 曾鑫
     * @return
     */
    public static Long getId(int startNum,int endNum){
    	Long res = null;
		IdRuleDemo idWorker = IdRuleDemo.getInstance(0, 0);
		res = idWorker.nextId();
		//res = PubFun.getLongValue(res.toString().substring(startNum, endNum));
    	return  res;
    }

    
    public static void main(String[] args) {
    	
    	new Thread(new Runnable() {
			@Override
			public void run() {
				IdRuleDemo idWorker = IdRuleDemo.getInstance(0, 0);
				for (int i = 0; i < 10; i++) {
		            long id = idWorker.nextId();
		            //System.out.println(Long.toBinaryString(id));
		            System.out.println(id+"");
		        }
			}
		}).start();
    	
    	new Thread(new Runnable() {
			@Override
			public void run() {
				IdRuleDemo idWorker = IdRuleDemo.getInstance(0, 0);
				for (int i = 0; i < 10; i++) {
		            long id = idWorker.nextId();
		            //System.out.println(Long.toBinaryString(id));
		            System.out.println(id);
		        }
			}
		}).start();
    }
}
