package com.example.yong04zhou.registerlogin.loginregister.login;

import android.content.Intent;
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
import com.example.yong04zhou.registerlogin.loginregister.data.remote.bean.loginregister.LoginResponseBean;
import com.example.yong04zhou.registerlogin.loginregister.forgetpassword.ForgetPasswordActivity;
import com.example.yong04zhou.registerlogin.loginregister.register.RegisterActivity;
import com.example.yong04zhou.registerlogin.loginregister.utils.CommonUtils;
import com.example.yong04zhou.registerlogin.loginregister.utils.LogUtils;
import com.example.yong04zhou.registerlogin.loginregister.utils.MD5Utils;
import com.example.yong04zhou.registerlogin.loginregister.utils.NetworkManagerUtils;
import com.example.yong04zhou.registerlogin.loginregister.utils.StringUtils;
import com.example.yong04zhou.registerlogin.token.TokenManager;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class LoginActivity extends RxFragmentActivity implements LoginContract.View {


    @BindView(R.id.phoneNumber)
    EditText mPhoneNumberEdit;

    @BindView(R.id.password)
    EditText mPasswordEdit;

    private boolean isPasswordDisplay = true;

    @BindView(R.id.password_display_imageview)
    ImageView password_display_imageview;

    @BindView(R.id.rememberme_checkbox)
    CheckBox rememberme_checkbox;

    @BindView(R.id.bt_login)
    Button bt_login;

    private String phoneNo = "";
    private String password = "";
    private String mAuthorizationCode = "";

    private LoginContract.Presenter loginPresenter;

    public static final String PHONE_NUMBER = "phone_number";
    public static final int REQUEST_CODE_MODIFY_PASSWORD = 10;
    public static final int REQUEST_CODE_REGISTER = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initViews();
        setEditTextListener();

        //init presenter
        loginPresenter = new LoginPresenter(this);

    }

    private void initViews() {
        password_display_imageview.setEnabled(true);

        boolean isRememberMe = DataRepository.getInstance().getRememberMe();
        rememberme_checkbox.setChecked(isRememberMe);

        try {
            if (this.getIntent() != null) {
                //back from modifypassword or forgetpassword
                String phone = this.getIntent().getStringExtra(PHONE_NUMBER);
                mPhoneNumberEdit.setText(phone);
                if (!StringUtils.isNull(phone)) {
                    mPasswordEdit.requestFocus();
                    mPasswordEdit.setText("");

                } else {
                    initRememberMe(isRememberMe);
                }
            } else {
                initRememberMe(isRememberMe);
            }
        } catch (Exception ex) {

        }
    }

    private void initRememberMe(boolean isRememberMe) {
        rememberme_checkbox.setChecked(isRememberMe);
        if (isRememberMe) {
            mPhoneNumberEdit.setText(DataRepository.getInstance().getUserName());
            mPasswordEdit.setText(DataRepository.getInstance().getPassword());
        } else {
            mPhoneNumberEdit.setText(null);
            mPasswordEdit.setText(null);
        }
    }


    private void setEditTextListener() {
        RxTextView.afterTextChangeEvents(mPhoneNumberEdit).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
            @Override
            public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                phoneNo = textViewAfterTextChangeEvent.editable().toString();
                if (!StringUtils.isNull(phoneNo)
                        && !StringUtils.isNull(password)) {
                    bt_login.setEnabled(true);
                } else {
                    bt_login.setEnabled(false);
                }
            }
        });

        RxTextView.afterTextChangeEvents(mPasswordEdit).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
            @Override
            public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                password = textViewAfterTextChangeEvent.editable().toString();
                if (!StringUtils.isNull(phoneNo)
                        && !StringUtils.isNull(password)) {
                    bt_login.setEnabled(true);
                } else {
                    bt_login.setEnabled(false);
                }
            }
        });
    }


    //Activity的方法
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d(this.getClass().getSimpleName(), "onNewIntent");
        try {
            String phone = intent.getStringExtra(PHONE_NUMBER);
            mPhoneNumberEdit.setText(phone);
            if (!StringUtils.isNull(phone)) {
                mPasswordEdit.requestFocus();
                mPasswordEdit.setText("");
            }
        } catch (Exception ex) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.d(this.getClass().getSimpleName(), "resultCode = " + resultCode);
        LogUtils.d(this.getClass().getSimpleName(), "requestCode = " + requestCode);
        if ((resultCode == RESULT_OK && requestCode == REQUEST_CODE_MODIFY_PASSWORD)) {
            try {
                String phone = data.getStringExtra(PHONE_NUMBER);
                mPhoneNumberEdit.setText(phone);
                if (!StringUtils.isNull(phone)) {
                    mPasswordEdit.requestFocus();
                    mPasswordEdit.setText("");
                }
            } catch (Exception ex) {

            }
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

    @OnClick(R.id.bt_login)
    public void login() {
        //斐讯账户登录
        phoneNo = mPhoneNumberEdit.getText().toString();
        password = mPasswordEdit.getText().toString();
        if (StringUtils.isNull(phoneNo)) {
            Toast.makeText(this, R.string.phonenum_is_null, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!CommonUtils.checkMobile(phoneNo)) {
            Toast.makeText(this, R.string.phonenum_is_illegal, Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.isNull(password)) {
            Toast.makeText(this, R.string.password_is_null, Toast.LENGTH_SHORT).show();
            return;
        }

        if (NetworkManagerUtils.instance().networkError()) {
            Toast.makeText(this, R.string.net_connect_fail, Toast.LENGTH_SHORT).show();
            return;
        }

        password = MD5Utils.encryptedByMD5(password);
        mAuthorizationCode = DataRepository.getInstance().getAuthorizationCode();

        if (StringUtils.isNull(mAuthorizationCode)) {
            loginPresenter.doAuthorization(AppConstants.CLIENT_ID, AppConstants.RESPONSE_TYPE, AppConstants.SCOPE, AppConstants.CLIENT_SECRET);
        } else {
            loginPresenter.doPhoneLogin(mAuthorizationCode, phoneNo, password);
        }
    }

    @OnClick(R.id.tv_register)
    public void clickRegister() {
        startActivityClearTop(null, RegisterActivity.class);
    }
    public void startActivityClearTop(Intent extras, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        this.startActivity(intent);
    }


    @OnClick(R.id.tv_forget_password)
    public void clickResetPassword() {
        Intent forgetPasswordIntent = new Intent(this, ForgetPasswordActivity.class);
        forgetPasswordIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(forgetPasswordIntent, REQUEST_CODE_MODIFY_PASSWORD);
    }


    @Override
    public Object getRxLifeCycleObj() {
        return this;
    }

    @Override
    public void analysisResponseBean(BaseResponseBean t) {
        if (t instanceof AuthorizationResponseBean) {
            AuthorizationResponseBean bean = (AuthorizationResponseBean) t;
            if (!StringUtils.isNull(bean.authorizationcode)) {
                mAuthorizationCode = bean.authorizationcode;
                DataRepository.getInstance().setAuthorizationCode(mAuthorizationCode);
                loginPresenter.doPhoneLogin(mAuthorizationCode, phoneNo, password);
            } else {
                Toast.makeText(this, R.string.authorization_error, Toast.LENGTH_SHORT).show();
            }
        } else if (t instanceof LoginResponseBean) {
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();

            LoginResponseBean bean = (LoginResponseBean) t;

            String uid = bean.uid;

            String access_token = bean.access_token;
            //保存斐讯云access_token
            DataRepository.getInstance().setAccessToken(access_token);
            DataRepository.getInstance().setCloudLoginStatus(true);
            TokenManager.getInstance().saveTokens(bean.access_token, bean.refresh_token,
                    bean.refresh_token_expire, bean.access_token_expire);

            //保存手机号
            DataRepository.getInstance().setUsername(phoneNo);

            //remember me
            if (rememberme_checkbox.isChecked()) {
                DataRepository.getInstance().setPassword(mPasswordEdit.getText().toString());
                DataRepository.getInstance().setRememberMe(true);
            } else {
                //clear remember me
                DataRepository.getInstance().setPassword("");
                DataRepository.getInstance().setRememberMe(false);
            }

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
