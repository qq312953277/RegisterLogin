package com.example.yong04zhou.registerlogin.loginregister.data.remote;


import android.content.Context;

import com.example.yong04zhou.registerlogin.loginregister.data.remote.http.HttpRemoteDataRepository;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;


/**
 * Created by wenhui02.liu on 2016/11/3.
 */
public class RemoteDataRepository {

    private static final String TAG = "RemoteDataRepository";
    private HttpRemoteDataRepository mHttpRemoteDataRepository;

    public RemoteDataRepository(Context context) {
        mHttpRemoteDataRepository = new HttpRemoteDataRepository();
    }

    /******************************************HTTP Remote Data**************************************/
    /**
     * getAuthorization
     *
     * @param subscriber
     * @param map
     */
    public Subscription getAuthorization(Object liftcycleObj,
                                         Observer subscriber,
                                         Map<String, String> map) {
        return mHttpRemoteDataRepository.getAuthorization(liftcycleObj, subscriber, map);
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
        return mHttpRemoteDataRepository.verifyCode(liftcycleObj, subscriber, map);
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
        return mHttpRemoteDataRepository.captcha(liftcycleObj, subscriber, map);
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
        return mHttpRemoteDataRepository.checkPhoneNumber(liftcycleObj, subscriber, map);
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
        return mHttpRemoteDataRepository.login(liftcycleObj, subscriber, body);
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
        return mHttpRemoteDataRepository.register(liftcycleObj, subscriber, body);
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
        return mHttpRemoteDataRepository.forgetPassword(liftcycleObj, subscriber, body);
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
        return mHttpRemoteDataRepository.refreshToken(liftcycleObj, subscriber, refreshToken, map);
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
        return mHttpRemoteDataRepository.accountDetail(liftcycleObj, subscriber, accessToken);
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
        return mHttpRemoteDataRepository.modifyPassword(liftcycleObj, subscriber, accessToken, body);
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
        return mHttpRemoteDataRepository.modifyProperty(liftcycleObj, subscriber, accessToken, body);
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
        return mHttpRemoteDataRepository.uploadAvatar(liftcycleObj, subscriber, accessToken, file, type);
    }


}
