package com.wujie.app.framework.constant;

/**
 * 公用返回的msg
 * @ClassName: SystemConstant.java
 * @author Mr.Chen->MengDaNai
 * @version 1.0
 * @Date 2019年7月19日 下午5:57:53
 */
public final class SystemConstant {

	// 系统标签ID-商品承诺
	public static final Long TAG_SYS_ID_GOODS_PROMISE = 1000L;

	/** 伪删除状态: true:0 有效**/
	public static final Integer DELETED_TRUE = 0;

	/** 伪删除状态: false:1 无效**/
	public static final Integer DELETED_FALSE = 1;

	/** 默认的页容量大小 **/
	public static final int DEFAULT_PAGESIZE_10 = 10;
	public static final int DEFAULT_PAGESIZE_20 = 20;

	public static final int INT_999 = 999;

	/**	-1,操作失败 	**/
	public static final int OPERATION_FAILED_CODE = -1;
	public static final String OPERATION_FAILED = "操作失败";

	/** 参数不能为空 **/
	public static final int DATA_NO_ISEMPTY_CODE = 345;
	public static final String DATA_NO_ISEMPTY = "参数不能为空";

	/**	0,成功状态码	**/
	public static final int SUCCESS_CODE = 0;

	/**	304,没有更多的数据了 **/
	public static final int NO_MORE_DATA_CODE = 304;
	public static final String NO_MORE_DATA = "没有更多数据了";

	/** 未获取到数据 **/
	public static final int NO_DATA_OBTAINED_CODE =	204 ;
	public static final String NO_DATA_OBTAINED = "未获取到数据";

	/**	320,未登入 **/
	public static final int NO_SESSION_CODE = 320;
	public static final String NO_SESSION = "未登入";

	/**	321,已注册 **/
	public static final int ALREADY_REGISTERED_CODE = 321;
	public static final String ALREADY_REGISTERED = "已注册";

	/**	323,修改失败 **/
	public static final int FAIL_TO_EDIT_CODE = 323;
	public static final String FAIL_TO_EDIT = "修改失败";

	/**	331,账号或密码错误 **/
	public static final int INCORRECT_USERNAME_OR_PASS_WORD_CODE = 331;
	public static final String INCORRECT_USERNAME_OR_PASS_WORD = "账号或密码错误";

	/**	344,数据校验不通过 **/
	public static final int DATA_ILLEGALITY_CODE = 344;
	public static final String DATA_ILLEGALITY = "数据不合法";
	public static final String THE_USER_DOES_NOT_EXIST = "该用户不存在";

	/**	401,未授权 **/
	public static final int AUTHORIZATION_FAILED_CODE = 401;
	public static final String AUTHORIZATION_FAILED = "用户未授权";

	/**	402,已冻结 **/
	public static final int FROZEN_CODE = 402;
	public static final String FROZEN = "已冻结";

	/**	403,验证码错误 **/
	public static final int ERROR_MESSAGE_CODE = 403;
	public static final String ERROR_MESSAGE = "验证码错误";

	/** 405,图形验证码错误**/
	public static final int ERROR_CHECKCODE_CODE = 405;
	public static final String ERROR_CHECKCODE = "图形验证码错误";

	/** 406,商铺不存在或已下架 **/
	public static final int NO_SHOP_CODE = 406;
	public static final String NO_SHOP = "商铺不存在或已下架";

	/** 407,商品不存在或已下架 **/
	public static final int NO_GOODS_CODE = 407;
	public static final String NO_GOODS = "商品不存在或已下架";

	/** 408,商品库存不足 **/
	public static final int NO_PRODUCTNUM_CODE = 408;
	public static final String NO_PRODUCTNUM = "商品库存不足";

	/** 409,没有收货地址 **/
	public static final int NO_ADDR_CODE = 409;
	public static final String NO_ADDR = "没有收货地址";

	/** 410,订单不存在 **/
	public static final int NO_ORDER_CODE = 410;
	public static final String NO_ORDER = "订单不存在";

	public static final int NO_MONEY_CODE = 412;
	public static final String NO_MONEY = "余额不足";

	public static final int UPLOAD_FAIL_CODE = 500;
	public static final String UPLOAD_FAIL = "上传失败";


	public static final int LOWER_SHELF_CODE = 501;
	public static final String LOWER_SHELF = "商品未下架，不能进行此操作";

	public static final int ADDRINFO_EXCEED_MAXNUM_CODE = 502;
	public static final String ADDRINFO_EXCEED_MAXNUM = "地址信息超过最大数量限制";

	public static final int PAY_ERROR_CODE = 504;

	//微信用户注册默认密码
	public static final String USER_WX_REGIST_DEFAULT_PASSWORD = "123456";





}
