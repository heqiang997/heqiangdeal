package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.*;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.*;
import com.zhimeng.caiwuweb.service.CustomerService;
import com.zhimeng.caiwuweb.util.MD5Utils;
import com.zhimeng.caiwuweb.util.RedisUtil;
import com.zhimeng.caiwuweb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liupengfei on 2018/10/16 15:04
 */
@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    TravelMapper travelMapper;
    @Autowired
    ZhibanMapper zhibanMapper;
    @Autowired
    AccountantMapper accountantMapper;
    @Autowired
    CityMapper cityMapper;
    @Autowired
    InstitutionMapper institutionMapper;

    /**
     * 新增用户
     * @param customer 用户实体
     * @return
     */
    @Override
    public JsonData addCustomer(Customer customer) {
//      判定用户姓名，手机号和所在地区不能为空，手机号为登录账号
        if (customer.getCusName()==null || customer.getCusName().trim().equals("")){
            return new JsonData(201,"用户名不能为空！");
        }else if (customer.getCusTel()==null || customer.getCusTel().trim().equals("")){
            return new JsonData(202,"手机号不能为空！");
        }else if (customer.getCusRegion()==null || customer.getCusRegion().trim().equals("")){
            return new JsonData(203,"所在地区不能为空！");
        }else if (customer.getCusAuthority()==null){
            return new JsonData(204,"请选择用户所属角色！");
        }
        Customer customer1 = customerMapper.selectByTel(customer.getCusTel());
        Customer customer2 = customerMapper.selectByName(customer.getCusName());
        if (customer1!=null){
            return new JsonData(205,"该手机账号已经存在！");
        }
        if (customer2!=null){
            return new JsonData(205,"该昵称已被占用！");
        }
        if (customer.getCusPwd()==null){
            return new JsonData(204,"密码不能为空");
        }
        customer.setCusPwd(MD5Utils.convertMD5(customer.getCusPwd()));

//      设置账号
        if (customer.getCusZhanghao()==null ||customer.getCusZhanghao().trim().equals("")){
            return new JsonData(204,"账号不能为空");
        }

        customer.setCusFirstName(StringUtil.StringToFirstChar(customer.getCusName())+customer.getCusName());

        int i = customerMapper.insertSelective(customer);
//          新增新的单位
        Institution institution = institutionMapper.selectByName(customer.getCusRegion());
        if (institution==null){
            Institution ins = new Institution();
            ins.setInstitutionName(customer.getCusRegion());
            institutionMapper.insertSelective(ins);
        }
        if (i!=0){
            // 同时更新出差模板
            return new JsonData(200,"新增用户成功！");
        }
        return new JsonData(204,"新增用户失败！");
    }

    @Override
    public JsonData updateCustomer(String password,String newpassword,HttpServletRequest request) {

        String authentication = request.getHeader("Authentication");

        String s = redisUtil.get(authentication);

        Customer customer1 = customerMapper.selectByName(s);

        if (MD5Utils.convertMD5(password).equals(customer1.getCusPwd())){
//         修改密码
            customer1.setCusPwd(MD5Utils.convertMD5(newpassword));

            customerMapper.updateByPrimaryKeySelective(customer1);
        }else {
            return new JsonData(204,"旧密码不正确！");
        }

        return new JsonData(200,"修改成功!");
    }

    @Override
    public JsonData updateAuth(Customer customer) {

        Customer customer1 = customerMapper.selectByPrimaryKey(customer.getCusId());

        Customer customer2 = customerMapper.selectByName(customer.getCusName());

        Customer customer3 = customerMapper.selectByZhanghao(customer.getCusZhanghao());

        if (customer3!=null && !customer.getCusZhanghao().equals(customer1.getCusZhanghao())){
            return new JsonData(205,"该账号已被占用");
        }
        if (customer2!=null){
            if (!customer2.getCusName().equals(customer1.getCusName()))
                return new JsonData(204,"该昵称已被占用！");
        }
//        修改用户名
        if (customer.getCusName()!=null && !customer.getCusName().equals(customer1.getCusName())){
            List<Travel> list = travelMapper.selectByName(customer1.getCusName());
//         更新查询表
            if (list!=null && list.size()!=0) {
                for (Travel travel : list) {
                    travel.setCusName(customer.getCusName());
                    travelMapper.updateByPrimaryKeySelective(travel);
                }
            }
//            更新值班表
            List<Zhiban> zhibans = zhibanMapper.selectByName(customer1.getCusName());

            for (Zhiban zhiban : zhibans) {
                zhiban.setCusName(customer.getCusName());
                zhibanMapper.updateByPrimaryKeySelective(zhiban);
            }
//            更新统计表
            List<Accountant> accountants = accountantMapper.selectName(customer1.getCusName());

            for (Accountant accountant : accountants) {
                accountant.setCusName(customer.getCusName());
                accountantMapper.updateByPrimaryKeySelective(accountant);
            }

            customer.setCusFirstName(StringUtil.StringToFirstChar(customer.getCusName())+customer.getCusName());

        }
//         修改角色
        if (customer.getCusAuthority()==null){
            return new JsonData(204,"请选择该用户的角色");
        }
        //        修改密码
        customer.setCusPwd(MD5Utils.convertMD5(customer.getCusPwd()));
        customerMapper.updateByPrimaryKeySelective(customer);
        return new JsonData(200,"修改成功！");
    }

    @Override
    public JsonData updateCity(City city) {

        if (city==null){
            return new JsonData(301,"信息不能为空！");
        }
        int i = cityMapper.updateByPrimaryKey(city);
        if (i!=0){
            return new JsonData(200,"修改成功！");
        }
        return new JsonData(204,"修改失败！");
    }


    /**
     * 初始化用户密码
     * @param customer 用户实体
     * @return
     */
    @Override
    public JsonData initPwd(Customer customer) {

        if (customer.getCusZhanghao()==null || customer.getCusZhanghao()==""){
            return new JsonData(205,"账号不能为空！");
        }
        Customer customer1 = customerMapper.selectByZhanghao(customer.getCusZhanghao());

        customer1.setCusPwd(MD5Utils.convertMD5("123456"));

        customerMapper.updateByPrimaryKeySelective(customer1);

        return new JsonData(200,"初始化密码成功！");
    }

    @Override
    public JsonData cusIsExist(String name) {
        Customer customer = customerMapper.selectByName(name);
        if (customer==null){
            return  new JsonData(402,"该用户不存在！");
        }
        return new JsonData(200,"查询成功！",customer);
    }

}
