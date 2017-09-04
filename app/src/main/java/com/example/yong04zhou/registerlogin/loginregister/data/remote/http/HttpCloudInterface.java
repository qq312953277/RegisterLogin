package com.example.yong04zhou.registerlogin.loginregister.data.remote.http;


import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.account.AccountDetailBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.account.ModifypasswordResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.account.ModifypropertyResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.AuthorizationResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.CaptchaResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.CheckphonenumberResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.ForgetpasswordResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.LoginResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.RegisterResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.TokenUpdateBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.VerifycodeResponseBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by yun.wang
 * Date :2017/6/26
 * Description: ***
 * Version: 1.0.0
 */

public interface HttpCloudInterface {

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("authorization")
    Observable<AuthorizationResponseBean> authorization(@QueryMap Map<String, String> map);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("verificationCode")
    Observable<VerifycodeResponseBean> verifyCode(@QueryMap Map<String, String> map);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("verificationMsg")
    Observable<VerifycodeResponseBean> verifyCodeCaptcha(@QueryMap Map<String, String> map);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("captcha")
    Observable<CaptchaResponseBean> captcha(@QueryMap Map<String, String> map);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("checkPhonenumber")
    Observable<CheckphonenumberResponseBean> checkPhonenumber(@QueryMap Map<String, String> map);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("token")
    Observable<TokenUpdateBean> refreshToken(@Header("Authorization") String access_token, @QueryMap Map<String, String> map);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("login")
    Observable<LoginResponseBean> login(@Body RequestBody body);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("account")
    Observable<RegisterResponseBean> register(@Body RequestBody body);

    @POST("forgetpassword")
    Observable<ForgetpasswordResponseBean> forgetPassword(@Body RequestBody body);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("accountDetail")
    Observable<AccountDetailBean> accountDetail(@Header("Authorization") String access_token);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("password")
    Observable<ModifypasswordResponseBean> modifyPassword(@Header("Authorization") String access_token, @Body RequestBody body);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("property")
    Observable<ModifypropertyResponseBean> modifyProperty(@Header("Authorization") String access_token, @Body RequestBody body);
}
