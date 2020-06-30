package com.wujie.app.business.util.jdbc;


import com.wujie.app.framework.page.PageAction;

import java.util.List;
import java.util.Map;

/**
 * 公用的jdbc方法
 *
 * @ClassName: SHCommonDao.java
 * @author MengDaNai
 * @version 1.0
 * @Date 2019年2月18日 下午3:53:29
 */
public interface SHCommonDao {

	/**
	 * sql查询需要判断是否为空
	 *
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午3:53:47
	 * @param sql
	 * @param map
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getSqlListCheakNull(String sql, Map<String, Object> map);

	/**
	 * sql查询
	 *
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午3:53:59
	 * @param sql
	 * @param map
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getSqlList(String sql, Map<String, Object> map);

	/**
	 * sql分页查询需要判断是否为空
	 *
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午3:54:07
	 * @param sql
	 * @param map
	 * @param pageAction
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getSqlPageListCheakNull(String sql, Map<String, Object> map, PageAction pageAction);

	/**
	 * sql分页查询
	 *
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午3:54:14
	 * @param sql
	 * @param map
	 * @param pageAction
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getSqlPageList(String sql, Map<String, Object> map, PageAction pageAction);

	/**
	 * 查询sql总条数
	 *
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午3:54:22
	 * @param sql
	 * @param map
	 * @return Integer
	 */
	Integer getSQLRecordNumber(String sql, Map<String, Object> map);

	/**
	 * sql修改判断是否执行成功
	 *
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午3:54:38
	 * @param sql
	 * @param map
	 * @return Integer
	 */
	Integer updateJDBCCheck(String sql, Map<String, Object> map);

	/**
	 * sql修改
	 *
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午3:54:45
	 * @param sql
	 * @param map
	 * @return Integer
	 */
	Integer updateJDBC(String sql, Map<String, Object> map);

	/**
	 *************************************************
	 * sql批量修改判断是否执行成功</br>
	 * StringBuffer stringBuffer1 = new StringBuffer();</br>
	 * StringBuffer stringBuffer2 = new StringBuffer();</br>
	 * stringBuffer1.append("update user_info_tbl set level_num=:level_num1");</br>
	 * stringBuffer2.append("update user_info_tbl set level_num=:level_num2");</br>
	 * Map<String, Map<String,Object>> mapmap = new HashMap<String,
	 * Map<String,Object>>();</br>
	 * Map<String, Object> map1 = new HashMap<String, Object>();</br>
	 * Map<String, Object> map2 = new HashMap<String, Object>();</br>
	 * map1.put("level_num1", "1");</br>
	 * map2.put("level_num2", "2");</br>
	 * mapmap.put(stringBuffer1.toString(), map1);</br>
	 * mapmap.put(stringBuffer2.toString(), map2);</br>
	 * shCommonDaoImpl.batchJDBC(mapmap);</br>
	 *
	 * @author MengDaNai
	 * @param list
	 * @return
	 * @date 2019年3月28日 创建文件
	 *************************************************
	 */
	boolean batchJDBCCheck(Map<String, Map<String, Object>> list);

	/**
	 *************************************************
	 * sql批量修改</br>
	 * StringBuffer stringBuffer1 = new StringBuffer();</br>
	 * StringBuffer stringBuffer2 = new StringBuffer();</br>
	 * stringBuffer1.append("update user_info_tbl set level_num=:level_num1");</br>
	 * stringBuffer2.append("update user_info_tbl set level_num=:level_num2");</br>
	 * Map<String, Map<String,Object>> mapmap = new HashMap<String,
	 * Map<String,Object>>();</br>
	 * Map<String, Object> map1 = new HashMap<String, Object>();</br>
	 * Map<String, Object> map2 = new HashMap<String, Object>();</br>
	 * map1.put("level_num1", "1");</br>
	 * map2.put("level_num2", "2");</br>
	 * mapmap.put(stringBuffer1.toString(), map1);</br>
	 * mapmap.put(stringBuffer2.toString(), map2);</br>
	 * shCommonDaoImpl.batchJDBC(mapmap);</br>
	 *
	 * @author MengDaNai
	 * @param list
	 * @return
	 * @date 2019年3月28日 创建文件
	 *************************************************
	 */
	boolean batchJDBC(Map<String, Map<String, Object>> list);

}
