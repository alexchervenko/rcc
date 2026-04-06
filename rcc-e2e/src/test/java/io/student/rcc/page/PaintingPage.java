package io.student.rcc.page;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class PaintingPage extends BasePage {

  public PaintingPage() {
    super("/painting");
  }

  @Step("Дождаться открытия страницы картин")
  @Override
  public PaintingPage waitForOpen() {
    $("h2").shouldHave(Condition.text("Картины"));
    return this;
  }
}
