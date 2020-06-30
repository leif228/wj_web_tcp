package com.wujie.app.business.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="node")
public class Node implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //
    @Column(name = "name")
    private String name;

    //
    @Column(name = "lft")
    private Long lft;

    //
    @Column(name = "rgt")
    private Long rgt;

    //
    @Column(name = "creat_time")
    private Date creatTime;

}