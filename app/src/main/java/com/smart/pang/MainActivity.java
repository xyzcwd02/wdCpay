package com.smart.pang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wenda.pay.AliPayAPI;
import com.wenda.pay.AliPayReq;
import com.wenda.pay.PayAPI;
import com.wenda.pay.WechatPayReq;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       findViewById(R.id.bt_alipay).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {//支付宝
               AliPayAPI.Config config = new AliPayAPI.Config.Builder()
                       .setOrderInfo("app_id=2017010904947813&biz_content=%7B%22out_trade_no%22%3A%221706263126%22%2C%22subject%22%3A%22%5Cu5468%5Cu5e84%5Cu68a6%5Cu8776+-+%5Cu60a6%5Cu8bfb%5Cu4e0a%5Cu6d77%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F211.144.104.253%3A65529%2Falinotify&sign=JU9UqRAsrmY%2FT5JSpCUqtXbZr%2Bck0BMI1yWgE5oiPSs0o%2B7DnGonnMDfuPDvGbTMOZz9uJzWk1hEQOBhv01DzCwGVs%2BWgcgmgWFqXqE7X6vra%2FJb1qAWll1DCgFyTY9Ea5%2BGrp%2BAv7U54FOOxz068YqFVpQlyvUis7CyQ2Bpkm5lWGZME9Q45InsS0gD82FA1gpngUNfndZ3%2FmoBebd0mRoilFSRMUHkLHmjQEVz8biMpr1%2FjJOHodbjJipLQ1%2BxAwuCf1z17Nb0R7vBcvDSmcFqbweyY3jyom1cpwZeMuXFjDx%2FRFVuS28dtzpu63UxM9Sav%2B0jk84pwQvlSetiLw%3D%3D&sign_type=RSA2&timestamp=2017-03-04+17%3A02%3A47&version=1.0") //设置私钥
                       .create();

               AliPayReq aliPayReq = new AliPayReq.Builder()
                       .with(MainActivity.this)
                       .apply(config)
                       .create()
                       .setOnAliPayListener(new AliPayReq.OnAliPayListener() {
                           @Override
                           public void onPaySuccess(String resultInfo) {
                               Toast.makeText(MainActivity.this,"支付成功",Toast.LENGTH_LONG).show();
                           }

                           @Override
                           public void onPayFailure(String resultInfo) {
                               Toast.makeText(MainActivity.this,"支付失败",Toast.LENGTH_LONG).show();
                           }

                           @Override
                           public void onPayConfirmimg(String resultInfo) {
                               Toast.makeText(MainActivity.this,"支付失败1",Toast.LENGTH_LONG).show();
                           }

                           @Override
                           public void onPayCheck(String status) {
                               Toast.makeText(MainActivity.this,"支付失败2",Toast.LENGTH_LONG).show();
                           }
                       });
               AliPayAPI.getInstance().sendPayReq(aliPayReq);
               //关于支付宝支付的回调
               //aliPayReq.setOnAliPayListener(new OnAliPayListener);

           }
       });

       findViewById(R.id.bt_wxpay).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {//微信
               getNetData();
           }
       });
    }

    private void getNetData() {
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(MainActivity.this) //activity实例
                .setAppId("wxff989c97f26783b1") //微信支付AppID
                .setPartnerId("1433741002")//微信支付商户号
                .setPrepayId("wx20170307112556bd9969dbfd0294057248")//预支付码
                .setNonceStr("evvVLW")
                .setTimeStamp("1488857156")//时间戳
                .setSign("7A5D7756CB59DDADB445F6FFC921EAF8")//签名
                .create();
        wechatPayReq.init(getApplicationContext(), "wxff989c97f26783b1");      //要在支付前调用
        WechatPayReq.getInstance().setWXPayResultCallBack(new WechatPayReq.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"支付成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(int error_code) {
                Toast.makeText(MainActivity.this,"s1",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this,"s2",Toast.LENGTH_LONG).show();
            }
        });
        PayAPI.getInstance().sendPayRequest(wechatPayReq);

    }
}
