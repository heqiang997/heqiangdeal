package com.zhimeng.caiwuweb.service;


import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Travel;
import com.zhimeng.caiwuweb.entity.TravelVo;
import com.zhimeng.caiwuweb.entity.Zhiban;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.List;

/**
 * 导入数据的接口 批量导入用户报销数据
 * 2019-11-19
 */
public interface ImportService {

    /**
     * 处理上传的文件
     * @param in
     * @param fileName
     * @return
     */
    public List getBankListByExcel(InputStream in, String fileName) throws Exception;

    /**
     *  判断文件格式
     * @param inStr
     * @param fileName
     * @return
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception;


    /**
     * 导入差旅费列表
     * @param list
     * @return
     */
    JsonData inputTravel(List<TravelVo> list);

    /**
     * 导入值班列表
     * @param list
     * @return
     */
    JsonData inputZhiban(List<Zhiban> list);
}
