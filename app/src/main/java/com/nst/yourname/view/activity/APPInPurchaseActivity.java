package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.Utils;
import java.util.Calendar;

public class APPInPurchaseActivity extends Activity {
    private static final String ACTIVITY_NUMBER = "activity_num";
    public static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk8jH+eNvdwbyf+4Rt4DcLFubCulWdwmVUattc8fbm1bcWLxsEdBl38RJx6ldeNfJkOTfU3If4R3+5bDEopvk8ymDUXlHVsQR8CiH8zS9Q34ax7WHXXvqhzMkzCAO5V91KWfkf+Pk+ec4bPPSZHsTDIa3ASTqTJWUfymB3xEOKiPc0PJeIzGIm/bWuyV9UWaShs32MzObNDYXQZ+g8ktftNeXoHsoxvmanNhY8cjGcvMAL8j0bHC4iTb2H/CTLIW1IUZ2D8ps+DpU+ZTh8DH8DVIdRaS6FgDjaItuJOLm2PA0a6UJgpukdW2NWEZH2tRwFtDH3D19N883sv8RCPK6AwIDAQAB";
    private static final String LOG_TAG = "iabv3";
    public static final String MERCHANT_ID = "12141350975697687473";
    public static final String PRODUCT_ID = "com.ktvpro.ktvproiptvbox.billing";
    private BillingProcessor bp;
    public Context context;
    @BindView(R.id.date)
    TextView date;
    public boolean readyToPurchase = false;
    @BindView(R.id.time)
    TextView time;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_app_in_purchase);
        ButterKnife.bind(this);
        this.context = this;
        new Thread(new CountDownRunner()).start();
        if (!BillingProcessor.isIabServiceAvailable(this)) {
            showToast("In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
        }
        this.bp = new BillingProcessor(this, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            /* class com.nst.yourname.view.activity.APPInPurchaseActivity.AnonymousClass1 */

            @Override // com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler
            public void onBillingError(int i, @Nullable Throwable th) {
            }

            @Override // com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler
            public void onProductPurchased(@NonNull String str, @Nullable TransactionDetails transactionDetails) {
                APPInPurchaseActivity.this.updateTextViews();
            }

            @Override // com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler
            public void onBillingInitialized() {
                boolean unused = APPInPurchaseActivity.this.readyToPurchase = true;
                APPInPurchaseActivity.this.updateTextViews();
            }

            @Override // com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler
            public void onPurchaseHistoryRestored() {
                APPInPurchaseActivity.this.updateTextViews();
            }
        });
    }

    public void onResume() {
        super.onResume();
        updateTextViews();
    }

    public void onDestroy() {
        if (this.bp != null) {
            this.bp.release();
        }
        super.onDestroy();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (!this.bp.handleActivityResult(i, i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
    }

    public void updateTextViews() {
        TextView textView = (TextView) findViewById(R.id.tv_app_purchased);
        Button button = (Button) findViewById(R.id.purchaseButton);
        if (this.bp.isPurchased(PRODUCT_ID)) {
            button.setVisibility(8);
            textView.setVisibility(0);
        } else {
            button.setVisibility(0);
            textView.setVisibility(8);
        }
        button.setOnFocusChangeListener(new OnFocusChangeAccountListener(button));
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.05f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                }
            }
        }

        private void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }

    private void showToast(String str) {
        Toast.makeText(this, str, 1).show();
    }

    public void onClick(View view) {
        if (this.readyToPurchase && view.getId() == R.id.purchaseButton) {
            this.bp.purchase(this, PRODUCT_ID);
        }
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    APPInPurchaseActivity.this.doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            /* class com.nst.yourname.view.activity.APPInPurchaseActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(APPInPurchaseActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (APPInPurchaseActivity.this.time != null) {
                        APPInPurchaseActivity.this.time.setText(time);
                    }
                    if (APPInPurchaseActivity.this.date != null) {
                        APPInPurchaseActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }
}
