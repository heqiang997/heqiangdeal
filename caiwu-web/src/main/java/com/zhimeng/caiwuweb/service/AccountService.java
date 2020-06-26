package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Accountant;

import java.util.List;

/**
 * Created by liupengfei on 2018/10/25 10:58
 */
public interface AccountService {
    /**
     * 会计审核
     * @param coding
     * @return
     */
    JsonData updateStatus(String coding);

}
