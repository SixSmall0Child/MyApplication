package com.dzone.plugin;

import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUploadUtil {

	  private static final String endPoint = "obs.cn-south-1.myhuaweicloud.com";

    private static final String ak = "TC2V4GGJ35BF8P76R5GV";

    private static final String sk = "Y1uTSE942CslU1bbXWcKDcg3X7DOVcDzG7bvQogF";

    //    public static final String BUCKET = "cpad-test";
    public static final String BUCKET = "cph-bucket";

    public static final String LEFT_SLASH = "/";

    private static final String HTTPS_PORT_STR = ":443/";

    private static final String HTTPS_SCHEMA = "https://";

    private static final String HTTP_SCHEMA = "http://";

    private static final String POINT = ".";

    private static final long expireSeconds = 3600L;

    public static final String USER_DATA_FOLDER = "userdata";

    public static final String USER_DATA_TYPE = ".zip";

	/**
	 * 上传文件，返回文件的url，但无法直接访问，有权限限制
	 * @param remoteFileName
	 * @param file
	 * @param remoteFolder
	 * @return
	 */
	public static String uploadFile(String remoteFileName, File file, String remoteFolder) {
		ObsClient obsClient = new ObsClient(ak,sk,endPoint);
		String key = remoteFolder == null ? remoteFileName : remoteFolder.concat(LEFT_SLASH).concat(remoteFileName);
		String fileUrl = null;
		try {
			obsClient.putObject(BUCKET, key, file);
			fileUrl = new StringBuffer().append(HTTPS_SCHEMA)
					.append(BUCKET).append(POINT).append(endPoint)
					.append(LEFT_SLASH).append(key)
					.toString();
		} finally {
			try {
				obsClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("uploadFile e = "+e);
			}
		}
		return fileUrl;
	}

	public static String uploadFile(String remoteFileName, InputStream is, String remoteFolder) {
		ObsClient obsClient = new ObsClient(ak,sk,endPoint);
		String key = remoteFolder == null ? remoteFileName : remoteFolder.concat(LEFT_SLASH).concat(remoteFileName);
		String fileUrl = null;
		try {
			obsClient.putObject(BUCKET, key, is);
			fileUrl = new StringBuffer().append(HTTPS_SCHEMA)
					.append(BUCKET).append(POINT).append(endPoint)
					.append(LEFT_SLASH).append(key)
					.toString();
		} finally {
			try {
				obsClient.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileUrl;
	}

	/**
	 *
	 * @param key remoteFolder/remoteFileName 如a/b.apk
	 * @return 签名后的url，可以直接访问
	 */
	public static String getSignedUrl(String key) {
		ObsClient obsClient = new ObsClient(ak,sk,endPoint);
		String url = null;
		try {
			boolean exist = obsClient.doesObjectExist(BUCKET, key);
			if(exist) {
				TemporarySignatureRequest req = new TemporarySignatureRequest(HttpMethodEnum.GET,BUCKET,key,null, expireSeconds);
				TemporarySignatureResponse res = obsClient.createTemporarySignature(req);
				if(res.getSignedUrl() != null) {
					url = res.getSignedUrl().replace(HTTPS_SCHEMA, HTTP_SCHEMA).replace(HTTPS_PORT_STR, LEFT_SLASH);
				}
			}

		} finally {
			try {
				obsClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return url;
	}


}
