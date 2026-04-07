package io.student.rcc.config;

import io.student.rcc.test.support.ThreadLocalCookieStore;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;

public class RetrofitClient {
    
    private static final String BASE_URL = "http://localhost:9000/"; // Порт auth сервиса
    
    private static final ThreadLocalCookieStore cookieStore = ThreadLocalCookieStore.INSTANCE;
    
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(30))
            .connectTimeout(Duration.ofSeconds(30))
            .readTimeout(Duration.ofSeconds(30))
            .writeTimeout(Duration.ofSeconds(30))
            .cookieJar(cookieStore)
            .build();
    
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    
    public static <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
    
    public static void setBaseUrl(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
    
    public static ThreadLocalCookieStore getCookieStore() {
        return cookieStore;
    }
}