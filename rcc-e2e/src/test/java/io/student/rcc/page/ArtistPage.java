package io.student.rcc.page;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class ArtistPage extends BasePage {

  public ArtistPage() {
    super("/artist");
  }

  @Step("Дождаться открытия страницы художников")
  @Override
  public ArtistPage waitForOpen() {
    $("h2:contains('Художники')").shouldBe(Condition.visible);
    return this;
  }
}
