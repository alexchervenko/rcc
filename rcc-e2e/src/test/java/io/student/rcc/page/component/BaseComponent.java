package io.student.rcc.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public abstract class BaseComponent {

  protected final SelenideElement container;

  protected BaseComponent(SelenideElement container) {
    this.container = container;
  }

  public BaseComponent shouldBeVisible() {
    container.shouldBe(Condition.visible);
    return this;
  }
}
