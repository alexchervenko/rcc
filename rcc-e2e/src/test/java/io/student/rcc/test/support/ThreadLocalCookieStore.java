package io.student.rcc.test.support;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadLocalCookieStore implements CookieJar {
    
    public static final ThreadLocalCookieStore INSTANCE = new ThreadLocalCookieStore();
    
    private final ThreadLocal<Map<String, List<Cookie>>> threadLocalCookies = 
        ThreadLocal.withInitial(HashMap::new);
    
    private ThreadLocalCookieStore() {
        // private constructor for singleton
    }
    
    @Override
    public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
        Map<String, List<Cookie>> cookieStore = threadLocalCookies.get();
        String host = url.host();
        
        if (!cookieStore.containsKey(host)) {
            cookieStore.put(host, new ArrayList<>());
        }
        
        List<Cookie> hostCookies = cookieStore.get(host);
        
        for (Cookie cookie : cookies) {
            // Remove old cookie if exists
            hostCookies.removeIf(c -> c.name().equals(cookie.name()));
            hostCookies.add(cookie);
        }
        
        cookieStore.put(host, hostCookies);
    }
    
    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
        Map<String, List<Cookie>> cookieStore = threadLocalCookies.get();
        String host = url.host();
        
        if (cookieStore.containsKey(host)) {
            return new ArrayList<>(cookieStore.get(host));
        }
        
        return new ArrayList<>();
    }
    
    public String cookieValue(String cookieName) {
        Map<String, List<Cookie>> cookieStore = threadLocalCookies.get();
        
        for (List<Cookie> cookies : cookieStore.values()) {
            for (Cookie cookie : cookies) {
                if (cookie.name().equals(cookieName)) {
                    return cookie.value();
                }
            }
        }
        
        return null;
    }
    
    public void clear() {
        threadLocalCookies.get().clear();
    }
}