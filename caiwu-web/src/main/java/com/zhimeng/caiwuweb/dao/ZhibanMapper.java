package com.zhimeng.caiwuweb.dao;

import com.zhimeng.caiwuweb.entity.DateVo;
import com.zhimeng.caiwuweb.entity.Zhiban;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZhibanMapper {

    int insert(Zhiban record);

    int insertSelective(Zhiban record);

    int deleteByCoding(Integer id);

    Zhiban selectByNameTime(Zhiban zhiban);

    List<Zhiban> selectByName(String name);

    List<Zhiban> selectById(Zhiban zhiban);

    List<Zhiban> selectByBatchNumber(String batchNumber);

    List<Zhiban> selectByNameStatus(Zhiban zhiban);

    List<Zhiban> selectBetweenTime(DateVo vo);

    int updateByPrimaryKeySelective(Zhiban zhiban);

    int updateByzhiban(Zhiban zhiban);

    int updateByCodingzhiban(Zhiban zhiban);

    List<Zhiban> selectByCoding(Zhiban zhiban);

//    List<Zhiban> selectByid(Integer id);

    List<Zhiban> selectAll(Zhiban zhiban);

}