package com.dzone.plugin;

public class Constant {
    public static final String HostUrl = "http://120.79.102.33:8080";

    public static final String UrlGetToken = "/user/login";
    public static final String UrlBatchCmd = "/phones/batch-command/";
    public static final String UrlGetTaskStatus = "/phones/list-jobs/";

    public static String USERNAME = "dimenspace";
    public static String PASSWORD = "6d82f59aaae6eee7c1ed85b6f0882023";
    public static String PROJECTID;
    public static String PROJECT;
    public static String[] PHONEIDS_GROUPS;//批量命令执行的云手机的 id 数组
    public static String[] SERVERIDS_GROUPS;//批量命令执行的服务器的 id 数组
    public static String ZONE;
    public static String[] BUILD_VARIANTS;
    public static String CMD_BEFORE_INSTALL;//安装前执行的命令集合
    public static String CMD_AFTER_INSTALL;//安装后执行的命令集合

    public static String TASK_IDS;
    //占位符
    public static final String PKG_NAME_PLACEHOLDER = "$packageName$";
}
