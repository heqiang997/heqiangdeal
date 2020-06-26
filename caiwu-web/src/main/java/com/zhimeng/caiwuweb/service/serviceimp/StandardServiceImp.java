package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.StandardMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Standard;
import com.zhimeng.caiwuweb.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liupengfei on 2018/11/4 21:11
 */
@Service
public class StandardServiceImp implements StandardService {

    @Autowired
    StandardMapper standardMapper;

    @Override
    public JsonData updateStandard(Standard standard) {

        if (standard==null){
            return new JsonData(204,"参数不能为空");
        }

        int i = standardMapper.updateByPrimaryKey(standard);

        if (i!=0){
            return new JsonData(200,"修改成功！");
        }

        return new JsonData(201,"修改失败！");
    }

    @Override
    public JsonData selectById(Integer id) {

        Standard standard = standardMapper.selectByPrimaryKey(id);

        return new JsonData(200,"查询成功！",standard);
    }
}
