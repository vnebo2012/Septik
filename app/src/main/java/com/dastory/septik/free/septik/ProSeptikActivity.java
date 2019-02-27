package com.dastory.septik.free.septik;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Timer;
import java.util.TimerTask;

public class ProSeptikActivity extends AppCompatActivity {

    private TextView mTextMessage;
    InterstitialAd mInterstitialAd;
    ImageButton mNewGameButton;

    private static int SPLASH_TIME_OUT = 5000;
    static Context context4;
    Timer t = new Timer();
    static Intent intent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mWebView.loadUrl("http://pro-septick.ru");
                    return true;

            }
            return false;


        }

    };

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_sep);

        //mNewGameButton = (ImageButton) findViewById(R.id.dom);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4882550262749386/6674802104");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                t.cancel();
            }
        });
        requestNewInterstitial();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        t.cancel();
                        ConnectivityManager icheck = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        boolean mob = icheck.getActiveNetworkInfo() != null;
                        if(mob) {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                beginPlayingGame();
                            }
                        } else {
                        }
                    }
                });
            }
        }, SPLASH_TIME_OUT, SPLASH_TIME_OUT);



        mWebView = (WebView) findViewById(R.id.webView);
        // включаем поддержку JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setDisplayZoomControls(true);
        // указываем страницу загрузки
        mWebView.loadUrl("http://pro-septick.ru");

        //mTextMessage = (TextView) findViewById(R.id.message);
       // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    //    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mWebView.setWebViewClient(new MyWebViewClient());

    }

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    //public void onBackPressed() {
    //public void onBackPressed22() {





    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()

                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")

                .build();
        mInterstitialAd.loadAd(adRequest);
    }
    private void beginPlayingGame() {
        // Play for a while, then display the New Game Button
    }










}
