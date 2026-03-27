package io.student.rcc.test.support;

import com.codeborne.selenide.Condition;
import io.student.rcc.page.LoginPage;
import io.student.rcc.page.ProfilePage;
import io.student.rcc.page.RegisterPage;

import static com.codeborne.selenide.Selenide.$x;

public final class AuthSteps {

  private AuthSteps() {
  }

  public static ProfilePage registerAndLoginEmptyUser() {
    TestUser user = new TestUser("user" + System.currentTimeMillis(), "123456");

    new RegisterPage()
        .open()
        .register(user.username(), user.password(), user.password());

    $x("//a[contains(normalize-space(.), 'Войти')]|//a[contains(normalize-space(.), 'Войти в систему')]")
        .shouldBe(Condition.visible);

    ProfilePage profilePage = new ProfilePage().open();
    profilePage.header().clickLogin();

    return new LoginPage().waitForOpen().login(user.username(), user.password());
  }
}
