package com.nst.yourname.view.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelper extends DefaultHandler {
    String TAG = "XMLHelper";
    private String URL_MAIN = "";
    private Context context;
    Boolean currTag = false;
    String currTagVal = "";
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences.Editor loginPrefsEditor;
    private MultiUserDBHandler multiuserdbhandler;
    private XMLTVProgrammePojo programme = null;
    private ArrayList<XMLTVProgrammePojo> programmes = new ArrayList<>();

    public ArrayList<XMLTVProgrammePojo> getPostsList() {
        return this.programmes;
    }

    public void get(Context context2) {
        this.context = context2;
        try {
            this.loginPreferencesAfterLogin = context2.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            String string = this.loginPreferencesAfterLogin.getString("username", "");
            String string2 = this.loginPreferencesAfterLogin.getString("password", "");
            if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(context2).equals(AppConst.TYPE_M3U)) {
                String urlepg = Utils.getUrlepg(context2);
                this.URL_MAIN = urlepg + "xmltv.php?username=" + string + "&password=" + string2;
            } else {
                this.multiuserdbhandler = new MultiUserDBHandler(context2);
                this.URL_MAIN = this.multiuserdbhandler.getUserEPG(SharepreferenceDBHandler.getUserID(context2));
            }
            if (this.URL_MAIN != null && !this.URL_MAIN.isEmpty() && !this.URL_MAIN.equalsIgnoreCase("")) {
                XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                xMLReader.setContentHandler(this);
                xMLReader.parse(new InputSource(new URL(this.URL_MAIN).openStream()));
            }
        } catch (Exception e) {
            String str = this.TAG;
            Log.e(str, "Exception: " + e.getMessage());
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        if (this.currTag.booleanValue()) {
            this.currTagVal += new String(cArr, i, i2);
            this.currTag = false;
        }
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        this.currTag = false;
        if (str2.equalsIgnoreCase("title")) {
            this.programme.setTitle(this.currTagVal);
        } else if (str2.equalsIgnoreCase("desc")) {
            this.programme.setDesc(this.currTagVal);
        } else if (str2.equalsIgnoreCase("programme")) {
            this.programmes.add(this.programme);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00a1 A[Catch:{ Exception -> 0x00ba }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00aa A[Catch:{ Exception -> 0x00ba }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b3 A[Catch:{ Exception -> 0x00ba }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        boolean z;
        boolean z2;
        String str4 = this.TAG;
        Log.i(str4, "TAG: " + str2);
        this.currTag = true;
        this.currTagVal = "";
        if (str2.equals("programme")) {
            this.programme = new XMLTVProgrammePojo();
            int i = 0;
            if (attributes != null) {
                try {
                    if (attributes.getLength() != 0) {
                        int i2 = 0;
                        z2 = false;
                        z = false;
                        while (i < attributes.getLength()) {
                            if (attributes.getLocalName(i) != null && attributes.getLocalName(i).equalsIgnoreCase("start")) {
                                this.programme.setStart(attributes.getValue(i));
                                i2 = 1;
                            } else if (attributes.getLocalName(i) != null && attributes.getLocalName(i).equalsIgnoreCase("stop")) {
                                this.programme.setStop(attributes.getValue(i));
                                z2 = true;
                            } else if (attributes.getLocalName(i) != null && attributes.getLocalName(i).equalsIgnoreCase("channel")) {
                                this.programme.setChannel(attributes.getValue(i));
                                z = true;
                            }
                            i++;
                        }
                        i = i2;
                        if (i == 0) {
                            this.programme.setStart("");
                        }
                        if (!z2) {
                            this.programme.setStop("");
                        }
                        if (z) {
                            this.programme.setChannel("");
                            return;
                        }
                        return;
                    }
                } catch (Exception unused) {
                    return;
                }
            }
            z2 = false;
            z = false;
            if (i == 0) {
            }
            if (!z2) {
            }
            if (z) {
            }
        }
    }
}
