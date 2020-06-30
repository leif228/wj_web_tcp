package com.wujie.app.business.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TreeVo implements Serializable {
    private String name;
    private List<TreeVo> children = new ArrayList<>();
 }
