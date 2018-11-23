package com.login.single.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {


    private static Map<String, String> sessionMap = new ConcurrentHashMap<>();

    public static Map<String, String> getSessionMap() {
        return sessionMap;
    }

    public static void setSessionMap(Map<String, String> sessionMap) {
        SessionManager.sessionMap = sessionMap;
    }
}
