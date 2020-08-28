package com.nst.yourname.view.ijkplayer.widget.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.preference.ListPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.nst.yourname.R;

public class IjkListPreference extends ListPreference {
    private CharSequence[] mEntrySummaries;

    public static String mv() {
        return "W91ciBUViBTZXJ2aWNlIFByb3ZpZGVyIGhhcyBub3QgcHJvdmlkZWQgeW91ciB0aGUgb3JpZ2luYWwgYXBwLg==";
    }

    public static String mw() {
        return "3VjaCBraW5kIG9mIGFwcHMgY291bGQgc2VuZCB0aGUgZGF0YSB0byBsZWdhbCBhdXRob3JpdGllcy4=";
    }

    public static String pZGV() {
        return "W";
    }

    public IjkListPreference(Context context) {
        super(context);
        initPreference(context, null);
    }

    public IjkListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initPreference(context, attributeSet);
    }

    public IjkListPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initPreference(context, attributeSet);
    }

    public IjkListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initPreference(context, attributeSet);
    }

    private void initPreference(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.IjkListPreference, 0, 0);
        if (obtainStyledAttributes != null) {
            this.mEntrySummaries = obtainStyledAttributes.getTextArray(0);
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.support.v7.preference.Preference
    public void onSetInitialValue(boolean z, Object obj) {
        super.onSetInitialValue(z, obj);
        syncSummary();
    }

    @Override // android.support.v7.preference.ListPreference
    public void setValue(String str) {
        super.setValue(str);
        syncSummary();
    }

    @Override // android.support.v7.preference.ListPreference
    public void setValueIndex(int i) {
        super.setValueIndex(i);
        syncSummary();
    }

    public int getEntryIndex() {
        CharSequence[] entryValues = getEntryValues();
        String value = getValue();
        if (entryValues == null || value == null) {
            return -1;
        }
        for (int i = 0; i < entryValues.length; i++) {
            if (TextUtils.equals(value, entryValues[i])) {
                return i;
            }
        }
        return -1;
    }

    public void setEntrySummaries(Context context, int i) {
        setEntrySummaries(context.getResources().getTextArray(i));
    }

    public void setEntrySummaries(CharSequence[] charSequenceArr) {
        this.mEntrySummaries = charSequenceArr;
        notifyChanged();
    }

    public CharSequence[] getEntrySummaries() {
        return this.mEntrySummaries;
    }

    private void syncSummary() {
        int entryIndex = getEntryIndex();
        if (entryIndex >= 0) {
            if (this.mEntrySummaries == null || entryIndex >= this.mEntrySummaries.length) {
                setSummary(getEntries()[entryIndex]);
            } else {
                setSummary(this.mEntrySummaries[entryIndex]);
            }
        }
    }
}
