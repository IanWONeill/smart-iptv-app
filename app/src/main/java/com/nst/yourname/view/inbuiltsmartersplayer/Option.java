package com.nst.yourname.view.inbuiltsmartersplayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Option implements Serializable, Cloneable {
    private int category;
    private String name;
    private Object value;

    private Option(int i, String str, Object obj) {
        this.category = i;
        this.name = str;
        this.value = obj;
    }

    public static Option create(int i, String str, String str2) {
        return new Option(i, str, str2);
    }

    public static Option create(int i, String str, Long l) {
        return new Option(i, str, l);
    }

    public int getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Option option = (Option) obj;
        if (this.category != option.category) {
            return false;
        }
        if (this.name == null ? option.name != null : !this.name.equals(option.name)) {
            return false;
        }
        if (this.value != null) {
            return this.value.equals(option.value);
        }
        if (option.value == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.category * 31) + (this.name != null ? this.name.hashCode() : 0)) * 31;
        if (this.value != null) {
            i = this.value.hashCode();
        }
        return hashCode + i;
    }

    @Override // java.lang.Object
    public Option clone() throws CloneNotSupportedException {
        return (Option) super.clone();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.view.inbuiltsmartersplayer.Option.create(int, java.lang.String, java.lang.Long):com.nst.yourname.view.inbuiltsmartersplayer.Option
     arg types: [int, java.lang.String, long]
     candidates:
      com.nst.yourname.view.inbuiltsmartersplayer.Option.create(int, java.lang.String, java.lang.String):com.nst.yourname.view.inbuiltsmartersplayer.Option
      com.nst.yourname.view.inbuiltsmartersplayer.Option.create(int, java.lang.String, java.lang.Long):com.nst.yourname.view.inbuiltsmartersplayer.Option */
    public static List<Option> preset4Realtime() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(create(4, "start-on-prepared", (Long) 0L));
        arrayList.add(create(1, "http-detect-range-support", (Long) 0L));
        arrayList.add(create(2, "skip_loop_filter", (Long) 8L));
        arrayList.add(create(1, "analyzemaxduration", (Long) 100L));
        arrayList.add(create(1, "probesize", (Long) 1024L));
        arrayList.add(create(1, "flush_packets", (Long) 1L));
        arrayList.add(create(1, "packet-buffering", (Long) 0L));
        arrayList.add(create(1, "framedrop", (Long) 1L));
        return arrayList;
    }
}
