package com.wujie.app.business.util.jdbc.impl;

import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.business.util.PubFun;
import com.wujie.app.business.util.jdbc.SHCommonDao;
import com.wujie.app.framework.constant.SystemConstant;
import com.wujie.app.framework.exception.CustomException;
import com.wujie.app.framework.page.PageAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 公用的jdbc方法实现
 *
 * @ClassName: SHCommonDaoImpl.java
 * @author MengDaNai
 * @version 1.0
 * @Date 2019年2月18日 下午3:55:20
 */
@Repository
public class SHCommonDaoImpl implements SHCommonDao {

	/** 持久化工具类 **/
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public SHCommonDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// sql查询需要判断是否为空
	@Override
	public List<Map<String, Object>> getSqlListCheakNull(String sql, Map<String, Object> map) {
		List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(sql, map);
		PubFun.isNull(result, SystemConstant.NO_MORE_DATA_CODE);
		return result;
	}

	// sql查询
	@Override
	public List<Map<String, Object>> getSqlList(String sql, Map<String, Object> map) {
		return namedParameterJdbcTemplate.queryForList(sql, map);
	}

	// sql分页查询需要判断是否为空
	@Override
	public List<Map<String, Object>> getSqlPageListCheakNull(String sql, Map<String, Object> map,
															 PageAction pageAction) {
		sql += " limit " + ((pageAction.getCurrentPage() - 1) * pageAction.getPageSize()) + ","
				+ pageAction.getPageSize();
		List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(sql, map);
		PubFun.isNull(result, SystemConstant.NO_MORE_DATA_CODE);
		return result;
	}

	// sql分页查询
	@Override
	public List<Map<String, Object>> getSqlPageList(String sql, Map<String, Object> map, PageAction pageAction) {
		sql += " limit " + ((pageAction.getCurrentPage() - 1) * pageAction.getPageSize()) + ","
				+ pageAction.getPageSize();
		return namedParameterJdbcTemplate.queryForList(sql, map);
	}

	// 查询sql总条数
	@Override
	public Integer getSQLRecordNumber(String sql, Map<String, Object> map) {
		List<Map<String, Object>> result = getSqlList(" select count(*) as sum from (" + sql + ") a ", map);
		PubFun.isNull(result, SystemConstant.NO_MORE_DATA_CODE);
		return PubFun.objectToInt(result.get(0).get("sum"));
	}

	// sql修改判断是否执行成功
	@Override
	public Integer updateJDBCCheck(String sql, Map<String, Object> map) {
		int i = namedParameterJdbcTemplate.update(sql, map);
		if (i == 0) {
			throw new CustomException(ErrorEnum.GATEWAY_ERROR);
		}
		return i;
	}

	// sql修改
	@Override
	public Integer updateJDBC(String sql, Map<String, Object> map) {
		return namedParameterJdbcTemplate.update(sql, map);
	}

	// sql批量修改判断是否执行成功
	@Override
	public boolean batchJDBCCheck(Map<String, Map<String, Object>> map) {
		PubFun.isNull(map, SystemConstant.DATA_ILLEGALITY_CODE);
		map.forEach((k, v) -> {
			if (namedParameterJdbcTemplate.update(k, v) == 0) {
				throw new CustomException(ErrorEnum.GATEWAY_ERROR);
			}
		});
		return true;
	}

	// sql批量修改
	@Override
	public boolean batchJDBC(Map<String, Map<String, Object>> map) {
		PubFun.isNull(map, SystemConstant.DATA_ILLEGALITY_CODE);
		map.forEach((k, v) -> namedParameterJdbcTemplate.update(k, v));
		return true;
	}

}
