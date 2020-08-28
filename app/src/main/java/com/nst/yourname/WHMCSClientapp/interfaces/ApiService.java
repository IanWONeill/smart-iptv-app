package com.nst.yourname.WHMCSClientapp.interfaces;

import com.nst.yourname.WHMCSClientapp.modelclassess.ActiveServiceModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.BuyNowModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.DepartmentClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.InvoicesModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.LoginWHMCSModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.OpenDepartmentClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.ServicesIncoiveTicketCoutModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.TickedMessageModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.TicketModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.UserAllServiceModelClass;
import com.nst.yourname.model.FreeTrailModelClass;
import java.util.ArrayList;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<LoginWHMCSModelClass> AuthcheckServices(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("username") String str5, @Field("password") String str6);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<OpenDepartmentClass> Opendeparments(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("message") String str4, @Field("deptid") String str5, @Field("clientid") int i, @Field("subject") String str6);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<HashMap> getAdduser(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("userid") int i, @Field("appkey") String str4, @Field("custom") String str5);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<ArrayList<ActiveServiceModelClass>> getAllActiveservices(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("clientid") int i, @Field("status") String str5);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<TickedMessageModelClass> getAllTicketMessage(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("ticketid") String str5);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<BuyNowModelClass> getButNow(@Field("api_username") String str, @Field("api_password") String str2, @Field("username") String str3, @Field("password") String str4, @Field("command") String str5, @Field("custom") String str6, @Field("clientid") int i);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<FreeTrailModelClass> getFreeTrailApi(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("emailaddress") String str5, @Field("username") String str6, @Field("password") String str7, @Field("activation_code") String str8, @Field("app_package") String str9);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<InvoicesModelClass> getInvoices(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("userid") int i, @Field("status") String str5);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<ServicesIncoiveTicketCoutModelClass> getServiceInvoiceTicketCount(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("clientid") int i);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<UserAllServiceModelClass> getUserAllServices(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("username") String str5, @Field("password") String str6);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<DepartmentClass> getdeparments(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("clientid") int i);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<TicketModelClass> getreply(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("message") String str5, @Field("clientid") int i, @Field("ticketid") String str6);

    @FormUrlEncoded
    @POST("modules/addons/AppProducts/response.php")
    Call<TicketModelClass> gettickets(@Field("api_username") String str, @Field("api_password") String str2, @Field("command") String str3, @Field("custom") String str4, @Field("clientid") int i);
}
