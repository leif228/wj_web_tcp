package com.wujie.app.framework.auth;

import com.wujie.app.business.vo.UserDetailsVo;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ProjectName: 20200420_zc
 * @Package: com.duoqio.boot.framework.auth
 * @ClassName: SecurityUtil
 * @Author: fanYang
 * @Description: 获取用户信息
 * @Date: 2020/4/22 17:17
 * @copyright: 重庆多企源科技有限公司
 * @website: http://www.duoqio.com/index.asp?source=code
 */
public class SecurityUtil {

    /**
     * @Title getUserInfo
     * @Description 获取用户信息
     * @Author yangf
     * @Date 2020/4/22 17:18
     * @Return com.duoqio.boot.business.vo.UserDetailsVo
     */
    public static UserDetailsVo getUserInfo(){
        return (UserDetailsVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
