package io.student.rcc.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;

public abstract class BaseComponent {

  protected final SelenideElement container;

  protected BaseComponent(@NonNull SelenideElement container) {
    this.container = container;
  }

  @Step("Проверить, что компонент видим")
  public BaseComponent shouldBeVisible() {
    container.shouldBe(Condition.visible);
    return this;
  }
}
