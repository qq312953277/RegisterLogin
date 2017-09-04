package com.example.yong04zhou.registerlogin.loginregister.forgetpassword;

import com.example.yong04zhou.registerlogin.loginregister.base.BaseResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.base.BaseView;

/**
 * Created by yun.wang
 * Date :2017/6/26
 * Description: ***
 * Version: 1.0.0
 */

public interface ForgetPasswordContract {

    interface View extends BaseView {
        //点击获取验证码按钮后，倒计时60s
        void startVerfyCodeTimerTask();

        //解析http response
        void analysisResponseBean(BaseResponseBean t);
    }

    interface Presenter {

        //request phicomm authorization
        void doAuthorization(String client_id, String response_type, String scope, String client_secret);

        //reset account password
        void doResetPassword(String authorizationcode, String phonenumber, String newpassword, String verificationcode);

        //get image code
        void doGetCaptchaImageCode(String authorizationcode);

        //check valid account
        void doCheckAccountRegistered(String authorizationcode, String phonenumber);

        //get sms verify code
        void doGetVerifyCode(String authorizationcode, String phonenumber, String verificationtype, String captcha, String captchaid);
    }
}
