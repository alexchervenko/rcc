package io.student.rcc.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class SearchField extends BaseComponent {

  private final SelenideElement input = container.$("input[type='search']");
  private final SelenideElement searchButton = container.$("button");

  public SearchField(SelenideElement container) {
    super(container);
  }

  public SearchField search(String text) {
    input.shouldBe(Condition.visible).setValue(text).pressEnter();
    return this;
  }

  public SearchField searchByButton(String text) {
    input.shouldBe(Condition.visible).setValue(text);
    searchButton.shouldBe(Condition.visible).click();
    return this;
  }

  public String value() {
    return input.shouldBe(Condition.visible).getValue();
  }
}
