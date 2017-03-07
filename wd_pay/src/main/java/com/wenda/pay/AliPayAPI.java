package com.wenda.pay;

/**
 * 支付宝支付API
 * 
 * 使用:
 * 
 * AliPayAPI.getInstance().apply(config).sendPayReq(aliPayReq);
 * 
 * @author Administrator
 *
 */
public class AliPayAPI {
	
	private Config mConfig;

	/**
	 * 获取支付宝支付API
	 */
    private static final Object mLock = new Object();
    private static AliPayAPI mInstance;

    public static AliPayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new AliPayAPI();
                }
            }
        }
        return mInstance;
    }

	/**
	 * 配置支付宝配置
	 * @param config
	 * @return
	 */
	public AliPayAPI apply(Config config){
		this.mConfig = config;
		return this;
	}
    

    public void sendPayReq(AliPayReq aliPayReq){
    	aliPayReq.sendPayReq(aliPayReq);
    }
    

    /**
     * 支付宝支付配置
     * @author Administrator
     *
     */
    public static class Config{
        private String orderInfo;

		public String getOrderInfo() {
			return orderInfo;
		}

		public void setOrderInfo(String orderInfo) {
			this.orderInfo = orderInfo;
		}

		public static class Builder{
            private String orderInfo;

            public Builder() {
				super();
			}

			public Builder setRsaPrivate(String orderInfo){
            	this.orderInfo = orderInfo;
            	return this;
            }
			public Builder setOrderInfo(String orderInfo){
				this.orderInfo = orderInfo;
				return this;
			}

			public Config create(){
				Config conf = new Config();
				conf.orderInfo = this.orderInfo;
				return conf;
			}
        }
    }

	/**
	 * 微信支付请求
	 * @param wechatPayReq
	 */
	public void sendPayRequest(WechatPayReq wechatPayReq){
		WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
	}


}
