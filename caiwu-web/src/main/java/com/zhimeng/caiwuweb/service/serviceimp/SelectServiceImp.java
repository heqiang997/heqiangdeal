package com.zhimeng.caiwuweb.service.serviceimp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhimeng.caiwuweb.dao.*;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.dto.JsonList;
import com.zhimeng.caiwuweb.entity.*;
import com.zhimeng.caiwuweb.service.SelectService;
import com.zhimeng.caiwuweb.util.MD5Utils;
import com.zhimeng.caiwuweb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liupengfei on 2018/10/18 20:21
 */
@Service
public class SelectServiceImp implements SelectService {

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CityMapper cityMapper;
    @Autowired
    AccountantMapper accountantMapper;
    @Autowired
    TravelMapper travelMapper;
    @Autowired
    ZhibanMapper zhibanMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public JsonData selectCus(String name,HttpServletRequest request) {

        String header = request.getHeader("Authentication");
        String s = redisUtil.get(header);
        Customer customer2 = customerMapper.selectByName(s);
        String cusAuthority = null;
        if(customer2.getCusAuthority()==3){
            cusAuthority = customer2.getCusAuthority().toString();
        }
        List<Customer> customers = customerMapper.selectAll(name,customer2.getCusRegion(),cusAuthority);

        if (customers.size()==0){
            return new JsonData(202,"未导入用户数据或该用户不在该单位");
        }

        for (Customer customer : customers) {
            customer.setCusPwd(MD5Utils.convertMD5(customer.getCusPwd()));
        }
        return new JsonData(200,"查询成功！",customers);
    }

    @Override
    public JsonData selectBackCus(String name) {

        List<Customer> customers = customerMapper.selectBackAll(name);

        if (customers.size()==0){
            return new JsonData(202,"未导入用户数据");
        }

        for (Customer customer : customers) {
            customer.setCusPwd(MD5Utils.convertMD5(customer.getCusPwd()));
        }
        return new JsonData(200,"查询成功！",customers);

    }

    @Override
    public JsonData selectCity(Integer travelStatus,Integer pid) {
//     辖区内
        if (travelStatus==1){
            City city = cityMapper.selectByCityName("贵州");
            City cityName = cityMapper.selectByCityName("毕节");
            List<City> cities = cityMapper.selectByPid(cityName.getId());
            Map map = new HashMap();
            map.put("sheng",city);
            map.put("shi",cityName);
            map.put("xian",cities);
            return new JsonData(200,"查询成功！",map);
        }
//        特殊地区
        if (travelStatus==2){
            City qinghai = cityMapper.selectByCityName("青海");
            City xizang = cityMapper.selectByCityName("西藏");
            City xinzhang = cityMapper.selectByCityName("新疆");

            List<City> list = new ArrayList<>();

            list.add(qinghai);
            list.add(xizang);
            list.add(xinzhang);
            return new JsonData(200,"查询成功！",list);
        }

        List<City> city = cityMapper.selectByPid(pid);

        if (city==null ||city.size()==0){
            return new JsonData(777,"未导入地区数据");
        }

        return new JsonData(200,"查询成功！",city);
    }


    /**
     * 2020-06-17
     * hewanli
     * 查询毕节单位信息,可级联查询
     * @param pid 单位ID
     * @return
     */
    public JsonData selectUnit(Integer pid){

        List<City> city = cityMapper.selectUnit(pid);

        if (city==null ||city.size()==0){
            return new JsonData(777,"未导入地区数据");
        }
        return new JsonData(200,"查询成功！",city);
    }


    public JsonData selectDivisionsPid(Integer pid){

        List<Divisions> city = cityMapper.selectDivisionsPid(pid);

        if (city==null ||city.size()==0){
            return new JsonData(777,"未导入地区数据");
        }
        return new JsonData(200,"查询成功！",city);
    }


    @Override
    public JsonData selectVehicle() {

        List<Vehicle> vehicles = vehicleMapper.selectAll();

        if (vehicles==null ||vehicles.size()==0){
            return new JsonData(777,"未录入交通工具数据");
        }
        return new JsonData(200,"查询成功！",vehicles);
    }

    @Override
    public JsonData selectAccountCoding(String coding,HttpServletRequest request,Integer type) {

        String header = request.getHeader("Authentication");
        String s = redisUtil.get(header);
        Customer customer = customerMapper.selectByName(s);
        //          没有就去值班表查
        double count=0;
        int i=0;
        if (type == 1){
            //         去统计表查
            Accountant acc = new Accountant();
            acc.setSelectId(coding);
            acc.setStatus(0);
            if (customer.getCusRegion()!=null){
                acc.setRepetition(0);
            }
            List<Accountant> accountants = accountantMapper.selectByCoding(acc);

            ArrayList<Accountant> list = new ArrayList<>();
            if (customer.getCusRegion()==null){
                list.addAll(accountants);
            }else {
                for (Accountant accountant : accountants) {
                    Customer customer1 = customerMapper.selectByName(accountant.getCusName());
                    if (customer1.getCusRegion().equals(customer.getCusRegion())){
                        list.add(accountant);
//                        count+=accountant.getCountMoney();
//                        String timeList = accountant.getTravelTimeList();
//                        String[] split = timeList.split(",");
//                        i+=split.length;
                    }
                }
            }
            if (list.size()==0) {
                return new JsonData(300,"未查到相关数据，请确认查询码！");
            }else {
                String names = "";
//                名字去重
                for ( int k = 0 ; k < list.size() - 1 ; k ++ ) {
                    for ( int j = list.size() - 1 ; j > k; j -- ) {
                        if (list.get(j).getCusName().equals(list.get(k).getCusName())) {
                            list.remove(j);
                        }
                    }
                }
                for (Accountant accountant : list) {
                    count+=accountant.getCountMoney();
                    String timeList = accountant.getTravelTimeList();
                    String[] split = timeList.split(",");
                    i+=split.length;
                }
//                把名字全部追加到第一个
                list.get(0).setCusName(names);
                list.get(0).setCountMoney(count);
                List<Accountant> listName = new ArrayList<>();

                listName.add(list.get(0));

                return new JsonData(200, "查询成功！", listName,count,i);
            }

        }else {
            Zhiban zhi = new Zhiban();
            zhi.setZhibanId(coding);
            zhi.setStatus(0);
            if (customer.getCusRegion()!=null){
                zhi.setRepetition(0);
            }
            List<Zhiban> zhibans = zhibanMapper.selectByCoding(zhi);
//            if (zhibans.size()==1){
//                zhibans = zhibanMapper.selectByCoding(zhi);
//                for (Zhiban zhiban : zhibans) {
//                    count+=zhiban.getZhibanMoney();
//                    i++;
//                }
//            }
            SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
            if (zhibans==null || zhibans.size()==0){
                return new JsonData(300,"未查到相关数据，请确认查询码！");
            }else {
                ArrayList<Zhiban> list = new ArrayList<>();
                if (customer.getCusRegion()==null){
                    list.addAll(zhibans);
                }else {
                    for (Zhiban zhiban : zhibans) {
                        Customer customer1 = customerMapper.selectByName(zhiban.getCusName());
                        if (customer.getCusRegion().equals(customer1.getCusRegion())){
                            list.add(zhiban);
//                            count+=zhiban.getZhibanMoney();
//                            i++;
//                            timeList = format.format(zhiban.getZhibanTime())+","+timeList;
                        }
                    }
                }
                if (list.size()==0) {
                    return new JsonData(202, "该查询码不在同一个地区！");
                }else {
                    for (Zhiban zhiban : list) {
                        count+=zhiban.getZhibanMoney();
                            i++;
                    }
//                    //                名字去重
//                    for ( int k = 0 ; k < zhibans.size() - 1 ; k ++ ) {
//                        for ( int j = zhibans.size() - 1 ; j > k; j -- ) {
//                            if (zhibans.get(j).getCusName().equals(zhibans.get(k).getCusName())) {
//                                zhibans.remove(j);
//                            }
//                        }
//                    }
//                    String names = "";
//                    for (Zhiban zhiban : list) {
//                        names =zhiban.getCusName()+","+names;
//                        count+=zhiban.getZhibanMoney();
//                        i++;
//                    }
//                    //                时间去重
//                    for ( int k = 0 ; k < zhibans.size() - 1 ; k ++ ) {
//                        for ( int j = zhibans.size() - 1 ; j > k; j -- ) {
//                            if (zhibans.get(j).getZhibanTime().equals(zhibans.get(k).getZhibanTime())) {
//                                zhibans.remove(j);
//                            }
//                        }
//                    }
//                    String timeList= "";
//                    for (Zhiban zhiban : zhibans) {
//                        timeList=format.format(zhiban.getZhibanTime())+","+timeList;
//                    }
//                把名字全部追加到第一个
//                    list.get(0).setCusName(names);
                    List<Zhiban> listName = new ArrayList<>();
//                    list.get(0).setTimelist(timeList);
                    list.get(0).setZhibanMoney(count);
                    listName.add(list.get(0));

                    return new JsonData(200, "查询成功！", listName,count,i);
                }
            }
        }
//          没有就去值班表查
/*
        double count=0;
        int i=0;
        if (accountants==null || accountants.size()==0){
            Zhiban zhi = new Zhiban();
            zhi.setZhibanId(coding);
            zhi.setStatus(0);
            if (customer.getCusRegion()!=null){
                zhi.setRepetition(0);
            }
            List<Zhiban> zhibans = zhibanMapper.selectByCoding(zhi);
            if (zhibans.size()==1){
              zhibans = zhibanMapper.selectByCoding(zhi);
                for (Zhiban zhiban : zhibans) {
                    count+=zhiban.getZhibanMoney();
                    i++;
                }
            }
            SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
            if (zhibans==null || zhibans.size()==0){
                return new JsonData(300,"未查到相关数据，请确认查询码！");
            }else {
                ArrayList<Zhiban> list = new ArrayList<>();
                if (customer.getCusRegion()==null){
                    list.addAll(zhibans);
                }else {
                    for (Zhiban zhiban : zhibans) {
                        Customer customer1 = customerMapper.selectByName(zhiban.getCusName());
                        if (customer.getCusRegion().equals(customer1.getCusRegion())){
                            list.add(zhiban);
                            count+=zhiban.getZhibanMoney();
                            i++;
//                            timeList = format.format(zhiban.getZhibanTime())+","+timeList;
                        }
                    }
                }
                if (list.size()==0) {
                    return new JsonData(202, "该查询码不在同一个地区！");
                }else {
                    //                名字去重
                    for ( int k = 0 ; k < zhibans.size() - 1 ; k ++ ) {
                        for ( int j = zhibans.size() - 1 ; j > k; j -- ) {
                            if (zhibans.get(j).getCusName().equals(zhibans.get(k).getCusName())) {
                                zhibans.remove(j);
                            }
                        }
                    }
                    String names = "";
                    for (Zhiban zhiban : list) {
                        names =zhiban.getCusName()+","+names;
                        count+=zhiban.getZhibanMoney();
                        i++;
                    }
                    //                时间去重
                    for ( int k = 0 ; k < zhibans.size() - 1 ; k ++ ) {
                        for ( int j = zhibans.size() - 1 ; j > k; j -- ) {
                            if (zhibans.get(j).getZhibanTime().equals(zhibans.get(k).getZhibanTime())) {
                                zhibans.remove(j);
                            }
                        }
                    }
                    String timeList= "";
                    for (Zhiban zhiban : zhibans) {
                        timeList=format.format(zhiban.getZhibanTime())+","+timeList;
                    }
//                把名字全部追加到第一个
                    list.get(0).setCusName(names);
                    List<Zhiban> listName = new ArrayList<>();
                    list.get(0).setTimelist(timeList);
                    list.get(0).setZhibanMoney(count);
                    listName.add(list.get(0));

                    return new JsonData(200, "查询成功！", listName,count,i);
                }
            }
        }else {
            ArrayList<Accountant> list = new ArrayList<>();
            if (customer.getCusRegion()==null){
                list.addAll(accountants);
            }else {
                for (Accountant accountant : accountants) {
                    Customer customer1 = customerMapper.selectByName(accountant.getCusName());
                    if (customer1.getCusRegion().equals(customer.getCusRegion())){
                        list.add(accountant);
                        count+=accountant.getCountMoney();
                        String timeList = accountant.getTravelTimeList();
                        String[] split = timeList.split(",");
                        i+=split.length;
                    }
                }
            }
            if (list.size()==0) {
                return new JsonData(202, "该查询码不在同一个地区！");
            }else {
                String names = "";
//                名字去重
                for ( int k = 0 ; k < list.size() - 1 ; k ++ ) {
                    for ( int j = list.size() - 1 ; j > k; j -- ) {
                        if (list.get(j).getCusName().equals(list.get(k).getCusName())) {
                            list.remove(j);
                        }
                    }
                }

                for (Accountant accountant : list) {
                    names =accountant.getCusName()+","+names;
                    count+=accountant.getCountMoney();
                    i++;
                }
//                把名字全部追加到第一个
                list.get(0).setCusName(names);
                list.get(0).setCountMoney(count);
                List<Accountant> listName = new ArrayList<>();

                listName.add(list.get(0));

                return new JsonData(200, "查询成功！", listName,count,i);
            }
*/
//        }
    }

    /**
     * 当前登陆的用户所在地区的所有出差统计
     * @param request
     * @param travelType    根据出差类型id
     * @param veType        根据交通工具id
     * @param pageNum
     * @param pageSize
     * @param type 排序默认升序不为空降序
     * @return
     */
    @Override
    public JsonData selectAccountAll(HttpServletRequest request,String name,Integer travelType,Integer veType,Integer pageNum,Integer pageSize,Integer type,Date start,Date end) {

        String header = request.getHeader("Authentication");
        String s = redisUtil.get(header);
        Customer customer = customerMapper.selectByName(s);

        Accountant accountant = new Accountant();
        accountant.setVehicleType(veType);
        accountant.setTravelType(travelType);

        accountant.setType(type);
        accountant.setTravelStartTime(start);
        accountant.setTravelEndTime(end);
        accountant.setStatus(0);
        if (customer.getCusRegion()!=null){
            //accountant.setRepetition(0);
        }
        accountant.setTravelRegion(customer.getCusRegion());
        accountant.setCusName(name);
//        if (name!=null && !name.equals("")){
//            Customer byName = customerMapper.selectByName(name);
//            if (!byName.getCusRegion().equals(customer.getCusRegion())){
//                return new JsonData(501,"该用户不在同一个单位！");
//            }
//            accountant.setCusName(name);
//        }
        //PageHelper.startPage(pageNum,pageSize);
        /**
         * 创建第二个对象
         */
        Accountant accountant2 = new Accountant();
        accountant2.setVehicleType(veType);
        accountant2.setTravelType(travelType);

        accountant2.setType(type);
        accountant2.setTravelStartTime(start);
        accountant2.setTravelEndTime(end);
        accountant2.setStatus(0);
        if (customer.getCusRegion()!=null){
            accountant2.setRepetition(0);
        }
        accountant2.setTravelRegion(customer.getCusRegion());
        accountant2.setCusName(name);



        Object unit = request.getParameter("unit");
        if (null != unit && !"".equals(unit)){
            accountant.setUnit(unit.toString());
            accountant2.setUnit(unit.toString());
        }

        // 判断是不是超级管理员并且不是贵阳的测试账号
        if(customer.getCusAuthority()==3 && !"贵阳".equals(customer.getCusRegion())){
            // 添加查询条件查询,单位查询本级及下级的
            accountant.setCusAuthority(customer.getCusAuthority());
            accountant2.setCusAuthority(customer.getCusAuthority());
        }

        // 查询总的数据,方便统计天数和总金额
        List<Accountant> list = accountantMapper.selectAll(accountant);
        System.out.println("数据数量："+list.size());
        // 重复的数据
        //List<Accountant> repetitions = new ArrayList<>();

        double count=0;
        double repetitionCount = 0;
        int i=0;
        for (Accountant accountant1 : list) {
            if (accountant1.getRepetition() == 0){
                count+=accountant1.getCountMoney();
                String timeList = accountant1.getTravelTimeList();
                String[] split = timeList.split(",");
                i+=split.length;
            }
            /**
             * 计算重复数据的金额
             */
            if (accountant1.getRepetition() == 1){
                repetitionCount+=accountant1.getCountMoney();
                //repetitions.add(accountant);
            }
        }

        // 判断是否查询重复的数据
        if (null != request.getParameter("repetition") && !"".equals(request.getParameter("repetition"))){
            accountant2.setRepetition(1);
        }


        // 开始分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Accountant> list1 = accountantMapper.selectAll(accountant2);
        PageInfo<Accountant> pageInfo = new PageInfo<>(list1);
        return new JsonData(200,"查询成功！",pageInfo,repetitionCount,count,i);
    }

    @Override
    public JsonData selectAccountAllZhiban(HttpServletRequest request,String name, Integer pageNum, Integer pageSize, Integer type,Date start,Date end) {

        String header = request.getHeader("Authentication");
        String s = redisUtil.get(header);
        Customer customer = customerMapper.selectByName(s);


        /**
         * 注释掉不在同一个单位判断的人，方便查询下级单位人员数据信息
         */
        /*if (name!=null){
            Customer customer1 = customerMapper.selectByName(name);
            if (customer.getCusRegion()!=null && !customer1.getCusRegion().equals(customer.getCusRegion())){
                return  new JsonData(501,"所选人员不在同一个单位");
            }
        }*/
        Object unit = request.getParameter("unit");  // 获取单位
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");

        Zhiban zhiban = new Zhiban();
        zhiban.setZhibanRegion(customer.getCusRegion());
        zhiban.setPaixu(type);
        zhiban.setCusName(name);
        zhiban.setRecordTime(start);
        zhiban.setZhibanTime(end);
        zhiban.setStatus(0);

        // 创建第二个对象
        Zhiban zhiban2 = new Zhiban();
        zhiban2.setZhibanRegion(customer.getCusRegion());
        zhiban2.setPaixu(type);
        zhiban2.setCusName(name);
        zhiban2.setRecordTime(start);
        zhiban2.setZhibanTime(end);
        zhiban2.setStatus(0);

        if (customer.getCusRegion()!=null){
            //zhiban.setRepetition(0);
            zhiban2.setRepetition(0);
        }
//        PageHelper.startPage(pageNum,pageSize);
        if (null != unit && !"".equals(unit)){
            zhiban.setUnit(unit.toString());
            zhiban2.setUnit(unit.toString());
        }
        // 判断是不是超级管理员并且不是贵阳的测试账号
        if(customer.getCusAuthority()==3 && !"贵阳".equals(customer.getCusRegion())){
            // 添加查询条件查询,单位查询本级及下级的
            zhiban.setCusAuthority(customer.getCusAuthority());
            zhiban2.setCusAuthority(customer.getCusAuthority());
        }
        // 查询总的数据方便统计天数和值班费
        List<Zhiban> list = zhibanMapper.selectAll(zhiban);
        // 重复的数据
        //List<Zhiban> repetitions = new ArrayList<>();

//        for ( int k = 0 ; k < list.size() - 1 ; k ++ ) {
//            for ( int j = list.size() - 1 ; j > k; j -- ) {
//                if (list.get(j).getCusName().equals(list.get(k).getCusName())) {
////                    timelist = format.format(list.get(j).getZhibanTime())+","+timelist;
////                    list.get(k).setTimelist(timelist);
//                    list.remove(j);
//                }
//            }
//        }
        double count=0;
        double repetitionCount = 0;
        int i=0;
//        String timelist = "";
        for (Zhiban z : list) {
            if (z.getRepetition() == 0){
                count+=z.getZhibanMoney();
                i++;
            }
            // 计算重复金额
            if (z.getRepetition() == 1){
                repetitionCount+=z.getZhibanMoney();
                //repetitions.add(z);
            }
        }

        for (Zhiban zhiban1 : list) {
            if (zhiban1.getTimelist()==null){
//                zhiban1.setTimelist(format.format(zhiban1.getZhibanTime()));
            }
        }

        if (null != request.getParameter("repetition") && !"".equals(request.getParameter("repetition"))){
            zhiban2.setRepetition(1);
        }
        // 开始分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Zhiban> list1 = zhibanMapper.selectAll(zhiban2);
        PageInfo<Zhiban> pageInfo = new PageInfo<>(list1);
        // 判断是否查询重复的数据


        return new JsonData(200,"查询成功！",pageInfo,repetitionCount,count,i);
    }


    @Override
    public JsonData selectTravelByName(String name,HttpServletRequest request,Integer pageNum,Integer pageSize) {

        String authentication = request.getHeader("Authentication");

        String s = redisUtil.get(authentication);

        Customer customer = customerMapper.selectByName(s);

        Customer customer1 = customerMapper.selectByName(name);

        if (!customer.getCusRegion().equals(customer1.getCusRegion())){

            return new JsonData(501,"该用户和您不在同一个单位无权限查询");
        }
        //      pageNum第几页，一页多少条
        PageHelper.startPage(pageNum,pageSize);
        List<Travel> travels = travelMapper.selectByName(name);
        PageInfo<Travel> pageInfo = new PageInfo<>(travels);
        if (travels==null || travels.size()==0){
            return new JsonData(778,"该用户未录入出差记录");
        }

        return new JsonData(200,"查询成功！",pageInfo);
    }

    @Override
    public JsonData selectTravelByTime(DateVo vo,HttpServletRequest request,Integer pageNum,Integer pageSize) {

        String authentication = request.getHeader("Authentication");

        String s = redisUtil.get(authentication);

        Customer customer = customerMapper.selectByName(s);

        //          格式化日期只有年月日
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s1 = format.format(vo.getStart());
        String s2 = format.format(vo.getEnd());
        try {
            Date parse = format.parse(s1);
            Date parse2 = format.parse(s2);
            vo.setStart(parse);
            vo.setEnd(parse2);
        } catch (ParseException e) {
            e.getMessage();
        }

        //      pageNum第几页，一页多少条
        PageHelper.startPage(pageNum,pageSize);
        List<Travel> travels = travelMapper.selectBetweenTime(vo);

        if (travels==null || travels.size()==0){
            return new JsonData(778,"该用户在这段时间未录入出差记录");
        }
//        另起一个list筛选在同一地区的列表返回
        List<Travel> list = new ArrayList<>();
        for (Travel travel : travels) {
            Customer byName = customerMapper.selectByName(travel.getCusName());
            if (byName.getCusRegion().equals(customer.getCusRegion())){
                list.add(travel);
            }
        }
        if (list.size()==0){
            return new JsonData(202,"这段时间本单位还没有出差记录");
        }
        PageInfo<Travel> pageInfo = new PageInfo<>(list);

        return new JsonData(200,"查询成功！",pageInfo);

    }

    @Override
    public JsonData selectZhibanByName(String name,HttpServletRequest request,Integer pageNum,Integer pageSize) {

        String authentication = request.getHeader("Authentication");

        String s = redisUtil.get(authentication);

        Customer customer = customerMapper.selectByName(s);

        Customer customer1 = customerMapper.selectByName(name);

        if (customer.getCusRegion()!=null && !customer.getCusRegion().equals(customer1.getCusRegion())){

            return new JsonData(501,"该用户和您不在同一个单位无权限查询");

        }
//      pageNum第几页，一页多少条
        PageHelper.startPage(pageNum,pageSize);
        List<Zhiban> zhibans = zhibanMapper.selectByName(name);
        PageInfo<Zhiban> pageInfo = new PageInfo<>(zhibans);

        if (zhibans==null||zhibans.size()==0){
            return new JsonData(778,"该用户未录入值班记录");
        }
        return new JsonData(200,"查询成功！",pageInfo);
    }

    @Override
    public JsonData selectZhibanByTime(DateVo vo,HttpServletRequest request,Integer pageNum,Integer pageSize) {

        String authentication = request.getHeader("Authentication");

        String s = redisUtil.get(authentication);

        Customer customer = customerMapper.selectByName(s);

        //          格式化日期只有年月日
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s1 = format.format(vo.getStart());
        String s2 = format.format(vo.getEnd());
        try {
            Date parse = format.parse(s1);
            Date parse2 = format.parse(s2);
            vo.setStart(parse);
            vo.setEnd(parse2);
        } catch (ParseException e) {
            e.getMessage();
        }

        //      pageNum第几页，一页多少条
        PageHelper.startPage(pageNum,pageSize);
        List<Zhiban> zhibans = zhibanMapper.selectBetweenTime(vo);
        if (zhibans==null || zhibans.size()==0){
            return new JsonData(778,"该用户在这段时间未录入值班记录");
        }
        List<Zhiban> list = new ArrayList<>();

        for (Zhiban zhiban : zhibans) {

            Customer customer1 = customerMapper.selectByName(zhiban.getCusName());
//        另起一个list筛选在同一地区的列表返回
            if (customer1.getCusRegion().equals(customer.getCusRegion())){
                list.add(zhiban);
            }
        }
        if (list.size()==0){
            return new JsonData(202,"这段时间本单位还没有值班记录");
        }
        PageInfo<Zhiban> pageInfo = new PageInfo<>(list);
        return new JsonData(200,"查询成功！",pageInfo);

    }

    @Override
    public JsonList selectZhibanAndTravel(HttpServletRequest request) {

        String authentication = request.getHeader("Authentication");

        String s = redisUtil.get(authentication);

        Customer customer = customerMapper.selectByName(s);

        Accountant accountant = new Accountant();
        accountant.setCusName(customer.getCusName());
        accountant.setStatus(0);
        accountant.setRepetition(0);
        List<Accountant> list = accountantMapper.selectAll(accountant);

        Double tavelCountMoney = 0.0;
        Double zhibanCountMoney = 0.0;
        for (Accountant accountant1 : list) {
        tavelCountMoney += accountant1.getCountMoney();
        }

        Zhiban zhiban = new Zhiban();
        zhiban.setCusName(customer.getCusName());
        zhiban.setStatus(0);
        zhiban.setRepetition(0);
        List<Zhiban> zhibans = zhibanMapper.selectAll(zhiban);

        for (Zhiban zhiban1 : zhibans) {
            zhibanCountMoney += zhiban1.getZhibanMoney();
        }

        return new JsonList(200,"查询成功！",list,zhibans,tavelCountMoney,zhibanCountMoney);
    }


    @Override
    public JsonData selectAccJoinTravel(Integer id,String coding,HttpServletRequest request) {

        String authentication = request.getHeader("Authentication");

        String s = redisUtil.get(authentication);

        Customer customer = customerMapper.selectByName(s);

        List<Accountant> list =null;
        if (id!=null){
            Accountant acc = new Accountant();
            acc.setId(id);
            acc.setStatus(0);
            if (customer.getCusRegion()!=null){
                acc.setRepetition(0);
            }
            list = accountantMapper.selectAccJoinTravelById(acc);
            if (list.size()==0){
                return new JsonData(200,"该查询码并未有数据，请确认查询码是否正确");
            }
            return new JsonData(200,"查询成功！",list);
        }else {
            Accountant seacc = new Accountant();
            seacc.setSelectId(coding);
            seacc.setStatus(0);
            if (customer.getCusRegion()!=null){
                seacc.setRepetition(0);
            }
            list = accountantMapper.selectAccJoinTravel(seacc);

            if (list.size()==0){
                return new JsonData(200,"该查询码并未有数据，请确认查询码是否正确");
            }

            String names = "";
//                名字去重
            for ( int k = 0 ; k < list.size() - 1 ; k ++ ) {
                for ( int j = list.size() - 1 ; j > k; j -- ) {
                    if (list.get(j).getCusName().equals(list.get(k).getCusName())) {
                        list.remove(j);
                    }
                }
            }
            for (Accountant accountant : list) {
                names =accountant.getCusName()+","+names;
            }
//                把名字全部追加到第一个
            list.get(0).setCusName(names);
            List<Accountant> listName = new ArrayList<>();

            listName.add(list.get(0));

            return new JsonData(200,"查询成功！",listName);
        }

    }

    @Override
    public JsonData selectZhibanByCoding(Integer id,String coding,HttpServletRequest request) {

        String authentication = request.getHeader("Authentication");
        String s1 = redisUtil.get(authentication);
        Customer customer = customerMapper.selectByName(s1);

        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        List<Zhiban> list = null;
        if (id!=null){
            Zhiban zhi = new Zhiban();
            zhi.setId(id);
            zhi.setStatus(0);
            if (customer.getCusRegion()!=null){
                zhi.setRepetition(0);
            }
            list = zhibanMapper.selectById(zhi);
            if (list.size()==0){
                return new JsonData(200,"未录入数据或数据已被删完");
            }

            list.get(0).setTimelist(format.format(list.get(0).getZhibanTime())+",");
            return new JsonData(200,"查询成功！",list);
        }else {
            Zhiban zhi = new Zhiban();
            zhi.setZhibanId(coding);
            zhi.setStatus(0);
            if (customer.getCusRegion()!=null){
                zhi.setRepetition(0);
            }
            list = zhibanMapper.selectByCoding(zhi);
            if (list==null || list.size()==0){
                return new JsonData(204,"未录入数据或数据已被删完");
            }
//            名字去重
            for ( int k = 0 ; k < list.size() - 1 ; k ++ ) {
                for ( int j = list.size() - 1 ; j > k; j -- ) {
                    if (list.get(j).getCusName().equals(list.get(k).getCusName())) {
                        list.remove(j);
                    }
                }
            }
//            名字集合
            List<String> nameslist = new ArrayList<>();
            for (Zhiban zhiban : list) {
                nameslist.add(zhiban.getCusName());
            }
            list = zhibanMapper.selectByCoding(zhi);

            List<Zhiban> backList = new ArrayList<>();
            for (String s : nameslist) {
                Zhiban z = new Zhiban();
                z.setZhibanId(list.get(0).getZhibanId());
                z.setRemark(list.get(0).getRemark());
                String timelist = "";
                Double money = 0.0;
                for (Zhiban zhiban : list) {
                    if (s.equals(zhiban.getCusName())){
                        timelist=format.format(zhiban.getZhibanTime())+","+timelist;
                        money +=zhiban.getZhibanMoney();
                    }
                }
                z.setCusName(s);
                z.setTimelist(timelist);
                z.setZhibanMoney(money);
                backList.add(z);
            }
            return new JsonData(202,"查询成功！",backList);
//            list = zhibanMapper.selectByCoding(coding);
//            if (list==null || list.size()==0){
//                return new JsonData(204,"未录入数据或数据已被删完");
//            }
//            String names = "";
//            String timeList = "";
////                名字去重
//            for ( int k = 0 ; k < list.size() - 1 ; k ++ ) {
//                for ( int j = list.size() - 1 ; j > k; j -- ) {
//                    if (list.get(j).getCusName().equals(list.get(k).getCusName())) {
//                        list.remove(j);
//                    }
//                }
//            }
//            Double money = 0.0;
//            if (list.size()==1){
//                list = zhibanMapper.selectByCoding(coding);
//                for (Zhiban zhiban : list) {
//                    timeList = format.format(zhiban.getZhibanTime())+","+timeList;
//                    money +=zhiban.getZhibanMoney();
//                }
//                list.get(0).setCusName(list.get(0).getCusName()+",");
//                list.get(0).setZhibanMoney(money);
//            }else {
//                for (Zhiban zhiban : list) {
//                    names =zhiban.getCusName()+","+names;
////                    timeList = format.format(zhiban.getZhibanTime())+","+timeList;
//                }
//                list = zhibanMapper.selectByCoding(coding);
////                时间去重
//                for ( int k = 0 ; k < list.size() - 1 ; k ++ ) {
//                    for ( int j = list.size() - 1 ; j > k; j -- ) {
//                        if (list.get(j).getZhibanTime().equals(list.get(k).getZhibanTime())) {
//                            list.remove(j);
//                        }
//                    }
//                }
//                for (Zhiban zhiban : list) {
////                    names =zhiban.getCusName()+","+names;
//                    timeList = format.format(zhiban.getZhibanTime())+","+timeList;
//                    money +=zhiban.getZhibanMoney();
//                }
//                list.get(0).setCusName(names);
//                list.get(0).setZhibanMoney(money);
//            }
////                把名字全部追加到第一个
//            list.get(0).setTimelist(timeList);
//            List<Zhiban> listName = new ArrayList<>();
//            listName.addAll(list);
//            return new JsonData(202,"查询成功！",listName);
        }

    }

//    @Override
//    public JsonData selectByid(Integer id) {
//
//        List<Zhiban> list = new ArrayList();
//        Zhiban zhiban = (Zhiban) zhibanMapper.selectById(id);
//        list.add(zhiban);
//        return new JsonData(200,"查询成功！",list);
//
//    }


    @Override
    public JsonData deleteByCoding(Integer id,String coding) {

        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (id!=null){
            Accountant accountants = accountantMapper.selectById(id);
            if (accountants!=null){
                Accountant accountant = new Accountant();
                accountant.setId(id);
                accountant.setStatus(1);
                accountant.setBatchNumber(format.format(new Date()));

                accountantMapper.updateByPrimaryKeySelective(accountant);

                travelMapper.deleteByNamePrimaryKey(accountants.getSelectId(),accountants.getCusName());
                return new JsonData(200,"删除成功！");
            }else{
                return new JsonData(400,"请输入正确的id");
            }
        }else {
            Accountant acc = new Accountant();
            acc.setSelectId(coding);
            List<Accountant> accountants = accountantMapper.selectByCoding(acc);

            for (Accountant accountant : accountants) {
                accountant.setStatus(1);
                accountant.setBatchNumber(format.format(new Date()));
                accountantMapper.updateByPrimaryKeySelective(accountant);
                travelMapper.deleteByPrimaryKey(accountant.getSelectId());
            }
            return new JsonData(200,"删除成功！");
        }
    }

    @Override
    public JsonData deleteZhiban(Integer id,String coding) {

        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (id !=null){
            Zhiban zhiban = new Zhiban();
            zhiban.setId(id);
            zhiban.setStatus(1);
            zhiban.setBatchNumber(format.format(new Date()));
            int i = zhibanMapper.updateByzhiban(zhiban);
            if (i!=0){
                return new JsonData(200,"删除成功！");
            }
            return new JsonData(500,"删除失败！");
        }else {

            Zhiban zhiban = new Zhiban();
            zhiban.setZhibanId(coding);
            zhiban.setStatus(1);
            zhiban.setBatchNumber(format.format(new Date()));
            int i = zhibanMapper.updateByCodingzhiban(zhiban);
            if (i!=0){
                return new JsonData(200,"删除成功！");
            }
            return new JsonData(500,"删除失败！");
        }

    }

    @Override
    public JsonData deleteAll() {
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Accountant temp = new Accountant();
        temp.setStatus(0);
        List<Accountant> accountants = accountantMapper.selectAll(temp);
        for (Accountant accountant : accountants) {
            accountant.setStatus(1);
            accountant.setBatchNumber(format.format(new Date()));
            accountantMapper.updateByPrimaryKeySelective(accountant);
            travelMapper.deleteByPrimaryKey(accountant.getSelectId());
        }
        return new JsonData(200,"删除成功！");
    }
}
