package com.zhimeng.caiwuweb.util;

import com.zhimeng.caiwuweb.entity.Area;
import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.exception.FileInputException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liupengfei on 2018/11/7 11:08
 */
public class ExcelUtils {

    /**
     * 读取excel文件
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public static List<City> getAreaExcel(MultipartFile file){

        List<City> list = new ArrayList<>();

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
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
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
                City ci = new City();
                //把每个单元格的值付给对象的对应属性
                if (row.getCell(0)!=null){
                    ci.setPid((int)row.getCell(0).getNumericCellValue());
                }
                if (row.getCell(1)!=null){
                    ci.setCityname(String.valueOf(row.getCell(1)));
                }
                if (row.getCell(2)!=null){
                    ci.setType((int)row.getCell(2).getNumericCellValue());
                }
                //遍历所有的列(把每一行的内容存放到对象中)
                list.add(ci);
            }
        }
        return list;
    }


    public static Set<Customer> getCustomerExcel(MultipartFile file){

        Set<Customer> list = new HashSet<>();

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
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
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
                Customer customer = new Customer();
                //把每个单元格的值付给对象的对应属性
                customer.setCusName(String.valueOf(row.getCell(0)));
                Cell cell1 = row.getCell(1);
                cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
                customer.setCusPwd(MD5Utils.convertMD5(cell1.toString()));
                customer.setCusRegion(String.valueOf(row.getCell(2)));
                customer.setCusAuthority((int) (row.getCell(3).getNumericCellValue()));

                Cell cell2 = row.getCell(4);
                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                customer.setCusZhanghao(cell2.toString());

                Cell cell3 = row.getCell(5);
                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                customer.setCusTel(cell3.toString());

                System.out.println(customer.toString());
                //遍历所有的列(把每一行的内容存放到对象中)
                list.add(customer);
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