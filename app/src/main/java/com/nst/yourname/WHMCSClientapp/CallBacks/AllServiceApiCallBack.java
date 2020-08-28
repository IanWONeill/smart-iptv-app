package com.nst.yourname.WHMCSClientapp.CallBacks;

import com.nst.yourname.WHMCSClientapp.modelclassess.ActiveServiceModelClass;
import java.util.ArrayList;

public interface AllServiceApiCallBack {
    void getAllServiceFailled(String str);

    void getAllServiceResponse(ArrayList<ActiveServiceModelClass> arrayList);
}
