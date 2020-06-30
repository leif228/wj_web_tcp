package com.wujie.app.business.util.pinyin4j;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 中文转拼音支持多音字
 * @author Administrator
 *
 */
public class pinyin4jUtil {
 
	public static Map<String, List<String>> pinyinMap = new HashMap<String, List<String>>();
 
	public static void main(String[] args) {
 
		String str = "重庆市";
		
		// 别忘了init
		initPinyin();
		
		String py = pinyin4j(str);
		System.out.println("拼音 --> "+py);
		
		String pyFirst = getFirstUp(py);
		System.out.println("首字母 --> "+pyFirst);
		
 
	}
	
	/**
	 * 获取第一个字母并大写
	 * @param py
	 * @return
	 */
	public static String getFirstUp(String py){
		StringBuffer sb = new StringBuffer(py);
		String uc = "";
		if (sb.length() > 1){
			String ss = sb.delete(1, sb.length()).toString();
			uc = String.valueOf(Character.toUpperCase(ss.toCharArray()[0]));
		}
		return uc;
	}
	/**
	 * 将某个字符串的首字母 大写
	 * @param str
	 * @return
	 */
	public static String convertInitialToUpperCase(String str){
		if(str==null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char[] arr = str.toCharArray();
		for(int i=0;i<arr.length;i++){
			char ch = arr[i];
			if(i==0){
				sb.append(String.valueOf(ch).toUpperCase());
			}else{
				sb.append(ch);
			}
		}
		
		return sb.toString();
	}
 
	/**
	 * 汉字转拼音 最大匹配优先
	 * @param chinese
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String pinyin4j(String chinese) {
		// 初始化
		initPinyin();
 
		StringBuffer pinyin = new StringBuffer();
 
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
 
		char[] arr = chinese.toCharArray();
 
		for (int i = 0; i < arr.length; i++) {
 
			char ch = arr[i];
 
			if (ch > 128) { // 非ASCII码
				// 取得当前汉字的所有全拼
				try {
 
					String[] results = PinyinHelper.toHanyuPinyinStringArray(
							ch, defaultFormat);
 
					if (results == null) {	//非中文
 
						return "";
					} else {
 
						int len = results.length;
 
						if (len == 1) { // 不是多音字
 
							pinyin.append(results[0]);
							String py = results[0];		
							if(py.contains("u:")){	//过滤 u:
								py = py.replace("u:", "v");
								System.out.println("filter u:"+py);
							}
							//pinyin.append(convertInitialToUpperCase(py));
							
						}else if(results[0].equals(results[1])){	//非多音字 有多个音，取第一个
							
							pinyin.append(results[0]);
							//pinyin.append(convertInitialToUpperCase(results[0]));
							
						}else { // 多音字
							
							System.out.println("多音字："+ch);
							
							int length = chinese.length();
							
							boolean flag = false;
							
							String s = null;
							
							List<String> keyList =null;
							
							for (int x = 0; x < len; x++) {
								
								String py = results[x];
								
								if(py.contains("u:")){	//过滤 u:
									py = py.replace("u:", "v");
									System.out.println("filter u:"+py);
								}
 
								keyList = pinyinMap.get(py);
								
								if (i + 3 <= length) {	//后向匹配2个汉字  大西洋 
									//System.out.println(11);
									s = chinese.substring(i, i + 3);
									if (keyList != null && (keyList.contains(s))) {
//									if (value != null && value.contains(s)) {
 
										System.out.println("last 2 > " + py);
										pinyin.append(results[x]);
										//pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
								
								if (i + 2 <= length) {	//后向匹配 1个汉字  大西
									//System.out.println(12);
									s = chinese.substring(i, i + 2);
									if (keyList != null && (keyList.contains(s))) {
 
										System.out.println("last 1 > " + py);
										pinyin.append(results[x]);
										//pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
								
								if ((i - 2 >= 0) && (i+1<=length)) {	// 前向匹配2个汉字 龙固大
									//System.out.println(13);
									s = chinese.substring(i - 2, i+1);
									if (keyList != null && (keyList.contains(s))) {
										
										System.out.println("before 2 < " + py);
										pinyin.append(results[x]);
										//pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
								
								if ((i - 1 >= 0) && (i+1<=length)) {	// 前向匹配1个汉字   固大
									//System.out.println(14);
									s = chinese.substring(i - 1, i+1);
									if (keyList != null && (keyList.contains(s))) {
										
										System.out.println("before 1 < " + py);
										pinyin.append(results[x]);
										//pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
								
								if ((i - 1 >= 0) && (i+2<=length)) {	//前向1个，后向1个      固大西
									//System.out.println(15);
									s = chinese.substring(i - 1, i+2);
									if (keyList != null && (keyList.contains(s))) {
										
										System.out.println("before last 1 <> " + py);
										pinyin.append(results[x]);
										//pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
							}
							
							if (!flag) {	//都没有找到，匹配默认的 读音  大 
								//System.out.println(111);
								
								s = String.valueOf(ch);
								
								for (int x = 0; x < len; x++) {
									
									String py = results[x];
									
									if(py.contains("u:")){	//过滤 u:
										py = py.replace("u:", "v");
										System.out.println("filter u:");
									}
									
									keyList = pinyinMap.get(py);
									//System.out.println(py);
									//System.out.println(keyList.contains(s));
									if (keyList != null && (keyList.contains(s))) {
										
										System.out.println("default = " + py);
										pinyin.append(results[x]);	//如果不需要拼音首字母大写 ，直接返回即可
										//pinyin.append(convertInitialToUpperCase(py));//拼音首字母 大写
										break;
									}else{
										pinyin.append(results[x]);	//如果不需要拼音首字母大写 ，直接返回即可
										break;
									}
								}
							}
						}
					}
 
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyin.append(arr[i]);
			}
		}
		return pinyin.toString();
	}
 
	/**
	 * 初始化 所有的多音字词组
	 * 
	 */
	public static void initPinyin() {
		// 读取多音字的全部拼音表;
		InputStream file = PinyinHelper.class.getResourceAsStream("/pinyin4j_duoyinzi.txt");
 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(file,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String s = null;
		try {
			while ((s = br.readLine()) != null) {
 
				if (s != null) {
					String[] arr = s.split("#");
					String pinyin = arr[0];
					String chinese = arr[1];
 
					if(chinese!=null){
						String[] strs = chinese.split(" ");
						List<String> list = Arrays.asList(strs);
						pinyinMap.put(pinyin, list);
					}
				}
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}