package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import io.student.rcc.page.component.MuseumFormModal;

public class MuseumPage extends BasePage {

  public MuseumPage() {
    super("/museum");
  }

  @Override
  public MuseumPage waitForOpen() {
    $x("//h2[contains(normalize-space(.), 'Музеи')]").shouldBe(Condition.visible);
    return this;
  }

  public MuseumPage clickAddMuseum() {
    $x("//button[contains(normalize-space(.), 'Добавить музей')]").shouldBe(Condition.visible).click();
    return this;
  }

  public MuseumFormModal museumFormModal() {
    MuseumFormModal modal = new MuseumFormModal();
    modal.shouldBeVisible();
    return modal;
  }

  public MuseumPage openMuseumByTitle(String title) {
    $x("//ul[contains(@class,'grid')]//a[.//div[contains(normalize-space(.), '" + title + "')]]")
        .shouldBe(Condition.visible)
        .click();
    return this;
  }

  public MuseumPage shouldShowMuseumTitle(String title) {
    $x("//header[contains(@class, 'card-header') and contains(normalize-space(.), '" + title + "')]")
        .shouldBe(Condition.visible);
    return this;
  }

  public MuseumPage clickEditMuseum() {
    $x("//button[@data-testid='edit-museum']").shouldBe(Condition.visible).click();
    return this;
  }
}
