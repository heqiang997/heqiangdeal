package com.zhimeng.caiwuweb.util;

/**
 * Created by liupengfei on 2018/11/6 14:59
 */
public class CodingUtil {

    public static String coding(String region){
        String l = String.valueOf(Math.random());
//        String s = "";
//        switch (region){
//            case "七星关区气象局":
//                s =("QXGCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "大方县气象局":
//                s =("DFCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "黔西县气象局":
//                s =("QXCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "金沙县气象局":
//                s =("JSCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "织金县气象局":
//                s =("ZJCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "纳雍县气象局":
//                s =("NYCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "威宁县气象局":
//                s =("WNCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "赫章县气象局":
//                s =("HZCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            default:  s =("BJCL"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//        }
        return l.substring(l.length()-6);
    }

    public static String zhibanCoding(String region){
        String l = String.valueOf(Math.random());
//        String s = "";
//        switch (region){
//            case "七星关区气象局":
//                s =("QXGZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "大方县气象局":
//                s =("DFZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "黔西县气象局":
//                s =("QXZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "金沙县气象局":
//                s =("JSZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "织金县气象局":
//                s =("ZJZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "纳雍县气象局":
//                s =("NYZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "威宁县气象局":
//                s =("WNZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            case "赫章县气象局":
//                s =("HZZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//            default:  s =("BJZB"+DateUtil.getYearTwo()+l.substring(l.length()-4));break;
//        }
        return l.substring(l.length()-6);
    }

}
