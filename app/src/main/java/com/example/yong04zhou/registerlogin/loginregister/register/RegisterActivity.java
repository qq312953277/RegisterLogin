package com.example.yong04zhou.registerlogin.loginregister.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yong04zhou.registerlogin.MainActivity;
import com.example.yong04zhou.registerlogin.R;
import com.example.yong04zhou.registerlogin.loginregister.base.BaseResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.constant.AppConstants;
import com.example.yong04zhou.registerlogin.loginregister.data.DataRepository;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.AuthorizationResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.CaptchaResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.CheckphonenumberResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.LoginResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.RegisterResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.VerifycodeResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.data.remote.http.HttpErrorCode;
import com.example.yong04zhou.registerlogin.loginregister.utils.CommonUtils;
import com.example.yong04zhou.registerlogin.loginregister.utils.MD5Utils;
import com.example.yong04zhou.registerlogin.loginregister.utils.NetworkManagerUtils;
import com.example.yong04zhou.registerlogin.loginregister.utils.StringUtils;
import com.example.yong04zhou.registerlogin.token.TokenManager;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

// .compose(this.<Integer>bindToLifecycle())   ->  RxFragmentActivity
public class RegisterActivity extends RxFragmentActivity implements RegisterContract.View {

    @BindView(R.id.phoneNumber)
    EditText mPhoneNumberEdit;

    @BindView(R.id.code)
    EditText mCodeEdit;

    @BindView(R.id.captcha)
    EditText mCaptchaEdit;

    @BindView(R.id.captcha_code)
    ImageView mCaptchaImage;

    @BindView(R.id.btn_code)
    Button mGetCodeBtn;

    @BindView(R.id.password)
    EditText mPasswordEdit;


    @BindView(R.id.password_display_imageview)
    ImageView password_display_imageview;


    @BindView(R.id.bt_register)
    Button mRegister;

    @BindView(R.id.protocal_checkbox)
    CheckBox mProtocalCheckbox;

    private boolean isPasswordDisplay = true;
    private boolean isProtocalChecked = false;
    private String phoneNo = "";
    private String verifyCode = "";
    private String captchaCode = "";
    private String captchaId = "";
    private String password = "";
    private String mAuthorizationCode = "";
    private boolean timerRunning = false;

    private RegisterContract.Presenter registerPresenter;
    private static final int INPUT_TEXT_SIZE = 14;
    private static final int INPUT_PASSWORD_HINT_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        password_display_imageview.setEnabled(true);
        registerPresenter = new RegisterPresenter(this);

        setEditTextListener();
        initViews();
        //get image code first
        getCaptchaCode();

    }


    private void setEditTextListener() {
        RxTextView.afterTextChangeEvents(mPhoneNumberEdit).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
            @Override
            public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                phoneNo = textViewAfterTextChangeEvent.editable().toString();
                if (!StringUtils.isNull(phoneNo)
                        && !StringUtils.isNull(captchaCode)
                        && !timerRunning) {
                    mGetCodeBtn.setEnabled(true);
                } else {
                    mGetCodeBtn.setEnabled(false);
                }

                if (!StringUtils.isNull(phoneNo)
                        && !StringUtils.isNull(captchaCode)
                        && !StringUtils.isNull(verifyCode)
                        && !StringUtils.isNull(password)
                        && isProtocalChecked) {
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setEnabled(false);
                }
            }
        });

        RxTextView.afterTextChangeEvents(mCaptchaEdit).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
            @Override
            public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                captchaCode = textViewAfterTextChangeEvent.editable().toString();
                if (!StringUtils.isNull(phoneNo)
                        && !StringUtils.isNull(captchaCode)
                        && !timerRunning) {
                    mGetCodeBtn.setEnabled(true);
                } else {
                    mGetCodeBtn.setEnabled(false);
                }

                if (!StringUtils.isNull(phoneNo)
                        && !StringUtils.isNull(captchaCode)
                        && !StringUtils.isNull(verifyCode)
                        && !StringUtils.isNull(password)
                        && isProtocalChecked) {
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setEnabled(false);
                }
            }
        });

        RxTextView.afterTextChangeEvents(mCodeEdit).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
            @Override
            public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                verifyCode = textViewAfterTextChangeEvent.editable().toString();
                if (!StringUtils.isNull(phoneNo)
                        && !StringUtils.isNull(captchaCode)
                        && !StringUtils.isNull(verifyCode)
                        && !StringUtils.isNull(password)
                        && isProtocalChecked) {
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setEnabled(false);
                }
            }
        });

        RxTextView.afterTextChangeEvents(mPasswordEdit).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
            @Override
            public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                password = textViewAfterTextChangeEvent.editable().toString();

                if (!StringUtils.isNull(password)) {
                    mPasswordEdit.setTextSize(INPUT_TEXT_SIZE);
                } else {
                    mPasswordEdit.setTextSize(INPUT_PASSWORD_HINT_SIZE);
                }

                if (!StringUtils.isNull(phoneNo)
                        && !StringUtils.isNull(captchaCode)
                        && !StringUtils.isNull(verifyCode)
                        && !StringUtils.isNull(password)
                        && isProtocalChecked) {
                    mRegister.setEnabled(true);
                } else {
                    mRegister.setEnabled(false);
                }

            }
        });
    }

    @OnClick(R.id.captcha_code)
    public void getCaptchaCode() {
        if (NetworkManagerUtils.instance().networkError()) {
            return;
        }

        mAuthorizationCode = DataRepository.getInstance(this).getAuthorizationCode();

        if (StringUtils.isNull(mAuthorizationCode)) {
            registerPresenter.doAuthorization(AppConstants.CLIENT_ID, AppConstants.RESPONSE_TYPE, AppConstants.SCOPE, AppConstants.CLIENT_SECRET);
        } else {
            mCaptchaImage.setImageResource(R.drawable.captcha_loading);
            registerPresenter.doGetCaptchaImageCode(mAuthorizationCode);
        }
    }

    @OnClick(R.id.password_display_imageview)
    public void clickDisplayPassword() {
        if (isPasswordDisplay) {
            isPasswordDisplay = false;
            password_display_imageview.setImageResource(R.drawable.icon_eye_open_white);
            //显示密码
            mPasswordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isPasswordDisplay = true;
            password_display_imageview.setImageResource(R.drawable.icon_eye_close_white);
            //隐藏密码
            mPasswordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mPasswordEdit.setSelection(mPasswordEdit.getText().toString().length());
    }

    private void initViews() {
        password_display_imageview.setEnabled(true);

        //防抖
        RxView.clicks(mGetCodeBtn)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        getVerifyCode();
                    }
                });
    }


    /**
     * 获取验证码
     */
    public void getVerifyCode() {
        if (!CommonUtils.checkMobile(mPhoneNumberEdit.getText().toString())) {
            Toast.makeText(this, R.string.phonenum_is_illegal, Toast.LENGTH_SHORT).show();
            return;
        }

        captchaCode = mCaptchaEdit.getText().toString();
        if (StringUtils.isNull(captchaCode)) {
            Toast.makeText(this, R.string.input_captcha_code, Toast.LENGTH_SHORT).show();
            return;
        }

        if (NetworkManagerUtils.instance().networkError()) {
            return;
        }

        mGetCodeBtn.setEnabled(false);
        mAuthorizationCode = DataRepository.getInstance().getAuthorizationCode();
        if (StringUtils.isNull(mAuthorizationCode)) {
            registerPresenter.doAuthorization(AppConstants.CLIENT_ID, AppConstants.RESPONSE_TYPE, AppConstants.SCOPE, AppConstants.CLIENT_SECRET);
        } else {
            registerPresenter.doCheckAccountRegistered(mAuthorizationCode, mPhoneNumberEdit.getText().toString());
        }
    }

    /**
     * 斐讯云注册
     */
    @OnClick(R.id.bt_register)
    public void register() {
        phoneNo = mPhoneNumberEdit.getText().toString();
        verifyCode = mCodeEdit.getText().toString();
        password = mPasswordEdit.getText().toString();

        captchaCode = mCaptchaEdit.getText().toString();

        if (StringUtils.isNull(phoneNo)) {
            Toast.makeText(this, "手机不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonUtils.checkMobile(phoneNo)) {
            Toast.makeText(this, "手机号非法", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.isNull(verifyCode)) {
            Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.isNull(captchaCode)) {
            Toast.makeText(this, "请输入图形验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.isNull(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "密码长度不够", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!CommonUtils.isPassword(password)) {
            Toast.makeText(this, "密码格式错误", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mProtocalCheckbox.isChecked()) {
            Toast.makeText(this, "请阅读并同意《用户注册协议》", Toast.LENGTH_SHORT).show();
            return;
        }

        if (NetworkManagerUtils.instance().networkError()) {
            return;
        }

        mAuthorizationCode = DataRepository.getInstance().getAuthorizationCode();
        password = MD5Utils.encryptedByMD5(password);
        if (StringUtils.isNull(mAuthorizationCode)) {
            registerPresenter.doAuthorization(AppConstants.CLIENT_ID, AppConstants.RESPONSE_TYPE, AppConstants.SCOPE, AppConstants.CLIENT_SECRET);
        } else {
            registerPresenter.doRegister(mAuthorizationCode, phoneNo, password, AppConstants.REQUEST_SOURCE, verifyCode);
        }
    }


    @Override
    public Object getRxLifeCycleObj() {
        return this;
    }

    /**
     * 点击获取验证码按钮后，倒计时60s
     */
    @Override
    public void startVerfyCodeTimerTask() {
        final int timer = 60;
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> arg0) {
                arg0.onStart();
                int i = timer - 1;
                while (i >= 0) {
                    try {
                        Thread.sleep(1000);
                        arg0.onNext(i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    i--;
                }
                arg0.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .compose(this.<Integer>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onStart() {
                        super.onStart();

                        int second = timer;
                        String showText = String.format("%s s", second);
                        mGetCodeBtn.setText(showText + getResources().getString(R.string.verifycode_resend));

                        mGetCodeBtn.setEnabled(false);
                        timerRunning = true;
                    }

                    @Override
                    public void onCompleted() {
                        mGetCodeBtn.setText(R.string.send_again);
                        mGetCodeBtn.setEnabled(true);
                        timerRunning = false;
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        //do nothing
                    }

                    @Override
                    public void onNext(Integer arg0) {
                        int second = arg0;
                        String showText = String.format("%s s", second);
                        mGetCodeBtn.setText(showText + getResources().getString(R.string.verifycode_resend));
                    }
                });
    }

    @Override
    public void analysisResponseBean(BaseResponseBean t) {
        if (t instanceof AuthorizationResponseBean) {
            AuthorizationResponseBean bean = (AuthorizationResponseBean) t;
            if (!StringUtils.isNull(bean.authorizationcode) && bean.error.equals(HttpErrorCode.success)) {
                mAuthorizationCode = bean.authorizationcode;
                DataRepository.getInstance().setAuthorizationCode(mAuthorizationCode);
                mCaptchaImage.setImageResource(R.drawable.captcha_loading);
                registerPresenter.doGetCaptchaImageCode(mAuthorizationCode);
            } else {
                Toast.makeText(this, "授权码错误", Toast.LENGTH_SHORT).show();
            }
        } else if (t instanceof CaptchaResponseBean) {
            CaptchaResponseBean bean = (CaptchaResponseBean) t;
            if (bean.error.equals(HttpErrorCode.success)) {
                captchaId = bean.captchaid;
                Bitmap bitmap = CommonUtils.base64ToBitmap((String) bean.captcha);
                if (bitmap != null) {
                    mCaptchaImage.setImageBitmap(bitmap);
                } else {
                    mCaptchaImage.setImageResource(R.drawable.captcha_no_internet);
                }
            } else {
                //handler abnormal resonose
                int errorCode = Integer.parseInt(bean.error);
                int errorStringRes = HttpErrorCode.getErrorStringByErrorCode(errorCode);
                Toast.makeText(this, getResources().getString(errorStringRes), Toast.LENGTH_SHORT).show();

                mCaptchaImage.setImageResource(R.drawable.captcha_no_internet);
            }
        } else if (t instanceof CheckphonenumberResponseBean) {
            CheckphonenumberResponseBean bean = (CheckphonenumberResponseBean) t;
            if (bean.error.equals(HttpErrorCode.success)) {
                mAuthorizationCode = DataRepository.getInstance().getAuthorizationCode();
                registerPresenter.doGetVerifyCode(mAuthorizationCode, mPhoneNumberEdit.getText().toString(), AppConstants.SMS_VERIFICATION,
                        captchaCode, captchaId);
            } else {
                int errorCode = Integer.parseInt(bean.error);

                if (errorCode == 14) {
                    Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
                } else {
                    int errorStringRes = HttpErrorCode.getErrorStringByErrorCode(errorCode);
                    Toast.makeText(this, getResources().getString(errorStringRes), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (t instanceof VerifycodeResponseBean) {
            VerifycodeResponseBean bean = (VerifycodeResponseBean) t;
            if (bean.error.equals(HttpErrorCode.success)) { //verifyCode、verifyCodeCaptcha都返回Observable<VerifycodeResponseBean>
                Toast.makeText(this, "验证码发送成功！", Toast.LENGTH_SHORT).show();
                startVerfyCodeTimerTask();
            } else {
                //handler captcha error 如果短信验证码错误，则刷新图形码
                int errorCode = Integer.parseInt(bean.error);
                int errorStringRes = HttpErrorCode.getErrorStringByErrorCode(errorCode);
                Toast.makeText(this, getResources().getString(errorStringRes), Toast.LENGTH_SHORT).show();
                mGetCodeBtn.setEnabled(true);

                if (errorStringRes == R.string.get_verifycode_failed
                        || errorStringRes == R.string.captcha_error
                        || errorStringRes == R.string.captcha_expire
                        || errorStringRes == R.string.get_verifycode_too_fast
                        || errorStringRes == R.string.get_verifycode_count_expire
                        ) {
                    //refresh captcha code
                    getCaptchaCode();
                    mCaptchaEdit.setText("");
                }
            }
        } else if (t instanceof RegisterResponseBean) {
            RegisterResponseBean bean = (RegisterResponseBean) t;
            if (bean.error.equals(HttpErrorCode.success)) {
                mAuthorizationCode = DataRepository.getInstance().getAuthorizationCode();
                registerPresenter.doPhoneLogin(mAuthorizationCode, phoneNo, password);
            } else {
                int errorCode = Integer.parseInt(bean.error);

                if (errorCode == 14) {
                    Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
                } else {
                    int errorStringRes = HttpErrorCode.getErrorStringByErrorCode(errorCode);
                    Toast.makeText(this, getResources().getString(errorStringRes), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (t instanceof LoginResponseBean) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

            LoginResponseBean bean = (LoginResponseBean) t;


            String access_token = bean.access_token;
            //保存斐讯云access_token
            DataRepository.getInstance().setAccessToken(access_token);
            DataRepository.getInstance().setCloudLoginStatus(true);
            TokenManager.getInstance().saveTokens(bean.access_token, bean.refresh_token,
                    bean.refresh_token_expire, bean.access_token_expire);

            //保存手机号
            DataRepository.getInstance().setUsername(phoneNo);

            //donot remember me when loging after register
            DataRepository.getInstance().setPassword("");
            DataRepository.getInstance().setRememberMe(false);

            //跳转到主页
            startActivityClearTopAndFinishSelf(null, MainActivity.class);
        }
    }

    public void startActivityClearTopAndFinishSelf(Intent extras, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        this.startActivity(intent);
        if (!this.isFinishing()) {
            this.finish();
        }
    }

}
