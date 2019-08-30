package com.sergey.taxiservice.util;

import android.support.annotation.Nullable;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UiUtils {

    public static void setErrorsIfExist(JSONObject errors, String errorKey, EditText field, String unknownError) {
        if(errors.has(errorKey)){
            try{
                JSONArray jsonArray = errors.getJSONArray(errorKey);
                field.setError(getAllStrings(jsonArray, unknownError));

            } catch (JSONException e){
                field.setError(unknownError);
            }
        }
    }

    private static StringBuilder getAllStrings(JSONArray jsonArray, @Nullable String ifEmpty){
        if(jsonArray.length() <= 0){
            return new StringBuilder(ifEmpty);
        }

        StringBuilder stringBuilder = new StringBuilder();
        try{
            stringBuilder.append(jsonArray.getString(0));
        } catch (JSONException e){
            e.printStackTrace();
            return stringBuilder.append(ifEmpty);
        }

        for(int i = 1; i < jsonArray.length(); i++){
            try{
                stringBuilder.append(", ").append(jsonArray.getString(i).toLowerCase());

            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        return stringBuilder;
    }
}
