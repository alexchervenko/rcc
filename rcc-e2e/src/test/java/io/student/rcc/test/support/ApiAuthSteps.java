package io.student.rcc.test.support;

import io.student.rcc.api.AuthApi;
import io.student.rcc.config.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ApiAuthSteps {
    
    private static final AuthApi authApi = RetrofitClient.createService(AuthApi.class);
    
    /**
     * Регистрация пользователя через API
     * @param username имя пользователя
     * @param password пароль
     * @return true если регистрация успешна
     */
    public static boolean registerUserViaApi(String username, String password) {
        try {
            // 1. Получаем HTML форму регистрации
            Call<ResponseBody> formCall = authApi.requestRegisterForm();
            Response<ResponseBody> formResponse = formCall.execute();
            
            if (!formResponse.isSuccessful()) {
                System.err.println("Failed to get registration form. Status: " + formResponse.code());
                return false;
            }
            
            // 2. Извлекаем CSRF токен из HTML ответа
            String responseBody = formResponse.body().string();
            String csrfToken = extractCsrfTokenFromHtml(responseBody);
            
            if (csrfToken == null || csrfToken.isEmpty()) {
                System.err.println("CSRF token not found in registration form");
                return false;
            }
            
            // 3. Выполняем регистрацию
            Call<ResponseBody> registerCall = authApi.register(
                username,
                password,
                password, // passwordSubmit такой же как password
                csrfToken
            );
            
            Response<ResponseBody> registerResponse = registerCall.execute();
            
            // Проверяем успешность регистрации
            // Ожидаем статус 201 Created или 200 OK
            return registerResponse.isSuccessful() || 
                   registerResponse.code() == 201 || 
                   registerResponse.code() == 200;
            
        } catch (IOException e) {
            System.err.println("Error during API registration: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Извлекает CSRF токен из HTML формы
     */
    private static String extractCsrfTokenFromHtml(String html) {
        // Ищем pattern: name="_csrf" value="TOKEN"
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("name=\"_csrf\" value=\"([^\"]+)\"");
        java.util.regex.Matcher matcher = pattern.matcher(html);
        
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        return null;
    }
    
    /**
     * Очищает cookie store
     */
    public static void clearCookies() {
        ThreadLocalCookieStore.INSTANCE.clear();
    }
}