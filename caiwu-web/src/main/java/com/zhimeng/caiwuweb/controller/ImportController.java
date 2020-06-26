package com.zhimeng.caiwuweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhimeng.caiwuweb.aop.AccountAccess;
import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.TravelVo;
import com.zhimeng.caiwuweb.entity.Zhiban;
import com.zhimeng.caiwuweb.service.ImportService;
import com.zhimeng.caiwuweb.util.ImportExcelUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

@RestController
public class ImportController {

    @Autowired
    private ImportService importService;


    @ApiOperation("导入值班费")
    @PostMapping("/importZhiban")
   /* @UserAccess
    @AccountAccess*/
    public JsonData excelArea(@RequestBody MultipartFile file){
        //File file = new File("C:\\Users\\Administrator\\Desktop\\值班费信息列表.xls");
        List<Zhiban> byExcel = ImportExcelUtils.getZhibanList(file);

        JsonData jsonData = importService.inputZhiban(byExcel);

        return jsonData;

    }

    @ApiOperation("导入差旅费")
    @PostMapping("/importTravel")
   /* @UserAccess
    @AccountAccess*/
    public JsonData excelCustomer(@RequestBody MultipartFile file){

        List<TravelVo> byExcel = ImportExcelUtils.getTravelVoList(file);

        JsonData jsonData = importService.inputTravel(byExcel);

        return jsonData;

    }



    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("filename");
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = importService.getBankListByExcel(inputStream, file.getOriginalFilename());
        inputStream.close();

        for (int i = 0; i < list.size(); i++) {
            List<Object> lo = list.get(i);
            //TODO 随意发挥
            System.out.println(lo);

        }
        return "上传成功";
    }


    @RequestMapping(value = "/download", method = {RequestMethod.POST,RequestMethod.GET})
    public void downloadFile(HttpServletResponse response,HttpServletRequest request) throws IOException {
        try {
           /* String fileName = "差旅导入模板.xls";
            if (null != request.getParameter("id") && "1".equals(request.getParameter("id"))){
                fileName = "值班导入模板.xls";
            }*/

            /**
             * 不再读取excel,读取配置文件，读取绝对路径文件
             */
            String fileName = "chailv";
            if (null != request.getParameter("id") && "1".equals(request.getParameter("id"))){
                fileName = "zhiban";
            }

            Resource resource = new ClassPathResource("template/config.json");
            String dataStr =  IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
            JSONObject jsonObject= JSONObject.parseObject(dataStr);


            //File file = resource.getFile();
            String filename = jsonObject.get(fileName).toString();
            InputStream inputStream = new FileInputStream(jsonObject.get("path")+jsonObject.get(fileName).toString());
            //强制下载不打开
            response.setContentType("application/force-download");
            OutputStream out = response.getOutputStream();
            //使用URLEncoder来防止文件名乱码或者读取错误
            response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(filename, "UTF-8"));
            int b = 0;
            byte[] buffer = new byte[1000000];
            while (b != -1) {
                b = inputStream.read(buffer);
                if(b!=-1) out.write(buffer, 0, b);
            }
            inputStream.close();
            out.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
