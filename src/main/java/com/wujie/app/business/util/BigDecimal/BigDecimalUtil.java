package com.wujie.app.business.util.BigDecimal;


import com.wujie.app.business.util.PubFun;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Description: BigDecimal基本处理
 * @ClassName: BigDecimalUtil.java
 * @author 董璨
 * @version 1.0
 * @Date 2019年7月17日 下午3:16:04
 */
public class BigDecimalUtil {

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
	 * @Title: add
	 * @Description: 加 v1 + v2
	 * @author 董璨
	 * @Date 2019年7月17日 下午3:17:16
	 * @param v1
	 * @param v2
	 * @return BigDecimal
	 */
	public static BigDecimal add(Object v1, Object v2) {
        return PubFun.ObjectToBigDecimal(v1).add(PubFun.ObjectToBigDecimal(v2));
    }

    /**
     * @Title: sub
     * @Description: 减 v1 - v2
     * @author 董璨
     * @Date 2019年7月17日 下午3:18:13
     * @param v1
     * @param v2
     * @return BigDecimal
     */
    public static BigDecimal sub(Object v1, Object v2) {
        return PubFun.ObjectToBigDecimal(v1).subtract(PubFun.ObjectToBigDecimal(v2));
    }

    /**
     * @Title: multiply
     * @Description: 乘 v1 * v2
     * @author 董璨
     * @Date 2019年7月17日 下午3:18:36
     * @param v1
     * @param v2
     * @return BigDecimal
     */
    public static BigDecimal multiply(Object v1, Object v2) {
        return PubFun.ObjectToBigDecimal(v1).multiply(PubFun.ObjectToBigDecimal(v2));
    }
    
    /**
     * @Title: multiply
     * @Description: 乘 v1 * v2 保留scale位小数
     * @author 董璨
     * @Date 2019年8月15日 下午2:05:19
     * @param v1
     * @param v2
     * @param scale
     * @return BigDecimal
     */
    public static BigDecimal multiply(Object v1, Object v2, int scale) {
        return BigDecimalUtil.round(PubFun.ObjectToBigDecimal(v1).multiply(PubFun.ObjectToBigDecimal(v2)), scale);
    }
    
    /**
     * @Title: multiplyPpt
     * @Description: 乘 v1 * 百分比数字 保留scale位小数
     * @author 董璨
     * @Date 2019年8月15日 下午2:05:19
     * @param v1
     * @param v2
     * @param scale
     * @return BigDecimal
     */
    public static BigDecimal multiplyPpt(Object v1, Object v2, int scale) {
        return BigDecimalUtil.round(PubFun.ObjectToBigDecimal(v1).multiply(PubFun.ObjectToBigDecimal(v2).divide(new BigDecimal(100))), scale);
    }

    /**
     * @Title: divide
     * @Description: 除 v1 除以 v2
     * @author 董璨
     * @Date 2019年7月17日 下午3:18:49
     * @param v1
     * @param v2
     * @scale 需要精确到小数点以后几位。
     * @return BigDecimal
     */
    public static BigDecimal divide(Object v1, Object v2, int scale) {
        // 2 = 保留小数点后两位   ROUND_HALF_UP = 四舍五入
        return PubFun.ObjectToBigDecimal(v1).divide(PubFun.ObjectToBigDecimal(v2), scale, BigDecimal.ROUND_HALF_UP);// 应对除不尽的情况
    }
    
    /**
     * @Title: divideAndRemainder
     * @Description: 取余运算 v1 模 v2
     * @author 董璨
     * @Date 2019年7月18日 上午11:07:43
     * @param v1
     * @param v2
     * @return BigDecimal
     */
    public static BigDecimal divideAndRemainder(Object v1, Object v2) {
    	return PubFun.ObjectToBigDecimal(v1).divideAndRemainder(PubFun.ObjectToBigDecimal(v2))[1];
    }
    
    /**
     * @Title: roundUp
     * @Description: 向上取整
     * @author 董璨
     * @Date 2019年7月31日 下午8:31:16
     * @param bd
     * @return BigDecimal
     */
    public static BigDecimal roundUp(BigDecimal bd) {
    	return bd.setScale(0, BigDecimal.ROUND_UP);
    }
    
    /**
     * @Title: roundDown
     * @Description: 向下取整
     * @author 董璨
     * @Date 2019年7月31日 下午8:31:16
     * @param bd
     * @return BigDecimal
     */
    public static BigDecimal roundDown(BigDecimal bd) {
    	return bd.setScale(0, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * @Title: round
     * @Description: 四舍五入，保留scale位小数
     * @author 董璨
     * @Date 2019年7月31日 下午8:34:38
     * @param bd
     * @param scale
     * @return BigDecimal
     */
    public static BigDecimal round(BigDecimal bd, int scale) {
    	return bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * @Title: compare
     * @Description: 比较
     * @author 董璨
     * @Date 2019年7月17日 下午4:02:16
     * @param a
     * @param b
     * @return int
     */
    public static int eq(BigDecimal a, BigDecimal b) {
    	// .compareTo(BigDecimal.ZERO) == 
    	// == -1 a小于b
    	// == 0  a等于b
    	// == 1  a大于b
    	// > -1  a大于等于b
    	// < 1   a小于等于b
    	return a.compareTo(b);
    }
    
    /**
     * @Title: eq
     * @Description: 比较
     * @author 董璨
     * @Date 2019年8月1日 下午7:56:12
     * @param a
     * @param b
     * @return int
     */
    public static int eq(Object a, Object b) {
    	return PubFun.ObjectToBigDecimal(a).compareTo(PubFun.ObjectToBigDecimal(b));
    }
    
    /**
     * @Title: moreThanZero
     * @Description: 判断大于0
     * @author 董璨
     * @Date 2019年8月5日 下午8:59:52
     * @param bd
     * @return boolean
     */
    public static boolean moreThanZero(Object bd) {
    	return BigDecimalUtil.eq(bd, BigDecimal.ZERO) == 1;
    }

    /**
     * @Title: moreThanZero
     * @Description: 判断小于0
     * @author 董璨
     * @Date 2019年8月5日 下午8:59:52
     * @param bd
     * @return boolean
     */
    public static boolean lessThanZero(Object bd) {
        return BigDecimalUtil.eq(bd, BigDecimal.ZERO) == -1;
    }
    
    /**
     * @Title: eqZero
     * @Description: 判断等于0
     * @author 董璨
     * @Date 2019年8月5日 下午9:00:04
     * @param bd
     * @return boolean
     */
    public static boolean eqZero(Object bd) {
    	return BigDecimalUtil.eq(bd, BigDecimal.ZERO) == 0;
    }

}
