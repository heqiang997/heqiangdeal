package com.zhimeng.caiwuweb.service.serviceimp;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhimeng.caiwuweb.dao.CustomerMapper;
import com.zhimeng.caiwuweb.dao.StandardMapper;
import com.zhimeng.caiwuweb.dao.TravelMapper;
import com.zhimeng.caiwuweb.dao.ZhibanMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.*;
import com.zhimeng.caiwuweb.service.ZhibanService;
import com.zhimeng.caiwuweb.util.CodingUtil;
import com.zhimeng.caiwuweb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ZhibanServiceImp implements ZhibanService {
    /**
     * 值班信息录入
     */
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    TravelMapper travelMapper;
    @Autowired
    ZhibanMapper zhibanMapper;

    @Autowired
    StandardMapper standardMapper;



    @Override
    @Transactional
    public JsonData insertZhiban(List<Zhiban> zhiban) {
       // 当前时间
        Date now = new Date();
        // 重复数据值班和出差
        List errorList_Z = Lists.newArrayList();
        List errorList_T = Lists.newArrayList();
        //      格式化日期只有年月日
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Standard standard = standardMapper.selectByPrimaryKey(1);
        Map<String,Double> map = Maps.newHashMap();
        // 正常工作日对应结果为 0, 法定节假日对应结果为 1, 节假日调休补班对应的结果为 2，休息日对应结果为 3
        map.put("0",standard.getZhibanDay());
        map.put("2",standard.getZhibanDay());
        map.put("3",standard.getZhibanWeekend());
        map.put("1",standard.getZhibanFestival());
        // 查询码
        String code = CodingUtil.zhibanCoding(null);
        zhiban.forEach(entity -> {
            if (entity.getTimelist() == null || entity.getTimelist() == "") {
                errorList_Z.add(entity);
                return;
            }
            System.out.println(entity.getTimelist());
            List<String> dateList = Arrays.asList(entity.getTimelist().split(","));
            Customer customer = customerMapper.selectByName(entity.getCusName());
            entity.setRecordTime(now);
            entity.setZhibanRegion(customer.getCusRegion());
            entity.setZhibanId(code);

            dateList.forEach(date -> {
                try {
                    entity.setZhibanTime(format.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 值班是否有重复
                if (zhibanMapper.selectByNameTime(entity) != null) {
                    errorList_Z.add(entity);
                    return;
                }
                // 出差是否重复
                Travel travel = new Travel();
                travel.setCusName(entity.getCusName());
                travel.setTravelTime(entity.getZhibanTime());
                Travel travel1 = travelMapper.selectByNameTime(travel);
                if (travel1 != null) {
                    if (travel1.getTrips()!=1){
                        Accountant acc = new Accountant();
                        acc.setCusName(travel.getCusName());
                        acc.setTravelTime(travel.getTravelTime());
                        acc.setTravelTimeList(format.format(travel.getTravelTime()));
                        errorList_T.add(acc);
                        return;
                    }
                }
                entity.setZhibanMoney(map.get(DateUtil.getData(date.replaceAll("-", ""))));
                zhibanMapper.insertSelective(entity);
            });
        });

        if (errorList_Z.size() > 0 || errorList_T.size() > 0) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            /**
             * 如果值班有重复,就添加到值班里头,记录为重复数据,方便用户作去重操作
             * 2019-11-21
             * HeWanLi
             */
            if (errorList_Z.size() > 0){
                for (int i = 0; i < errorList_Z.size(); i++) {
                    Zhiban entity = (Zhiban) errorList_Z.get(i);
                    entity.setRepetition(1);
                    String date = format.format(entity.getZhibanTime());
                    entity.setZhibanMoney(map.get(DateUtil.getData(date.replaceAll("-", ""))));
                    zhibanMapper.insertSelective(entity);
                }

            }
            /**
             * 如果有重复出差的,那也添加到值班,值班费为0
             */
            if (errorList_T.size() > 0){
                for (int i = 0; i < errorList_T.size(); i++) {
                    Zhiban entity = new Zhiban();
                    Accountant accountant = (Accountant) errorList_T.get(i);
                    entity.setRepetition(1);
                    entity.setZhibanMoney(0.0);
                    entity.setRemark(zhiban.get(0).getRemark());
                    entity.setCusName(accountant.getCusName());
                    entity.setRecordTime(new Date());
                    entity.setZhibanId(code);
                    Customer customer = customerMapper.selectByName(accountant.getCusName());
                    entity.setRecordTime(now);
                    entity.setZhibanRegion(customer.getCusRegion());
                    entity.setZhibanTime(accountant.getTravelTime());
                    zhibanMapper.insertSelective(entity);
                }

            }


            return new JsonData(201, "提交失败,有重复记录！", errorList_T, errorList_Z);
        }
        return new JsonData(200,"审核通过！");
    }
}
