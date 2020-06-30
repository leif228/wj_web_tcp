package com.wujie.netty.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class Device {
    //
    private String uniqueNo;

    //
    private String name;

    public Device(String uniqueNo, String name) {
        this.uniqueNo = uniqueNo;
        this.name = name;
    }
}
