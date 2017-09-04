package com.example.yong04zhou.registerlogin.loginregister.register;

import com.example.yong04zhou.registerlogin.loginregister.base.BaseResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.base.BaseView;

/**
 * Created by yong04.zhou on 2017/8/31.
 */

public interface RegisterContract {


    interface View extends BaseView {

        //点击获取验证码按钮后，倒计时60s
        void startVerfyCodeTimerTask();

        //解析http response
        void analysisResponseBean(BaseResponseBean t);
    }

    interface Presenter {

        //request phicomm authorization
        void doAuthorization(String client_id, String response_type, String scope, String client_secret);

        //register new account
        void doRegister(String authorizationcode, String phonenumber, String password, String registersource, String verificationcode);

        //check whether the account is registered
        void doCheckAccountRegistered(String authorizationcode, String phonenumber);

        //get sms verify code
        void doGetVerifyCode(String authorizationcode, String phonenumber, String verificationtype, String captcha, String captchaid);

        //get image code
        void doGetCaptchaImageCode(String authorizationcode);

        //request phicomm login
        void doPhoneLogin(String authorizationcode, String phonenumber, String password);
    }
}
