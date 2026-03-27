package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

public class PaintingPage extends BasePage {

  public PaintingPage() {
    super("/painting");
  }

  @Step("Дождаться открытия страницы картин")
  @Override
  public PaintingPage waitForOpen() {
    $x("//h2[contains(normalize-space(.), 'Картины')]").shouldBe(Condition.visible);
    return this;
  }
}
