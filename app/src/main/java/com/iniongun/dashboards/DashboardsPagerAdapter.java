package com.iniongun.dashboards;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iniongun.dashboards.utilities.Constants;
import com.iniongun.dashboards.utilities.SecurePreferences;
import com.iniongun.dashboards.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class DashboardsPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    SecurePreferences preferences;
    //List<String> urlList = Arrays.asList("https://ndr.shieldnigeriaproject.com/", "http://wallboard.mgic-nigeria.org:8000/dashboard");
    List<String> urlList;

    public DashboardsPagerAdapter(Context mContext) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        preferences = Utils.getSecurePreferences(mContext);
//        urlList = Arrays.asList(preferences.getString(Constants.DASHBOARD_URLS_KEY).split("|"));
        urlList = new ArrayList<>();
        String[] urls = preferences.getString(Constants.DASHBOARD_URLS_KEY).split("#");
        String timer = preferences.getString(Constants.DASHBOARD_SLIDER_TIMER_KEY);
        for(String url: urls){
            if (!url.equalsIgnoreCase(""))
                urlList.add(url);
        }
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.single_dashboard_layout, container, false);

        WebView webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("DashboardsPagerAdapter", "Web page started loading...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("DashboardsPagerAdapter", "Webpage finished loading");
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("DashboardsPagerAdapter", "An error occurred while loading webpage");
                Log.e("DashboardsPagerAdapter", (String) error.getDescription());
            }
        });
        webView.addJavascriptInterface(new WebViewJSInterface(mContext), "webViewJS");
        webView.loadUrl(urlList.get(position));

        //Log.i("DashboardsPagerAdapter", urlList.get(position));

        container.addView(view);

        return view;
    }

    private class WebViewJSInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebViewJSInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void jsOperations() {}
    }

}
