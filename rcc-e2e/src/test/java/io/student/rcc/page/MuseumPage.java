package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;

public class MuseumPage extends BasePage {

  public MuseumPage() {
    super("/museum");
  }

  @Override
  public MuseumPage waitForOpen() {
    $x("//h2[contains(normalize-space(.), 'Музеи')]").shouldBe(Condition.visible);
    return this;
  }
}
