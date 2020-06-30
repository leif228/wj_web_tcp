package com.wujie.app.framework.exception;

import com.wujie.app.business.enums.ErrorEnum;
import lombok.Data;

/**
 * @ProjectName: example
 * @Package: com.duoqiyuan.yf.example.utils.exception
 * @ClassName: CustomException
 * @Author: Administrators
 * @Description: 自定义异常
 * @Date: 2019/2/25 13:52
 * @Version: 1.0
 */
@Data
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = -4082529557977754328L;

    private String code;

    public CustomException(ErrorEnum errorEnum) {
        super(errorEnum.getErrMsg());
        this.setCode(errorEnum.getErrCode());
    }

    public CustomException(String code, String errMsg) {
        super(errMsg);
        this.setCode(code);
    }

    public CustomException(String errMsg) {
        super(errMsg);
    }
}
