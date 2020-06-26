package com.zhimeng.caiwuweb.util;

import com.alibaba.fastjson.JSONObject;
import com.zhimeng.caiwuweb.exception.DateUtilException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liupengfei on 2018/11/2 10:54
 * 获取国家法定节假日2，周末1，工作日0
 */
public class DateUtil {

    public static  String getData(String date){
        URL url = null;
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try{
            // 这个三方接口无法使用了,需要替换一个
            //url = new URL("http://api.goseek.cn/Tools/holiday?date="+date);

            /**
             * 新的地址：http://timor.tech/api/holiday/info/
             */
            // 转换日期
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date now = format.parse(date);
            //System.out.println(format.parse(s).getTime());
            format = new SimpleDateFormat("yyyy-MM-dd");
            //date = format.format(now);

            /**
             * 再次修改新的地址：2020-05-08
             * http://39.98.195.112/api/holiday/info/
             */

            /**
             * 再次修改接口地址信息http://vip.bitefu.net/jiari/?d=20200509&apikey=bjcwsh&info=2
             */


            url = new URL("http://vip.bitefu.net/jiari/?d="+date+"&apikey=bjcwsh&info=2");
            in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8") );
            String str = null;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ex) {
            throw new DateUtilException("第三方节假日接口出错！");
        } finally{
            try{
                if(in!=null) {
                    in.close();
                }
            }catch(IOException ex) {
            }
        }
        String  data =sb.toString();
        JSONObject jsonObject = getjson(data);
        if (Integer.parseInt(jsonObject.get("status").toString()) == 0){
            throw new DateUtilException("第三方节假日接口出错！");
        }
        System.out.println(jsonObject);
        String type = jsonObject.get("data").toString();
        if("1".equals(type)){
            type = "0";
        }else if("4".equals(type)){
            type = "1";
        }
       /* jsonObject = getjson(jsonObject.get("type").toString());
        System.out.println(jsonObject);
        String type = "";
        if ("1".equals( jsonObject.get("type").toString())){
            type = "3";
        }else if ("2".equals( jsonObject.get("type").toString())){
            type ="1";
        }else if("3".equals( jsonObject.get("type").toString())){
            type = "2";
        }*/
        System.out.println(type);
        return  type;
        //return jsonObject.get("type").toString();
    }



    //json串转化为json对象
    public static JSONObject getjson(String data) {
        JSONObject json = JSONObject.parseObject(data);
        return json;
    }

    public static String getYearTwo(){

        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.YEAR);
        return String.valueOf(i).substring(String.valueOf(i).length()-2);
    }

//    public static void main(String[] args) {
//        System.out.println(getYearTwo());
//    }

    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2017-10-15 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String s = "20200510";
        getData(s);
        //System.out.println(format.parse(s));
        //Date date = format.parse(s);
        //System.out.println(format.parse(s).getTime());
        //format = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println(format.format(date));
    }


}
