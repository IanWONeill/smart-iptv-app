package com.nst.yourname.WebServiceHandler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Globals {
    public static String RandomNumber = "";
    public static int frag_ID;
    public static Fragment fragment;
    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    public static Webservices getWebservices;
    public static List<ParamsGetter> getterList = new ArrayList();
    public static JSONArray jsonArr;
    public static JSONObject jsonObj;

    public static String S(String str) {
        try {
            return (jsonObj.getString(str) == null || jsonObj.getString(str).equals("null") || jsonObj.getString(str).equals("")) ? "" : jsonObj.getString(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static ParamsGetter P(String str, String str2) {
        return new ParamsGetter(str, str2);
    }
}
