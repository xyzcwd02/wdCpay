package com.wenda.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * 支付宝支付请求
 * 
 * @author Administrator
 *
 */
public class AliPayReq {

	/**
	 * ali pay sdk flag
	 */
	private static final int SDK_PAY_FLAG = 1;

	private Activity mActivity;

	//支付宝支付的配置
	private AliPayAPI.Config mConfig;
	
	// 服务器异步通知页面路径
	private String callbackUrl;
	
	private Handler mHandler;


	public AliPayReq() {
		super();
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case SDK_PAY_FLAG: {
						PayResult payResult = new PayResult((Map<String, String>) msg.obj);
						String resultInfo = payResult.getResult();// 同步返回需要验证的信息
						String resultStatus = payResult.getResultStatus();
						// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
						if (TextUtils.equals(resultStatus, "9000")) {
							if(mOnAliPayListener != null) mOnAliPayListener.onPaySuccess(resultInfo);
//                        Toast.makeText(OtherOrderDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
						} else {
							// 判断resultStatus 为非“9000”则代表可能支付失败
							// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
							if (TextUtils.equals(resultStatus, "8000")) {
								Toast.makeText(mActivity, "支付结果确认中", Toast.LENGTH_SHORT).show();
								if(mOnAliPayListener != null) mOnAliPayListener.onPayConfirmimg(resultInfo);

							} else {
								// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
								Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
								if(mOnAliPayListener != null) mOnAliPayListener.onPayFailure(resultInfo);
							}}
						break;
					}
				default:
					break;
				}
			}
			
		};
	}

	public void sendPayReq(AliPayReq aliPayReq){
		aliPayReq.aliPay();
	}


	/**
	 * 支付宝支付
	 */
	private void aliPay() {
		final String orderInfo = this.mConfig.getOrderInfo();
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				PayTask alipay = new PayTask(mActivity);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}



	public static class Builder{
		//上下文
		private Activity activity;
		//支付宝支付配置
		private AliPayAPI.Config config;
		private String orderInfo;
		public Builder() {
			super();
		}
		
		public Builder with(Activity activity){
			this.activity = activity;
			return this;
		}

		public Builder apply(AliPayAPI.Config config){
			this.config = config;
			return this;
		}
		
		public Builder apply(String orderInfo){
			this.orderInfo = orderInfo;
			return this;
		}
		public AliPayReq create(){
			AliPayReq aliPayReq = new AliPayReq();
			aliPayReq.mActivity = this.activity;
			aliPayReq.mConfig = this.config;
			return aliPayReq;
		}
	}
	
	
	//支付宝支付监听
	private OnAliPayListener mOnAliPayListener;
	public AliPayReq setOnAliPayListener(OnAliPayListener onAliPayListener) {
		this.mOnAliPayListener = onAliPayListener;
		return this;
	}

	/**
	 * 支付宝支付监听
	 * @author Administrator
	 *
	 */
	public interface OnAliPayListener{
		public void onPaySuccess(String resultInfo);
		public void onPayFailure(String resultInfo);
		public void onPayConfirmimg(String resultInfo);
		public void onPayCheck(String status);
	}
}
