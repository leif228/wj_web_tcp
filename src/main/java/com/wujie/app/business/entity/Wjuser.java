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
@Table(name="wjuser")
public class Wjuser implements Serializable {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //
    @Column(name = "user_name")
    private String userName;

    //
    @Column(name = "pass_word")
    private String passWord;

    //
    @Column(name = "idcard")
    private String idcard;

    //
    @Column(name = "phone")
    private String phone;

    //
    @Column(name = "addr")
    private String addr;

    // 0个人1团体
    @Column(name = "user_type")
    private Integer userType;

    //
    @Column(name = "creat_time")
    private Date creatTime;

  }