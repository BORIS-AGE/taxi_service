package com.sergey.taxiservice.api.legacy;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import rx.functions.Func1;

public abstract class Mapper<T> implements Func1<JsonObject, ApiResponse<T>> {

    @Override
    public ApiResponse<T> call(JsonObject jsonObject) {
        try{
            JSONObject errors = null;
            boolean success = true;

            if(jsonObject.has("errors")){
                errors = new JSONObject(jsonObject.get("errors").getAsJsonObject().toString());
            }

            if(jsonObject.has("success")){
                success = jsonObject.get("success").getAsBoolean();
            }

            return new ApiResponse<>(errors, success, map(jsonObject));

        } catch (JSONException e){
            throw new RuntimeException("Error while mapping");
        }
    }

    public abstract T map(JsonObject jsonObject);
}
