package com.x2.myteammanager2.adapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public interface OnEventListener <T>{
    void onSuccess(JSONArray object);
    void onFailure(Exception e);
}