package com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.account;


import com.example.yong04zhou.registerlogin.loginregister.base.BaseResponseBean;

import java.io.Serializable;

/**
 * Created by yun.wang
 * Date :2017/6/28
 * Description: ***
 * Version: 1.0.0
 */

public class AccountDetailBean extends BaseResponseBean implements Serializable {

    public AccountData data;
    public String token_status;
}
