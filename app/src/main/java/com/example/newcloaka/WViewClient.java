package com.example.newcloaka;

import android.net.http.SslError;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;

public class WViewClient extends WebViewClient {


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        final AlertDialog.Builder b = new AlertDialog.Builder(view.getContext());

        String message = "Ошибка SSL сертификата.";
        b.setTitle(message);
        if (error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
            message = "Центру сертификации не доверяют.";
        } else if (error.getPrimaryError() == SslError.SSL_EXPIRED) {
            message = "Срок действия сертификата истек.";
        } else if (error.getPrimaryError() == SslError.SSL_IDMISMATCH) {
            message = "Несоответствие имени хоста сертификата.";
        } else if (error.getPrimaryError() == SslError.SSL_NOTYETVALID) {
            message = "Сертификат еще не действителен.";
        }

        message += " Продолжить?";

        b.setTitle("SSL Certificate Error");
        b.setMessage(message);

        b.setPositiveButton("продолжить", (dialog, which) -> handler.proceed());
        b.setNegativeButton("закрыть", (dialog, which) -> handler.cancel());


        final AlertDialog dialog = b.create();
        dialog.show();
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        CookieManager.getInstance().flush();
        return false;
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        CookieManager.getInstance().flush();
    }


    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        CookieManager.getInstance().flush();
    }

}
