package com.iteration.bookmyservice.network;

import com.iteration.bookmyservice.model.Message;
import com.iteration.bookmyservice.model.MessageOTP;
import com.iteration.bookmyservice.model.ServiceList;
import com.iteration.bookmyservice.model.Slider;
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
    @POST("webservice/emailsendotp.php")
    Call<MessageOTP> getEmailSendOtpData(@Field("email") String booking_email);

    @FormUrlEncoded
    @POST("webservice/addbooking.php")
    Call<Message> getAddBookingData(@Field("booking_name") String booking_name,
                                    @Field("booking_email") String booking_email,
                                    @Field("booking_phone") String booking_phone,
                                    @Field("booking_address") String booking_address,
                                    @Field("booking_service_id") String booking_service_id,
                                    @Field("booking_date") String booking_date,
                                    @Field("booking_vinno") String booking_vinno,
                                    @Field("booking_comment") String booking_comment,
                                    @Field("booking_t_id") String booking_t_id,
                                    @Field("booking_status") String booking_status);

}
