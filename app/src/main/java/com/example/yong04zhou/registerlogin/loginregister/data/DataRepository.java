package com.example.yong04zhou.registerlogin.loginregister.data;

import android.content.Context;

import com.example.yong04zhou.registerlogin.loginregister.data.local.LocalDataRepository;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.RemoteDataRepository;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.account.AccountDetailBean;
import com.example.yong04zhou.registerlogin.loginregister.utils.AESEncryptorUtils;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;

/**
 * Created by yong04.zhou on 2017/8/31.
 */

public class DataRepository {

    private static DataRepository INSTANCE = null;
    private LocalDataRepository mLocalDataRepository;
    private RemoteDataRepository mRemoteDataRepository;

    private DataRepository(Context context) {
        mLocalDataRepository = new LocalDataRepository(context);
        mRemoteDataRepository = new RemoteDataRepository(context);
    }
    public static DataRepository getInstance() {
        return INSTANCE;
    }

    public static DataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(context);
        }
        return INSTANCE;
    }



    /**
     * 获取授权码
     */
    public String getAuthorizationCode() {


        return AESEncryptorUtils.decrypt(mLocalDataRepository.getAuthorizationCode());
    }

    /**
     * 设置授权码
     */
    public void setAuthorizationCode(String code) {
        mLocalDataRepository.setAuthorizationCode(AESEncryptorUtils.encrypt(code));
    }

    /**
     * app是否引导过
     *
     * @return
     */
    public boolean isAppGuided() {
        return mLocalDataRepository.isAppGuided();
    }

    /**
     * 记录下app已经被用户引导过了
     */
    public void setAppGuided(boolean isguided) {
        mLocalDataRepository.setAppGuided(isguided);
    }

    /**
     * 云端之前是否已经登录了
     *
     * @return 登录状态
     */
    public boolean isCloudLogined() {
        return mLocalDataRepository.isCloudLogined();
    }

    /**
     * 设置云端的登录状态
     *
     * @param loginStatus 已登录为true，已登出为false
     */
    public void setCloudLoginStatus(boolean loginStatus) {
        mLocalDataRepository.setCloudLoginStatus(loginStatus);
    }

    /**
     * 设置用户的基本信息
     */
    public void setAccountDetailInfo(AccountDetailBean accountDetailBean) {
        mLocalDataRepository.setAccountDetailInfo(accountDetailBean);
    }

    /**
     * 获取用户的基本信息
     *
     * @return
     */
    public AccountDetailBean getAccountDetailInfo() {
        return (AccountDetailBean) mLocalDataRepository.getAccountDetailInfo();
    }

    /**
     * getAccessToken
     *
     * @return
     */
    public String getAccessToken() {
        return (String) AESEncryptorUtils.decrypt(mLocalDataRepository.getAccessToken());
    }

    /**
     * setAccessToken
     */
    public void setAccessToken(String accessToken) {
        mLocalDataRepository.setAccessToken(AESEncryptorUtils.encrypt(accessToken));
    }

    /**
     * getRefreshToken
     *
     * @return
     */
    public String getRefreshToken() {
        return (String) AESEncryptorUtils.decrypt(mLocalDataRepository.getRefreshToken());
    }

    /**
     * setRefreshToken
     *
     * @param refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        mLocalDataRepository.setRefreshToken(AESEncryptorUtils.encrypt(refreshToken));
    }

    /**
     * getAccessValidity
     *
     * @return
     */
    public Long getAccessValidity() {
        return (Long) mLocalDataRepository.getAccessValidity();
    }

    /**
     * setAccessValidity
     *
     * @param accessValidity
     */
    public void setAccessValidity(Long accessValidity) {
        mLocalDataRepository.setAccessValidity(accessValidity);
    }

    /**
     * getRefreshValidity
     *
     * @return
     */
    public Long getRefreshValidity() {
        return (Long) mLocalDataRepository.getRefreshValidity();
    }

    /**
     * setRefreshValidity
     *
     * @param refreshValidity
     */
    public void setRefreshValidity(Long refreshValidity) {
        mLocalDataRepository.setRefreshValidity(refreshValidity);
    }

    /**
     * getRefreshStartTime
     *
     * @return
     */
    public Long getRefreshStartTime() {
        return (Long) mLocalDataRepository.getRefreshStartTime();
    }

    /**
     * setRefreshStartTime
     *
     * @param refreshStartTime
     */
    public void setRefreshStartTime(Long refreshStartTime) {
        mLocalDataRepository.setRefreshStartTime(refreshStartTime);
    }

    /**
     * V
     *
     * @return
     */
    public Long getAccessStartTime() {
        return (Long) mLocalDataRepository.getAccessStartTime();
    }

    /**
     * setAccessStartTime
     *
     * @param accessStartTime
     */
    public void setAccessStartTime(Long accessStartTime) {
        mLocalDataRepository.setAccessStartTime(accessStartTime);
    }

    /**
     * getUserName
     */
    public String getUserName() {
        return AESEncryptorUtils.decrypt(mLocalDataRepository.getUserName());
    }

    /**
     * setUsername
     *
     * @param username
     */
    public void setUsername(String username) {
        mLocalDataRepository.setUsername(AESEncryptorUtils.encrypt(username));
    }

    /**
     * getPassword
     */
    public String getPassword() {
        return AESEncryptorUtils.decrypt(mLocalDataRepository.getPassword());
    }

    /**
     * setPassword
     *
     * @param password
     */
    public void setPassword(String password) {
        mLocalDataRepository.setPassword(AESEncryptorUtils.encrypt(password));
    }

    /**
     * getRememberMe
     *
     * @return
     */
    public boolean getRememberMe() {
        return mLocalDataRepository.getRememberMe();
    }

    /**
     * setRememberMe
     *
     * @param rememberMe
     */
    public void setRememberMe(boolean rememberMe) {
        mLocalDataRepository.setRememberMe(rememberMe);
    }

    /**
     * 退出登录
     * 清除all SP
     */
    public void clearAllSP() {
        mLocalDataRepository.clearAllSP();
    }

    /**
     * 退出制定的SP
     */
    public void clearSPByName(String SP) {
        mLocalDataRepository.clearSPByName(SP);
    }

    /**
     * 保存wifi信息
     *
     * @param ssid     账号
     * @param password 密码
     */
    public void setWifiPassword(String ssid, String password) {
        mLocalDataRepository.setWifiPassword(ssid, password);
    }

    public String getWifiPassword(String ssid) {
        return mLocalDataRepository.getWifiPassword(ssid);
    }
    /******************************************Remote Data**************************************/

    /**
     * getAuthorization
     *
     * @param subscriber
     * @param map
     */
    public Subscription getAuthorization(Object liftcycleObj,
                                         Observer subscriber,
                                         Map<String, String> map) {
        return mRemoteDataRepository.getAuthorization(liftcycleObj, subscriber, map);
    }

    /**
     * verifyCode
     *
     * @param subscriber
     * @param map
     */
    public Subscription verifyCode(Object liftcycleObj,
                                   Observer subscriber,
                                   Map<String, String> map) {
        return mRemoteDataRepository.verifyCode(liftcycleObj, subscriber, map);
    }

    /**
     * captcha
     *
     * @param subscriber
     * @param map
     */
    public Subscription captcha(Object liftcycleObj,
                                Observer subscriber,
                                Map<String, String> map) {
        return mRemoteDataRepository.captcha(liftcycleObj, subscriber, map);
    }

    /**
     * checkPhoneNumber
     *
     * @param subscriber
     * @param map
     */
    public Subscription checkPhoneNumber(Object liftcycleObj,
                                         Observer subscriber,
                                         Map<String, String> map) {
        return mRemoteDataRepository.checkPhoneNumber(liftcycleObj, subscriber, map);
    }

    /**
     * login
     *
     * @param subscriber
     * @param body
     */
    public Subscription login(Object liftcycleObj,
                              Observer subscriber,
                              RequestBody body) {
        return mRemoteDataRepository.login(liftcycleObj, subscriber, body);
    }

    /**
     * register
     *
     * @param subscriber
     * @param body
     */
    public Subscription register(Object liftcycleObj,
                                 Observer subscriber,
                                 RequestBody body) {
        return mRemoteDataRepository.register(liftcycleObj, subscriber, body);
    }

    /**
     * forgetPassword
     *
     * @param subscriber
     * @param body
     */
    public Subscription forgetPassword(Object liftcycleObj,
                                       Observer subscriber,
                                       RequestBody body) {
        return mRemoteDataRepository.forgetPassword(liftcycleObj, subscriber, body);
    }

    /**
     * refreshToken
     *
     * @param subscriber
     * @param map
     */
    public Subscription refreshToken(Object liftcycleObj,
                                     Observer subscriber,
                                     String refreshToken,
                                     Map<String, String> map) {
        return mRemoteDataRepository.refreshToken(liftcycleObj, subscriber, refreshToken, map);
    }

    /**
     * accountDetail
     *
     * @param subscriber
     * @param accessToken
     */
    public Subscription accountDetail(Object liftcycleObj,
                                      Observer subscriber,
                                      String accessToken) {
        return mRemoteDataRepository.accountDetail(liftcycleObj, subscriber, accessToken);
    }

    /**
     * modifyPassword
     *
     * @param subscriber
     * @param body
     */
    public Subscription modifyPassword(Object liftcycleObj,
                                       Observer subscriber,
                                       String accessToken,
                                       RequestBody body) {
        return mRemoteDataRepository.modifyPassword(liftcycleObj, subscriber, accessToken, body);
    }

    /**
     * modifyProperty
     *
     * @param subscriber
     * @param accessToken
     * @param body
     */
    public Subscription modifyProperty(Object liftcycleObj,
                                       Observer subscriber,
                                       String accessToken,
                                       RequestBody body) {
        return mRemoteDataRepository.modifyProperty(liftcycleObj, subscriber, accessToken, body);
    }

    /**
     * @param subscriber
     * @param accessToken
     * @param file
     * @param type
     */
    public Subscription uploadAvatar(Object liftcycleObj,
                                     Observer subscriber,
                                     String accessToken, File file, String type) {
        return mRemoteDataRepository.uploadAvatar(liftcycleObj, subscriber, accessToken, file, type);
    }


}
