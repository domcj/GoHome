package com.huarun.gohome.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownFileUtil {

	private static Logger log = LoggerFactory.getLogger(DownFileUtil.class);
	private static String pictureBaseUrl = "D:\\picture\\";
	private static String searchBaseUrl = "D:\\searchPicture\\";


	public static String downImage(MultipartFile file, String fileName, String type) {
		try {
			String files = pictureBaseUrl + fileName;
			if (!"0".equals(type)) {
				files = searchBaseUrl + fileName;
			}
			byte[] bytes = file.getBytes();
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(files));//打开输入流
			imageOutput.write(bytes, 0, bytes.length);//将byte写入硬盘
			imageOutput.close();
		} catch(Exception e) {
			log.info(fileName+"->保存到本地异常", e);
			return null;
		}
		return fileName;
	}

	public static byte[] loadImage(String fileName, String type) {
		try {
			String path = pictureBaseUrl+fileName;
			if (!"0".equals(type)) {
				path = searchBaseUrl+fileName;
			}
			File file = new File(path);
			return Files.readAllBytes(file.toPath());
		} catch(Exception e) {
			log.info(fileName+"->加载图片异常", e);
		}
		return null;
	}

	public final static byte[] getImageBytesByUrl(String url) {
		InputStream inputStream = null;
		Request req = (new Request.Builder()).url(url).get().build();
		Response response = null;
		try {
			response = new OkHttpClient().newCall(req).execute();
			if (!response.isSuccessful()) {
				log.error("【调用HTTP请求异常】 code:{},message:{}", response.code(), response.message());
				return null;
			}
			inputStream = response.body().byteStream();
			return inputToByte(inputStream);
		} catch (Exception e) {
			log.error("发起请求出现异常:", e);
			return null;
		} finally {
			try {
				inputStream.close();
			} catch (Exception var12) {
				log.error("【关闭流异常】");
			}
		}
	}
	private static byte[] inputToByte(InputStream inputStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int rc;
		while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
	public static String getFileName(String url) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		for (String str : url.split("/")) {
			if (str.contains("jpg")) {
				return str;
			}
		}
		return null;
	}

}
