package com.example.yong04zhou.registerlogin.loginregister.data.remote.http;

import com.example.yong04zhou.registerlogin.R;
import com.example.yong04zhou.registerlogin.loginregister.base.BaseApplication;
import com.example.yong04zhou.registerlogin.loginregister.base.BaseResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.account.ModifypasswordResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.CaptchaResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.CheckphonenumberResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.LoginResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.RegisterResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.TokenUpdateBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.VerifycodeResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.event.DeviceListResultEvent;
import com.example.yong04zhou.registerlogin.loginregister.event.LogoutEvent;
import com.example.yong04zhou.registerlogin.loginregister.event.MultiLogoutEvent;
import com.example.yong04zhou.registerlogin.loginregister.utils.CommonUtils;
import com.example.yong04zhou.registerlogin.loginregister.utils.LogUtils;
import com.example.yong04zhou.registerlogin.loginregister.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by yun.wang
 * Date :2017/6/26
 * Description: ***
 * Version: 1.0.0
 */

public abstract class CustomSubscriber<T> extends Subscriber<T> {

    private final String TAG = "OkHttpLogInfo";

    abstract public void onCustomNext(T t);

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            LogUtils.d(TAG, "onError:" + e.toString());
        }

        //get errorstring by exception type
        int errorStringRes = R.string.common_error;
        if (e instanceof SocketTimeoutException) {
            errorStringRes = R.string.connect_timeout;
        } else if (e instanceof UnknownHostException || e instanceof SocketException) {
            errorStringRes = R.string.net_connect_fail;
        }

        CommonUtils.showToastBottom(BaseApplication.getContext().getString(errorStringRes));

    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onNext(T t) {
        if (interceptAbnormalResponse(t)) {
            return;
        }
        onCustomNext(t);
    }

    public boolean interceptAbnormalResponse(T t) {
        if (t instanceof BaseResponseBean) {
            BaseResponseBean baseResponseBean = (BaseResponseBean) t;
            if (StringUtils.isNull(baseResponseBean.error) || baseResponseBean.error.equals("0")) {
                return false;//not intercept
            } else {
                try {

                    //token异常问题需要提前拦截
                    if (baseResponseBean.error.equals("5") //token失效
                            || baseResponseBean.error.equals("26")) { //账户已退出
                        //send logout event

                        EventBus.getDefault().post(new LogoutEvent());
                        return true;
                    } else if (baseResponseBean.error.equals("30")) { //多端登录
                        //multi login,need logout

                        EventBus.getDefault().post(new MultiLogoutEvent());
                        return true;
                    }

                    //not intercept list
                    if (t instanceof TokenUpdateBean
                            || t instanceof CaptchaResponseBean
                            || t instanceof VerifycodeResponseBean
                            || t instanceof CheckphonenumberResponseBean
                            || t instanceof RegisterResponseBean) {
                        return false;//not intercept
                    }


                    if (baseResponseBean.error.equals("113") || baseResponseBean.error.equals("100")) {
                        //no device binded，设备列表为空
                        EventBus.getDefault().post(new DeviceListResultEvent(baseResponseBean.error));
                        return true;
                    }

                    //handler abnormal resonose
                    int errorCode = Integer.parseInt(baseResponseBean.error);
                    int errorStringRes = HttpErrorCode.getErrorStringByErrorCode(errorCode);

                    if (errorStringRes == R.string.password_error && t instanceof ModifypasswordResponseBean) {
                        errorStringRes = R.string.old_password_error;
                    } else if (errorStringRes == R.string.password_error && t instanceof LoginResponseBean) {
                        errorStringRes = R.string.account_password_not_match;
                    }

                    CommonUtils.showToastBottom(BaseApplication.getContext().getString(errorStringRes));
                } catch (Exception ex) {

                }

                return true;
            }
        }
        return false;
    }

}
