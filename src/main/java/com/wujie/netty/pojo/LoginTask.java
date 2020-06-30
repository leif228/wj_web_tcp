package com.wujie.netty.pojo;

import java.io.Serializable;

public class LoginTask implements Serializable {
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
