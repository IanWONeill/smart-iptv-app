package com.nst.yourname.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import java.util.ArrayList;
import java.util.Iterator;

public class ParentalControlSettingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.bt_save_password)
    Button btSavePassword;
    private String confirmPassword = "";
    private Context context;
    private LiveStreamDBHandler liveStreamDBHandler;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    private String newPassword = "";
    private String oldPassword = "";
    @BindView(R.id.tv_confirm_password)
    EditText tvConfirmPassword;
    @BindView(R.id.tv_new_password)
    EditText tvNewPassword;
    @BindView(R.id.tv_old_password)
    EditText tvOldPassword;
    Unbinder unbinder;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ParentalControlSettingFragment newInstance(String str, String str2) {
        ParentalControlSettingFragment parentalControlSettingFragment = new ParentalControlSettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        parentalControlSettingFragment.setArguments(bundle);
        return parentalControlSettingFragment;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate((int) R.layout.fragment_parental_control_setting, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        initialize();
        return inflate;
    }

    private void initialize() {
        this.context = getContext();
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.oldPassword = String.valueOf(this.tvOldPassword.getText());
        this.newPassword = String.valueOf(this.tvNewPassword.getText());
        this.confirmPassword = String.valueOf(this.tvConfirmPassword.getText());
        this.tvOldPassword.requestFocus();
        ((InputMethodManager) this.context.getSystemService("input_method")).showSoftInput(this.tvOldPassword, 1);
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onAttach(Context context2) {
        super.onAttach(context2);
        if (context2 instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context2;
            return;
        }
        throw new RuntimeException(context2.toString() + " must implement OnFragmentInteractionListener");
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @OnClick({R.id.bt_save_password})
    public void onViewClicked() {
        if (this.context != null) {
            this.oldPassword = String.valueOf(this.tvOldPassword.getText());
            this.newPassword = String.valueOf(this.tvNewPassword.getText());
            this.confirmPassword = String.valueOf(this.tvConfirmPassword.getText());
            String string = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString("username", "");
            if (!passwordValidationCheck(string, this.oldPassword, SharepreferenceDBHandler.getUserID(this.context))) {
                if (this.context != null) {
                    Toast.makeText(this.context, getResources().getString(R.string.invalid_old_password), 0).show();
                }
                clearEditText();
            } else if (!compNewConfirmPassword(this.newPassword, this.confirmPassword)) {
            } else {
                if (this.newPassword.equals(this.confirmPassword)) {
                    updateSuccessfull(this.liveStreamDBHandler.upDatePassword(string, this.newPassword, SharepreferenceDBHandler.getUserID(this.context)));
                    getActivity().finish();
                    return;
                }
                if (this.context != null) {
                    Toast.makeText(this.context, getResources().getString(R.string.parental_setting_new_confirm_password_error), 0).show();
                }
                clearEditText();
            }
        }
    }

    private void clearEditText() {
        if (this.tvOldPassword != null && this.tvConfirmPassword != null && this.tvNewPassword != null) {
            this.tvOldPassword.getText().clear();
            this.tvConfirmPassword.getText().clear();
            this.tvNewPassword.getText().clear();
        }
    }

    private void updateSuccessfull(boolean z) {
        if (z) {
            if (this.context != null) {
                Toast.makeText(this.context, getResources().getString(R.string.password_updated), 0).show();
            }
            clearEditText();
            return;
        }
        if (this.context != null) {
            Toast.makeText(this.context, getResources().getString(R.string.something_wrong), 0).show();
        }
        clearEditText();
    }

    private boolean compNewConfirmPassword(String str, String str2) {
        if (str == null || str.equals("") || str.isEmpty()) {
            if (this.context != null) {
                Toast.makeText(this.context, getResources().getString(R.string.enter_new_password_error), 0).show();
            }
            return false;
        }
        if ((str == null || str.isEmpty() || str.equals("") || str2 != null || !str2.isEmpty()) && !str2.equals("")) {
            if ((str == null || str.isEmpty() || str.equals("") || str2 == null || str2.isEmpty()) && str2.equals("")) {
                return false;
            }
            return true;
        } else if (this.context != null) {
            Toast.makeText(this.context, getResources().getString(R.string.enter_confirm_password_error), 0).show();
            return false;
        }
        return false;
    }

    private boolean passwordValidationCheck(String str, String str2, int i) {
        String str3;
        boolean z;
        ArrayList<PasswordDBModel> allPassword = new LiveStreamDBHandler(this.context).getAllPassword(SharepreferenceDBHandler.getUserID(this.context));
        if (allPassword != null) {
            Iterator<PasswordDBModel> it = allPassword.iterator();
            str3 = "";
            z = false;
            while (it.hasNext()) {
                PasswordDBModel next = it.next();
                if (next.getUserDetail().equals(str) && !next.getUserPassword().isEmpty()) {
                    str3 = next.getUserPassword();
                    z = true;
                }
            }
        } else {
            str3 = "";
            z = false;
        }
        return z && str2 != null && !str2.isEmpty() && !str2.equals("") && !str3.equals("") && str3.equals(str2);
    }
}
