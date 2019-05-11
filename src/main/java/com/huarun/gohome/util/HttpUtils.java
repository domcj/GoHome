package com.huarun.gohome.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    private static final MediaType MediaType_APPLICATION = MediaType.parse("application/octet-stream");

    private static final String KEY_IMAGE_FILE = "image_file";
    public static final String KEY_IMAGE_FILE_1 = "image_file1";
    public static final String KEY_IMAGE_FILE_2 = "image_file2";

    public static final String KEY_TEMPLATE_FILE = "template_file";
    public static final String KEY_MERGE_FILE = "merge_file";

    private static OkHttpClient okHttpClient;

    /**
     * 不带文件参数的请求
     */
    public static String  post(String url, Map<String, String> params) {
        return post(url, params, new byte[]{});
    }

    /**
     * 带文件参数的请求
     */
    public static String post(String url, final Map<String, String> params, byte[] file) {
        Map<String, byte[]> fileParams = new HashMap<>();
        fileParams.put(KEY_IMAGE_FILE, file);
        return post(url, params, fileParams, null);
    }

    /**
     * 带文件参数的请求
     */
    public static String post(String url, final Map<String, String> params, Map<String, byte[]> file1, Map<String, byte[]> file2) {
        try {
            // 构建请求体
            RequestBody requestBody = buildRequestBody(params, file1, file2);

            // 发送请求
            return doPost(new Request.Builder().url(url).post(requestBody).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建请求参数
     */
    private static RequestBody buildRequestBody(final Map<String, String> params, Map<String, byte[]> filePath1, Map<String, byte[]> filePath2) throws Exception {
        // 普通参数
        final MultipartBody.Builder bodyBuilder = addNormalParams(params);

        // 文件参数
        addFileParamsByKey(filePath1, bodyBuilder);
        addFileParamsByKey(filePath2, bodyBuilder);

        return bodyBuilder.build();
    }

    /**
     * 普通参数
     */
    private static MultipartBody.Builder addNormalParams(final Map<String, String> params) {

        final MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            bodyBuilder.addFormDataPart(key, value);
        }
        return bodyBuilder;
    }

    /**
     * 发送请求
     */
    private static String doPost(Request request) {
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient();
        }
        Response response = null;
        String result = null;
        try {
            response = okHttpClient.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private static void addFileParams(MultipartBody.Builder bodyBuilder, String key, byte[] file) throws Exception {
        bodyBuilder.addFormDataPart(key, key, RequestBody.create(MediaType_APPLICATION, file));
    }

    private static boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;
    }

    private static void addFileParamsByKey(Map<String, byte[]> filePath, MultipartBody.Builder bodyBuilder) throws Exception {
        if (null != filePath) {
            getDataByKey(filePath, KEY_IMAGE_FILE, bodyBuilder);
            getDataByKey(filePath, KEY_IMAGE_FILE_1, bodyBuilder);
            getDataByKey(filePath, KEY_IMAGE_FILE_2, bodyBuilder);
            getDataByKey(filePath, KEY_TEMPLATE_FILE, bodyBuilder);
            getDataByKey(filePath, KEY_MERGE_FILE, bodyBuilder);
        }
    }

    private static void getDataByKey(Map<String, byte[]> file, String key, MultipartBody.Builder bodyBuilder) throws Exception {
        if (!isEmpty(file.get(key))) {
            addFileParams(bodyBuilder, key, file.get(key));
        }
    }
}
