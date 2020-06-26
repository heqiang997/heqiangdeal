package com.zhimeng.caiwuweb.service.serviceimp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhimeng.caiwuweb.dao.AccountantMapper;
import com.zhimeng.caiwuweb.dao.CustomerMapper;
import com.zhimeng.caiwuweb.dao.ZhibanMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Accountant;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.entity.Zhiban;
import com.zhimeng.caiwuweb.service.WebService;
import com.zhimeng.caiwuweb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupengfei on 2018/10/31 11:26
 */
@Service
public class WebServiceImp implements WebService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    AccountantMapper accountantMapper;
    @Autowired
    ZhibanMapper zhibanMapper;

    @Override
    public JsonData selectAccountStatus(HttpServletRequest request, Integer status,  Integer paixu) {


        String header = request.getHeader("Authentication");
        String s = redisUtil.get(header);
        Customer customer = customerMapper.selectByName(s);

        if (status==null){
            status=0;
        }
        Accountant accountant= new Accountant();
        accountant.setStatus(status);
        accountant.setTravelRegion(customer.getCusRegion());
        accountant.setType(paixu);
//        PageHelper.startPage(pageNum,pageSize);
        List<Accountant> accountants = accountantMapper.selectAll(accountant);
//       PageInfo<Accountant> pageInfo = new PageInfo<>(accountants);

        return new JsonData(200,"查询成功！",accountants);
    }

    @Override
    public JsonData selectZhibanStatus(HttpServletRequest request,Integer status, Integer paixu) {

        String header = request.getHeader("Authentication");
        String s = redisUtil.get(header);
        Customer customer = customerMapper.selectByName(s);

        if (status==null){
            status=0;
        }

        Zhiban zhiban = new Zhiban();
        zhiban.setStatus(status);
        zhiban.setZhibanRegion(customer.getCusRegion());
        zhiban.setPaixu(paixu);
//        PageHelper.startPage(pageNum,pageSize);
        List<Zhiban> zhibans = zhibanMapper.selectAll(zhiban);
//        PageInfo<Zhiban> pageInfo = new PageInfo<>(zhibans);

        return new JsonData(200,"查询成功！",zhibans);

    }
}
