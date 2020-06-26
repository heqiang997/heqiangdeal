package com.zhimeng.caiwuweb.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by liupengfei on 2018/12/5 10:21
 */
public class StringUtil {
    //private static Log logger = LogFactory.getLog(StringUtil.class);
// 国标码和区位码转换常量
    int GB_SP_DIFF = 160;
    //存放国标一级汉字不同读音的起始区位码
    int[] secPosValueList = {
            1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787,
            3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
            4390, 4558, 4684, 4925, 5249, 5600};
    //存放国标一级汉字不同读音的起始区位码对应读音
    char[] firstLetter = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'w', 'x', 'y', 'z'};
    char convert(String ch) {
        byte[] bytes=new byte[2];
        try {
            bytes = ch.getBytes("GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }

    public static String format(String s){
        String str=s.replaceAll(
            "[`qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
    return str; }

    public static String StringToFirstChar(String str){

        String format = format(str);

        StringUtil stringUtil = new StringUtil();

        char[] cc = format.toCharArray();

        String firstStr="";

        for (char c : cc) {
           firstStr += String.valueOf(stringUtil.convert(String.valueOf(c)));
        }

        return firstStr;

    }


//    public static void main(String[] args) {
//
//        String s = StringUtil.StringToFirstChar("犇");
//
//        System.out.println(s);
//    }

}