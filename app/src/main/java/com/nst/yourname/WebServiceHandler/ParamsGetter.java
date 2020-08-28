package com.nst.yourname.WebServiceHandler;

import java.io.File;

public class ParamsGetter {
    private File file = null;
    private String key;
    private String values;

    public ParamsGetter() {
    }

    public ParamsGetter(String str, String str2) {
        setKey(str);
        setValues(str2);
    }

    public ParamsGetter(String str, File file2) {
        setKey(str);
        setFile(file2);
    }

    public String getValues() {
        return this.values;
    }

    public void setValues(String str) {
        this.values = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file2) {
        this.file = file2;
    }
}
