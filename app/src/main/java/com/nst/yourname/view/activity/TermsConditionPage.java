package com.nst.yourname.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;

public class TermsConditionPage extends AppCompatActivity {
    @BindView(R.id.accept)
    Button accept;
    WebView mWebView;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    @SuppressLint({"SetJavaScriptEnabled"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_terms_condition_page);
        ButterKnife.bind(this);
        changeStatusBarColor();
        this.mWebView = (WebView) findViewById(R.id.webview);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.loadUrl("file:///android_asset/terms.html");
        this.accept.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.TermsConditionPage.AnonymousClass1 */

            public void onClick(View view) {
                TermsConditionPage.this.startActivity(new Intent(TermsConditionPage.this, LoginActivity.class));
            }
        });
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(67108864);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(Integer.MIN_VALUE);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }
}
