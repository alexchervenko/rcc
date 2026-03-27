package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import lombok.NonNull;

public class ArtistPage extends BasePage {

  public ArtistPage() {
    super("/artist");
  }

  @Step("Дождаться открытия страницы художников")
  @Override
  public ArtistPage waitForOpen() {
    $x("//h2[contains(normalize-space(.), 'Художники')]").shouldBe(Condition.visible);
    return this;
  }
}
