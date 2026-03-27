package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;

public class LoginPage {

  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitButton = $("button[type='submit']");

  @Step("Дождаться открытия страницы логина")
  public LoginPage waitForOpen() {
    usernameInput.shouldBe(Condition.visible);
    return this;
  }

  @Step("Войти пользователем: {username}")
  public ProfilePage login(@NonNull String username, @NonNull String password) {
    usernameInput.shouldBe(Condition.visible).setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
    return new ProfilePage().waitForOpen();
  }
}
