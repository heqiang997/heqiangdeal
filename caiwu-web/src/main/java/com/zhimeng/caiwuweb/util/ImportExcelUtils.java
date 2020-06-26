package com.zhimeng.caiwuweb.util;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Institution;
import com.zhimeng.caiwuweb.entity.Travel;
import com.zhimeng.caiwuweb.entity.TravelVo;
import com.zhimeng.caiwuweb.entity.Zhiban;
import com.zhimeng.caiwuweb.exception.FileInputException;
import com.zhimeng.caiwuweb.service.InstitutionService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读取Excel表格数据
 * 2019-11-19
 * HeWanLi
 */
@Component
public class ImportExcelUtils {

    @Autowired
    InstitutionService institutionService;

    public static ImportExcelUtils utils;

    @PostConstruct
    public void init(){
        utils = this;
        utils.institutionService = this.institutionService;
    }

    /**
     * 读取excel文件
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public static List getExcelData(MultipartFile file){

        List list = new ArrayList<>();

        Workbook work = null;


        String fileName = file.getOriginalFilename();
        String s = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (s.equals(".xls")){
                work = new HSSFWorkbook(file.getInputStream());
            }
            if (s.equals(".xlsx")){
                work = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (work==null) {
            throw new FileInputException("只支持excel导入！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //遍历Excel中所有的sheet
        sheet = work.getSheetAt(0);

        //遍历当前sheet中的所有行
        for (int j =1; j <= sheet.getLastRowNum(); j++) {
            row = sheet.getRow(j);
            if (row == null ) {
                continue;
            }
            List<Object> li = new ArrayList<>();
            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                cell = row.getCell(y);
                li.add(cell);
            }
            list.add(li);
        }

       /* for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
        }*/
        return list;
    }


    /**
     * 组装获取出差与详情的信息列表
     * @param file
     * @return
     */
    public static List<TravelVo> getTravelVoList(MultipartFile file){
        List<TravelVo> list = new ArrayList<>();

        Workbook work = null;


        String fileName = file.getOriginalFilename();
        String s = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (s.equals(".xls")){
                work = new HSSFWorkbook(file.getInputStream());
            }
            if (s.equals(".xlsx")){
                work = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (work==null) {
            throw new FileInputException("只支持excel导入！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //遍历Excel中所有的sheet
        for (int i = 0; i < 1; i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j =1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null ) {
                    continue;
                }
                TravelVo travelVo = new TravelVo();
                List<Travel> travelList = new ArrayList<>();

                //把每个单元格的值付给对象的对应属性
                /**
                 * 第一列表示姓名列表
                 * 第二列表示出差时间
                 * 第三列表示出差类型
                 * 第四列表示是否食宿自理
                 * 第五列表示交通工具
                 * 第六列表示辖区类型
                 * 第七列表示出差单位
                 * 第八列表示出差地点
                 * 第九列表示出差事由
                 */
                if (row.getCell(0)!=null){
                    String nameStr = String.valueOf(row.getCell(0));
                    List<String> names = Arrays.asList(nameStr.split(","));
                    travelVo.setNames(names);
                }
                if (row.getCell(1)!=null && !"".equals(row.getCell(1))){
                    // 表示时间,可以把所有数据添加到travel里面
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    List<String> dateList = Arrays.asList(String.valueOf(row.getCell(1)).split(","));
                    try {
                        for (int x = 0; x < dateList.size();x++){
                            Travel travel = new Travel();
                            travel.setTravelTime(format.parse(dateList.get(x)));

                            if (row.getCell(2)!=null){
                                String travelStatus = String.valueOf(row.getCell(2));
                                // 这个要根据传入的文字分别为：培训：0   会议：1    其他：2 应该是数据库写错了小程序获取是这个样子的
                                if ("培训".equals(travelStatus)){
                                    travel.setTravelStatus(0);
                                }else if ("会议".equals(travelStatus)){
                                    travel.setTravelStatus(1);
                                }else if ("其他".equals(travelStatus)){
                                    travel.setTravelStatus(2);
                                }
                            }

                            if (row.getCell(4)!=null){
                                String vehicleId = String.valueOf(row.getCell(4));
                                if ("单位派车".equals(vehicleId)){
                                    travel.setVehicleId(1);
                                }else if ("非单位派车".equals(vehicleId)){
                                    travel.setVehicleId(2);
                                }
                            }
                            if (row.getCell(8)!=null){
                                travel.setReason(String.valueOf(row.getCell(8)));
                            }
                            travel.setFoodMoney(0.0);
                            travel.setVehicleMoney(0.0);
                            travel.setTrips(0);
                            travelList.add(travel);
                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    travelVo.setList(travelList);
                }

                if (row.getCell(3)!=null){
                    String foodType = String.valueOf(row.getCell(3));
                    travelVo.setFoodtype("是".equals(foodType)?1:0);
                }

                if (row.getCell(5)!=null){
                    String travelStatus = String.valueOf(row.getCell(5));
                    // 这个要根据传入的文字分别为：辖区外：0   辖区内：1    特殊地区：2
                    if ("辖区外".equals(travelStatus)){
                        travelVo.setTravelStatus(0);
                    }else if ("辖区内".equals(travelStatus)){
                        travelVo.setTravelStatus(1);
                    }else if ("特殊地区".equals(travelStatus)){
                        travelVo.setTravelStatus(2);
                    }
                }

                if (row.getCell(6)!=null){
                    String area = String.valueOf(row.getCell(6));
                    //根据地区匹配ID
                    travelVo.setInstitutionid(getInstitution(area));
                }

                if (row.getCell(7)!=null){
                    String travelArea = String.valueOf(row.getCell(7));
                    travelVo.setTravelArea(travelArea);
                }

                //遍历所有的列(把每一行的内容存放到对象中)
                list.add(travelVo);
            }
        }
        return list;
    }

    private static Integer getInstitution(String area){
        Map<String,Integer> areaMap = new HashMap();
        JsonData jsonData = utils.institutionService.selectAll();
        List<Institution> list = ( List<Institution>)jsonData.getData();
        for(int i = 0; i< list.size();i++){
            Institution institution = list.get(i);
            areaMap.put(institution.getInstitutionName(),institution.getId());
        }
        return areaMap.get(area);
    }




    /**
     * 获取出差数据
     * @param
     * @return
     */
    public static List<Travel> getTravelList(MultipartFile file){
        List<Travel> list = new ArrayList<>();

        Workbook work = null;


        String fileName = file.getOriginalFilename();
        String s = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (s.equals(".xls")){
                work = new HSSFWorkbook(file.getInputStream());
            }
            if (s.equals(".xlsx")){
                work = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (work==null) {
            throw new FileInputException("只支持excel导入！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //遍历Excel中所有的sheet
        for (int i = 0; i < 1; i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j =1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null ) {
                    continue;
                }
                Travel travel = new Travel();
                //把每个单元格的值付给对象的对应属性
                if (row.getCell(0)!=null){
                    travel.setAreaId((int)row.getCell(0).getNumericCellValue());
                }
                if (row.getCell(1)!=null){
                    travel.setCusName(String.valueOf(row.getCell(1)));
                }
                if (row.getCell(2)!=null){
                    travel.setType((int)row.getCell(2).getNumericCellValue());
                }
                //遍历所有的列(把每一行的内容存放到对象中)
                list.add(travel);
            }
        }
        return list;
    }

    /**
     * 获取值班数据
     * @param
     * @return
     */
    public static List<Zhiban> getZhibanList(MultipartFile file){
        List<Zhiban> list = new ArrayList<>();

        Workbook work = null;


        String fileName = file.getOriginalFilename();
        String s = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (s.equals(".xls")){
                work = new HSSFWorkbook(file.getInputStream());
            }
            if (s.equals(".xlsx")){
                work = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (work==null) {
            throw new FileInputException("只支持excel导入！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //遍历Excel中所有的sheet
        for (int i = 0; i < 1; i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j =1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null ) {
                    continue;
                }
                Zhiban zhiban = new Zhiban();
                //把每个单元格的值付给对象的对应属性
                if (row.getCell(0)!=null){
                    zhiban.setCusName(String.valueOf(row.getCell(0)));
                }
                if (row.getCell(1)!=null && !"".equals(row.getCell(1))){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    String dateStr = "";
                    List<String> dateList = Arrays.asList(String.valueOf(row.getCell(1)).split(","));
                    try {
                        for (int k = 0; k < dateList.size(); k++) {
                            format = new SimpleDateFormat("yyyy/MM/dd");
                            Date date = format.parse(dateList.get(k));
                            format = new SimpleDateFormat("yyyy-MM-dd");
                            if (k == 0){
                                dateStr = format.format(date);
                            }else {
                                String newDateStr = format.format(date);
                                dateStr+=","+newDateStr;
                            }

                        }
                    }catch (ParseException e) {
                        e.printStackTrace();
                    }

                    zhiban.setTimelist(dateStr);
                   /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        zhiban.setZhibanTime(format.parse(String.valueOf(row.getCell(1))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/

                }
                if (row.getCell(2)!=null){
                    zhiban.setRemark(String.valueOf(row.getCell(2)));
                }

                //遍历所有的列(把每一行的内容存放到对象中)
                list.add(zhiban);
            }
        }
        return list;
    }


    public static String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (c >= 0 && c <= 255){sb.append(c);}
            else{
                byte[] b;
                try { b = Character.toString(c).getBytes("utf-8");}
                catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

}