/**
 * 
 */
package com.linkus.superlamp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.widget.TextView;


import com.linkus.superlamp.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 辅助工具类
 * @创建时间： 2015年11月24日 上午11:46:50
 * @项目名称： AMapLocationDemo2.x
 * @author cjz
 * @文件名称: Utils.java
 * @类型名称: Utils
 */
public class Utils {
	/**
	 *  开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP= 2;
	
	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";
//	/**
//	 * 根据定位结果返回定位信息的字符串
//	 * @param location
//	 * @return
//	 */
//	public synchronized static String getLocationStr(AMapLocation location){
//		if(null == location){
//			return null;
//		}
//		StringBuffer sb = new StringBuffer();
//		//errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
//		if(location.getErrorCode() == 0){
//			sb.append("定位成功" + "\n");
//			sb.append("定位类型: " + location.getLocationType() + "\n");
//			sb.append("经    度    : " + location.getLongitude() + "\n");
//			sb.append("纬    度    : " + location.getLatitude() + "\n");
//			sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//			sb.append("提供者    : " + location.getProvider() + "\n");
//
//			sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//			sb.append("角    度    : " + location.getBearing() + "\n");
//			// 获取当前提供定位服务的卫星个数
//			sb.append("星    数    : " + location.getSatellites() + "\n");
//			sb.append("国    家    : " + location.getCountry() + "\n");
//			sb.append("省            : " + location.getProvince() + "\n");
//			sb.append("市            : " + location.getCity() + "\n");
//			sb.append("城市编码 : " + location.getCityCode() + "\n");
//			sb.append("区            : " + location.getDistrict() + "\n");
//			sb.append("区域 码   : " + location.getAdCode() + "\n");
//			sb.append("地    址    : " + location.getAddress() + "\n");
//			sb.append("兴趣点    : " + location.getPoiName() + "\n");
//			//定位完成的时间
//			sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
//		} else {
//			//定位失败
//			sb.append("定位失败" + "\n");
//			sb.append("错误码:" + location.getErrorCode() + "\n");
//			sb.append("错误信息:" + location.getErrorInfo() + "\n");
//			sb.append("错误描述:" + location.getLocationDetail() + "\n");
//		}
//		//定位之后的回调时间
//		sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
//		return sb.toString();
//	}

	private static SimpleDateFormat sdf = null;
	public  static String formatUTC(long l, String strPattern) {
		if (TextUtils.isEmpty(strPattern)) {
			strPattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (sdf == null) {
			try {
				sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
			} catch (Throwable e) {
			}
		} else {
			sdf.applyPattern(strPattern);
		}
		return sdf == null ? "NULL" : sdf.format(l);
	}

	public static String sHA1(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			byte[] cert = info.signatures[0].toByteArray();
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] publicKey = md.digest(cert);
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < publicKey.length; i++) {
				String appendString = Integer.toHexString(0xFF & publicKey[i])
						.toUpperCase(Locale.US);
				if (appendString.length() == 1)
					hexString.append("0");
				hexString.append(appendString);
				hexString.append(":");
			}
			String result = hexString.toString();
			return result.substring(0, result.length()-1);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isMounted(){
		return Environment.getExternalStorageState()== Environment.MEDIA_MOUNTED;
	}

//	public void writeFile(String fileName,){
//		if (!isMounted()){
//			return;
//		}
//
//
//	}

	public static File readFile(Context context, String dir, String fileName){
		Logger.i("readFile",""+isMounted());
//		if (!isMounted()) {
//			return null;
//		}
		String path = Environment.getDataDirectory().getAbsolutePath() + File.separator + dir + File.separator + fileName;
		Logger.i(path,"path"+path);
		File file = new File(context.getFilesDir(),fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static void writeObject(Context context, String dir, String fileName, Object object){
		File file = readFile(context,dir, fileName);
		if (file != null){
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(new FileOutputStream(file));
				out.writeObject(object);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Object readObject(Context context, String dir, String fileName) {
		File file = readFile(context, dir, fileName);
		Object o = null;
		if (file != null) {
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(new FileInputStream(file));
				o = in.readObject();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return o;
	}

	@SuppressLint("ParcelCreator")
	public static class NoUnderlineSpan extends URLSpan {
		public NoUnderlineSpan(String url) {
			super(url);
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			if (ds != null) {
				ds.setColor(ds.linkColor);
				ds.setUnderlineText(false);
			}
		}

	}
	public static void stripUnderlines(TextView textView) {
		if (textView.getText() instanceof Spannable) {
			URLSpan[] urlSpans = (((Spannable) textView.getText()).getSpans(0, textView.getText().length() - 1, URLSpan.class));
			for (URLSpan urlSpan : urlSpans) {
				String url = urlSpan.getURL();
				int start = ((Spannable) textView.getText()).getSpanStart(urlSpan);
				int end = ((Spannable) textView.getText()).getSpanEnd(urlSpan);
				NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan(url);
				Spannable s = (Spannable) textView.getText();
				s.setSpan(noUnderlineSpan, start, end, Spanned.SPAN_POINT_MARK);
			}
		}
	}
}
