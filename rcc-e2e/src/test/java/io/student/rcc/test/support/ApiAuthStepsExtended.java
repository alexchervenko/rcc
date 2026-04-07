package io.student.rcc.test.support;

import io.qameta.allure.Step;
import io.student.rcc.page.LoginPage;
import io.student.rcc.page.ProfilePage;

/**
 * Расширенные шаги для аутентификации через API
 * Альтернатива AuthSteps, которая использует API вместо UI
 */
public class ApiAuthStepsExtended {

    /**
     * Регистрирует пользователя через API и возвращает тестового пользователя
     * Просто создает пользователя, логин выполняется отдельно в тесте
     */
    @Step("Зарегистрировать нового пользователя через API")
    public static TestUser registerUserViaApi() {
        String username = "user" + System.currentTimeMillis();
        String password = "123456";
        
        TestUser user = new TestUser(username, password);
        
        boolean success = ApiAuthSteps.registerUserViaApi(user.username(), user.password());
        
        if (!success) {
            throw new RuntimeException("Failed to register user via API");
        }
        
        // Очищаем cookies
        ApiAuthSteps.clearCookies();
        
        return user;
    }

    /**
     * Логинит пользователя через UI
     * Используется после регистрации через API
     */
    @Step("Залогинить пользователя через UI")
    public static ProfilePage loginUserViaUi(TestUser user) {
        // Открываем страницу профиля и кликаем "Войти"
        ProfilePage profilePage = new ProfilePage().open();
        profilePage.header().clickLogin();
        
        // Логинимся
        return new LoginPage().waitForOpen().login(user.username(), user.password());
    }
}