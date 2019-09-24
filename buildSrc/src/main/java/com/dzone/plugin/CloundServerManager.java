package com.dzone.plugin;


import com.dzone.plugin.json.JSONArray;
import com.dzone.plugin.json.JSONException;
import com.dzone.plugin.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CloundServerManager {
    private static final String TAG = "CloundServerManager";
    public static CloundServerManager mInstace = new CloundServerManager();

    private CloundServerManager() {
    }

    public static CloundServerManager getInstance() {
        return mInstace;
    }

    private ConcurrentHashMap<String, String> mCachedTokens = new ConcurrentHashMap();


    public String installApkToCloud(File uploadFile, String applicationId,String cmdsBeforeInstall,String cmdsAfterInstall) {
        String token = getToken(Constant.USERNAME, Constant.PASSWORD);
        String remoteApkPath = doUpload(uploadFile);
        String taskId = doInstall(remoteApkPath, token, applicationId,cmdsBeforeInstall,cmdsAfterInstall);

        /*System.out.println("wait 60s to check install status...");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        String taskStatus = getTaskStatus(taskId, token);
        System.out.println("installApkToCloud: status = " + taskStatus);


        return taskId;
    }

    private String doUpload(File uploadfile) {
        String remoteApkPath = "";
        try {
            if (uploadfile.exists()) {
                System.out.println("start upload");
                String remoteName = uploadfile.getName();
                String remoteFolder = null;
                String url = FileUploadUtil.uploadFile(remoteName, uploadfile, remoteFolder);
                System.out.println("run: " + "url = " + url);
                remoteApkPath = remoteFolder == null ? remoteName : (remoteFolder + remoteName);
            } else {
                System.out.println("uploadfile not exist,path = " + uploadfile.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return remoteApkPath;
    }

    private String getToken(String username, String pwd) {
        System.out.println("username =" + username + " pwd = " + pwd);
        if (username == null || pwd == null) {
            return "getToken failed,username == null || pwd == null";
        }
        /*if (!isEmpty(mCachedTokens.get(username))) {
            return mCachedTokens.get(username);
        }*/
        String token = "";

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(Constant.HostUrl + Constant.UrlGetToken)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String reponseStr = response.body().string();
                System.out.println("getToken: reponseStr = " + reponseStr);
                JSONObject responseJsonObject = new JSONObject(reponseStr);
                token = (String) responseJsonObject.get("data");
                mCachedTokens.put(username, token);
                return token;
            } else {
                System.out.println("getToken: failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }

    private String doInstall(String remoteApkPath, String token, String applicationId, String cmdsBeforeInstall, String cmdsAfterInstall) {
        String taskId = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String requestUrl = new StringBuffer().append(Constant.HostUrl)
                .append(Constant.UrlBatchCmd)
                .append(Constant.PROJECT)
                .append("/")
                .append(Constant.PROJECTID).toString();
        JSONObject requestJsonObject = generateRequestJsonObject(remoteApkPath, applicationId, cmdsBeforeInstall, cmdsAfterInstall);
        System.out.println("doInstall: requestJsonStr = " + requestJsonObject);
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(requestJsonObject));
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("Token", token)
                .post(requestBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String reponseStr = response.body().string();
                System.out.println("getToken: reponseStr = " + reponseStr);
                JSONObject responseJsonObject = new JSONObject(reponseStr);
                taskId = (String) responseJsonObject.get("data");
                return taskId;
            } else {
                System.out.println("doInstall: failed " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskId;
    }

    private JSONObject generateRequestJsonObject(String remoteApkPath, String applicationId, String cmdsBeforeInstall, String cmdsAfterInstall) {
        JSONObject requestJsonObject = new JSONObject();
        JSONArray cmdJsonArray = new JSONArray();
        try {
            if (!isEmpty(cmdsBeforeInstall)){
                String[] cmdbeforeArr = cmdsBeforeInstall.split(";");
                for (int i = 0; i < cmdbeforeArr.length; i++) {
                    String[] cmd = cmdbeforeArr[i].split(",");
                    if (cmd.length == 2){
                        cmdJsonArray.put(getCmdJsonObject(cmd[0].trim(),cmd[1].trim()));
                    }
                }

            }
            cmdJsonArray.put(getCmdJsonObject("install", "-r obs://" + FileUploadUtil.BUCKET + "/" + remoteApkPath));
            if (!isEmpty(cmdsAfterInstall)){
                String[] cmdAfterArr = cmdsAfterInstall.split(";");
                for (int i = 0; i < cmdAfterArr.length; i++) {
                    String[] cmd = cmdAfterArr[i].split(",");
                    if (cmd.length == 2){
                        String command = cmd[0].trim();
                        String content = cmd[1].trim().replace(Constant.PKG_NAME_PLACEHOLDER, applicationId);
                        cmdJsonArray.put(getCmdJsonObject(command,content));
                    }
                }
            }
//            cmdJsonArray.put(getCmdJsonObject("shell",
//                    "am broadcast -a com.dzone.cpad_COMMAND_ACTION -e command install -e pkg " + applicationId));

            requestJsonObject.put("command_sequences", cmdJsonArray);
            if (Constant.PHONEIDS_GROUPS != null) {
                requestJsonObject.put("phone_ids_groups", getPhoneIdsGroupsJsonArr());
            }
            if (Constant.SERVERIDS_GROUPS != null) {
                requestJsonObject.put("server_ids_groups", getServerIdsGroupsJsonArr());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestJsonObject;
    }

    private JSONArray getServerIdsGroupsJsonArr() {
        if (Constant.SERVERIDS_GROUPS == null) return null;
        JSONArray jsonArray = new JSONArray();
        String[] arr = Constant.SERVERIDS_GROUPS;
        for (int i = 0; i < arr.length; i++) {
            jsonArray.put(arr[i].trim());
        }
        JSONArray jsonArrayOut = new JSONArray();
        jsonArrayOut.put(jsonArray);
        return jsonArrayOut;
    }

    private JSONArray getPhoneIdsGroupsJsonArr() {
        if (Constant.PHONEIDS_GROUPS == null) return null;
        String[] arr = Constant.PHONEIDS_GROUPS;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < arr.length; i++) {
//            jsonArray.put("68366b7f94e54ea98de4c717ea44fc1e");
            jsonArray.put(arr[i].trim());
        }
        JSONArray jsonArrayOut = new JSONArray();
        jsonArrayOut.put(jsonArray);
        return jsonArrayOut;
    }

    private JSONObject getCmdJsonObject(String command, String content) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", command);
        jsonObject.put("content", content);
        return jsonObject;
    }

    private String getTaskStatus(String taskId, String token) {
//        System.out.println("taskId = " + taskId + " ,token=" + token);
        if (isEmpty(taskId) || isEmpty(token)) return "taskId or token is null";
        String status = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        String requestUrl = Constant.HostUrl + Constant.UrlGetTaskStatus + taskId;
        Request request = new Request.Builder()
                .addHeader("Token", token)
                .url(requestUrl)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                status = response.body().string();
                System.out.println("taskId = " + taskId + ",getTaskStatus: " + status);
            } else {
                System.out.println("taskId = " + taskId + "getTaskStatus: failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;

    }

    public void queryTaskStatus(String taskId) {
        String token = getToken(Constant.USERNAME, Constant.PASSWORD);
        getTaskStatus(taskId, token);
    }

    public static boolean isEmpty(CharSequence s) {
        if (s == null) {
            return true;
        } else {
            return s.length() == 0;
        }
    }
}
