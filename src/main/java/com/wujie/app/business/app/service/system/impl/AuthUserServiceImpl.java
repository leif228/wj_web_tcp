package com.wujie.app.business.app.service.system.impl;

import com.wujie.app.business.app.service.system.AuthUserService;
import com.wujie.app.business.entity.Wjuser;
import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.business.repository.WjuserMapper;
import com.wujie.app.business.vo.UserDetailsVo;
import com.wujie.app.business.util.MDA;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: 20200420_zc
 * @Package: com.duoqio.boot.business.pc.service.system.impl
 * @ClassName: AuthUserServiceImpl
 * @Author: fanYang
 * @Description: auth验证
 * @Date: 2020/4/21 17:14
 * @copyright: 重庆多企源科技有限公司
 * @website: http://www.duoqio.com/index.asp?source=code
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

    private WjuserMapper wjuserMapper;


    @Lazy
    @Autowired
    public AuthUserServiceImpl( WjuserMapper wjuserMapper) {
        this.wjuserMapper = wjuserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Wjuser userInfoTbl = wjuserMapper.findByUserInfoName(username);
        if(ObjectUtils.isEmpty(userInfoTbl)){
            throw new UsernameNotFoundException(ErrorEnum.NOT_USER_ERR.getErrMsg());
        }
//        ResultVo<List<SysPermisTbl>> userAllPermis = sysPermisService.findUserAllPermis(userInfoTbl.getUserId());
//        List<SysPermisTbl> permisTbls = userAllPermis.getData();
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(userAllPermis.getData())) {
//            for (SysPermisTbl sysPermission : permisTbls) {
//                authorityList.add(new SimpleGrantedAuthority(sysPermission.getPermis()));
//            }
//        }
        UserDetailsVo userInfoVo = new UserDetailsVo(userInfoTbl.getUserName(), userInfoTbl.getPassWord(), authorityList);
        BeanUtils.copyProperties(userInfoTbl, userInfoVo);
        return userInfoVo;
    }

}
