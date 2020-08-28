package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.model.database.SharepreferenceDBHandler;

public class MultiSettingActivity extends AppCompatActivity {
    static final boolean $assertionsDisabled = false;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.btn_multi)
    Button btn_multi;
    Context context;
    ImageView defaults;
    @BindView(R.id.save)
    Button save;
    ImageView screen1;
    ImageView screen2;
    ImageView screen3;
    ImageView screen4;
    ImageView screen5;
    @BindView(R.id.screen_pic)
    ImageView screen_pic;
    @BindView(R.id.show_popup)
    CheckBox showPopup;
    public PopupWindow sreenPopupWindow;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_multi_setting_new);
        ButterKnife.bind(this);
        this.context = this;
        initialize();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0064, code lost:
        if (r1.equals("screen1") != false) goto L_0x0068;
     */
    private void initialize() {
        boolean isChecked = SharepreferenceDBHandler.isChecked(this.context);
        String screenTYPE = SharepreferenceDBHandler.getScreenTYPE(this.context);
        char c = 0;
        if (isChecked) {
            this.showPopup.setChecked(true);
            SharepreferenceDBHandler.setShowPop(true, this.context);
        } else {
            this.showPopup.setChecked(false);
            SharepreferenceDBHandler.setShowPop(false, this.context);
        }
        switch (screenTYPE.hashCode()) {
            case 1926384965:
                break;
            case 1926384966:
                if (screenTYPE.equals("screen2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1926384967:
                if (screenTYPE.equals("screen3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1926384968:
                if (screenTYPE.equals("screen4")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1926384969:
                if (screenTYPE.equals("screen5")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.screen_pic.setImageDrawable(getResources().getDrawable(R.drawable.screen1));
                break;
            case 1:
                this.screen_pic.setImageDrawable(getResources().getDrawable(R.drawable.screen2));
                break;
            case 2:
                this.screen_pic.setImageDrawable(getResources().getDrawable(R.drawable.screen3));
                break;
            case 3:
                this.screen_pic.setImageDrawable(getResources().getDrawable(R.drawable.screen4));
                break;
            case 4:
                this.screen_pic.setImageDrawable(getResources().getDrawable(R.drawable.screen5));
                break;
            default:
                this.screen_pic.setImageDrawable(getResources().getDrawable(R.drawable.default_));
                break;
        }
        if (this.showPopup != null) {
            this.showPopup.setOnFocusChangeListener(new onFocus(this.showPopup));
        }
        if (this.save != null) {
            this.save.setOnFocusChangeListener(new onFocus(this.save));
        }
        if (this.back != null) {
            this.back.setOnFocusChangeListener(new onFocus(this.back));
        }
        if (this.btn_multi != null) {
            this.btn_multi.setOnFocusChangeListener(new onFocus(this.btn_multi));
        }
        this.btn_multi.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass1 */

            public void onClick(View view) {
                MultiSettingActivity.this.screenPopup(MultiSettingActivity.this.context);
            }
        });
        this.save.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass2 */

            public void onClick(View view) {
                MultiSettingActivity.this.submit();
            }
        });
        this.back.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass3 */

            public void onClick(View view) {
                MultiSettingActivity.this.onBackPressed();
            }
        });
    }

    @SuppressLint({"ResourceType"})
    public void screenPopup(final Context context2) {
        View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate((int) R.layout.screen_selector_popup, (LinearLayout) findViewById(R.id.screenPopup));
        this.defaults = (ImageView) inflate.findViewById(R.id.deafult);
        this.screen1 = (ImageView) inflate.findViewById(R.id.screen1);
        this.screen2 = (ImageView) inflate.findViewById(R.id.screen2);
        this.screen3 = (ImageView) inflate.findViewById(R.id.screen3);
        this.screen4 = (ImageView) inflate.findViewById(R.id.screen4);
        this.screen5 = (ImageView) inflate.findViewById(R.id.screen5);
        this.sreenPopupWindow = new PopupWindow(context2);
        this.sreenPopupWindow.setContentView(inflate);
        this.sreenPopupWindow.setWidth(-1);
        this.sreenPopupWindow.setHeight(-1);
        this.sreenPopupWindow.setFocusable(true);
        this.sreenPopupWindow.showAtLocation(inflate, 0, 0, 0);
        this.defaults.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass4 */

            public void onClick(View view) {
                SharepreferenceDBHandler.setScreenTYPE("default", context2);
                MultiSettingActivity.this.screen_pic.setImageDrawable(MultiSettingActivity.this.getResources().getDrawable(R.drawable.default_));
                MultiSettingActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen1.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass5 */

            public void onClick(View view) {
                SharepreferenceDBHandler.setScreenTYPE("screen1", context2);
                MultiSettingActivity.this.screen_pic.setImageDrawable(MultiSettingActivity.this.getResources().getDrawable(R.drawable.screen1));
                MultiSettingActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen2.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass6 */

            public void onClick(View view) {
                SharepreferenceDBHandler.setScreenTYPE("screen2", context2);
                MultiSettingActivity.this.screen_pic.setImageDrawable(MultiSettingActivity.this.getResources().getDrawable(R.drawable.screen2));
                MultiSettingActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen3.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass7 */

            public void onClick(View view) {
                SharepreferenceDBHandler.setScreenTYPE("screen3", context2);
                MultiSettingActivity.this.screen_pic.setImageDrawable(MultiSettingActivity.this.getResources().getDrawable(R.drawable.screen3));
                MultiSettingActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen4.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass8 */

            public void onClick(View view) {
                SharepreferenceDBHandler.setScreenTYPE("screen4", context2);
                MultiSettingActivity.this.screen_pic.setImageDrawable(MultiSettingActivity.this.getResources().getDrawable(R.drawable.screen4));
                MultiSettingActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen5.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiSettingActivity.AnonymousClass9 */

            public void onClick(View view) {
                SharepreferenceDBHandler.setScreenTYPE("screen5", context2);
                MultiSettingActivity.this.screen_pic.setImageDrawable(MultiSettingActivity.this.getResources().getDrawable(R.drawable.screen5));
                MultiSettingActivity.this.sreenPopupWindow.dismiss();
            }
        });
    }

    public void submit() {
        if (this.showPopup.isChecked()) {
            SharepreferenceDBHandler.setValueChecked(true, this.context);
            SharepreferenceDBHandler.setShowPop(true, this.context);
            Toast.makeText(this.context, getResources().getString(R.string.player_setting_save), 0).show();
        } else {
            SharepreferenceDBHandler.setValueChecked(false, this.context);
            SharepreferenceDBHandler.setShowPop(false, this.context);
            Toast.makeText(this.context, getResources().getString(R.string.player_setting_save), 0).show();
        }
        onBackPressed();
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class onFocus implements View.OnFocusChangeListener {
        private final View view;

        public onFocus(View view2) {
            this.view = view2;
        }

        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                if (this.view.getTag() != null && this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.blue_btn_effect);
                } else if (this.view.getTag() != null && this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view.getTag() != null && this.view.getTag().equals("3")) {
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                if (this.view.getTag() != null && this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag() != null && this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag() != null && this.view.getTag().equals("3")) {
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
}
