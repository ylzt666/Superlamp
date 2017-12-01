package com.linkus.superlamp.utils;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class PayUtils {
    private static String TAG = "PayUtils";
    private static PayUtils instacne ;
//    private static  ThreadPoolManager poolManager = null;
//    private IWXAPI api;
//    private PrepareBean prepareBean ;
//
//    private PayUtils(){
//        poolManager = ThreadPoolManager.getInstance();
//    }
//
    public static PayUtils getInstance() {
        synchronized (PayUtils.class) {
            if (instacne == null) {
                instacne = new PayUtils();
                return instacne;
            } else {
                return instacne;
            }
        }
    }
//
//    public void Alipay(final String orderInfo, final Activity act, final PrepareBean prepareBean){
//        poolManager.addTask(new Runnable() {
//            @Override
//            public void run() {
//                String pay = new PayTask(act).pay(orderInfo, true);
//                Logger.i(TAG, "pay result >> " + pay);
//                PayResult payResult = new PayResult(pay);
//                AlipayResult alipayResult = new AlipayResult(prepareBean, payResult);
//                EventBus.getDefault().post(new EventCenter(alipayResult, Constant.MESSAGE_PAY_REUSLT));
//            }
//        });
//    }
//
//    public void WechatPay(Context context, WechatBean wechatBean, PrepareBean prepareBean) {
//
//        assert context == null;
//        assert wechatBean == null;
//        if (api == null) {
//            api = WXAPIFactory.createWXAPI(context, Constant.APPID, false);
//            // 注册app
//            api.registerApp(Constant.APPID);
//        }
//        boolean wxAppInstalled = api.isWXAppInstalled();
//        Logger.i(TAG,"user  install wx app ? >> "+wxAppInstalled);
//        if (!wxAppInstalled) {
//            Toast.makeText(context, "暂未安装微信，不能支付", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
//        Logger.i(TAG, "user build version support ? >> " + isPaySupported);
//        if (!isPaySupported) {// 不支持
//            Toast.makeText(context, "当前微信版本不支持支付", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        this.prepareBean = new PrepareBean(prepareBean.orderType,
//                prepareBean.orderNo,
//                prepareBean.curOrderNo,
//                prepareBean.curOrderOrgion);
//        this.prepareBean.setPayTyp(prepareBean.payTyp);
//        Logger.i(TAG, "WechatPay " + this.prepareBean);
//        if (wechatBean != null) {
//            payReq(wechatBean);
//        }
//    }
//
//    private void payReq(WechatBean pay) {
//        PayReq req = new PayReq();
//        req.appId = pay.getAppid();
//        req.partnerId = pay.getPartnerid();
//        req.prepayId = pay.getPrepayid();
//        req.packageValue = pay.getPackageX();
//        req.nonceStr = pay.getNoncestr();
//        req.timeStamp = pay.getTimestamp();
//        req.sign = pay.getSign();
//        boolean isSend = api.sendReq(req);
//        Log.i("info", "微信支付请求是否发送===isSend==="+ isSend);
//    }
//
//    /**
//     * wechat
//     * send one get one
//     * @return bean
//     */
//    public PrepareBean getCurPreparebean(){
//        return this.prepareBean;
//    }
}
