package com.wujie.app.business.app.controller;

import com.wujie.app.business.app.service.system.UserService;
import com.wujie.app.business.entity.Node;
import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.business.vo.NodeVo;
import com.wujie.app.business.vo.ResultVo;
import com.wujie.app.business.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserController
 * @Description: 用户控制层
 * @Date: 2020/4/21 14:27
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/deviceRegist")
    public ResultVo<Object> deviceRegist(@RequestParam(value = "userId") Long userId,
                                         @RequestParam(value = "deviceSelected") String deviceSelected,
                                         @RequestParam(value = "deviceName") String deviceName,
                                         @RequestParam(value = "ip") String ip,
                                         @RequestParam(value = "port") String port,
                                         @RequestParam(value = "nodeId") Long nodeId) {
        try {
            return userService.deviceRegist(userId, deviceSelected, deviceName, ip, port, nodeId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.err(ErrorEnum.ERR_ADD_NODE);
        }
    }

    @PostMapping("/getChildNodes")
    public ResultVo<List<Node>> getChildNodes(@RequestParam(value = "nodeId") Long nodeId) {
        return userService.getChildNodes(nodeId);
    }

    @PostMapping("/getTreeData")
    public ResultVo<NodeVo> getTreeData(@RequestParam(value = "nodeId") Long nodeId) {
        return userService.getTreeData(nodeId);
    }

    @PostMapping("/userRegist")
    public ResultVo<Object> userRegist(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "password") String password,
                                       @RequestParam(value = "idcard") String idcard,
                                       @RequestParam(value = "phone") String phone,
                                       @RequestParam(value = "userSelected") String userSelected) {
        return userService.userRegist(username, password, idcard, phone, userSelected);
    }

    @PostMapping("/userLogin")
    public ResultVo<Object> userLogin(@RequestParam(value = "username") String username,
                                      @RequestParam(value = "password") String password
    ) {
        return userService.userLogin(username, password);
    }
}
