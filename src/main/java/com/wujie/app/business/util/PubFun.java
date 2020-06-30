package com.wujie.app.business.util;

import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.business.util.date.DateUtil;
import com.wujie.app.framework.constant.SystemConstant;
import com.wujie.app.framework.exception.CustomException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

/**
 * 公用的工具类
 * @ClassName: PubFun.java
 * @author MengDaNai
 * @version 1.0
 * @Date 2019年2月1日 下午5:48:57
 */
@Component
public class PubFun {

	private static final Pattern MYREGEX = Pattern.compile("myRegex");
	public static final int INT_2 = 2;
	private static final int INT_10 = 10;

	/**
	 * 判断是否为空
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:49:09
	 * @param object
	 * @param code
	 * @return void
	 */
	public static void isNull(Object object, int code) {
		if (Objects.isNull(object)) {
			throw new CustomException(ErrorEnum.GATEWAY_ERROR);
		}
	}

	/**
	 * 判断是否为空
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:49:18
	 * @param object
	 * @param code
	 * @param msg
	 * @return void
	 */
	public static void isNull(Object object, int code, String msg) {
		if (Objects.isNull(object)) {
			throw new CustomException(ErrorEnum.GATEWAY_ERROR);
		}
	}

	/**
	 * Object转int
	 * @author MengDaNai
	 * @Date 2019年3月4日 下午5:49:30
	 * @param param
	 * @return int
	 */
	public static int objectToInt(Object param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return stringToInt(objectToString(param));
	}
	public static int objectStrongToInt(Object obj) {
		return Objects.isNull(obj)?0:stringToInt(objectToString(obj));
	}

	/**
	 * Object转String
	 * @author MengDaNai
	 * @Date 2019年3月4日 下午5:50:16
	 * @param param
	 * @return String
	 */
	public static String objectToString(Object param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return param.toString();
	}
	public static String objectStrongToString(Object param) {
		return Objects.isNull(param)?"":param.toString();
	}

	/**
	 * Object转long
	 * @author MengDaNai
	 * @Date 2019年2月1日 下午5:51:13
	 * @param param
	 * @return long
	 */
	public static long objectToLong(Object param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return Long.valueOf(objectToString(param));
	}
	public static long objecStrongtToLong(Object param) {
		return Objects.isNull(param)?0:Long.valueOf(objectToString(param));
	}

	/**
	 * Object转double
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:51:23
	 * @param param
	 * @return double
	 */
	public static double objectToDouble(Object param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return Double.parseDouble(objectToString(param));
	}
	public static double objectStrongToDouble(Object param) {
		return Objects.isNull(param)?0.00:Double.parseDouble(objectToString(param));
	}

	/**
	 * String转int
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:51:34
	 * @param param
	 * @return int
	 */
	public static int stringToInt(String param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return Integer.parseInt(param);
	}
	public static int stringStrongToInt(String param) {
		return Objects.isNull(param)?0:Integer.parseInt(param);
	}

	/**
	 * String转long
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:51:40
	 * @param param
	 * @return long
	 */
	public static long stringToLong(String param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return Long.valueOf(param);
	}
	public static long stringStrongToLong(String param) {
		return Objects.isNull(param)?0:Long.valueOf(param);
	}

	/**
	 * String转double
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:51:45
	 * @param param
	 * @return double
	 */
	public static double stringToDouble(String param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return Double.parseDouble(param);
	}
	public static double stringStrongToDouble(String param) {
		return Objects.isNull(param)?0.00:Double.parseDouble(param);
	}

	/**
	 * int转string
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:51:55
	 * @param param
	 * @return String
	 */
	public static String intToString(int param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return String.valueOf(param);
	}
	public static String intStrongToString(int param) {
		return param==0?"":String.valueOf(param);
	}

	/**
	 * int转double
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:52:02
	 * @param param
	 * @return double
	 */
	public static double intToDouble(int param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return param;
	}
	public static double intStrongToDouble(int param) {
		return param==0?0.00:param;
	}

	/**
	 * int转long
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:52:11
	 * @param param
	 * @return long
	 */
	public static long intToLong(int param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return (long) param;
	}
	public static long intStrongToLong(int param) {
		return param==0?0:param;
	}

	/**
	 * double转long
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:52:19
	 * @param param
	 * @return long
	 */
	public static long doubleToLong(double param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return Math.round(param);
	}
	public static long doubleStrongToLong(double param) {
		return param==0?0:Math.round(param);
	}

	/**
	 * double转String
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:52:26
	 * @param param
	 * @return String
	 */
	public static String doubleToString(double param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return String.valueOf(param);
	}
	public static String doubleStrongToString(double param) {
		return param==0?"":String.valueOf(param);
	}

	/**
	 * double转int
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:52:33
	 * @param param
	 * @return int
	 */
	public static int doubleToInt(double param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return (int) param;
	}
	public static int doubleStrongToInt(double param) {
		return param==0?0:(int) param;
	}

	/**
	 * Long转int
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:52:45
	 * @param param
	 * @return int
	 */
	public static int longToInt(Long param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return Integer.parseInt(longToString(param));
	}
	public static int longStrongToInt(Long param) {
		return param==0?0:Integer.parseInt(longToString(param));
	}

	/**
	 * Long转string
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:53:02
	 * @param param
	 * @return String
	 */
	public static String longToString(Long param) {
		isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		return param.toString();
	}
	public static String longStrongToString(Long param) {
		return param==0?"":param.toString();
	}

	/**
	 * @Title: getBigDecimal
	 * @Description: Object转BigDecimal
	 * @author 董璨
	 * @Date 2019年7月17日 下午3:31:35
	 * @param obj
	 * @return BigDecimal
	 */
	public static BigDecimal ObjectToBigDecimal(Object obj) {
		BigDecimal ret = null;
		if(obj != null) {
			if(obj instanceof BigDecimal) {
				ret = (BigDecimal) obj;
			} else if(obj instanceof String) {
				ret = new BigDecimal((String) obj);
			} else if(obj instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) obj);
			} else if(obj instanceof Number) {
				ret = new BigDecimal(((Number)obj).doubleValue());
			} else {
				ret = new BigDecimal(0.0);
			}
		}
		return ret;
	}

	/**
	 *************************************************
	 *	Object转BigDecimal 保留roundNum位小数
	 *************************************************
	 */
	public static BigDecimal ObjectToBigDecimal(Object obj, Integer roundNum) {
		BigDecimal ret = null;
		if (obj != null) {
			if (obj instanceof BigDecimal) {
				ret = (BigDecimal) obj;
			} else if (obj instanceof String) {
				ret = new BigDecimal((String) obj);
			} else if (obj instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) obj);
			} else if (obj instanceof Number) {
				ret = new BigDecimal(((Number) obj).doubleValue());
			} else {
				StringBuffer str = new StringBuffer();
				str.append("0.");
				for (int i = 0; i < roundNum; i++) {
					str.append("0");
				}
				ret = new BigDecimal(str.toString());
			}
		}
		return ret.setScale(roundNum, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 加法运算
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:53:13
	 * @param m1 被加数
	 * @param m2 加数
	 * @return Double
	 */
	public static Double addBigDecimal(Object m1, Object m2) {
		BiFunction<Object, Object, Double> deal = (o, t) -> {
			BigDecimal p1 = new BigDecimal(Double.toString(objectToDouble(m1)));
			BigDecimal p2 = new BigDecimal(Double.toString(objectToDouble(m2)));
			return p1.add(p2).doubleValue();
		};
		return deal.apply(m1, m2);
	}

	public static BigDecimal addBigDecimal(BigDecimal m1, BigDecimal m2) {
		return m1.add(m2);
	}
	/**
	 * 减法运算
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:53:26
	 * @param m1 被减数
	 * @param m2 减数
	 * @return Double
	 */
	public static Double minusBigDecimal(Object m1, Object m2) {
		BiFunction<Object, Object, Double> deal = (o, t) -> {
			BigDecimal p1 = new BigDecimal(Double.toString(objectToDouble(m1)));
			BigDecimal p2 = new BigDecimal(Double.toString(objectToDouble(m2)));
			return p1.subtract(p2).doubleValue();
		};
		return deal.apply(m1, m2);
	}

	public static BigDecimal minusBigDecimal(BigDecimal m1, BigDecimal m2) {
		return m1.subtract(m2);
	}


	/**
	 * 乘法运算
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:53:34
	 * @param m1 被乘数
	 * @param m2 乘数
	 * @return double
	 */
	public static double multiplicationBigDecimal(Object m1, Object m2) {
		BiFunction<Object, Object, Double> deal = (o, t) -> {
			BigDecimal p1 = new BigDecimal(Double.toString(objectToDouble(m1)));
			BigDecimal p2 = new BigDecimal(Double.toString(objectToDouble(m2)));
			return p1.multiply(p2).doubleValue();
		};
		return deal.apply(m1, m2);
	}

	public static BigDecimal multiplicationBigDecimal(BigDecimal m1, BigDecimal m2) {
		return m1.multiply(m2);
	}

	/**
	 * 除法运算
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:53:55
	 * @param m1 除数
	 * @param m2  被除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return double
	 */
	public static double divisionBigDecimal(Object m1, Object m2, int scale) {
		BiFunction<Object, Object, Double> deal = (o, t) -> {
			if (scale < 0) {
				throw new IllegalArgumentException("Parameter error");
			}
			BigDecimal p1 = new BigDecimal(Double.toString(objectToDouble(m1)));
			BigDecimal p2 = new BigDecimal(Double.toString(objectToDouble(m2)));
			return p1.divide(p2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		};
		return deal.apply(m1, m2);
	}

	/**
	 * @Title comparisonOfSize
	 * @Description 比较大小 false: m1 < m2 ture m1 >= m2
	 * @Author yangf
	 * @Date 2020/5/26 14:46
	 * @param: m1
	 * @param: m2
	 * @Return java.lang.Boolean
	 */
	public static Boolean comparisonOfSize(Object m1, Object m2){
		BigDecimal p1 = new BigDecimal(Double.toString(objectToDouble(m1)));
		BigDecimal p2 = new BigDecimal(Double.toString(objectToDouble(m2)));
		if (p1.compareTo(p2) == -1){
			return false;
		}
		return true;
	}

	/**
	 * 判断指定文件是否存在 不存在则创建
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:54:09
	 * @param filePath
	 * @return void
	 */
	public static void checkFileExists(String filePath) {
		PubFun.isNull(filePath, SystemConstant.DATA_ILLEGALITY_CODE);
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				boolean newFile = file.createNewFile();
				if (!newFile){
					throw new CustomException(ErrorEnum.GATEWAY_ERROR);
				}
			} catch (IOException e) {
				throw new CustomException(ErrorEnum.GATEWAY_ERROR);
			}
		}
	}

	/**
	 * 判断指定文件夹是否存在 不存在则创建
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:54:17
	 * @param filePath
	 * @return void
	 */
	public static void checkFolderExists(String filePath) {
		PubFun.isNull(filePath, SystemConstant.DATA_ILLEGALITY_CODE);
		File file = new File(filePath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}

	/**
	 * 获取文件后缀
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:54:26
	 * @param fileName
	 * @return String
	 */
	public static String getFileSuffix(String fileName) {
		PubFun.isNull(fileName, SystemConstant.DATA_ILLEGALITY_CODE);
		return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
	}

	/**
	 * 获取主机名称 和 主机ip
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:54:31
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> getHostName() {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("HostId", InetAddress.getLocalHost().getHostAddress());
			map.put("HostName", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			throw new CustomException(ErrorEnum.GATEWAY_ERROR);
		}
		return map;
	}

	/**
	 * 获取订单号
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:54:44
	 * @return String
	 */
	public static String getTheOrderNumber() {
		return DateUtil.getDateTime("YYYYMMddHHmmss") + System.nanoTime();
	}

	/**
	 * 去掉最后一个字符
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:54:53
	 * @param str
	 * @return String
	 */
	public static String removeTheLastOne(String str) {
		PubFun.isNull(str, SystemConstant.DATA_ILLEGALITY_CODE);
		return str.substring(0, str.length() - 1);
	}

	/**
	 * 去掉最后一个字符
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:55:00
	 * @param str
	 * @return Boolean
	 */
	public static Boolean digitalCheck(String str) {
		PubFun.isNull(str, SystemConstant.DATA_ILLEGALITY_CODE);
		return MYREGEX.matcher(str).matches();
	}

	/**
	 * 生成随机数
	 * @author MengDaNai
	 * @Date 2019年7月19日 下午5:55:11
	 * @param len
	 * @return
	 * @return String
	 */
	public static String generateRandomNumbersMax10(int len) {
		PubFun.isNull(len, SystemConstant.DATA_ILLEGALITY_CODE);
		if (len > INT_10) {
			throw new CustomException(ErrorEnum.GATEWAY_ERROR);
		}
		return doubleToString(Math.random()).substring(INT_2, len+INT_2);
	}

	/**
	 * 功能描述: <br>
	 * 〈分组 数据返回list〉
	 * @Param: [list, deal]
	 * @Return: java.util.Map<K,java.util.List<T>>
	 * @Author: SHISHI
	 * @Date: 2019/6/25 17:25
	 */
	public static <T,K> Map<K,List<T>> listToMapByGroupList(List<T> list,Function<T,K> deal){
		return list.stream().collect(groupingBy(deal));
	}
	/**
	 * 功能描述: <br>
	 * 〈分组 数据返回Object〉
	 * @Param: [list, deal]
	 * @Return: java.util.Map<K,T>
	 * @Author: SHISHI
	 * @Date: 2019/6/25 17:26
	 */
	public static <T,K> Map<K,T> listToMapByGroupObject(List<T> list, Function<T,K> deal){
		return list.stream().collect(groupingBy(deal)).entrySet().stream().collect(toMap(Map.Entry::getKey,e -> e.getValue().get(0)));
	}
	/**
	 * 功能描述: <br>
	 * 〈分组 数据返回Object〉
	 * @Param: [list, deal]
	 * @Return: java.util.Map<K,T>
	 * @Author: SHISHI
	 * @Date: 2019/6/25 17:26
	 */
	public static <T,K> Map<K,T> listToMapByGroupObject(Set<T> list, Function<T,K> deal){
		return list.stream().collect(groupingBy(deal)).entrySet().stream().collect(toMap(Map.Entry::getKey,e -> e.getValue().get(0)));
	}
	/**
	 * 功能描述: <br>
	 * 〈分组 数据个数〉
	 * @Param: [list, deal]
	 * @Return: java.util.Map<K,T>
	 * @Author: SHISHI
	 * @Date: 2019/6/25 17:26
	 */
	public static <T,K> Map<K,Long> listToMapByGroupCount(List<T> list,Function<T,K> deal){
		return list.stream().collect(groupingBy(deal,counting()));
	}

	public static <T> boolean baseCheck(T t, Predicate<T> deal){
		return deal.test(t);
	}


	// 将十进制转换成IP地址
	public static String num2ip(int ip) {
		int[] b = new int[4];
		String x = "";
		b[0] = (int) ((ip >> 24) & 0xff);
		b[1] = (int) ((ip >> 16) & 0xff);
		b[2] = (int) ((ip >> 8) & 0xff);
		b[3] = (int) (ip & 0xff);
		x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);
		return x;
	}

	/**
	 * @Title getRandomIp
	 * @Description 获取随机IP
	 * @Author yangf
	 * @Date 2019/12/30 9:19
	 * @param: null
	 * @Return java.lang.String
	 */
	public static String getRandomIp() {
		// 需要排除监控的ip范围
		int[][] range = { { 607649792, 608174079 }, // 36.56.0.0-36.63.255.255
				{ 1038614528, 1039007743 }, // 61.232.0.0-61.237.255.255
				{ 1783627776, 1784676351 }, // 106.80.0.0-106.95.255.255
				{ 2035023872, 2035154943 }, // 121.76.0.0-121.77.255.255
				{ 2078801920, 2079064063 }, // 123.232.0.0-123.235.255.255
				{ -1950089216, -1948778497 }, // 139.196.0.0-139.215.255.255
				{ -1425539072, -1425014785 }, // 171.8.0.0-171.15.255.255
				{ -1236271104, -1235419137 }, // 182.80.0.0-182.92.255.255
				{ -770113536, -768606209 }, // 210.25.0.0-210.47.255.255
				{ -569376768, -564133889 }, // 222.16.0.0-222.95.255.255
		};

		Random rdint = new Random();
		int index = rdint.nextInt(10);
		String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
		return ip;
	}

}