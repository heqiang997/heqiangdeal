package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.AdminAccess;
import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dao.AreaMapper;
import com.zhimeng.caiwuweb.dao.CustomerMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Area;
import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.exception.FileInputException;
import com.zhimeng.caiwuweb.service.CustomerService;
import com.zhimeng.caiwuweb.service.ExcelService;
import com.zhimeng.caiwuweb.util.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * Created by liupengfei on 2018/11/7 10:26
 */
@RestController
@Api("Excel导入")
public class ExcelController {


    @Autowired
    ExcelService excelService;

    /**@
     * 	导出demo
     */
    @GetMapping("/excelCity")
    @ApiOperation("导入地区模板")
    public void excelCity(HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("地区模板");
        String fileName = "地区信息表"  + ".xls";	//新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "pid(上级地区id)", "城市名称", "地区类型(1省2市3县区4乡镇)"};			//headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);	 //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + ExcelUtils.toUtf8String(fileName));
        response.setCharacterEncoding("utf-8");
        //设置浏览器响应头对应的Content-disposition

        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/ExcelCustomer")
    @ApiOperation("导入用户模板")
    public void ExcelCustomer(HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户信息模板");
        String fileName = "用户信息表"  + ".xls";	//新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "用户姓名", "用户密码(6位以上)", "所属单位","所属角色(1用户，2会计，3超管)","用户账号(推荐手机号)","用户手机号"};			//headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);	 //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + ExcelUtils.toUtf8String(fileName));
        response.setCharacterEncoding("utf-8");
        //设置浏览器响应头对应的Content-disposition

        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @ApiOperation("导入地区")
    @PostMapping("/excelArea")
    @UserAccess
    @AdminAccess
    public JsonData excelArea(@RequestBody MultipartFile file){

        List<City> byExcel = ExcelUtils.getAreaExcel(file);

        JsonData jsonData = excelService.inputArea(byExcel);

        return jsonData;

    }

    @ApiOperation("导入用户")
    @PostMapping("/excelCustomer")
    @UserAccess
    @AdminAccess
    public JsonData excelCustomer(@RequestBody MultipartFile file){

        Set<Customer> byExcel = ExcelUtils.getCustomerExcel(file);

        JsonData jsonData =excelService.inputCustomer(byExcel);

        return jsonData;

    }

}
