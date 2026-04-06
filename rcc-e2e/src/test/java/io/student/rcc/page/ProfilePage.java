package io.student.rcc.page;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import io.student.rcc.page.component.ProfileFormModal;

import static com.codeborne.selenide.Selenide.$;

public class ProfilePage extends BasePage {

  public ProfilePage() {
    super("/");
  }

  @Step("Открыть страницу профиля")
  @Override
  public ProfilePage open() {
    super.open();
    return this;
  }

  @Step("Дождаться открытия страницы профиля")
  @Override
  public ProfilePage waitForOpen() {
    $("nav[aria-label='Основная навигация']").shouldBe(Condition.visible);
    return this;
  }

  @Step("Открыть модалку профиля")
  public void openProfileModal() {
    $("header button[class*='btn-icon']").click();
  }

  @Step("Получить модалку формы профиля")
  public ProfileFormModal profileFormModal() {
    ProfileFormModal modal = new ProfileFormModal();
    modal.shouldBeVisible();
    return modal;
  }
}
