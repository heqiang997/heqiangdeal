package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.InstitutionMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Institution;
import com.zhimeng.caiwuweb.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liupengfei on 2018/11/8 18:43
 */
@Service
public class InstitutionServiceImp implements InstitutionService {

    @Autowired
    InstitutionMapper institutionMapper;

    @Override
    public JsonData selectAll() {

        List<Institution> institutions = institutionMapper.selectAll();

        if (institutions==null ||institutions.size()==0){
            return new JsonData(204,"没有单位数据");
        }

        return new JsonData(200,"查询成功！",institutions);
    }
}
