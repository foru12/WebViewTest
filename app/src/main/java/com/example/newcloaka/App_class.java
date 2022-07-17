package com.example.newcloaka;

import static android.content.Context.TELEPHONY_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;

public class App_class {


    WebView webView;
    Context context;

    public App_class(Context context) {
        this.context = context;
    }

    public void GetView(){
        WebViewLoading();

    }

    private void WebViewLoading() {

        if(isOnline()) {
            websetting();
            String link = "https://yandex.ru"; //ссылка
            webView.loadUrl(link);


            webView.setWebViewClient(new WViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url == null || url.startsWith("http://") || url.startsWith("https://"))
                        return false;
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        view.getContext().startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        return true;
                    }
                }

                @Override //отслеживание редиректа
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    String url_two = Uri.parse(link).getHost();
                    webView.setVisibility(View.VISIBLE);

                }
            });
        }
        else context.startActivity(new Intent(context,MainActivity.class));
    }


    //настроики  webView
    private void websetting() {
        webView = ((Activity)context).findViewById(R.id.webView);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_NONE, null);
    }

    //если устроиство онлайн
    private boolean isOnline() {
        ConnectivityManager connManager = (ConnectivityManager) ((Activity)context).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
