package com.nst.yourname.WHMCSClientapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.nst.yourname.R;
import java.lang.reflect.Field;

public class InvoiceFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String clientEmail = "";
    private String client_id = "";
    private String invoiceId = "";
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    Unbinder ubinder;
    @BindView(R.id.webview)
    WebView webView;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static InvoiceFragment newInstance(String str, String str2) {
        InvoiceFragment invoiceFragment = new InvoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        invoiceFragment.setArguments(bundle);
        return invoiceFragment;
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
        View inflate = layoutInflater.inflate((int) R.layout.fragment_invoice_data, viewGroup, false);
        this.ubinder = ButterKnife.bind(this, inflate);
        getDtaa();
        return inflate;
    }

    private void getDtaa() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.invoiceId = arguments.getString("invoice_id");
        }
        this.webView.setWebChromeClient(new WebChromeClient());
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        try {
            Field declaredField = Fragment.class.getDeclaredField("mChildFragmentManager");
            declaredField.setAccessible(true);
            declaredField.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }
}
