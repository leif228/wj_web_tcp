package com.wujie.app.business.app.service.system.impl;

import com.wujie.app.business.app.service.system.AuthUserService;
import com.wujie.app.business.app.service.system.UserService;
import com.wujie.app.business.entity.Device;
import com.wujie.app.business.entity.Node;
import com.wujie.app.business.entity.NodeStandby;
import com.wujie.app.business.entity.Wjuser;
import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.business.repository.DeviceMapper;
import com.wujie.app.business.repository.NodeMapper;
import com.wujie.app.business.repository.NodeStandbyMapper;
import com.wujie.app.business.repository.WjuserMapper;
import com.wujie.app.business.util.NumConvertUtil;
import com.wujie.app.business.util.date.DateUtil;
import com.wujie.app.business.vo.*;
import com.wujie.app.business.util.MDA;
import com.wujie.app.framework.auth.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private DeviceMapper deviceMapper;
    private NodeMapper nodeMapper;
    private NodeStandbyMapper nodeStandbyMapper;
    private WjuserMapper wjuserMapper;
    private AuthUserService authUserService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl(NodeStandbyMapper nodeStandbyMapper, NodeMapper nodeMapper, WjuserMapper wjuserMapper, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder, AuthUserService authUserService, DeviceMapper deviceMapper) {
        this.nodeStandbyMapper = nodeStandbyMapper;
        this.nodeMapper = nodeMapper;
        this.deviceMapper = deviceMapper;
        this.wjuserMapper = wjuserMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.authUserService = authUserService;
    }

    @Override
    public ResultVo<Object> userLogin(String username, String password) {
        UserDetailsVo userInfoVo = (UserDetailsVo) authUserService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userInfoVo.getPassword())) {
            return ResultVo.err(ErrorEnum.USERNAME_PASS_ERR);
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfoVo, null, userInfoVo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userInfoVo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("user", userInfoVo);
        return ResultVo.ok(resultMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<Object> deviceRegist(Long userId, String deviceSelected, String deviceName, String ip, String port, Long nodeId) {
        Wjuser wjuser = wjuserMapper.selectByPrimaryKey(userId);
        if (wjuser == null)
            return ResultVo.err(ErrorEnum.NOT_USER_ERR);

        Device device = new Device();
        device.setUserId(userId);
        device.setDeviceType(Integer.valueOf(deviceSelected));
        device.setDeviceName(deviceName);
        device.setIp(ip);
        device.setPort(port);
        device.setCreatTime(DateUtil.getDate());

        //生成泛在网编号
        String fzwNo = "";

        String country = "chn";
        String area = wjuser.getIdcard().substring(0, 6);
        String timeStr = DateUtil.getDateTime("yyyyMMdd");
        String seqno = "";//序列号表示该国家该地区当天注册的序号，以16进制字符串的形式表现，如FFFF表示65535号
        String catMaxFzwno = deviceMapper.findByFzwnoLikeCAT(country + area + timeStr);
        if (null == catMaxFzwno) {
            seqno = NumConvertUtil.IntToHexStringLimit4(1);
        } else {
            String limitPre = catMaxFzwno.substring(0, 21).substring(17);
            int seqInt = NumConvertUtil.HexStringToInt(limitPre);
            seqno = NumConvertUtil.IntToHexStringLimit4(seqInt + 1);//生成当前序号
            if (seqno == null)
                return ResultVo.err(ErrorEnum.ERR_SEQNO_MAX);
        }
        int userType = wjuser.getUserType();//1表示个人2团体、公司等

        fzwNo = country + area + timeStr + seqno + userId;

        device.setFzwno(fzwNo);
        deviceMapper.insertSelective(device);

        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setIp("");
        deviceVo.setPort("");
        deviceVo.setFzwno(fzwNo);

        //增加下级节点
        if (MDA.numEnum.ZERO.ordinal() == Integer.valueOf(deviceSelected)) {
            Node preNode = nodeMapper.selectByPrimaryKey(nodeId);
            NodeStandby preNodeStandby = nodeStandbyMapper.findByNodeAndType(nodeId, MDA.numEnum.ZERO.ordinal());
            deviceVo.setIp(preNodeStandby.getIp());
            deviceVo.setPort(preNodeStandby.getPort());

            nodeMapper.updateRgt(preNode.getRgt());
            nodeMapper.updateLft(preNode.getRgt());

            Node currNode = new Node();
            currNode.setName(deviceName);
            currNode.setLft(preNode.getRgt());
            currNode.setRgt(preNode.getRgt() + 1);
            currNode.setCreatTime(DateUtil.getDate());
            nodeMapper.insertSelective(currNode);

            NodeStandby nodeStandby = new NodeStandby();
            nodeStandby.setNodeId(currNode.getId());
            nodeStandby.setDeviceId(device.getId());
            nodeStandby.setIp(ip);
            nodeStandby.setPort(port);
            nodeStandby.setType(MDA.numEnum.ZERO.ordinal());//默认设置为当前节点的主服务器
            nodeStandby.setCreatTime(DateUtil.getDate());
            nodeStandbyMapper.insertSelective(nodeStandby);
        }

        log.info("设备注册成功" + fzwNo);

        return ResultVo.ok(deviceVo);
    }

    @Override
    public ResultVo<Object> userRegist(String username, String password, String idcard, String phone, String userSelected) {
        Wjuser wjuser = wjuserMapper.findByUserInfoName(username);
        if (wjuser != null)
            return ResultVo.err(ErrorEnum.PRESENCE_USER_ERR);

        wjuser = new Wjuser();
        wjuser.setUserName(username);
        wjuser.setPassWord(passwordEncoder.encode(password));
        wjuser.setIdcard(idcard);
        wjuser.setUserType(Integer.valueOf(userSelected));
        wjuser.setPhone(phone);
        wjuser.setCreatTime(DateUtil.getDate());

        wjuserMapper.insertSelective(wjuser);

        UserDetailsVo userDetailsVo = (UserDetailsVo) authUserService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailsVo, null, userDetailsVo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetailsVo);

        log.info("用户注册成功");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("user", userDetailsVo);
        return ResultVo.ok(resultMap);
    }

    @Override
    public ResultVo<List<Node>> getChildNodes(Long nodeId) {
        Node node = nodeMapper.selectByPrimaryKey(nodeId);
        List<Node> childs = nodeMapper.getChildNodes(node.getLft(), node.getRgt());

        return ResultVo.ok(childs);
    }

    @Override
    public ResultVo<NodeVo> getTreeData(Long nodeId) {
        NodeVo nodeVo = new NodeVo();
        List<NodeVo> list = nodeMapper.getAllChildNodeVosLayer();
        if(list.size()>0){
            Map<Integer, List<NodeVo>> map = list.stream().collect(Collectors.groupingBy(NodeVo::getLayer));
            int mapSize = map.size();
            if (mapSize > 0) {
                int firstLayer = 1;
                for (int i = firstLayer; i <= mapSize; i++) {
                    List<NodeVo> list0 = map.get(i);
                    int j = i + 1;
                    if (j > mapSize)
                        break;
                    List<NodeVo> list1 = map.get(j);
                    for (NodeVo parent : list0) {
                        if (!parent.getName().contains(":"))
                            parent.setName(parent.getName() + "(" + parent.getIp() + ":" + parent.getPort() + ")");
                        for (NodeVo child : list1) {
                            if (parent.getRgt() > child.getRgt() && parent.getLft() < child.getLft()) {
                                parent.getChildren().add(child);
                                if (!child.getName().contains(":"))
                                    child.setName(child.getName() + "(" + child.getIp() + ":" + child.getPort() + ")");
                            }
                        }
                    }
                }
                nodeVo = map.get(firstLayer).get(0);
                log.debug(nodeVo.toString());
            }
        }

        return ResultVo.ok(nodeVo);
    }

}
