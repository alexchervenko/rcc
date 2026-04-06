package io.student.rcc.page;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import io.student.rcc.page.component.MuseumFormModal;
import lombok.NonNull;

import static com.codeborne.selenide.Selenide.$;

public class MuseumPage extends BasePage {

  public MuseumPage() {
    super("/museum");
  }

  @Step("Дождаться открытия страницы музеев")
  @Override
  public MuseumPage waitForOpen() {
    $("h2:contains('Музеи')").shouldBe(Condition.visible);
    return this;
  }

  @Step("Нажать кнопку добавления музея")
  public MuseumPage clickAddMuseum() {
    $("button:contains('Добавить музей')").click();
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
    $("ul[class*='grid'] a:has(div:contains('" + title + "'))").click();
    return this;
  }

  @Step("Проверить отображение названия музея: {title}")
  public MuseumPage shouldShowMuseumTitle(@NonNull String title) {
    $("header[class*='card-header']:contains('" + title + "')").shouldBe(Condition.visible);
    return this;
  }

  @Step("Нажать кнопку редактирования музея")
  public MuseumPage clickEditMuseum() {
    $("button[data-testid='edit-museum']").click();
    return this;
  }
}
