package com.a.b;

public class Constant {
    public static final String HostUrl = "http://120.79.102.33:8080";
//    public static final String PROJECTID = "2b62f64de48749218ec366efb05f77b3";//华东-上海二
//    public static final String PROJECT = "cn-east-2";//华东-上海二

    public static final String UrlGetToken = "/user/login";
    public static final String UrlBatchCmd = "/phones/batch-command/";
    public static final String UrlGetTaskStatus = "/phones/list-jobs/";

    public static String USERNAME;
    public static String PASSWORD;
    public static String PROJECTID;
    public static String PROJECT;
    public static String[] PhoneIdsGroups;//批量命令执行的云手机的 id 数组
    public static String[] ServerIdsGroups;//批量命令执行的服务器的 id 数组
    public static String ZONE;
}
