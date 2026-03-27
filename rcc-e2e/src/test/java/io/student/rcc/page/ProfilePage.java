package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import io.student.rcc.page.component.ProfileFormModal;
import lombok.NonNull;

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
    $x("//nav[@aria-label='Основная навигация']").shouldBe(Condition.visible);
    return this;
  }

  @Step("Открыть модалку профиля")
  public void openProfileModal() {
    $x("//header//button[contains(@class, 'btn-icon')]").shouldBe(Condition.visible).click();
  }

  @Step("Получить модалку формы профиля")
  public ProfileFormModal profileFormModal() {
    ProfileFormModal modal = new ProfileFormModal();
    modal.shouldBeVisible();
    return modal;
  }
}
