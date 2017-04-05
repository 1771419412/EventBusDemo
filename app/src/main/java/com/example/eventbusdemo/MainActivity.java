package com.example.eventbusdemo;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity {
    private Button btn_start;
    private TextView tv;
    private WebView mWebView;
    private String url = "http://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //注册EventBus
        EventBus.getDefault().register(this);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        initViews();
        initX5();


    }

    private void initX5() {
        mWebView= (WebView) findViewById(R.id.web_context);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });

    }

   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void initViews() {
        btn_start= (Button) findViewById(R.id.btn_start);
        tv= (TextView) findViewById(R.id.tv);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),ActivityB.class);
                startActivity(intent);
            }
        });


    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void  onEventMainThread(Event event){
        String msg="onEventMainThread收到了消息：" + event.getMsg();
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }
}
