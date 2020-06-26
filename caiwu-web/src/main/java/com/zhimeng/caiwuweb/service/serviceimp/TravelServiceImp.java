package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.*;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.*;
import com.zhimeng.caiwuweb.service.TravelService;
import com.zhimeng.caiwuweb.util.CodingUtil;
import com.zhimeng.caiwuweb.util.CopyListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TravelServiceImp implements TravelService {
    @Autowired
    TravelMapper travelMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    ZhibanMapper zhibanMapper;
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    VehicleMapper vehicleMapper;
    @Autowired
    AccountantMapper accountantMapper;
    @Autowired
    StandardMapper standardMapper;
    @Autowired
    InstitutionMapper institutionMapper;
    /**
     * 差旅报审信息录入
     * @param list 差旅实体list
     * @return
     */
    @Override
    public JsonData insertTravel(Integer travelStatus,String travelArea,Integer institutionid,List<String> names,List<Travel> list,Integer foodtype) {
//      返回数据
        List<Accountant> accountantListback = new ArrayList<>();
        List<Zhiban> zhibanListback = new ArrayList<>();
        List<Zhiban> zhibanList = new ArrayList<>();
        if(list==null || list.size()==0){
            return new JsonData(301,"信息不能为空");
        }
        if(names==null || names.size()==0){
            return new JsonData(301,"用户名不能为空");
        }
        Institution institution = institutionMapper.selectByPrimaryKey(institutionid);
        for (String s : names) {
            Customer customer = customerMapper.selectByName(s);
            if (!customer.getCusRegion().equals(institution.getInstitutionName())){
                return new JsonData(500,customer.getCusName()+"不在所选单位中");
            }
        }

        Zhiban z = new Zhiban();
            if (list.get(0).getVehicleId()==null){
                return new JsonData(301,"出差交通工具不能为空");
            }
            if(list.get(0).getTravelStatus()==null){
                return new JsonData(301,"出差类型不能为空！");
            }
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        String coding = CodingUtil.coding(institution.getInstitutionName());

        List<Travel> travelrepetition = new ArrayList<>();
        List<Travel> newList = CopyListUtil.deepCopyList(list);

        for (String s : names) {
            list = CopyListUtil.deepCopyList(newList);
            for (Travel travel : list) {
//                          格式化日期只有年月日
                String s1 = format.format(travel.getTravelTime());
                try {
                    Date parse = format.parse(s1);
                    travel.setTravelTime(parse);
                } catch (ParseException e) {
                    e.getMessage();
                }

                travel.setCusName(s);
                Travel travel1 = travelMapper.selectByNameTime(travel);
                if (travel1!=null){
                    travelrepetition.add(travel1);
//                    return new JsonData(303, travel1.getCusName()+"审核不通过有重复出差记录"+format.format(travel1.getTravelTime()), travel1);
                }else{
                    z.setCusName(travel.getCusName());
                    z.setZhibanTime(travel.getTravelTime());
                    Zhiban zhiban1 = zhibanMapper.selectByNameTime(z);
                    if (zhiban1!=null){
                        zhibanList.add(zhiban1);
                        travelrepetition.add(travel);
//                        return new JsonData(303, zhiban1.getCusName()+"审核不通过有重复值班记录"+format.format(zhiban1.getZhibanTime()), zhiban1);
                    }
                }
            }
        }


        for (String name : names) {
            list = CopyListUtil.deepCopyList(newList);
            Customer customer = customerMapper.selectByName(name);
            //          判定用户
            if (customer==null){
                return new JsonData(305,"该用户不存在！");
            }

            for (Travel travel : list) {
//              将名字添加到出差录入对象中
                travel.setCusName(name);
                travel.setRecordTime(new Date());
                //          格式化日期只有年月日
                String s = format.format(travel.getTravelTime());
                try {
                    Date parse = format.parse(s);
                    travel.setTravelTime(parse);
                } catch (ParseException e) {
                    e.getMessage();
                }
//                Travel travel1 = travelMapper.selectByNameTime(travel);
//                if (travel1!=null){
//                    travelrepetition.add(travel1);
////                    return new JsonData(303, travel1.getCusName()+"审核不通过有重复出差记录"+format.format(travel1.getTravelTime()), travel1);
//                }else{
//                    z.setCusName(travel.getCusName());
//                    z.setZhibanTime(travel.getTravelTime());
//                    Zhiban zhiban1 = zhibanMapper.selectByNameTime(z);
//                    if (zhiban1!=null){
//                        travelrepetition.add(travel);
////                        return new JsonData(303, zhiban1.getCusName()+"审核不通过有重复值班记录"+format.format(zhiban1.getZhibanTime()), zhiban1);
//                    }
//                }
            }

            /**
             * 另起一个list如果出差小于3天都有出差补贴
             * 如果大于3天，会议和培训只有来回有补贴，期间没有
             * 如果是其他，每天都有
             * listinner
             */
            ArrayList<Travel> listinner = new ArrayList<>();
            if (list.size()>=3 && list.get(0).getTravelStatus()!=2 && foodtype!=1){
                listinner.add(list.get(0));
                listinner.add(list.get(list.size()-1));
            }else {
                    for (Travel travel : list) {
                        listinner.add(travel);
                    }
//                }
            }
            for (Travel travel:listinner) {
//          非空条件
                if (travel.getTravelTime()==null){
                    return new JsonData(302,"出差日期不能为空！");
                }
//          判断地区是否在辖区内
//                Area area = areaMapper.selectByPrimaryKey(travel.getAreaId());
                Standard standard = standardMapper.selectByPrimaryKey(1);
//            在辖区内 60元一天
                if (travelStatus == 1) {
                    travel.setFoodMoney(standard.getTravelNei());
                }
//              辖区外 100元一天
                if (travelStatus==0){
                    travel.setFoodMoney(standard.getTravelWai());
                }
//                特殊地区120，西藏，青海，新疆
                if (travelStatus == 2) {
                    travel.setFoodMoney(standard.getSpecialArea());
                }
//               是否是单位派车
                Vehicle vehicle = vehicleMapper.selectByVehicle("单位派车");
                if (travel.getVehicleId().equals(vehicle.getId())) {
                    travel.setVehicleMoney(0.0);
                } else {
//                   其他交通工具80元一天
                    travel.setVehicleMoney(standard.getVeMoney());
                }
//          格式化日期只有年月日
                String s = format.format(travel.getTravelTime());
                try {
                    Date parse = format.parse(s);
                    travel.setTravelTime(parse);
                } catch (ParseException e) {
                    e.getMessage();
                }
                travel.setId(coding);
                if (travelrepetition.size()==0){
                    travelMapper.insertSelective(travel);
                }
            }
            //            把会议或者培训中的出差日期录入进去
            if (foodtype == 1){
                if (list.get(0).getTravelStatus()!=2){
                    for (int i=1;i<list.size()-1;i++){
//                     更新车费为0
//                        list.get(i).setId(coding);
//                        list.get(i).setFoodMoney(0.0);
                        list.get(i).setVehicleMoney(0.0);
                        if (travelrepetition.size()==0){
                            travelMapper.updateByPrimaryKeyAndNameSelective(list.get(i));
                        }
                    }
                }
            }else {
                if (list.get(0).getTravelStatus()!=2){
                    for (int i=1;i<list.size()-1;i++){
//                    编码
                        list.get(i).setId(coding);
                        list.get(i).setFoodMoney(0.0);
                        list.get(i).setVehicleMoney(0.0);
                        if (travelrepetition.size()==0){
                        travelMapper.insertSelective(list.get(i));
                        }
                    }
                }
            }

            //         插入出差统计数据
            Double food = 0.0;
            Double ve = 0.0;
            String timelist="";

//            Iterator<Travel> iterator = list.iterator();
//            while (iterator.hasNext()){
//                Travel next = iterator.next();
//                for (Travel travel1 : travelrepetition) {
//                    if (next.equals(travel1)){
//                        iterator.remove();
//                    }
//                }
//            }
            if (travelrepetition.size()==0) {
                for (Travel t : list) {
                    food = food + t.getFoodMoney();
                    ve = ve + t.getVehicleMoney();
                    String s = format.format(t.getTravelTime());
                    timelist = s + "," + timelist;
                }
                Accountant accountant = new Accountant();
                    //            设置编码
                    accountant.setSelectId(coding);
                    accountant.setCountMoney(food + ve);
                    accountant.setCusName(name);
                    accountant.setVehicleMoney(ve);
                    accountant.setFoodMoney(food);
                    accountant.setTravelStartTime(list.get(0).getTravelTime());
                    accountant.setTravelEndTime(list.get(list.size() - 1).getTravelTime());
                    accountant.setTravelType(list.get(0).getTravelStatus());
                    accountant.setVehicleType(list.get(0).getVehicleId());
                    accountant.setType(0);
                    accountant.setRemark(list.get(0).getReason());
                    accountant.setRecordTime(list.get(0).getRecordTime());
                    accountant.setTravelTimeList(timelist);
                    accountant.setTravelRegion(institution.getInstitutionName());
                    accountant.setTravelArea(travelArea);
//            accountant.setBatchNumber(coding);
                    accountantMapper.insertSelective(accountant);
                }
//            ___________________________________________________________________________________________
        }

        List<String> namelist = new ArrayList<>();

        if (travelrepetition.size()!=0) {
            for (Travel travel : travelrepetition) {
                namelist.add(travel.getCusName());
                travelMapper.deleteByTimeAndNamePrimaryKey(travel.getTravelTime(), travel.getCusName(),coding);
            }
            //                姓名去重
            for ( int k = 0 ; k < namelist.size() - 1 ; k ++ ) {
                for ( int j = namelist.size() - 1 ; j > k; j -- ) {
                    if (namelist.get(j).equals(namelist.get(k))) {
                        namelist.remove(j);
                    }
                }
            }
/*            //                时间去重
            for ( int k = 0 ; k < travelrepetition.size() - 1 ; k ++ ) {
                for ( int j = travelrepetition.size() - 1 ; j > k; j -- ) {
                    if (travelrepetition.get(j).getTravelTime().equals(travelrepetition.get(k).getTravelTime())) {
                        travelrepetition.remove(j);
                    }
                }
            }*/

            for (String name : namelist) {
                //         插入出差统计数据
                Double food = 0.0;
                Double ve = 0.0;
                String timelist="";
                List<Travel> travels =new ArrayList<>();
                for (Travel travel : travelrepetition) {
                    if (travel.getCusName().equals(name)){
                        travels.add(travel);
                    }
                }
                            //                时间去重
            for ( int k = 0 ; k < travels.size() - 1 ; k ++ ) {
                for ( int j = travels.size() - 1 ; j > k; j -- ) {
                    if (travels.get(j).getTravelTime().equals(travels.get(k).getTravelTime())) {
                        travels.remove(j);
                    }
                }
            }
                for (Travel t : travels) {
                    food=food+t.getFoodMoney();
                    ve = ve+t.getVehicleMoney();
                    String s = format.format(t.getTravelTime());
                    timelist=s+","+timelist;
                }
                Accountant accountant = new Accountant();
                //            设置编码
                accountant.setSelectId(coding);
                accountant.setCountMoney(food+ve);
                accountant.setCusName(name);
                accountant.setVehicleMoney(ve);
                accountant.setFoodMoney(food);
                accountant.setTravelStartTime(travels.get(0).getTravelTime());
                accountant.setTravelEndTime(travels.get(travels.size()-1).getTravelTime());
                accountant.setTravelType(travels.get(0).getTravelStatus());
                accountant.setVehicleType(travels.get(0).getVehicleId());
                accountant.setType(0);
                //accountant.setRemark(travels.get(0).getReason());   将原因修改为实际填写原因
                accountant.setRemark(list.get(0).getReason());
                //accountant.setRecordTime(travels.get(0).getRecordTime());  // 将时间修改为系统时间
                accountant.setRecordTime(new Date());
                accountant.setTravelTimeList(timelist);
                accountant.setTravelRegion(institution.getInstitutionName());
                accountant.setTravelArea(travelArea);
                accountant.setRepetition(1);

                accountantMapper.insertSelective(accountant);

                accountantListback.add(accountant);
            }
        }

        if (accountantListback.size()==0){
            return new JsonData(200,"审核通过");
        }

        if (zhibanList.size()!=0){
            List<String> zbnmlist = new ArrayList<>();
            for (Zhiban zhiban : zhibanList) {
                zbnmlist.add(zhiban.getCusName());
            }
            //                姓名去重
            for ( int k = 0 ; k < zbnmlist.size() - 1 ; k ++ ) {
                for ( int j = zbnmlist.size() - 1 ; j > k; j -- ) {
                    if (zbnmlist.get(j).equals(zbnmlist.get(k))) {
                        zbnmlist.remove(j);
                    }
                }
            }
            for (String s : zbnmlist) {
                String timelist="";
                List<Zhiban> zb = new ArrayList<>();
                for (Zhiban zhiban : zhibanList) {
                    if (zhiban.getCusName().equals(s)){
                        zb.add(zhiban);
                    }
                }
                //                时间去重
                for ( int k = 0 ; k < zb.size() - 1 ; k ++ ) {
                    for ( int j = zb.size() - 1 ; j > k; j -- ) {
                        if (zb.get(j).getZhibanTime().equals(zb.get(k).getZhibanTime())) {
                            zb.remove(j);
                        }
                    }
                }

                for (Zhiban t : zb) {
                    String time = format.format(t.getZhibanTime());
                    timelist=time+","+timelist;
                }

                Zhiban zhiban = new Zhiban();
                zhiban.setCusName(s);
                zhiban.setTimelist(timelist);
                zhibanListback.add(zhiban);
            }
        }
        if (zhibanListback.size()!=0){
            for (Accountant accountant : accountantListback) {
                String time = "";
                for (Zhiban zhiban : zhibanListback) {
                    if (accountant.getCusName().equals(zhiban.getCusName())) {
                        String[] split = accountant.getTravelTimeList().split(",");
                        String[] split1 = zhiban.getTimelist().split(",");
                        for (int i = 0; i < split.length; i++) {
                            for (int i1 = 0; i1 < split1.length; i1++) {
                                if (split[i].equals(split1[i1])){
                                    time=split[i]+","+time;
                                }
                            }
                        }
                    }
                }
                String newtime = "";
                String[] split = time.split(",");
                String[] split1 = accountant.getTravelTimeList().split(",");
                for (String s : split1) {
                    int i = 0;
                    for (String s1 : split) {
                        if (s.equals(s1)){
                            i++;
                        }
                    }
                    if (i==0){
                        newtime = s+","+newtime;
                    }
                }
                accountant.setTravelTimeList(newtime);
            }
        }

        Iterator<Accountant> iterator = accountantListback.iterator();

            while (iterator.hasNext()){
                Accountant next = iterator.next();
                if (next.getTravelTimeList().equals("") || next.getTravelTimeList()==null){
                    iterator.remove();
                }
            }

        Iterator<Zhiban> iterator2 = zhibanListback.iterator();
        while (iterator2.hasNext()){
            Zhiban next = iterator2.next();
            if (next.getTimelist().equals("") || next.getTimelist()==null){
                iterator2.remove();
            }
        }

        return new JsonData(201,"有重复记录",accountantListback,zhibanListback);
    }

}
