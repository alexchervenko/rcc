package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;

public class RegisterPage {

  private static final String AUTH_BASE_URL = System.getProperty("auth.url", "http://127.0.0.1:9000");

  private final SelenideElement usernameInput = $("#username");
  private final SelenideElement passwordInput = $("#password");
  private final SelenideElement passwordSubmitInput = $("#passwordSubmit");
  private final SelenideElement submitButton = $("button[type='submit']");

  @Step("Открыть страницу регистрации")
  public RegisterPage open() {
    com.codeborne.selenide.Selenide.open(AUTH_BASE_URL + "/register");
    return waitForOpen();
  }

  @Step("Дождаться открытия страницы регистрации")
  public RegisterPage waitForOpen() {
    usernameInput.shouldBe(Condition.visible);
    return this;
  }

  @Step("Зарегистрировать пользователя: {username}")
  public RegisterPage register(@NonNull String username, @NonNull String password, @NonNull String passwordSubmit) {
    usernameInput.shouldBe(Condition.visible).setValue(username);
    passwordInput.setValue(password);
    passwordSubmitInput.setValue(passwordSubmit);
    submitButton.click();
    return this;
  }
}
