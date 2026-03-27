package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;

public class ArtistPage extends BasePage {

  public ArtistPage() {
    super("/artist");
  }

  @Override
  public ArtistPage waitForOpen() {
    $x("//h2[contains(normalize-space(.), 'Художники')]").shouldBe(Condition.visible);
    return this;
  }
}
