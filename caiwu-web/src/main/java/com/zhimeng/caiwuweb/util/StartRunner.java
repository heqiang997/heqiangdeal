package com.zhimeng.caiwuweb.util;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.Divisions;
import com.zhimeng.caiwuweb.entity.Institution;
import com.zhimeng.caiwuweb.service.InstitutionService;
import com.zhimeng.caiwuweb.service.SelectService;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

@Component
public class StartRunner implements CommandLineRunner {

    private static final Log logger = LogFactory.getLog(StartRunner.class);
  /*  @Value("classpath:template/config.json")
    private Resource areaRes;*/
  @Autowired
  InstitutionService institutionService;

  @Autowired
  SelectService selectService;


    @Override
    public void run(String... args) {
        logger.info("服务器启动后，我就会被执行");

        setTemplate(); // 启动服务器数据填充模板

    }

    public void removeRow(HSSFSheet sheet, int rowIndex) {
        int lastRowNum=sheet.getLastRowNum();
        if(rowIndex>=0&&rowIndex<lastRowNum)
            sheet.shiftRows(rowIndex+1,lastRowNum,-1);//将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行
        if(rowIndex==lastRowNum){
            HSSFRow removingRow=sheet.getRow(rowIndex);
            if(removingRow!=null)
                sheet.removeRow(removingRow);
        }
    }

    public  void setTemplate(){
        FileInputStream fs= null;
        try {
            Resource resource = new ClassPathResource("template/config.json");
            String dataStr =  IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
            JSONObject jsonObject= JSONObject.parseObject(dataStr);
            //System.out.println(jsonObject.get("zhiban"));

            /**
             * 往excel中写入数据
             */
            fs = new FileInputStream(jsonObject.get("path")+jsonObject.get("chailv").toString());
            POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
            HSSFWorkbook wb=new HSSFWorkbook(ps);
            HSSFSheet sheet=wb.getSheetAt(1);  //获取到工作表，因为一个excel可能有多个工作表
            HSSFRow row=sheet.getRow(0);  //获取第一行（excel中的行默认从0开始，所以这就是为什么，一个excel必须有字段列头），即，字段列头，便于赋值
            //removeRow(sheet,0);
            for (int i = 0; i < sheet.getLastRowNum();i++){
                HSSFRow row1 = sheet.getRow(i+1);
                sheet.removeRow(row1);
            }
            removeRow(sheet,1);

            System.out.println(sheet.getLastRowNum()+" "+row.getLastCellNum());  //分别得到最后一行的行号，和一条记录的最后一个单元格

            FileOutputStream out=new FileOutputStream(jsonObject.get("path")+jsonObject.get("chailv").toString());

            // 获取出差单位
            JsonData jsonData = institutionService.selectAll();
            List<Institution> list = ( List<Institution>)jsonData.getData();

            // 查询所有的省
            jsonData = selectService.selectCity(0,0);

            List<City> provinces = (List<City>) jsonData.getData();
            int num = 0;
            for (int i = 0; i < provinces.size();i++){
                // 根据省查询市
                jsonData = selectService.selectDivisionsPid(provinces.get(i).getId());
                List<Divisions> cities = (List<Divisions>) jsonData.getData();

                for (int j = 0 ; j < cities.size(); j++){
                    row=sheet.createRow((short)(sheet.getLastRowNum()+1));
                    if (num < list.size()){
                        row.createCell(0).setCellValue(list.get(num).getInstitutionName());
                    }
                    row.createCell(1).setCellValue(cities.get(j).getProvince());
                    row.createCell(2).setCellValue(cities.get(j).getCity());
                    row.createCell(3).setCellValue(cities.get(j).getCounty());
                    row.createCell(4).setCellValue(cities.get(j).getTown());
                    num++;
                }
            }
            out.flush();
            wb.write(out);
            wb.close();
            out.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

