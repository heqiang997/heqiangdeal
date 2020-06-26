package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.TravelMapper;
import com.zhimeng.caiwuweb.dao.ZhibanMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Travel;
import com.zhimeng.caiwuweb.entity.TravelVo;
import com.zhimeng.caiwuweb.entity.Zhiban;
import com.zhimeng.caiwuweb.service.ImportService;
import com.zhimeng.caiwuweb.service.TravelService;
import com.zhimeng.caiwuweb.service.ZhibanService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现批量导入值班费列表和出差申请列表
 * 2019-11-19
 * HeWanLi
 */
@Service
public class ImportServiceImp implements ImportService {

    @Autowired
    TravelMapper travelMapper;
    @Autowired
    ZhibanMapper zhibanMapper;

    @Autowired
    TravelService travelService;

    @Autowired
    ZhibanService zhibanService;

    @Override
    public List getBankListByExcel(InputStream in, String fileName) throws Exception {
        List list = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                list.add(li);
            }
        }
        work.close();
        return list;
    }

    @Override
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("请上传excel文件！");
        }
        return workbook;
    }

    @Override
    public JsonData inputTravel(List<TravelVo> list) {
        for (TravelVo travelVo : list) {
            travelService.insertTravel(travelVo.getTravelStatus(),travelVo.getTravelArea(),travelVo.getInstitutionid(),travelVo.getNames(),travelVo.getList(),travelVo.getFoodtype());
        }

        return new JsonData(200,"导入成功！");
    }

    @Override
    public JsonData inputZhiban(List<Zhiban> list) {
        zhibanService.insertZhiban(list);

        return new JsonData(200,"导入成功！");
    }
}
