package com.wenda.pay;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付请求
 * 
 * @author Administrator
 *
 */
public class WechatPayReq implements IWXAPIEventHandler {
	
	private static final String TAG = WechatPayReq.class.getSimpleName();

	private Activity mActivity;

	//微信支付AppID
	private String appId;
	//微信支付商户号
	private String partnerId;
	//预支付码（重要）
	private String prepayId;
	//"Sign=WXPay"
	private String packageValue;
	private String nonceStr;
	//时间戳
	private String timeStamp;
	//签名
	private String sign;
	
	//微信支付核心api
    IWXAPI mWXApi;

	public static final int NO_OR_LOW_WX = 1;   //未安装微信或微信版本过低
	public static final int ERROR_PAY_PARAM = 2;  //支付参数错误
	public static final int ERROR_PAY = 3;  //支付失败


	private static WechatPayReq mWXPay;

	public WechatPayReq() {
		super();
	}

	public static WechatPayReq getInstance(){
		return mWXPay;
	}

	public IWXAPI getWXApi() {
		return mWXApi;
	}

	public static void init(Context context, String wx_appid) {
		if(mWXPay == null) {
			mWXPay = new WechatPayReq(context, wx_appid);
		}
	}

	public WechatPayReq(Context context, String wx_appid) {
		mWXApi = WXAPIFactory.createWXAPI(context, null);
		mWXApi.registerApp(wx_appid);
	}


	/**
	 * 发送微信支付请求
	 */
	public void send() {
        mWXApi = WXAPIFactory.createWXAPI(mActivity, appId);
        mWXApi.handleIntent(mActivity.getIntent(), this);
        
        mWXApi.registerApp(this.appId);
        
        PayReq request = new PayReq();

        request.appId = this.appId;
        request.partnerId = this.partnerId;
        request.prepayId= this.prepayId;
        request.packageValue = this.packageValue != null ? this.packageValue : "Sign=WXPay";
        request.nonceStr= this.nonceStr;
        request.timeStamp= this.timeStamp;
        request.sign = this.sign;
		mWXApi.openWXApp();
        mWXApi.sendReq(request);
	}
	
	public static class Builder{
		//上下文
		private Activity activity;
		//微信支付AppID
		private String appId;
		//微信支付商户号
		private String partnerId;
		//预支付码（重要）
		private String prepayId;
		//"Sign=WXPay"
		private String packageValue="Sign=WXPay";
		private String nonceStr;
		//时间戳
		private String timeStamp;
		//签名
		private String sign;
		public Builder() {
			super();
		}
		
		public Builder with(Activity activity){
			this.activity = activity;
			return this;
		}
		
		/**
		 * 设置微信支付AppID
		 * @param appId
		 * @return
		 */
		public Builder setAppId(String appId){
			this.appId = appId;
			return this;
		}
		
		/**
		 * 微信支付商户号
		 * @param partnerId
		 * @return
		 */
		public Builder setPartnerId(String partnerId){
			this.partnerId = partnerId;
			return this;
		}
		
		/**
		 * 设置预支付码（重要）
		 * @param prepayId
		 * @return
		 */
		public Builder setPrepayId(String prepayId){
			this.prepayId = prepayId;
			return this;
		}
		
		
		/**
		 * 设置
		 * @param packageValue
		 * @return
		 */
		public Builder setPackageValue(String packageValue){
			this.packageValue = packageValue;
			return this;
		}
		
		
		/**
		 * 设置
		 * @param nonceStr
		 * @return
		 */
		public Builder setNonceStr(String nonceStr){
			this.nonceStr = nonceStr;
			return this;
		}
		
		/**
		 * 设置时间戳
		 * @param timeStamp
		 * @return
		 */
		public Builder setTimeStamp(String timeStamp){
			this.timeStamp = timeStamp;
			return this;
		}
		
		/**
		 * 设置签名
		 * @param sign
		 * @return
		 */
		public Builder setSign(String sign){
			this.sign = sign;
			return this;
		}
		
		
		
		public WechatPayReq create(){
			WechatPayReq wechatPayReq = new WechatPayReq();
			
			wechatPayReq.mActivity = this.activity;
			//微信支付AppID
			wechatPayReq.appId = this.appId;
			//微信支付商户号
			wechatPayReq.partnerId = this.partnerId;
			//预支付码（重要）
			wechatPayReq.prepayId = this.prepayId;
			//"Sign=WXPay"
			wechatPayReq.packageValue = this.packageValue;
			wechatPayReq.nonceStr = this.nonceStr;
			//时间戳
			wechatPayReq.timeStamp = this.timeStamp;
			//签名
			wechatPayReq.sign = this.sign;
			
			return wechatPayReq;
		}
		
	}


	private WXPayResultCallBack mCallback;
	public WechatPayReq setWXPayResultCallBack(WXPayResultCallBack mCallback) {
		this.mCallback = mCallback;
		return this;
	}

	public interface WXPayResultCallBack {
		void onSuccess(); //支付成功
		void onError(int error_code);   //支付失败
		void onCancel();    //支付取消
	}

	@Override
	public void onReq(BaseReq baseReq) {
		Toast.makeText(this.mActivity, "onReq===>>>get baseReq.getType : "+baseReq.getType(), Toast.LENGTH_LONG).show();
        Log.d(TAG,"onReq===>>>get baseReq.getType : "+baseReq.getType());
	}

	@Override
	public void onResp(BaseResp resp) {

	}


	//支付回调响应
	public void onResp(int error_code) {
		if(mCallback == null) {
			return;
		}

		if(error_code == 0) {   //成功
			mCallback.onSuccess();
		} else if(error_code == -1) {   //错误
			mCallback.onError(ERROR_PAY);
		} else if(error_code == -2) {   //取消
			mCallback.onCancel();
		}

		mCallback = null;
	}
}
