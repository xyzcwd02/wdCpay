package com.smart.pang;

/**
 * Created by wenda on 2017/1/18.
 */

public class PayBean {

    /**
     * order_sn : 1701690041
     * wxpayinfo : {"appid":"wxff989c97f26783b1","partnerid":"1433741002","prepayid":"wx20170120152038b514ea8e320470581600","package":"Sign=WXPay","noncestr":"JRNUxa","timestamp":1484896838,"sign":"D0ED1F02136392ACBB894B587F7460EC"}
     */

    private String order_sn;
    private String payinfo;
    private WxpayinfoBean wxpayinfo;


    public String getPayinfo() {
        return payinfo;
    }

    public void setPayinfo(String payinfo) {
        this.payinfo = payinfo;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public WxpayinfoBean getWxpayinfo() {
        return wxpayinfo;
    }

    public void setWxpayinfo(WxpayinfoBean wxpayinfo) {
        this.wxpayinfo = wxpayinfo;
    }

    public static class WxpayinfoBean {
        /**
         * appid : wxff989c97f26783b1
         * partnerid : 1433741002
         * prepayid : wx20170120152038b514ea8e320470581600
         * package : Sign=WXPay
         * noncestr : JRNUxa
         * timestamp : 1484896838
         * sign : D0ED1F02136392ACBB894B587F7460EC
         */

        private String appid;
        private String partnerid;
        private String prepayid;
//        @SerializedName("package")
//        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }


        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
