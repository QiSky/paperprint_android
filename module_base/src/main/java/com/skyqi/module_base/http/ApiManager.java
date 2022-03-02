package com.skyqi.module_base.http;///

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/1 3:46 下午
/// * @Description: 文件说明
///
public interface ApiManager {

    String REST_URL = "";

    ///短信、密码登陆
    String REST_LOGIN_CODE = "/user/login/code";

    ///token自动登陆
    String REST_LOGIN_TOKEN = "/user/login/token";
}
