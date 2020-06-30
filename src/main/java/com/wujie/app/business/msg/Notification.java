package com.wujie.app.business.msg;


import com.wujie.app.business.util.system.SystemConfig;

/**
 * 
 *************************************************
 * 系统消息通知文字公用类
 * @author  MengDaNai                   
 * @version 1.0                
 * @date    2019年6月14日 创建文件                                            
 * @See                            
 *************************************************
 */
public class Notification {

	Notification(){}
	
	public static final String INSERT_XS_SHOP = "感谢你申请线上商铺 </br>1.请根据网址："+ SystemConfig.getHostDomain()+" （输入你的账号密码进入）</br> 2.商铺管理（线上）->商铺管理 完善信息后</br>3.联系管理员完成商铺上架";
	
	public static final String NO_DATA = "查询数据为空!";
}
