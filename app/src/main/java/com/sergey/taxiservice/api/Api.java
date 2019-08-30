package com.sergey.taxiservice.api;

import com.google.gson.JsonObject;
import com.sergey.taxiservice.models.geo.api.GeoData;
import com.sergey.taxiservice.models.BaseModel;
import com.sergey.taxiservice.models.Cost;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.models.companion.CreateCompanion;
import com.sergey.taxiservice.models.companion.LoadCompanion;
import com.sergey.taxiservice.models.companion.RideInfo;
import com.sergey.taxiservice.models.order.CreateOrder;
import com.sergey.taxiservice.models.order.LoadOrder;
import com.sergey.taxiservice.models.share.ActionModel;
import com.sergey.taxiservice.models.share.FeedbackModel;
import com.sergey.taxiservice.models.share.InviteModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

public interface Api {

    @FormUrlEncoded
    @POST("api/auth.login")
    Observable<JsonObject> signIn(@Field("phone_number") String phoneNumber,
                                  @Field("password") String password,
                                  @Field("fcm_token") String token);

    @FormUrlEncoded
    @POST("api/auth.register")
    Observable<JsonObject> signUp(@Field("phone_number") String phoneNumber,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("password_confirmation") String confirmPassword,
                                  @Field("name") String firstName,
                                  @Field("surname") String lastName,
                                  @Field("fcm_token") String token);

    @FormUrlEncoded
    @POST("api/auth.sendSmsVerification")
    Observable<JsonObject> sendSmsVerification(@Field("token") String token);

    @FormUrlEncoded
    @POST("api/auth.confirmSmsVerification")
    Observable<JsonObject> confirmSmsVerification(@Field("token") String token,
                                                  @Field("confirm_code") String code);

    @Multipart
    @POST("api/client.edit")
    Observable<JsonObject> editClient(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("api/client.get")
    Observable<UserModel> getClient(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/client.resetAvatar")
    Observable<BaseModel> deleteAvatar(@Field("token") String token);

    @FormUrlEncoded
    @POST("api/rides.searchGeoData")
    Observable<GeoData> ridesSearchGeoData(@Field("token") String token,
                                           @Field("query") String query);

    @FormUrlEncoded
    @POST("api/rides.searchGeoDataLatLng")
    Observable<GeoData> searchAddress(@Field("token") String token,
                                      @Field("lat") double lat,
                                      @Field("lng") double lng);

    @FormUrlEncoded
    @POST("api/rides.getCost")
    Observable<Cost> getCost(@Field("token") String token,
                             @Field("routes") String routeArray);

    @FormUrlEncoded
    @POST("api/rides.create")
    Observable<CreateOrder> createOrder(@Field("token") String token,
                                        @Field("routes") String routeArray,
                                        @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/rides.companion.create")
    Observable<CreateCompanion> createCompanion(@Field("token") String token,
                                                @Field("routes") String routeArray,
                                                @FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/rides.get")
    Observable<LoadOrder> getRide(@Field("token") String token,
                                  @Field("id") int id);

    @FormUrlEncoded
    @POST("api/rides.companion.get")
    Observable<LoadOrder> getCompanionRide(@Field("token") String token,
                                           @Field("id") int id);

    @FormUrlEncoded
    @POST("api/rides.companion.map")
    Observable<LoadCompanion> getCompanion(@Field("token") String token,
                                           @Field("id") int id);

    @FormUrlEncoded
    @POST("api/rides.cancel")
    Observable<BaseModel> cancelOrder(@Field("token") String token,
                                      @Field("id") int id);

    @FormUrlEncoded
    @POST("api/rides.companion.cancel")
    Observable<BaseModel> cancelCompanion(@Field("token") String token,
                                          @Field("id") int id);

    @FormUrlEncoded
    @POST("api/rides.companion.sendRequest")
    Observable<InviteModel> sendInvite(@Field("token") String token,
                                       @Field("id_host") int host_id,
                                       @Field("id_client") int client_id);

    @FormUrlEncoded
    @POST("api/rides.companion.changeRideRequestStatus")
    Observable<BaseModel> updateInvite(@Field("token") String token,
                                       @Field("id") int id,
                                       @Field("client_id") int clientId,
                                       @Field("status") int status);

    @FormUrlEncoded
    @POST("api/rides.companion.getRide")
    Observable<RideInfo> loadCompanionInfo(@Field("token") String token,
                                           @Field("id") int id);

    @FormUrlEncoded
    @POST("api/rides.companion.createRide")
    Observable<CreateOrder> startCompanion(@Field("token") String token,
                                           @Field("id") int id);

    @FormUrlEncoded
    @POST("api/feedback.getReceived")
    Observable<List<FeedbackModel>> getFeedback(@Field("token") String token,
                                                @Field("user_id") int id);

    @FormUrlEncoded
    @POST("api/respects.send")
    Observable<ActionModel> sendRespect(@Field("token") String token,
                                        @Field("receiver_id") int id);

    @FormUrlEncoded
    @POST("api/winks.send")
    Observable<ActionModel> sendWinks(@Field("token") String token,
                                      @Field("receiver_id") int id);
}
