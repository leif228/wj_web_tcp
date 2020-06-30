package com.wujie.app.business.repository;


import com.wujie.app.business.entity.Node;
import com.wujie.app.business.vo.NodeVo;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface NodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Node record);

    @Options(useGeneratedKeys = true)
    int insertSelective(Node record);

    Node selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Node record);

    int updateByPrimaryKey(Node record);

    void updateRgt(Long rgt);

    void updateLft(Long rgt);

    List<Node> getChildNodes(Long lft, Long rgt);

    List<NodeVo> getChildNodeVos(Long nodeId);

    int getLayer(Long nodeId);

    List<NodeVo> getAllChildNodeVosLayer();
}