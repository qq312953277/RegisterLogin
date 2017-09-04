package com.example.yong04zhou.registerlogin.loginregister.data.local.preference;

/**
 * Created by yun.wang
 * Date :2017/6/26
 * Description: ***
 * Version: 1.0.0
 */
public class PreferenceDef {

    //user info
    public static final String SP_NAME_USER_COOKIE = "sp_cookie_utils";
    public static final String CLOUD_LOGIN_STATUS = "cloud_login_status";
    public static final String CLOUD_USERNAME = "cloud_username";
    public static final String CLOUD_PASSWORD = "cloud_password";
    public static final String CLOUD_REMEMBER_ME = "remember_me";
    public static final String APP_GUIDE = "app_guide";
    public static final String AUTHORIZATION_CODE = "authorization_code";

    //token manager
    public static final String SP_NAME_TOKEN = "sp_token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String ACCESS_TOKEN_START_TIME = "access_token_start_time";
    public static final String REFRESH_TOKEN_START_TIME = "refresh_token_start_time";
    public static final String ACCESS_TOKEN_VALIDITY = "access_token_validity";
    public static final String REFRESH_TOKEN_VALIDITY = "refresh_token_validity";

    // version update
    public static final String VERSION_CONFIG = "version_config";
    public static final String APK_HAS_NEW_VERSION = "has_new_version";
}
