package com.sergey.taxiservice.interactor;

import android.net.Uri;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.api.Api;
import com.sergey.taxiservice.api.legacy.ApiResponse;
import com.sergey.taxiservice.api.legacy.Mapper;
import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.models.geo.Transformer;
import com.sergey.taxiservice.models.geo.api.GeoData;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
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
import com.sergey.taxiservice.util.RxTransformers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

@Singleton
public class DataStore {

    private Api api;
    private Gson gson;
    private PreferencesManager preferencesManager;

    @Inject
    public DataStore(Api api, Gson gson, PreferencesManager preferencesManager) {
        this.api = api;
        this.gson = gson;
        this.preferencesManager = preferencesManager;
    }

    public Observable<ApiResponse<String>> signUp(String phoneNumber, String email, String password, String confirmPassword, String firstName, String lastName) {
        return api.signUp(phoneNumber, email, password, confirmPassword, firstName, lastName, FirebaseInstanceId.getInstance().getToken()).map(new Mapper<String>() {
            @Override
            public String map(JsonObject jsonObject) {
                return jsonObject.has("token") ? jsonObject.get("token").getAsString() : null;
            }
        }).compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<ApiResponse<String>> signIn(String phoneNumber, String password) {
        return api.signIn(phoneNumber, password, FirebaseInstanceId.getInstance().getToken()).map(new Mapper<String>() {
            @Override
            public String map(JsonObject jsonObject) {
                return jsonObject.has("token") ? jsonObject.get("token").getAsString() : null;
            }
        }).compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<ApiResponse<Boolean>> sendSmsVerification(){
        return api.sendSmsVerification(preferencesManager.getToken()).map(new Mapper<Boolean>() {
            @Override
            public Boolean map(JsonObject jsonObject) {
                return jsonObject.has("success") && jsonObject.get("success").getAsBoolean();
            }
        }).compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<ApiResponse<Boolean>> confirmSmsVerification(String code){
        return api.confirmSmsVerification(preferencesManager.getToken(), code).map(new Mapper<Boolean>() {
            @Override
            public Boolean map(JsonObject jsonObject) {
                return jsonObject.has("success") && jsonObject.get("success").getAsBoolean();
            }
        }).compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<ApiResponse<Boolean>> editUserModel(UserModel userModel, Uri uri) {
        Map<String, RequestBody> params = userModel.toRemoteModel();
        params.put("token", RequestBody.create(MediaType.parse("text/plain"), preferencesManager.getToken()));

        if (uri != null && uri.getPath() != null) {
            File file = new File(uri.getPath());
            params.put("picture\"; filename=\"random.png\" ", RequestBody.create(MediaType.parse("image/*"), file));
        }

        return api.editClient(params).map(new Mapper<Boolean>() {
            @Override
            public Boolean map(JsonObject jsonObject) {
                return jsonObject.has("success") && jsonObject.get("success").getAsBoolean();
            }
        }).compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<UserModel> getUserModel() {
        return getUserModel(null)
                .doOnNext(userModel -> preferencesManager.saveUser(userModel));
    }

    public Observable<UserModel> getUserModel(@Nullable Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", preferencesManager.getToken());

        if(userId != null) {
            params.put("id", userId);
        }

        return api.getClient(params)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<Boolean> deleteAvatar() {
        return api.deleteAvatar(preferencesManager.getToken())
                .flatMap(baseModel -> getUserModel())
                .map(userModel -> userModel.getAvatar() == null || userModel.getAvatar().equals(""))
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<List<AddressModel>> searchGeoData(String query){
        return api.ridesSearchGeoData(preferencesManager.getToken(), query)
                .compose(RxTransformers.applyApiRequestSchedulers())
                .flatMap(this::saveGeoDataToDb);
    }

    public Observable<List<AddressModel>> searchAddress(double lat, double lng) {
        return api.searchAddress(preferencesManager.getToken(), lat, lng)
                .compose(RxTransformers.applyApiRequestSchedulers())
                .flatMap(this::saveGeoDataToDb);
    }

    public Observable<Cost> getCost(List<AddressModel> addresses) {
        return Observable.just(addresses)
                .map(Transformer::transform)
                .flatMap(maps -> api.getCost(preferencesManager.getToken(), gson.toJson(maps)))
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<CreateOrder> createOrder(List<AddressModel> addresses, Map<String, Object> params) {
        return Observable.just(addresses)
                .map(Transformer::transform)
                .flatMap(maps -> api.createOrder(preferencesManager.getToken(), gson.toJson(maps), params))
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<CreateCompanion> createCompanion(List<AddressModel> addresses, Map<String, Object> params) {
        params.putAll(addresses.remove(0).convertToStartRemoteModel());
        params.putAll(addresses.remove(addresses.size() - 1).convertToFinishRemoteModel());
        params.put("timeout", 120);

        return Observable.just(addresses)
                .map(Transformer::transform)
                .flatMap(maps -> api.createCompanion(preferencesManager.getToken(), gson.toJson(maps), params))
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<LoadOrder> getOrder(int orderId) {
        return api.getRide(preferencesManager.getToken(), orderId)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<LoadOrder> getCompanionOrder(int orderId) {
        return api.getCompanionRide(preferencesManager.getToken(), orderId)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<LoadCompanion> getCompanion(int id) {
        return api.getCompanion(preferencesManager.getToken(), id)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<BaseModel> cancelOrder(int orderId) {
        return api.cancelOrder(preferencesManager.getToken(), orderId)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<BaseModel> cancelCompanion(int orderId) {
        return api.cancelCompanion(preferencesManager.getToken(), orderId)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<InviteModel> sendInvite(int hostId, int clientId) {
        return api.sendInvite(preferencesManager.getToken(), hostId, clientId)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<BaseModel> updateInvite(int id, int clientId, int status) {
        return api.updateInvite(preferencesManager.getToken(), id, clientId, status)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<CreateOrder> startCompanion(int id) {
        return api.startCompanion(preferencesManager.getToken(), id)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<RideInfo> loadCompanionInfo(int id) {
        return api.loadCompanionInfo(preferencesManager.getToken(), id)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<List<FeedbackModel>> loadFeedback(int userId) {
        return api.getFeedback(preferencesManager.getToken(), userId)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<ActionModel> sendWinks(int userId) {
        return api.sendWinks(preferencesManager.getToken(), userId)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    public Observable<ActionModel> sendRespect(int userId) {
        return api.sendRespect(preferencesManager.getToken(), userId)
                .compose(RxTransformers.applyApiRequestSchedulers());
    }

    private Observable<List<AddressModel>> saveGeoDataToDb(GeoData geoData) {
        return Observable.just(geoData)
                .map(Transformer::transform)
                .map(this::saveToDb)
                .map(Transformer::transform);
    }

    private <T extends RealmObject> RealmList<T> saveToDb(RealmList<T> list) {
        Realm realm = TaxiApplication.getDbInstance();
        realm.executeTransaction(r -> {
            r.insertOrUpdate(list);
            r.close();
        });

        return list;
    }
}
