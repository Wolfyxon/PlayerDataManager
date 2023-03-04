package com.wolfyxon.playerdatamgr;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MojangAPI {
    PlayerDataMgr plugin;
    Utils utils;
    public MojangAPI(PlayerDataMgr main){plugin = main;utils=plugin.utils;}
    public static String httpRequest(String urlString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String res = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setConnectTimeout(5000);

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                res = stringBuilder.toString();
            }
        } catch (IOException e) {e.printStackTrace();return null;} finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                connection.disconnect();
            }
        }

        return res;
    }////////////////////////////////////

    public Map<String, JSONObject> profileCache = new HashMap<String, JSONObject>();

    public boolean isProfileCached(String username){
        return profileCache.containsKey(username);
    }
    public JSONObject getCachedProfile(String username){
        if(!isProfileCached(username)) return null;
        return profileCache.get(username);
    }
    public JSONObject getUserProfileFromAPI(String username) {
        String raw = httpRequest("https://api.mojang.com/users/profiles/minecraft/" + username);
        if (raw == null) {return null;}
        try {
            JSONObject json = new JSONObject(raw);
            return json;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    public JSONObject getUserProfile(String username){
        JSONObject cached = getCachedProfile(username);
        if(cached != null) return cached;
        JSONObject online = getUserProfileFromAPI(username);
        if(online != null) profileCache.put(username,online);
        return online;
    }

    public UUID getOnlineUUID(String username){
        JSONObject json = getUserProfile(username);
        if(json==null){return null;}
        String strUUID = (String) json.get("id");
        if(strUUID==null){return null;}
        return utils.str2uuid(strUUID);
    }


}
