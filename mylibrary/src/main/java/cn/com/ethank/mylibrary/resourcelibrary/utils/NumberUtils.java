package cn.com.ethank.mylibrary.resourcelibrary.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zjgz on 2017/12/12.
 */

public class NumberUtils {
    public static double todoubleSaveTwo(double count) {
        BigDecimal bigDecimal = new BigDecimal(count);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static float toFloatSaveOne(float count) {
       DecimalFormat decimalFormat =new DecimalFormat("0.0");
        String format = decimalFormat.format(count);
        return Float.parseFloat(format);
    }
    public static boolean isNumeric(String str) {
        if (!TextUtils.isEmpty(str)) {
//            if (str.startsWith("0")) {
//                if ("0".equals(str)) {
//                    return true;
//                }
//                str = str.substring(1, str.length());
//            }
            Pattern pattern = Pattern.compile("([0-9]\\d*\\.?\\d*)|(0\\.\\d*[0-9])");
            Matcher isNum = pattern.matcher(str);
            return isNum.matches();
        }
        return false;
    }
}
