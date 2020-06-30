package com.wujie.app.framework.quartz;

import com.wujie.app.business.util.jdbc.impl.SHCommonDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 *************************************************
 * Spring Task定时器    
 * @author  MengDaNai                   
 * @version 1.1                
 * @date    2019年2月25日 创建文件                                             
 * @see                        
 *************************************************
 */
@Component
public class ScheduledService {
	
	@Autowired
	private SHCommonDaoImpl shCommonDaoImpl;
	
	// 每日凌晨将用户超过7天的签到清空
	// @Scheduled(cron = "0 0 0 * * ?")
	public void timerOne() {
		StringBuilder sql = new StringBuilder();
		sql.append("update user_info_tbl set sign_num=0 ,sign_money=0 where sign_num>=7 and delete_flag=0 ");
		shCommonDaoImpl.updateJDBC(sql.toString(), null);
	}
	
	
}
