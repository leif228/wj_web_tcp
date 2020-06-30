package com.wujie.app.business.util;

/**
 * @ProjectName: news
 * @Package: vo
 * @ClassName: MDA
 * @Author: fanYang
 * @Description: 常量类
 * @Date: 2020/3/18 16:22
 * @copyright: 重庆物界
 * @website: www.wujie.com
 */
public class MDA {




    public enum numEnum{
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN
    }
    /** 手机号码验证*/
    public static final String REGEX_MOBILE ="((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";
    /** 默认验证码，上生产不使用 */
    public static final String DEFAULT_CODE = "154264";
    /** 默认邀请码*/
    public static final Integer DEFAULT_INVITATION_CODE = 300000;

    /** redis 缓存key */
    /** 轮播图*/
    public static final String CACHE_CAROUSEL = "cache_carousel";
    public static final String CACHE_CAROUSEL_SHOP = "cache_carousel_shop";
    /** 验证码60秒过期后才能再次获取  */
    public static final Integer BE_OVERDUE_TIME = 60;
    /** 真实验证码过期时间  */
    public static final Integer REAL_BE_OVERDUE_TIME = 5;
    /** 用户获取验证码key，用于判断重复发送验证码 */
    public static final String APP_LOGIN_TEL_CODE = "app_login_tel_code_{0}";
    /** 用户获取验证码key 用户判断验证码是否正确 */
    public static final String REAL_APP_LOGIN_TEL_CODE = "app_login_tel_code_{0}";

    /** 分享注册获取验证码key，用于判断重复发送验证码 */
    public static final String SHARE_USER_REGIST_CODE = "share_user_regist_code_{0}";
    /** 分享注册获取验证码key 用户判断验证码是否正确 */
    public static final String REAL_SHARE_USER_REGIST_CODE = "real_share_user_regist_code_{0}";

    /** 用户缓存key  */
    public static final String CACHE_USER_KEY = "cache_user_key_{0}";
    /** 公用配置缓存 */
    public static final String CACHE_ALL_SYSTEM_CFG = "cache_all_system_cfg";
    /** 店铺信息缓存 */
    public static final String CACHE_SHOP_INFO = "cache_shop_info_{0}";
    /** 用户邀请码缓存 */
    public static final String CACHE_INVITATION_CODE = "cache_invitation_code";
    /** 防止并发下验证码错乱 */
    public static final String CACHE_CONCURRENT_REGISTER = "cache_concurrent_register";

    /** 用户支付锁，支付订单金额，提现等使用 */
    public static final String CACHE_USER_PAY_KEY = "cache_user_pay_key_{0}";
    /** 锁过期时间 */
    public static final long CACHE_LOCK_EXPIRE_DATE = 5;
    /** 提现锁 */
    public static final String CACHE_WITHDRAW_KEY = "cache_withdraw_key";


    /** 房间信息缓存 */
    public static final String CACHE_ROOM_INFO = "cache_room_info_{0}";
    /** redis 缓存key */




    /** 直播 */
    /** 直播流key */
    public static final String LIVE_KEY = "a_live_room_{0}";
    public static final String LIVE_ROOM_COVER = "live_room_cover";

    /** 直播商品缓存*/
    public static final String CACHE_LIVE_GOODSINFO = "CACHE_LIVE_GOODSINFO_{0}";
    /** 直播*/

    /** 踢下线原因 */
    public static final String LIVE_DISABLE_REASON = "传播不良信息, 禁用一天";
}
