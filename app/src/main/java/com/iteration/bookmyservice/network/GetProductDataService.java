package com.iteration.bookmyservice.network;

import com.iteration.bookmyservice.model.BookingList;
import com.iteration.bookmyservice.model.Message;
import com.iteration.bookmyservice.model.MessageLogin;
import com.iteration.bookmyservice.model.MessageOTP;
import com.iteration.bookmyservice.model.ServiceList;
import com.iteration.bookmyservice.model.SliderList;
import com.iteration.bookmyservice.model.TimeslotList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetProductDataService {

    @GET("webservice/slider.php")
    Call<SliderList> getSliderData();

    @GET("webservice/service.php")
    Call<ServiceList> getServiceData();

    @GET("webservice/timeslot.php")
    Call<TimeslotList> getTimeslotData();

    @FormUrlEncoded
    @POST("webservice/booking_timeslot.php")
    Call<TimeslotList> getBookingTimeslotData(@Field("booking_date") String booking_date,
                                              @Field("booking_service_opt") String booking_service_opt);

    @FormUrlEncoded
    @POST("webservice/emailsendotp.php")
    Call<MessageOTP> getEmailSendOtpData(@Field("email") String booking_email);

    @FormUrlEncoded
    @POST("webservice/addbooking.php")
    Call<Message> getAddBookingData(@Field("booking_name") String booking_name,
                                    @Field("booking_email") String booking_email,
                                    @Field("booking_phone") String booking_phone,
                                    @Field("booking_service_opt") String booking_service_opt,
                                    @Field("booking_address") String booking_address,
                                    @Field("booking_service_name") String booking_service_name,
                                    @Field("booking_date") String booking_date,
                                    @Field("booking_vinno") String booking_vinno,
                                    @Field("booking_make") String booking_make,
                                    @Field("booking_model") String booking_model,
                                    @Field("booking_msgyear") String booking_msgyear,
                                    @Field("booking_enginetype") String booking_enginetype,
                                    @Field("booking_vanplateno") String booking_vanplateno,
                                    @Field("booking_comment") String booking_comment,
                                    @Field("booking_t_id") String booking_t_id,
                                    @Field("booking_status") String booking_status,
                                    @Field("booking_time") String booking_time);

    @FormUrlEncoded
    @POST("webservice/login.php")
    Call<MessageLogin> getLoginData(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("webservice/admin_booking_list.php")
    Call<BookingList> getAdminBookingData(@Field("booking_date") String booking_date,
                                          @Field("booking_service_opt") String booking_service_opt,
                                          @Field("booking_status") String booking_status);

    @FormUrlEncoded
    @POST("webservice/manage_booking.php")
    Call<BookingList> getManageBookingData(@Field("booking_email") String email);

    @FormUrlEncoded
    @POST("webservice/delete_booking.php")
    Call<Message> getDeleteBookingData(@Field("booking_id") String booking_id);

    @FormUrlEncoded
    @POST("webservice/edit_booking.php")
    Call<Message> getEditBookingData(@Field("booking_id") String booking_id,
                                     @Field("booking_service_name") String booking_service_name,
                                     @Field("booking_t_id") String booking_t_id);

    @FormUrlEncoded
    @POST("webservice/conform_booking.php")
    Call<Message> getConformBookingData(@Field("booking_id") String booking_id,
                                        @Field("booking_status") String booking_status,
                                        @Field("booking_price") String booking_price,
                                        @Field("booking_admin_comment") String booking_admin_comment);

    @FormUrlEncoded
    @POST("webservice/addinquiry.php")
    Call<Message> getInquiryData(@Field("i_name") String i_name,
                                 @Field("i_email") String i_email,
                                 @Field("i_phone") String i_phone,
                                 @Field("i_comment") String i_comment);


}
