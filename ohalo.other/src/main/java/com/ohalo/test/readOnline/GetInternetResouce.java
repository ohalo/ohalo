package com.ohalo.test.readOnline;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ohalo.test.readOnline.utils.HtmlUtils;

/**
 * \
 * 
 * <pre>
 * ###description###
 * 
 * 获取网络资源
 * 
 * #################
 * </pre>
 * 
 * @author Z.D.Halo
 * @since 2013-4-28
 * @version 1.0
 */

public class GetInternetResouce {

	private static String image9 = "http://www.luckywomenl.com/user48/sky0738/iceusa0/";

	private static Log logger = LogFactory.getLog(HtmlUtils.class);

	public static int i = 1024;

	private static Set<String> imageType = new HashSet<String>();

	static {
		// BMP(位图)
		imageType.add("BMP");
		// GIF（支持透明，甚至可以产生动画哦！）
		imageType.add("GIF");
		// JPEG 图片支持最高级别的压缩。
		imageType.add("JPEG");
		// PNG格式图片因其高保真性、透明性及文件大小较小等特性，被广泛应用于网页设计、平面设计中。
		imageType.add("PNG");
		// JPG
		imageType.add("JPG");
	}

	public static void main1(String[] args) throws IOException {
		for (int i = 0; i < 30; i++) {
			URL url = new URL(image9 + i + ".jpg");
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			int code = urlConnection.getResponseCode();
			if (code == 200) {
				System.out.println(code + "," + url.getFile());
				FileUtils.copyURLToFile(
						url,
						new File("d://image//"
								+ i
								+ url.getFile().substring(
										url.getFile().lastIndexOf("/"))));
			}
		}
	}

	public static void downLoadPic(String strUrl, int i) throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		int code = urlConnection.getResponseCode();
		if (code == 200) {
			if (logger.isDebugEnabled()) {
				logger.debug("响应编码：" + code + ",请求地址：" + url.getFile());
			}
			String path = url.getFile();
			path = "/" + i + path.substring(path.lastIndexOf("/") + 1);
			if (path.length() < 3
					|| !imageType.contains(path.substring(path.length() - 3)
							.toUpperCase())) {
				path = path + i + ".jpg";
			}
			FileUtils.copyURLToFile(url, new File("E://达盖尔的旗帜//" + path));
		}
	}
	public static void main(String[] args) throws IOException {
		String path = "/asdas/asdas/asdasd/jpg.jpg";
		path = path.substring(path.lastIndexOf("/"));
		System.out.println(path.substring(path.length() - 3));
		if (imageType.contains(path.substring(path.length() - 3).toUpperCase())) {
			System.out.println(path);
		}
	}
}
