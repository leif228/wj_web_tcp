package com.wujie.app.business.entity;

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
@Table(name="node_standby")
public class NodeStandby implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 关联node表id
    @Column(name = "node_id")
    private Long nodeId;

    // 关联device表id
    @Column(name = "device_id")
    private Long deviceId;

    // 0主服务1备份服务
    @Column(name = "type")
    private Integer type;

    //
    @Column(name = "ip")
    private String ip;

    //
    @Column(name = "port")
    private String port;

    //
    @Column(name = "creat_time")
    private Date creatTime;

   }