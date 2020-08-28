package com.nst.yourname.WHMCSClientapp.CallBacks;

import com.nst.yourname.WHMCSClientapp.modelclassess.InvoicesModelClass;
import java.util.List;

public interface InvoiceData {
    void getAllInvoiceResponse(List<InvoicesModelClass.Invoices.Invoice> list);

    void getResultFailed(String str);
}
