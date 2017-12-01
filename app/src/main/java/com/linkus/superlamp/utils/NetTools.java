package com.linkus.superlamp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.linkus.superlamp.App;
import com.linkus.superlamp.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetTools {
    private static final String TAG = "NetTools";

    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;




    public static String getWifiName() {
        WifiManager wifi = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getSSID();
    }

	/*
     * public static String getWifiMac(){ String mac=getMacLevel9("wlan[0-9]+");
	 * if ( mac.equals("") ) { mac = getMacNetcfg("wlan[0-9]+"); } if(
	 * TextUtils.isEmpty(mac) ) { mac.toUpperCase(); } if( mac.length() != 17 )
	 * { Logger.e(TAG, "getWLANMac mac:"+mac); } else { Logger.i(TAG,
	 * "getWLANMac mac:"+mac); } return mac; }
	 */



    /**
     * 读取网口工作状态
     *
     * @return true: UP false:DOWN
     */
    public static boolean getPortIsWork() {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("netcfg");
            BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            proc.waitFor();
            Pattern result_pattern = Pattern.compile("^([a-z0-9]+)\\s+(UP|DOWN)\\s+([0-9./]+)\\s+.+\\s+([0-9a-f:]+)" +
                    "$", Pattern.CASE_INSENSITIVE);
            while (is.ready()) {
                String info = is.readLine();
                Matcher m = result_pattern.matcher(info);
                if (m.matches()) {
                    String name = m.group(1).toLowerCase(Locale.CHINA);
                    String status = m.group(2);
                    String addr = m.group(3);
                    String mac = m.group(4).toUpperCase(Locale.CHINA).replace(':', '-');
                    Logger.i(TAG, "getPortIsWork(), match success:" + name + " " + status + " " + addr + " " + mac);
                    if (name.matches("eth[0-9]+") && status.matches("UP") && (!addr.matches("0.0.0.0")))

                    {
                        return true;
                    }
                    if (name.matches("wlan[0-9]+") && status.matches("UP") && (!addr.matches("0.0.0.0")))

                    {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            Logger.i(TAG, "getPortIsWork(), Exception: IOException.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Logger.i(TAG, "getPortIsWork(), Exception: InterruptedException.");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)
                    context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 读取设备的有线网络的地址
     *
     * @return AA-BB-CC-DD-EE-FF 格式 如果不存在，返回""
     */
    public static String getLANMac() {
        String mac = getMacLevel9("eth[0-9]+");
        if (mac.equals("")) {
            mac = getMacNetcfg("eth[0-9]+");
        }

        // MAC地址总是使用大写的。
        if (!TextUtils.isEmpty(mac)) {
            mac.toUpperCase(Locale.CHINA);
        }

        if (mac.length() != 17) {
            Logger.e(TAG, "getLANMac mac:" + mac);
        } else {
            Logger.i(TAG, "getLANMac mac:" + mac);
        }

        return mac;
    }

    public static String getWifiMac() {
        String mac = getMacLevel9("wlan[0-9]+");
        if (mac.equals("")) {
            mac = getMacNetcfg("wlan[0-9]+");
        }
        if (!TextUtils.isEmpty(mac)) {
            mac.toUpperCase(Locale.CHINA);
        }
        if (mac.length() != 17) {
            Logger.e(TAG, "getWLANMac mac:" + mac);
        } else {
            Logger.i(TAG, "getWLANMac mac:" + mac);
        }
        return mac;
    }

    /**
     * 获取第一个符合name_pattern的网卡的MAC地址 需要API Level 9,
     * <uses-permission android:name="android.permission.INTERNET"/>
     */
    private static String getMacLevel9(String name_pattern_rgx) {
        try {
            Method getHardwareAddress = NetworkInterface.class.getMethod("getHardwareAddress");
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface n = nis.nextElement();
                getHardwareAddress.invoke(n);
                byte[] hw_addr = (byte[]) getHardwareAddress.invoke(n);
                if (hw_addr != null) {
                    String name = n.getName().toLowerCase(Locale.CHINA);
                    String mac = MacString(hw_addr);
                    Logger.d("NetTools.getMacLevel9", name + " " + mac);
                    if (name.matches(name_pattern_rgx)) {
                        return mac;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String MacString(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (byte v : mac) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append(String.format("%02X", v));
        }
        return sb.toString();
    }

    /**
     * 获取第一个符合name_pattern的网卡的MAC地址 需要netcfg工具, <uses-permission
     * android:name="android.permission.INTERNET"/>
     */
    private static String getMacNetcfg(String name_pattern_rgx) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("netcfg");
            BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            proc.waitFor();
            Pattern result_pattern = Pattern
                    .compile("^([a-z0-9]+)\\s+(UP|DOWN)\\s+([0-9./]+)\\s+.+\\s+([0-9a-f:]+)$", Pattern
                            .CASE_INSENSITIVE);
            while (is.ready()) {
                String info = is.readLine();
                Matcher m = result_pattern.matcher(info);
                if (m.matches()) {
                    String name = m.group(1).toLowerCase(Locale.CHINA);
                    String status = m.group(2);
                    String addr = m.group(3);
                    String mac = m.group(4).toUpperCase(Locale.CHINA).replace(':', '-');
                    Logger.d("NetTools.getMacNetcfg", "match success:" + name + " " + status + " " + addr + " " + mac);
                    if (name.matches(name_pattern_rgx)) {
                        return mac;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断当前的网络连接类型是否是WiFi
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 读取设备的默认网关IP地址
     *
     * @return "000.000.000.000" 格式 如果不存在，返回""
     */
    public static String getGatewayIp() {
        String ip = "";
        String info = "";
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("ip route");
            BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            proc.waitFor();
            Pattern result_pattern = Pattern.compile("default\\s+via\\s+(\\d+(?:\\.\\d+){3})\\s+dev.+", Pattern
                    .CASE_INSENSITIVE);
            while (is.ready()) {
                info = is.readLine();
                Logger.i(TAG, "getGatewayIp() ip route:" + info);

                Matcher m = result_pattern.matcher(info);
                if (m.matches()) {
                    ip = m.group(1).toLowerCase(Locale.CHINA);
                    Logger.d(TAG, "getGatewayIp() m.group(1). ip:" + ip);
                    return ip;
                }
            }
        } catch (IOException e) {
            Logger.d(TAG, "getGatewayIp() Exception: IOException.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Logger.d(TAG, "getGatewayIp() Exception: InterruptedException.");
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 判断网络是否连接成功
     */
    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    /**
     * 获取本机IP地址
     */
    public static String getLocalIpAddress() {
        String ipaddress = "";
//        try {
//            Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces();
//            // 遍历所用的网络接口
//            while (en.hasMoreElements()) {
//                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
//                Enumeration<InetAddress> inet = nif.getInetAddresses();
//                // 遍历每一个接口绑定的所有ip
//                while (inet.hasMoreElements()) {
//                    InetAddress ip = inet.nextElement();
//                    if (!ip.isLoopbackAddress()
//                            && InetAddressUtils.isIPv4Address(ip
//                            .getHostAddress())) {
//                        return ip.getHostAddress();
//                    }
//                }
//
//            }
//        } catch (SocketException e) {
//            Log.e("feige", "获取本地ip地址失败");
//            e.printStackTrace();
//        }
        return ipaddress;
    }

    /**
     * 获取Wifi的IP地址
     */
    public static String getWlanIp(Context context) {
        WifiManager wifimanage = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifimanage.isWifiEnabled()) {
            wifimanage.setWifiEnabled(true);
        }
        WifiInfo wifiinfo = wifimanage.getConnectionInfo();
        return intToIp(wifiinfo.getIpAddress());
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
    }

    public static NetState getNetState(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting() && netInfo.isAvailable()) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetState.WIFI;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                return NetState.NET;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = netInfo.getSubtypeName();
                int networkType = netInfo.getSubtype();// TD-SCDMA   networkType is 17
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        return NetState.MOBILE_2G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        return NetState.MOBILE_3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        return NetState.MOBILE_4G;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA")
                                || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            return NetState.MOBILE_3G;
                        }else{
                            return NetState.MOBILE_2G;//默认处理
                        }
                }
            }

        }
        return NetState.NONE;
    }

    public enum NetState {
        WIFI("wifi"), NET("net"), NONE("none"), MOBILE_2G("2g"), MOBILE_3G("3g"), MOBILE_4G("4g"),MOBILE("mobile");

        private final String value;

        NetState(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static NetState getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NetState.WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NetState.MOBILE;
            }
        } else {
            return NetState.MOBILE;
        }
        return NetState.MOBILE;
    }

}
