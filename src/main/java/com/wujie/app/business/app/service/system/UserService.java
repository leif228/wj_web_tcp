package com.wujie.app.business.app.service.system;

import com.github.pagehelper.PageInfo;
import com.wujie.app.business.entity.Node;
import com.wujie.app.business.vo.NodeVo;
import com.wujie.app.business.vo.ResultVo;
import com.wujie.app.business.vo.TreeVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {

    ResultVo<Object> userLogin(String username, String password);

    ResultVo<Object> deviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId) ;

    ResultVo<Object> userRegist(String username, String password, String idcard, String phone, String userSelected);

    ResultVo<List<Node>> getChildNodes(Long nodeId);

    ResultVo<NodeVo> getTreeData(Long nodeId);
}
