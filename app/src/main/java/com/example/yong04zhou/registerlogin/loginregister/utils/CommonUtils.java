package com.example.yong04zhou.registerlogin.loginregister.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.yong04zhou.registerlogin.loginregister.base.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yun.wang
 * Date :2017/6/30
 * Description: ***
 * Version: 1.0.0
 */
public class CommonUtils {
    private static Toast toast;


    /**
     * 判断字符串是否为空
     *
     * @param input 字符串
     * @return true为空，false不为空
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input.trim()) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static void cancleShortToast() {
        if (null != toast) {
            toast.cancel();
        }
        toast = null;
    }

    public static void showToastCenter(String str) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), str, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }
        toast.setText(str);
        toast.show();
    }

    public static void showToastCenter(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }
        toast.setText(str);
        toast.show();
    }

    public static void showToastBottom(String str) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), str, Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }


    public static void showToastCenter(Context context, int strID) {
        if (toast == null) {
            toast = Toast.makeText(context, strID, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }
        toast.setText(context.getString(strID));
        toast.show();
    }

    public static void showLongToast(Context context, String str) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        toast.show();
    }


    /**
     * 验证手机号是否合法
     *
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        return p.matcher(mobile).matches();
    }

    /**
     * 验证密码规格，至少含数字、字母、符号中的两种
     *
     * @param pwd
     * @return
     */
    public static boolean isPassword(String pwd) {
        if (pwd.length() < 6 || pwd.length() > 18) {
            return false;
        }
        String fuhao = "\\W";
        if (pwd.matches("^\\d+$") || pwd.matches("^[a-zA-Z]+$") || pwd.matches("^[" + fuhao + "]+$")) {
            return false;
        } else if (pwd.matches("^[0-9A-Za-z]+$") || pwd.matches("^[0-9" + fuhao + "]+$") || pwd.matches("^[A-Za-z" + fuhao + "]+$")) {
            return true;
        } else if (pwd.matches("^[0-9A-Za-z" + fuhao + "]+$")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取密码强度
     *
     * @param pwd
     * @return
     */
    public static int getPasswordStrength(String pwd) {
        String fuhao = "\\W";
        if (pwd.matches("^\\d+$") || pwd.matches("^[a-zA-Z]+$") || pwd.matches("^[" + fuhao + "]+$")) {
            return 1;
        } else if (pwd.matches("^[0-9A-Za-z]+$") || pwd.matches("^[0-9" + fuhao + "]+$") || pwd.matches("^[A-Za-z" + fuhao + "]+$")) {
            return 2;
        } else if (pwd.matches("^[0-9A-Za-z" + fuhao + "]+$")) {
            return 3;
        } else {
            return -1;
        }
    }

    /**
     * 验证验证码是否合法
     */
    public static boolean checkVerifyCode(String verify_code) {
        Pattern p = Pattern.compile("^\\d{6}$"); // 检查验证码位数
        return p.matcher(verify_code).matches();
    }

    public static boolean checkCard(String card) {
        if (isEmpty(card)) {
            return false;
        } else if (card.length() == 18) {
            Pattern pattern = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
            return pattern.matcher(card).matches();
        } else if (card.length() == 15) {
            Pattern pattern = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
            return pattern.matcher(card).matches();
        }
        return false;
    }

    /**
     * 判断密码长度 [6,20]
     *
     * @param psd
     */
    public static boolean checkPsdLength(String psd) {
        if (!CommonUtils.isEmpty(psd)) {
            if (psd.length() > 16 || psd.length() < 6) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 打开软键盘
     */
    public static void openKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyboard(Context context) {
        View view = ((Activity) context).getWindow().peekDecorView();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getAppFIlePath() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".ody" + File.separator;
        return path;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }




    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    private static String CACHE_FILE_NAME = "datacache";

    public static long getCacheSize(Context context) {
        File cacheFile = new File(context.getFilesDir().getParent() + "/shared_prefs/" + CACHE_FILE_NAME + ".xml");
        if (cacheFile.exists()) {
            return cacheFile.length();
        }
        return 0;
    }

    public static String replace(String string, String oldChar, String newChar) {
        if (!StringUtils.isNull(string) && string.contains(oldChar)) {
            string = string.replace(oldChar, newChar);
        }
        return string;
    }

    /**
     * base64ToBitmap
     *
     * @param base64String
     * @return
     */
    public static Bitmap base64ToBitmap(String base64String) {
        if (StringUtils.isNull(base64String)) {
            return null;
        }
        try {
            byte[] bytes = Base64.decode((String) base64String, 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        } catch (Exception ex) {
            return null;
        }
    }




    /**
     * 中英文，数字，下划线
     *
     * @param str
     * @return
     */
    public static boolean isNickname(String str) {
        if (str.matches("[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,11}")) {
            return true;
        }
        return false;
    }

    /**
     * hide soft input method
     *
     * @param activity
     */
    public static void hideSoftInputMethod(Activity activity) {
        if (activity == null) {
            return;
        }

        try {
            InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {
            if (ex != null) {
                ex.printStackTrace();
            }
        }
    }


    public static boolean isEmojiCharacter(String codePoint) {
        String regex = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(codePoint);
        return m.find();
    }

    public static boolean isChinese(String sting) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, sting);
    }

    public static boolean hasSpecialCharacters(String s) {
        // match chinese number and letter
        String regEx = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(s);

        return !m.find();
    }
}
