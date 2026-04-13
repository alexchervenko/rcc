package io.student.rcc.test.support;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import io.student.rcc.page.LoginPage;
import io.student.rcc.page.ProfilePage;
import io.student.rcc.page.RegisterPage;

import static com.codeborne.selenide.Selenide.$$;

public final class AuthSteps {

  private AuthSteps() {
  }

  @Step("Зарегистрировать и залогинить нового пустого пользователя")
  public static ProfilePage registerAndLoginEmptyUser() {
    TestUser user = new TestUser("user" + System.currentTimeMillis(), "123456");

    new RegisterPage()
        .open()
        .register(user.username(), user.password(), user.password());

    $$("a").findBy(Condition.text("Войти")).shouldBe(Condition.visible);

    ProfilePage profilePage = new ProfilePage().open();
    profilePage.header().clickLogin();

    return new LoginPage().waitForOpen().login(user.username(), user.password());
  }
}
