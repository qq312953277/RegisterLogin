package com.example.yong04zhou.registerlogin.loginregister.data.remote.http;

import com.example.yong04zhou.registerlogin.loginregister.base.BaseResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.utils.LogUtils;
import com.trello.rxlifecycle.components.RxActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpRemoteDataRepository {

    private static final String TAG = "HttpRemoteData";
    private static final int DEFAULT_TIMEOUT = 15;
    private Retrofit phicommCommonRetrofit;
    private Retrofit phicommAvatarRetrofit;
    private HttpCloudInterface myHttpApiInterface;
    private HttpAvatarInterface avaterHttpApiInterface;


    //http log
    class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            LogUtils.d("OkHttpLogInfo", message);
        }
    }

    private Retrofit getRetrofit(String baseUrl, String retrofitId) {
        //log
        HttpLoggingInterceptor okhttpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        okhttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //https cer
        HttpsCerUtils.SSLParams sslParams = HttpsCerUtils.getSslSocketFactory(null, null, null);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(okhttpLoggingInterceptor) //log
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        return new Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public HttpRemoteDataRepository() {
        phicommCommonRetrofit = getRetrofit(HttpConfig.PHICOMM_CLOUD_BASE_URL, "0");
        myHttpApiInterface = phicommCommonRetrofit.create(HttpCloudInterface.class);

        phicommAvatarRetrofit = getRetrofit(HttpConfig.PHICOMM_AVATER_BASE_URL, "0");
        avaterHttpApiInterface = phicommAvatarRetrofit.create(HttpAvatarInterface.class);


    }

    public <T> Observable.Transformer<? super T, ?> getLifeCycleObj(Object liftcycleObj, Class<T> t) {
        if (liftcycleObj instanceof RxActivity) {
            RxActivity rxObj = (RxActivity) liftcycleObj;
            return ((rxObj.<T>bindToLifecycle()));

        } else if (liftcycleObj instanceof RxFragmentActivity) {
            RxFragmentActivity rxObj = (RxFragmentActivity) liftcycleObj;
            return ((rxObj.<T>bindToLifecycle()));

        } else if (liftcycleObj instanceof RxFragment) {
            RxFragment rxObj = (RxFragment) liftcycleObj;
            return ((rxObj.<T>bindToLifecycle()));

        } else if (liftcycleObj instanceof RxAppCompatActivity) {
            RxAppCompatActivity rxObj = (RxAppCompatActivity) liftcycleObj;
            return ((rxObj.<T>bindToLifecycle()));

        }
        return null;
    }

    /**
     * getAuthorization
     *
     * @param subscriber
     * @param map
     */
    //RxJava中有个叫做Subscription的接口,可以用来取消订阅
    public Subscription getAuthorization(@NotNull Object liftcycleObj,
                                         Observer subscriber,
                                         Map<String, String> map) {
        return myHttpApiInterface.authorization(map)  //return Observable<AuthorizationResponseBean>
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * verifyCode
     *
     * @param subscriber
     * @param map
     */
    public Subscription verifyCode(@NotNull Object liftcycleObj,
                                   Observer subscriber,
                                   Map<String, String> map) {
        return myHttpApiInterface.verifyCodeCaptcha(map)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * captcha
     *
     * @param subscriber
     * @param map
     */
    public Subscription captcha(@NotNull Object liftcycleObj,
                                Observer subscriber,
                                Map<String, String> map) {
        return myHttpApiInterface.captcha(map)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * checkPhoneNumber
     *
     * @param subscriber
     * @param map
     */
    public Subscription checkPhoneNumber(@NotNull Object liftcycleObj,
                                         Observer subscriber,
                                         Map<String, String> map) {
        return myHttpApiInterface.checkPhonenumber(map)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * login
     *
     * @param subscriber
     * @param body
     */
    public Subscription login(@NotNull Object liftcycleObj,
                              Observer subscriber,
                              RequestBody body) {
        return myHttpApiInterface.login(body)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * register
     *
     * @param subscriber
     * @param body
     */
    public Subscription register(@NotNull Object liftcycleObj,
                                 Observer subscriber,
                                 RequestBody body) {
        return myHttpApiInterface.register(body)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * forgetPassword
     *
     * @param subscriber
     * @param body
     */
    public Subscription forgetPassword(@NotNull Object liftcycleObj,
                                       Observer subscriber,
                                       RequestBody body) {
        return myHttpApiInterface.forgetPassword(body)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * refreshToken
     *
     * @param subscriber
     * @param map
     */
    public Subscription refreshToken(@NotNull Object liftcycleObj,
                                     Observer subscriber,
                                     String refreshToken,
                                     Map<String, String> map) {
        return myHttpApiInterface.refreshToken(refreshToken, map)
//                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class)) //refresh token neednot liftcycleobj
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * accountDetail
     *
     * @param subscriber
     * @param accessToken
     */
    public Subscription accountDetail(@NotNull Object liftcycleObj,
                                      Observer subscriber,
                                      String accessToken) {
        return myHttpApiInterface.accountDetail(accessToken)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * modifyPassword
     *
     * @param subscriber
     * @param body
     */
    public Subscription modifyPassword(@NotNull Object liftcycleObj,
                                       Observer subscriber,
                                       String accessToken,
                                       RequestBody body) {
        return myHttpApiInterface.modifyPassword(accessToken, body)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * modifyProperty
     *
     * @param subscriber
     * @param accessToken
     * @param body
     */
    public Subscription modifyProperty(@NotNull Object liftcycleObj,
                                       Observer subscriber,
                                       String accessToken,
                                       RequestBody body) {
        return myHttpApiInterface.modifyProperty(accessToken, body)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * @param subscriber
     * @param accessToken
     * @param file
     * @param type
     */
    public Subscription uploadAvatar(@NotNull Object liftcycleObj,
                                     Observer subscriber,
                                     String accessToken, File file, String type) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody typeBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), type);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("type", typeBody);
        return avaterHttpApiInterface.uploadAvatar(accessToken, filePart, map)
                .compose(getLifeCycleObj(liftcycleObj, BaseResponseBean.class))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
