package com.wujie.app.business.util.validator;

import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.framework.exception.CustomException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 * @ProjectName: foh
 * @Package: com.duoqio.boot.business.util.validator
 * @ClassName: ValidatorUtils
 * @Author: Administrator
 * @Description: Hibernate-validator校验工具类
 * @Date: 2019/8/29 15:27
 * @copyright: 重庆物界
 * @website: www.wujie.com
 */


@Component
public class ValidatorUtils {

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws Exception  校验不通过，则报Exception异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws CustomException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new CustomException(ErrorEnum.GATEWAY_ERROR.getErrCode(), constraint.getMessage());
        }
    }

}
