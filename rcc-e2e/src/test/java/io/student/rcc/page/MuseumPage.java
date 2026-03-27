package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import io.student.rcc.page.component.MuseumFormModal;
import lombok.NonNull;

public class MuseumPage extends BasePage {

  public MuseumPage() {
    super("/museum");
  }

  @Step("Дождаться открытия страницы музеев")
  @Override
  public MuseumPage waitForOpen() {
    $x("//h2[contains(normalize-space(.), 'Музеи')]").shouldBe(Condition.visible);
    return this;
  }

  @Step("Нажать кнопку добавления музея")
  public MuseumPage clickAddMuseum() {
    $x("//button[contains(normalize-space(.), 'Добавить музей')]").shouldBe(Condition.visible).click();
    return this;
  }

  @Step("Получить модалку формы музея")
  public MuseumFormModal museumFormModal() {
    MuseumFormModal modal = new MuseumFormModal();
    modal.shouldBeVisible();
    return modal;
  }

  @Step("Открыть музей по названию: {title}")
  public MuseumPage openMuseumByTitle(@NonNull String title) {
    $x("//ul[contains(@class,'grid')]//a[.//div[contains(normalize-space(.), '" + title + "')]]")
        .shouldBe(Condition.visible)
        .click();
    return this;
  }

  @Step("Проверить отображение названия музея: {title}")
  public MuseumPage shouldShowMuseumTitle(@NonNull String title) {
    $x("//header[contains(@class, 'card-header') and contains(normalize-space(.), '" + title + "')]")
        .shouldBe(Condition.visible);
    return this;
  }

  @Step("Нажать кнопку редактирования музея")
  public MuseumPage clickEditMuseum() {
    $x("//button[@data-testid='edit-museum']").shouldBe(Condition.visible).click();
    return this;
  }
}
