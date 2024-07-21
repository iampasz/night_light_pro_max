package com.appsforkids.pasz.nightlightpromax.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONValidator {
    public static boolean isJSONValid(String jsonString) {
        try {
            // Попытка создать JSONObject из строки
            new JSONObject(jsonString);
            return true;
        } catch (JSONException e) {
            try {
                // Если не удалось создать JSONObject, попытка создать JSONArray
                new JSONArray(jsonString);
                return true;
            } catch (JSONException ex) {
                // Если не удалось создать JSONObject и JSONArray, строка не является JSON
                return false;
            }
        }
    }
}