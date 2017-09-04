package com.example.yong04zhou.registerlogin.loginregister.login;

import com.example.yong04zhou.registerlogin.loginregister.base.BaseResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.base.BaseView;

/**
 * Created by yun.wang
 * Date :2017/6/26
 * Description: ***
 * Version: 1.0.0
 */

public interface LoginContract {
    interface View extends BaseView {
        //解析http response
        void analysisResponseBean(BaseResponseBean t);
    }

    interface Presenter {
        //request phicomm authorization
        void doAuthorization(String client_id, String response_type, String scope, String client_secret);

        //request phicomm login
        void doPhoneLogin(String authorizationcode, String phonenumber, String password);

        //request qq login
        void doQQLogin();

        //request wechat login
        void doWechatLogin();
    }
}
