package com.skyqi.module_base.utils;///

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/9 3:10 下午
/// * @Description: 文件说明
///
public class DataValidator {

    private static final String phoneRegex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    public static boolean validPhone(String phone) {
        if(phone.length() != 11)
            return false;
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }
}
