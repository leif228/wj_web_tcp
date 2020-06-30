/**
* @title: OperationLogger.java
* @package com.duoqio.common.annotation
* @describe 
* @author tanghu
* @date 2019年6月20日
* @version V1.0
*/
package com.wujie.app.framework.systemlog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author tanghu
 * @date 2019年6月20日
 * @describe 操作日志
 * @version V1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.METHOD })
public @interface SystemLog {

	/**
	 * @title: methods
	 */
	String methods() default "";

	/**
	 * 日志类型
	 * 
	 * @title: type
	 */
	LogType type() default LogType.OPERATION;
}
