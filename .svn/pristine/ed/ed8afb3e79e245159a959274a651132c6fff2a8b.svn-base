package com.modbus.mqtt;

public class StringUtils {
	
	/**
	 * 判断对象字符串是否为空
	 * null、""、"null"皆判断为空
	 * @param str
	 * @return 为空场合：true、非空场合：false
	 */
	public static boolean isNull(String str) {
		return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
	}
	
	public static void appendData(StringBuffer target, String key, String value, boolean... isLast) {
		if (value != null && !value.equals("null")) 
			value = value.trim().replace(" ","");
		else
			return;	
		if (key != null) {
			target.append(key.trim() + "=");
		}
		target.append(value);
		if (isLast.length == 0 || !isLast[0]) {
			target.append(",");
		}
	}
	
	public static void appendData(StringBuffer target, String key, char value, boolean... isLast) {
		appendData(target, key, String.valueOf(value), isLast);
	}
	
	/**
	 * 字符串转化成为16进制字符串
	 * @param s
	 * @return
	 */
	public static String stringToHex(String s) {
	    String str = "";
	    for (int i = 0; i < s.length(); i++) {
	        int ch = (int) s.charAt(i);
	        String s4 = Integer.toHexString(ch);
	        str = str + s4;
	    }
	    return str;
	}
	
	/**
	 * 16进制转换成为string类型字符串
	 * @param s
	 * @return
	 */
	public static String hexToString(String s) {
	    if (s == null || s.equals("")) {
	        return null;
	    }
	    s = s.replace(" ", "");
	    byte[] baKeyword = new byte[s.length() / 2];
	    for (int i = 0; i < baKeyword.length; i++) {
	        try {
	            baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    try {
	        s = new String(baKeyword, "UTF-8");
	        new String();
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	    return s;
	}
}
