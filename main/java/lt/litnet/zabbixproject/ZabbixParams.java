package lt.litnet.zabbixproject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * Created by justas on 1/10/17.
 */
public class ZabbixParams implements Serializable {
    static String zabbix_url;
    static boolean auth;
    static String username;
    static String password;
    static String method;
    static String token;
    static JSONObject params;

    ZabbixParams(String zabbix_url, boolean auth, String username, String password, String method) {
        this.zabbix_url = zabbix_url;
        this.auth = auth;
        this.username = username;
        this.password = password;
        this.method = method;
    }

    static String getZabbix_url(){
        return zabbix_url;
    }

    static boolean getAuth(){
        return auth;
    }

    static String getUsername(){
        return username;
    }

    static String getPassword(){
        return password;
    }

    static String getMethod(){
        return method;
    }

    static JSONObject getParams() {
        return params;
    }

    void setParams (JSONObject params1){
        params = params1;
    }

    void setMethod(String method1){
        method = method1;
    }

    void setAuth(boolean reiksme){
        auth = reiksme;
    }

    static String getToken() { return token;}

    void setToken(String token1){
        token = token1;
    }
}
