package com.wujie.app.business.enums;

/**
 * @ProjectName: news
 * @Package: com.yang.utils
 * @ClassName: ErrorEnum
 * @Author: fanYang
 * @Description: sentinel 错误枚举类
 * @Date: 2020/3/16 19:50
 * @copyright: fanYang
 * @website: 1009983833@qq.com
 */
public enum ErrorEnum {

    SYSTEM_ERR("S00000", "系统内部异常"),
    /**
     * 限流
     */
    FLOW_RULE_ERR("S00001", "系统繁忙, 请稍候在试"),
    /**
     * 参数流控
     */
    HOT_PARAM_FLOW_RULE_ERR("S00002", "系统繁忙, 请稍候在试"),
    /**
     * 授权
     */
    AUTH_RULE_ERR("S00003", "系统繁忙, 请稍候在试"),
    /**
     * 系统规则
     */
    SYS_RULE_ERR("S00004", "系统繁忙, 请稍候在试"),
    /**
     * 降级
     */
    DEGRADE_RULE_ERR("S00005", "系统繁忙, 请稍候在试"),
    /**
     * 请求头为空
     */
    UNAUTHORIZED_HEADER_IS_EMPTY("S006", "数据不合法"),
    /**
     * 数据不合法
     */
    DATA_IS_ILLEGAL("S00007", "数据不合法"),
    /**
     * 尚未拥有权限
     */
    FORBIDDEN("S00008", "尚未拥有权限"),
    /**
     * 生成公钥异常
     */
    GET_TOKEN_KEY_ERROR("S00009", "系统异常"),
    /**
     * 网关响应异常
     */
    GATEWAY_ERROR("S00010", "系统异常"),
    /**
     * 网关连接超时
     */
    GATEWAY_CONNECT_TIME_OUT("S00011", "系统异常"),
    /**
     * 网关空异常
     */
    GATEWAY_NOT_FOUND_SERVICE("S00012", "系统异常"),
    /**
     * 用户名或密码不正确
     */
    USERNAME_PASS_ERR("S00013", "用户名或密码不正确"),
    /**
     * 用户被禁用
     */
    USER_PROHIBIT("S00014", "你已被限制不能登录,限制原因：{0}，如有疑问请联系客户"),
    UNAUTHORIZED("S00015", "暂未登录或token已经过期"),
    ERR_VERIFICATION_CODE("S00016", "验证码错误"),
    ERR_TEL("S00017", "错误的手机号码"),
    ERR_REPEAT_TEL_CODE("S00018", "验证码已发送，请稍候在试"),
    ERR_VERIFICATION_TIMES("S00019", "已达到当日验证最大限制，请明日在试"),
    ERR_PAY_PASS("S00020", "支付密码错误"),
    ERR_BALANCE_INSUFFICIENT("S00021", "当前可用余额不足"),
    ERR_IDENTITY_INFO_MISMATCH("S00022", "身份信息不匹配"),
    IO_CLOSE_ERR("S00034", "IO 关闭异常"),
    TO_JSON_ERR("S00035", "转JSON异常"),
    NOT_USER_ERR("S00036", "不存在该用户"),
    DATA_DOES_NOT_EXIST("S00037", "产品不存在或已下架"),
    ERR_REAL_NAME_INFORMATION("S00038", "实名认证信息有误"),
    ERR_EXISTED_USER("S00039", "改手机号码已被注册"),
    ERR_INVITATION_CODE("S00040", "错误的邀请码"),
    ERR_REPEAT_ATTENTION_SHOP("S00041", "您已经关注了，请不要重复关注"),
    ERR_LIVE_CREATE("S00042", "创建直播流失败，请稍候在试"),
    ERR_LIVE_GET("S00043", "获取直播流失败"),
    ERR_LIVE_DISABLE_FLOW("S00044", "禁用流失败"),
    ERR_LOGIN_ROLE_TYPE("S00045", "用户类型不匹配，登陆失败"),
    ERR_NOT_LIVE("S00046", "操作失败：未直播"),
    ERR_APPLY_REPEAT("S00047", "已经提交过产品入驻申请，请等待审批"),
    NOT_DATA_ERR("S00048", "数据不存在"),
    PRESENCE_USER_ERR("S00049", "该用户已存在"),
    ERR_ROLE_TYPE("S00050", "没有相应的登陆权限"),
    ERR_SYSTEM_ROLE("S00051", "系统级角色不可修改和删除"),
    ERR_PARAMETER_IS_INVALID("S00052", "参数不合法，请检查请求参数"),
    ERR_INVITATE_USER_DISABLED("S00053", "推荐人已被禁用"),
    ERR_APPLY_BUTLER("S00054", "已经提交过申请，请勿重复操作"),
    ERR_GENERATE_QR_CODE("S00055", "生成二维码异常"),

    ERR_MONEY("S00060", "金额有误"),
    ERR_INITIATE_PAY("S00065", "发起支付失败"),
    ERR_CHANNEL_DEL("S00080", "请先删除下级分类"),
    ERR_CHANNEL_MAX("S00082", "最多添加20个分类"),
    ERR_COUPON_HANDLE_ENABLE("S00090", "不能操作发行中的优惠券"),
    ERR_ACTIVITY_HANDLE_ENABLE("S00091", "不能操作发行中的活动"),
    ERR_APPLY_STATUS("S00095", "申请状态已改变,请刷新后再试"),

    ERR_NO_GOODS("S00120", "商品不存在或已下架"),
    ERR_NO_SHOP("S00121", "店铺不存在或已下架"),
    ERR_NO_PRODUCT_ITEM("S00122", "商品规格不存在或已下架"),
    ERR_NO_ORDER("S00123", "订单已删除或不存在"),
    ERR_GOODS_NUM("S001235", "商品库存不足"),

    ERR_WX_DECRYPT("S00150", "微信数据解析错误，请重试！"),
    ERR_WX_NOT_REGIST("S00151", "用户未注册，请先注册！"),
    ERR_WX_REPEAT_BIND("S00152", "该手机号已经绑定过，不要重复绑定！"),
    ERR_WX_EXISTED("S00153", "微信账号已经存在，可直接登录"),
    ERR_USER_INVITATION_SMALL("S00154", "推荐人只能是小管家！"),
    ERR_USER_INVITATION_BIG("S00155", "推荐人只能是大管家！"),
    ERR_NUM_HANDLE("S00156", "数量为1不能进行删除操作！"),
    ERR_NULL_ADDR("S00157", "没有地址，请先添加地址！"),
    ERR_REPEAT_GET("S00158", "已经取过，不能重复获取！"),
    ERR_ORDER_NOT_FINISH("S00158", "订单未完成，不能删除！"),
    ERR_ORDEREFUND_EXISTED("S00159", "该订单售后不能重复提交！"),
    ERR_ORDER_PAYED("S00160", "该订单已经支付过，不能重复支付！"),
    ERR_WX_PAY_UNIFIED_ORDER("S00161", "微信下单接口错误！"),
    ERR_ORDER_CANCEL("S00162", "只有订单为待付款状态下才能取消该订单！"),
    ERR_SEQNO_MAX("S00164", "当前生成泛在网编号内序号已经到过最大值,请明天再来试！"),
    ERR_ADD_NODE("S00165", "节点增加失败！"),
    ERR_ORDER_SUREGOODS("S00163", "只有订单为待收货状态下才能确认收货操作！");


    private String code;

    private String errMsg;

    ErrorEnum(String code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
