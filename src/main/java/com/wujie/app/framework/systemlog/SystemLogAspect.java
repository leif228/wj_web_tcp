/**
* @title: SystemLogAspect.java
* @package com.duoqio.common.aop
* @describe 
* @author tanghu
* @date 2019年6月20日
* @version V1.0
*/
package com.wujie.app.framework.systemlog;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author tanghu
 * @date 2019年6月20日
 * @describe 系统日志
 * @version V1.0
 */
@Log4j2
@Aspect
@Component
public class SystemLogAspect {
//	private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal beginTime");
//
//	private LogService logService;
//
//	@Autowired
//	public SystemLogAspect(LogService logService) {
//		super();
//		this.logService = logService;
//	}
//
//	@Pointcut("@annotation(com.duoqio.boot.framework.systemlog.SystemLog)")
//	public void logPoinCut() {
//	}
//
//	@Before("logPoinCut()")
//	public void doBefore(JoinPoint joinPoint) throws InterruptedException {
//		// 线程绑定变量（该数据只有当前请求的线程可见）
//		Date beginTime = Calendar.getInstance().getTime();
//		beginTimeThreadLocal.set(beginTime);
//	}
//
//	/**
//	 * @Title: afterReturning
//	 * @Description: 后置增强，相当于AfterReturningAdvice，方法正常退出时执行[操作成功后进入]
//	 * @author:sdz
//	 * @date: 2019年7月16日上午10:59:32
//	 * @return:void 返回类型
//	 */
//	@AfterReturning("logPoinCut()")
//	public void afterReturning(JoinPoint joinPoint) {
//		try {
//			HttpServletRequest request = ServletUtils.getRequest();
//			if (request == null) {
//				return;
//			}
//
//			LogInfoTbl log = new LogInfoTbl();
//
//			UserDetailsVo user = SecurityUtil.getUserInfo();
//			if(!Objects.isNull(user) && !Objects.isNull(user.getUserId())) {
//				// 请求用户
//				log.setUserId(user.getUserId());
//			}
//			// 日志标题
//			log.setName(getControllerMethodInfo(joinPoint).get("name").toString());
//			// 日志类型
//			log.setLogType((int) getControllerMethodInfo(joinPoint).get("type"));
//			// 日志请求url
//			log.setRequestUrl(request.getRequestURI());
//			// 请求方式
//			log.setRequestType(request.getMethod());
//			// 请求参数
//			Map<String, String[]> logParams = request.getParameterMap();
//			log.setRequestParam(JSON.toJSONString(logParams));
//
//			// 请求IP
//			log.setIp(IPUtils.getIpAddr(request));
//			// 请求开始时间
//			long beginTime = beginTimeThreadLocal.get().getTime();
//			long endTime = System.currentTimeMillis();
//			// 请求耗时
//			Long logElapsedTime = endTime - beginTime;
//			log.setCostTime(logElapsedTime.intValue());
//			//操作时间
//			log.setAddTime(DateUtil.getDate());
//			//请求是否成功
//			log.setStatus(0);//成功
//
//			log.setDeleteFlag(0);
//
//			// 调用线程保存至数据库
//			ThreadUtils.getPool().execute(new SaveSystemLogThread(log, logService));
//
//		} catch (Exception e) {
//			log.info("--- AOP后置增强通知异常", e);
//		}
//	}
//
//	/**
//	* @describe 保存日志至数据库
//	* @title SystemLogAspect.java
//	* @author tanghu
//	* @date 2019年6月20日
//	*/
//	private static class SaveSystemLogThread implements Runnable {
//
//		private LogInfoTbl log;
//		private LogService logService;
//
//		public SaveSystemLogThread(LogInfoTbl log, LogService logService) {
//			this.log = log;
//			this.logService = logService;
//		}
//
//		@Override
//		public void run() {
//			logService.save(log);
//		}
//	}
//
//	/**
//	* @describe 获取注解中对方法的描述信息 用于Controller层注解
//	* @title getControllerMethodInfo
//	* @author tanghu
//	* @date 2019年6月20日
//	* @param  joinPoint 切点
//	* @return
//	* @throws Exception
//	*/
//	public static Map<String, Object> getControllerMethodInfo(JoinPoint joinPoint) throws Exception {
//		Map<String, Object> map = new HashMap<>();
//		// 获取目标类名
//		String targetName = joinPoint.getTarget().getClass().getName();
//		// 获取方法名
//		String methodName = joinPoint.getSignature().getName();
//		// 获取相关参数
//		Object[] arguments = joinPoint.getArgs();
//		// 生成类对象
//		Class<?> targetClass = Class.forName(targetName);
//		// 获取该类中的方法
//		Method[] methods = targetClass.getMethods();
//
//		String description = "";
//		Integer type = null;
//
//		for (Method method : methods) {
//			if (!method.getName().equals(methodName)) {
//				continue;
//			}
//			Class<?>[] clazzs = method.getParameterTypes();
//			if (clazzs.length != arguments.length) {
//				continue;
//			}
//			description = method.getAnnotation(SystemLog.class).methods();
//			type = method.getAnnotation(SystemLog.class).type().ordinal();
//			map.put("name", description);
//			map.put("type", type);
//		}
//		return map;
//	}

}
