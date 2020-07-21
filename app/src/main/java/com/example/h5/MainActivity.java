package com.example.h5;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button call_js_btn;
    private Button call_android_btn;
    private WebView webView;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        webView = new WebView(this);
//        webView  = findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("https://www.baidu.com/");
//        setContentView(webView);

        call_js_btn = findViewById(R.id.button);
        call_android_btn = findViewById(R.id.button2);
        webView = findViewById(R.id.webView);
        call_js_btn.setOnClickListener(this);
        call_android_btn.setOnClickListener(this);

        webView.getSettings().setJavaScriptEnabled(true);      // 设置可以加载javascript
        webView.loadUrl("file:///android_asset/sjq.html");     // 从assets目录下面的加载html
        webView.setWebChromeClient(new WebChromeClient() {});  // 不设置的话button点击事件alert无法弹出

        // 打开js接口給H5调用，参数1为本地类名，参数2为别名；h5用window.别名.类名里的方法名才能调用方法里面的内容，例如：window.android.onSumResult();
        webView.addJavascriptInterface(new JsInterface(), "android");
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                // Android调用H5中JS不带返回值的方法，传参
                webView.loadUrl("javascript:androidCallJs('我来自Android')");
                // 当传入变量名时，需要用转义符隔开
//                String content="9880";
//                webView.loadUrl("javascript:androidCallJs(\""   + content +   "\")"   );
                break;
            case R.id.button2:
                // Android调用H5中JS有返回值的方法,Android4.4以上才能用这个方法
                webView.loadUrl("javascript:getSummation(1,2)");
                webView.evaluateJavascript("getSum2(424121,2)", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.d("demo", "onReceiveValue:---------> " + s);
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    /**
     * 自己定义一个类，里面是提供给H5访问的方法
     */
    public class JsInterface {
        @JavascriptInterface // 一定要写，不然H5调不到下面的方法
        public void startFunction(final String text) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(MainActivity.this).
                            setMessage(text).show();
                }
            });
        }


        @JavascriptInterface
        public void onSumResult(int sum) {
            Log.d("demo", "onSumResult: ------>" + sum);
            Toast.makeText(MainActivity.this, sum + "", Toast.LENGTH_SHORT).show();
        }
    }



}
