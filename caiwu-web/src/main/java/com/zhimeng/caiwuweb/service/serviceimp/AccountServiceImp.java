package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.AccountantMapper;
import com.zhimeng.caiwuweb.dao.ZhibanMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Accountant;
import com.zhimeng.caiwuweb.entity.Zhiban;
import com.zhimeng.caiwuweb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liupengfei on 2018/10/25 11:00
 */
@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    AccountantMapper accountantMapper;
    @Autowired
    ZhibanMapper zhibanMapper;
    @Override
    public JsonData updateStatus(String coding) {
        Accountant accountant = new Accountant();
        accountant.setStatus(1);
        accountant.setSelectId(coding);
        List<Accountant> list = accountantMapper.selectByCoding(accountant);
        if (list == null || list.size() == 0) {
            Zhiban zhiban = new Zhiban();
            zhiban.setStatus(1);
            zhiban.setZhibanId(coding);
            int i = zhibanMapper.updateByPrimaryKeySelective(zhiban);
            if (i == 0) {
                return new JsonData(204, "请输入正确的查询码！");
            }
        } else {
            int i = accountantMapper.updateByPrimaryKeySelective(accountant);
            if (i == 0) {
                return new JsonData(204, "请输入正确的查询码！");
            }
        }
        return new JsonData(200, "审核完成");
    }
}
